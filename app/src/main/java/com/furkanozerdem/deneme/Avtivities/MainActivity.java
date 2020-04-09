package com.furkanozerdem.deneme.Avtivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.furkanozerdem.deneme.Models.Kisiler;
import com.furkanozerdem.deneme.R;
import com.furkanozerdem.deneme.deneme;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Animation anim;
    ConstraintLayout layout;
    EditText name;
    Kisiler kisi;
    FirebaseDatabase db ;
    DatabaseReference myRef;
    ProgressBar pb;
    Button sendButton;
    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // Intent intent = new Intent(MainActivity.this,deneme.class);
       // startActivity(intent);



        anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.animation);
        layout = findViewById(R.id.myLayout);
        layout.startAnimation(anim);
        name = findViewById(R.id.editText3);
        pb = findViewById(R.id.progressBar);
        sendButton = findViewById(R.id.send);
        mAuth = FirebaseAuth.getInstance();

        name.setEnabled(false);
        sendButton.setEnabled(false);

     /**/   db = FirebaseDatabase.getInstance();
        myRef = db.getReference("Kuzenler");


        myRef.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
       for(DataSnapshot d: dataSnapshot.getChildren()) {
         kisi = d.getValue(Kisiler.class);
            //System.out.println("******** " + kisi.getKisi_key() + " *******");
            //System.out.println("******** " + kisi.getIsim() + " *******");
            //System.out.println("******** " + kisi.isKatilim() + " *******");

        if(kisi.getKisi_key().equals(getMacAddr())) {
            Intent intent = new Intent(MainActivity.this,Main2Activity.class);
            finish();
            startActivity(intent);
                }
            }
            pb.setVisibility(View.INVISIBLE);
            name.setEnabled(true);
            sendButton.setEnabled(true);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });




    }

    public void login(View view) {
        if(name.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(),"Lütfen isim giriniz.",Toast.LENGTH_LONG).show();
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                v.vibrate(500);
            }
            return;
        }
        else {
            mAuth.signInAnonymously().addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Kisiler k = new Kisiler(getMacAddr(),name.getText().toString(),false);
                    myRef.child(getMacAddr()).setValue(k);
                    Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                    startActivity(intent);

                    Toast.makeText(MainActivity.this,"Giriş başarılı, hoşgeldiniz" + name.getText().toString(),Toast.LENGTH_LONG).show();
                }
            });



        }

   //    Toast.makeText(getApplicationContext(), "Başarılı, hoşgeldin ", Toast.LENGTH_LONG).show();
    }

    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }

}
