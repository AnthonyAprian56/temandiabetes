package com.example.temandiabetes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterPertanyaan extends RecyclerView.Adapter<AdapterPertanyaan.MyViewHolder> {

    ArrayList<Model> mList;
    Context context;

    public AdapterPertanyaan(Context context, ArrayList<Model> mList){
        this.mList = mList;
        this.context = context;
    }

    @Override
    public AdapterPertanyaan.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_forum, parent, false);
        return new AdapterPertanyaan.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPertanyaan.MyViewHolder holder, int position) {
        Model model = mList.get(position);
        holder.namaTanya.setText(model.getNamaPenanya());
        holder.judulPertanyaan.setText(model.getJudulPertanyaan());
        holder.isiPertanyaan.setText(model.getIsiPertanyaan());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView fotoProfil;
        TextView namaTanya, judulPertanyaan, isiPertanyaan;
        CardView card;


        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            card = itemView.findViewById(R.id.card_list_forum);
            fotoProfil = itemView.findViewById(R.id.foto_profil1);
            namaTanya = itemView.findViewById(R.id.nama_profil);
            judulPertanyaan = itemView.findViewById(R.id.judul_pertanyaan);
            isiPertanyaan = itemView.findViewById(R.id.isi_pertanyaan);
        }
    }
}


