package com.furkanozerdem.deneme.Avtivities.Main2ActivityFragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.text.BoringLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.furkanozerdem.deneme.Adapters.RecyclerViewAdapter;
import com.furkanozerdem.deneme.Avtivities.Main2Activity;
import com.furkanozerdem.deneme.Models.Kisiler;
import com.furkanozerdem.deneme.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class KisiListesiActivity extends Fragment {
        FirebaseDatabase db;
        DatabaseReference myRef;
        RecyclerView recyclerView;
        ArrayAdapter<String> adp;
        ArrayList<Kisiler> kisiListesi;
        Switch aSwitch;
        TextView durumText;
        RecyclerViewAdapter rw;
        FirebaseAuth mAuth;
        Kisiler k;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.kisilistesi,container,false);

        //Bu sayfada kişi listesi görüntülenecek.
        db = FirebaseDatabase.getInstance();
        myRef = db.getReference("Kuzenler");
       recyclerView = rootView.findViewById(R.id.kisiRecyclerView);
        kisiListesi = new ArrayList<Kisiler>();
        aSwitch = rootView.findViewById(R.id.switch1);
        durumText = rootView.findViewById(R.id.durumText);
        mAuth=FirebaseAuth.getInstance();


        //aSwitch.setChecked(durumum);


        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                myRef.child(getMacAddr()).child("katilim").setValue(b);
            }
        });





        rw = new RecyclerViewAdapter(kisiListesi);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(rw);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                kisiListesi.clear();
                for(DataSnapshot d : dataSnapshot.getChildren()) {
                     k = d.getValue(Kisiler.class);
                    kisiListesi.add(k);
                    if(k.getKisi_key().equals(getMacAddr())) {
                       aSwitch.setChecked(k.isKatilim());

                    }


                }

                rw.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),databaseError.toString(),Toast.LENGTH_LONG).show();

            }
        });




        return rootView;
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
