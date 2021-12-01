package com.example.temandiabetes;

import android.content.Context;
import android.media.Image;
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

public class AdapterDiskusi extends RecyclerView.Adapter<AdapterDiskusi.MyViewHolder> {

    ArrayList<Model> mList;
    Context context;

    public AdapterDiskusi(Context context, ArrayList<Model> mList){
        this.mList = mList;
        this.context = context;
    }


    @Override
    public AdapterDiskusi.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_forum, parent, false);
        return new AdapterDiskusi.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDiskusi.MyViewHolder holder, int position) {
        Model model = mList.get(position);
        holder.setIsRecyclable(false);
        holder.namaTanya.setText(model.getNamaPenanya());
        holder.judulPertanyaan.setText(model.getJudulPertanyaan());
        holder.isiPertanyaan.setText(model.getIsiPertanyaan());
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


        TextView namaTanya, judulPertanyaan, isiPertanyaan;
        CardView card;


        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            card = itemView.findViewById(R.id.card_list_forum);
            namaTanya = itemView.findViewById(R.id.nama_profil);
            judulPertanyaan = itemView.findViewById(R.id.judul_pertanyaan);
            isiPertanyaan = itemView.findViewById(R.id.isi_pertanyaan);
        }
    }

}
