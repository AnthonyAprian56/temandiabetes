package com.example.temandiabetes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterRiwayatGulaDarah extends RecyclerView.Adapter<AdapterRiwayatGulaDarah.MyViewHolder> {

    ArrayList<Model> mList;
    Context context;


    public AdapterRiwayatGulaDarah(Context context, ArrayList<Model> mList){
        this.mList = mList;
        this.context = context;
    }

    @Override
    public AdapterRiwayatGulaDarah.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_riwayat_gula_darah, parent, false);
        return new AdapterRiwayatGulaDarah.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRiwayatGulaDarah.MyViewHolder holder, int position) {
        Model model = mList.get(position);
        holder.tanggalGulaDarah.setText(model.getTanggalGulaDarah());
        holder.waktuGulaDarah.setText(model.getWaktuInputGulaDarah());
        holder.jenisPemeriksaan.setText(model.getJenisPemeriksaan());
        holder.gulaDarah.setText(model.getGulaDarah());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tanggalGulaDarah, waktuGulaDarah, jenisPemeriksaan, gulaDarah;
        CardView card;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            card = itemView.findViewById(R.id.card_riwayat_gula_darah);
            tanggalGulaDarah = itemView.findViewById(R.id.tanggal_input_gula_darah);
            waktuGulaDarah = itemView.findViewById(R.id.waktu_input_gula_darah);
            jenisPemeriksaan = itemView.findViewById(R.id.jenis_pemeriksaan_edit);
            gulaDarah = itemView.findViewById(R.id.gula_darah_edit);
        }
    }

}
