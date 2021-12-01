package com.example.temandiabetes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PertanyaanForumActivity extends AppCompatActivity {
    private ImageView fotoProfil;
    private TextView namaProfil, jenisPemeriksaanProfil;
    private RecyclerView recyclerView;
    private AdapterPertanyaan adapter;
    private ArrayList<Model> list;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
    private DatabaseReference root2 = db.getReference().child("PertanyaanDiskusiProfil").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pertanyaan_forum);

        getSupportActionBar().setTitle("Pertanyaan Diskusi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        fotoProfil = (ImageView) findViewById(R.id.foto_profil3);
        namaProfil = (TextView) findViewById(R.id.nama_profil1);
        jenisPemeriksaanProfil = (TextView) findViewById(R.id.jenis_pemeriksaan1);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview5);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new AdapterPertanyaan(this, list);

        recyclerView.setAdapter(adapter);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String namaLengkap = snapshot.child("Nama").getValue().toString();
                namaProfil.setText(namaLengkap);

                if(snapshot.child("JenisDiabetes").exists()) {
                    String txtJenisDiabetes = snapshot.child("JenisDiabetes").getValue().toString();
                    jenisPemeriksaanProfil.setText(txtJenisDiabetes);
                }else{
                    jenisPemeriksaanProfil.setText("Belum ada status jenis pemeriksaan.");
                }

                if(snapshot.child("NamaDepan").exists()){
                    fotoProfil.setVisibility(View.VISIBLE);
                    ColorGenerator generator = ColorGenerator.MATERIAL;
                    int color1 = generator.getRandomColor();
                    String namaDepan = snapshot.child("NamaDepan").getValue().toString();
                    TextDrawable drawable2 = TextDrawable.builder().beginConfig().withBorder(4).endConfig().buildRound(namaDepan, color1);
                    fotoProfil.setImageDrawable(drawable2);
                }

                else{
                    fotoProfil.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        root2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Model model = dataSnapshot.getValue(Model.class);
                    list.add(model);

                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}