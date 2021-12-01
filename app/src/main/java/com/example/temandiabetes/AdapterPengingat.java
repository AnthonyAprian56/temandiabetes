package com.example.temandiabetes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterPengingat extends RecyclerView.Adapter<AdapterPengingat.MyViewHolder> {

    ArrayList<Model> mList;
    Context context;

    public AdapterPengingat(Context context, ArrayList<Model> mList){
        this.mList = mList;
        this.context = context;
    }


    @Override
    public AdapterPengingat.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_pengingat_obat, parent, false);
        return new AdapterPengingat.MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterPengingat.MyViewHolder holder, int position) {
        Model model = mList.get(position);
        holder.namaObat.setText(model.getObat());
        holder.aturan.setText(model.getAturan());
        holder.waktuObat.setText(model.getWaktu());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView namaObat, aturan, waktuObat;
        CardView card;


        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            card = itemView.findViewById(R.id.card_list_pengingat);
            namaObat = itemView.findViewById(R.id.nama_obat1);
            aturan = itemView.findViewById(R.id.aturan_makan_obat);
            waktuObat = itemView.findViewById(R.id.waktu_minum_obat);
        }
    }
}
