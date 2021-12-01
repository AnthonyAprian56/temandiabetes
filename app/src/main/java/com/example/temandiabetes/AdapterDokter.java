package com.example.temandiabetes;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterDokter extends RecyclerView.Adapter<AdapterDokter.MyViewHolder> {

    ArrayList<Model> mList;
    Context context;
    String text = "Mohon maaf, saat ini dokter sedang tidak aktif.";


    public AdapterDokter(Context context, ArrayList<Model> mList){
        this.mList = mList;
        this.context = context;
    }


    @Override
    public AdapterDokter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_dokter, parent, false);
        return new AdapterDokter.MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterDokter.MyViewHolder holder, int position) {
        Model model = mList.get(position);
        Glide.with(context).load(model.getFoto()).fitCenter().into(holder.fotoDokter);
        holder.namaDokter.setText(model.getNamaDokter());
        holder.spesialisDokter.setText(model.getSpesialis());
        holder.statusDokter.setText(model.getStatus());
        holder.pengalamanKerjaDokter.setText(model.getPengalaman());




        if (holder.statusDokter.getText().toString().equals(text)){
            holder.online.setVisibility(View.INVISIBLE);
            holder.lihat.setBackgroundColor(Color.parseColor("#A7A7A7"));
            holder.statusDokter.setTextColor(Color.parseColor("#EF434F"));
            holder.lihat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Mohon maaf, dokter sedang tidak aktif!", Toast.LENGTH_SHORT).show();
                }
            });

        } else{
            holder.online.setVisibility(View.VISIBLE);
            holder.lihat.setBackgroundColor(Color.parseColor("#3C9CDC"));
            holder.statusDokter.setTextColor(Color.parseColor("#000000"));
            holder.lihat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ShowDokterActivity.class);
                    intent.putExtra("foto", model.getFoto() );
                    intent.putExtra("nama", model.getNamaDokter());
                    intent.putExtra("spesialis", model.getSpesialis());
                    intent.putExtra("status", model.getStatus());
                    intent.putExtra("pengalaman",model.getPengalaman());
                    intent.putExtra("jeniskelamin", model.getJenisKelamin());
                    intent.putExtra("lulusan", model.getLulusan());
                    intent.putExtra("tempatkerja", model.getTempatKerja());
                    context.startActivity(intent);
                }
            });
        }




        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(context, ShowDokterActivity.class);
                    intent.putExtra("foto", model.getFoto() );
                    intent.putExtra("nama", model.getNamaDokter());
                    intent.putExtra("spesialis", model.getSpesialis());
                    intent.putExtra("status", model.getStatus());
                    intent.putExtra("pengalaman",model.getPengalaman());
                    intent.putExtra("jeniskelamin", model.getJenisKelamin());
                    intent.putExtra("lulusan", model.getLulusan());
                    intent.putExtra("tempatkerja", model.getTempatKerja());
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

        TextView namaDokter, spesialisDokter, statusDokter, pengalamanKerjaDokter;
        ImageView fotoDokter, online;
        Button lihat;
        CardView card;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            card = itemView.findViewById(R.id.card_list_dokter);
            fotoDokter = itemView.findViewById(R.id.foto_dokter);
            namaDokter = itemView.findViewById(R.id.nama_dokter);
            spesialisDokter = itemView.findViewById(R.id.spesialis_dokter);
            statusDokter = itemView.findViewById(R.id.status_dokter1);
            pengalamanKerjaDokter = itemView.findViewById(R.id.pengalaman_kerja_dokter);
            lihat = itemView.findViewById(R.id.chat);
            online = itemView.findViewById(R.id.online);
        }
    }

}
