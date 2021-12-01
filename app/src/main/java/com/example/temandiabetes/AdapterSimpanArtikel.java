package com.example.temandiabetes;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterSimpanArtikel extends RecyclerView.Adapter<AdapterSimpanArtikel.MyViewHolder>{

        ArrayList<Model> mList;
        Context context;
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference root = db.getReference().child("SimpanArtikel").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        //String placeId = root.push().getKey();


        public AdapterSimpanArtikel(Context context, ArrayList<Model> mList){
                this.mList = mList;
                this.context = context;
                }

        @Override
        public AdapterSimpanArtikel.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(context).inflate(R.layout.item_penyimpanan_artikel, parent, false);
                return new AdapterSimpanArtikel.MyViewHolder(v);
                }

        @Override
        public void onBindViewHolder(@NonNull AdapterSimpanArtikel.MyViewHolder holder, int position) {
                Model model = mList.get(position);
                holder.judulArtikel.setText(model.getJudulArtikel());

                    holder.hapus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                          //root.child("SimpanArtikel").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(placeId).removeValue();
                            Toast.makeText(context.getApplicationContext(), "Berhasil Menghapus Data", Toast.LENGTH_SHORT).show();
                      }
                    });
                }

        @Override
        public int getItemCount() {
                return mList.size();
                }


        public static class MyViewHolder extends RecyclerView.ViewHolder {

            TextView judulArtikel, namaPengarang;
            ImageButton hapus;
            CardView card;

            public MyViewHolder(@NonNull View itemView){
                super(itemView);
                card = itemView.findViewById(R.id.card_list_penyimpanan_artikel);
                judulArtikel = itemView.findViewById(R.id.judul_artikel);
                hapus = itemView.findViewById(R.id.hapus_artikel);

            }
        }

}
