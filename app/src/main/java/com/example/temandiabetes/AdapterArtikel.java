package com.example.temandiabetes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterArtikel extends RecyclerView.Adapter<AdapterArtikel.MyViewHolder>{

    ArrayList<Model> mList;
    Context context;


    public AdapterArtikel(Context context, ArrayList<Model> mList){
        this.mList = mList;
        this.context = context;
    }

    @Override
    public AdapterArtikel.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_artikel, parent, false);
        return new AdapterArtikel.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterArtikel.MyViewHolder holder, int position) {
        Model model = mList.get(position);
        Glide.with(context).load(model.getFotoPengarang()).centerCrop().into(holder.fotoPengarang);
        Glide.with(context).load(model.getFotoArtikel()).centerCrop().into(holder.fotoArtikel);
        holder.namaPengarang.setText(model.getNamaPengarang());
        holder.judulArtikel.setText(model.getJudulArtikel());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowArtikelActivity.class);
                intent.putExtra("fotoArtikel", model.getFotoArtikel() );
                intent.putExtra("fotoPengarang", model.getFotoPengarang() );
                intent.putExtra("judul", model.getJudulArtikel());
                intent.putExtra("namaPengarang", model.getNamaPengarang());
                intent.putExtra("isi", model.getIsiArtikel());
                intent.putExtra("sumber", model.getSumber());
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void filterlist(ArrayList<Model> filterdlist){
        mList = filterdlist;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView judulArtikel, namaPengarang;
        ImageView fotoArtikel, fotoPengarang;
        CardView card;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            card = itemView.findViewById(R.id.card_list_artikel);
            judulArtikel = itemView.findViewById(R.id.judul_artikel);
            namaPengarang = itemView.findViewById(R.id.pengarang_artikel);
            fotoArtikel = itemView.findViewById(R.id.foto_artikel);
            fotoPengarang = itemView.findViewById(R.id.foto_pengarang_artikel);
        }
    }

}
