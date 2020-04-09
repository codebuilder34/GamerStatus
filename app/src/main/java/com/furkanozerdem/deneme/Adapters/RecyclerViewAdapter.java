package com.furkanozerdem.deneme.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.furkanozerdem.deneme.Models.Kisiler;
import com.furkanozerdem.deneme.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends  RecyclerView.Adapter<RecyclerViewAdapter.Card> {


    private ArrayList<Kisiler> k;

    public RecyclerViewAdapter( ArrayList<Kisiler> k) {
        this.k = k;
    }





    @NonNull
    @Override
    public Card onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cardlayout,parent,false);
        return new Card(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull Card holder, int position) {
    holder.nameText.setText(k.get(position).getIsim());

    if(k.get(position).isKatilim()){
        holder.situationText.setText("Oyunda");
        holder.situationText.setTextColor(Color.GREEN);
    }
    else {
        holder.situationText.setText("Oyunda Değil");
        holder.situationText.setTextColor(Color.RED);
    }


    }

    @Override
    public int getItemCount() {
        return k.size();
    }

    public class Card extends  RecyclerView.ViewHolder {
        ImageView img;
        TextView nameText;
        TextView situationText;


        public Card(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.isimText);
            situationText = itemView.findViewById(R.id.durumText);
            img = itemView.findViewById(R.id.kisiImage);

        }

    }
}
