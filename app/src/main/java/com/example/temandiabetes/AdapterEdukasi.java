package com.example.temandiabetes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterEdukasi extends RecyclerView.Adapter<AdapterEdukasi.MyViewHolder> {

    ArrayList<Model> mList;
    Context context;


    public AdapterEdukasi(Context context, ArrayList<Model> mList){
        this.mList = mList;
        this.context = context;
    }


    @Override
    public AdapterEdukasi.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_edukasi, parent, false);
        return new AdapterEdukasi.MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterEdukasi.MyViewHolder holder, int position) {
        Model model = mList.get(position);
        Glide.with(context).load(model.getGambar()).centerCrop().into(holder.gambarEdukasi);
        holder.judulEdukasi.setText(model.getJudul());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowEdukasiActivity.class);
                intent.putExtra("gambarkonten", model.getGambarkonten());
                intent.putExtra("konten", model.getKonten());
                intent.putExtra("judul", model.getJudul());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView judulEdukasi;
        ImageView gambarEdukasi;
        CardView card;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            card = itemView.findViewById(R.id.card_list_edukasi);
            judulEdukasi = itemView.findViewById(R.id.text_reedukasi);
            gambarEdukasi = itemView.findViewById(R.id.foto_edukasi);

        }
    }

}
