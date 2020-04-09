package com.furkanozerdem.deneme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.furkanozerdem.deneme.Adapters.RecyclerViewAdapter;
import com.furkanozerdem.deneme.Models.Kisiler;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class deneme extends AppCompatActivity {

    RecyclerView rw;
    RecyclerViewAdapter adapter;
    FirebaseDatabase db;
    DatabaseReference myRef;
    ArrayList<Kisiler> kisiler;
    Switch aSwitch;
    Boolean durumum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deneme);
        db = FirebaseDatabase.getInstance();
        myRef = db.getReference("Kuzenler");
        rw = findViewById(R.id.recyclerView);
        aSwitch = findViewById(R.id.switch3);
        kisiler = new ArrayList<Kisiler>();
        adapter = new RecyclerViewAdapter(kisiler);


        rw.setAdapter(adapter);
        rw.setLayoutManager(new LinearLayoutManager(this));

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                kisiler.clear();
                for(DataSnapshot d: dataSnapshot.getChildren()) {
                    Kisiler k = d.getValue(Kisiler.class);
                    kisiler.add(k);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Query sorgu = myRef.orderByChild("kisi_key").equalTo(getMacAddr());
                durumum = b;
                sorgu.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot d : dataSnapshot.getChildren()) {
                            Kisiler k = d.getValue(Kisiler.class);
                            if(k.getKisi_key().equals(getMacAddr())) {
                                HashMap veri = new HashMap();
                                veri.put("katilim",durumum);
                                myRef.child(d.getKey()).updateChildren(veri);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });


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
