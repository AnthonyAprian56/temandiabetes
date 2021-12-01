package com.example.temandiabetes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView nama;
    private CardView rekaman, artikel, konsultasi, diskusi, belanja, pengingat, edukasi, lihatGrafik;
    private RelativeLayout kotakrekaman;
    private ImageView fotoprofil;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        kotakrekaman = (RelativeLayout) findViewById(R.id.kotak_rekaman);
        fotoprofil = (ImageView) findViewById(R.id.foto_profil);
        //membuat card
        rekaman = (CardView) findViewById(R.id.card_rekaman);
        artikel = (CardView) findViewById(R.id.card_artikel);
        konsultasi = (CardView) findViewById(R.id.card_konsultasi);
        diskusi = (CardView) findViewById(R.id.card_diskusi);
        belanja = (CardView) findViewById(R.id.card_belanja);
        pengingat = (CardView) findViewById(R.id.card_pengingat);
        edukasi = (CardView) findViewById(R.id.card_edukasi);
        lihatGrafik = (CardView) findViewById(R.id.card_grafik);


        //membuat click ke dalam card
        kotakrekaman.setOnClickListener(this);
        fotoprofil.setOnClickListener(this);
        rekaman.setOnClickListener(this);
        artikel.setOnClickListener(this);
        konsultasi.setOnClickListener(this);
        diskusi.setOnClickListener(this);
        belanja.setOnClickListener(this);
        pengingat.setOnClickListener(this);
        edukasi.setOnClickListener(this);
        lihatGrafik.setOnClickListener(this);

        //Gambar Profil
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("NamaDepan").exists()){
                    fotoprofil.setVisibility(View.VISIBLE);
                    ColorGenerator generator = ColorGenerator.MATERIAL;
                    int color1 = generator.getRandomColor();
                    String namaDepan = snapshot.child("NamaDepan").getValue().toString();
                    TextDrawable drawable2 = TextDrawable.builder().beginConfig().withBorder(4).endConfig().buildRound(namaDepan, color1);
                    fotoprofil.setImageDrawable(drawable2);
                }

                else{
                    fotoprofil.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        //toolbar Teman Diabetes
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initialize And Assign variables
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        //beranda dipilih
        bottomNavigationView.setSelectedItemId(R.id.ic_beranda);

        //perform item select listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_simpan:
                        startActivity(new Intent(getApplicationContext()
                        , ArtikelSimpanActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.ic_beranda:
                        return true;

                    case R.id.ic_profil:
                        startActivity(new Intent(getApplicationContext()
                                , ProfilActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()) {
            case R.id.kotak_rekaman : i = new Intent(this, RekamanActivity.class); startActivity(i);break;
            case R.id.foto_profil : i = new Intent(this, ProfilActivity.class); startActivity(i);break;
            case R.id.card_rekaman : i = new Intent(this, RekamanActivity.class); startActivity(i);break;
            case R.id.card_artikel : i = new Intent(this, ArtikelActivity.class); startActivity(i);break;
            case R.id.card_konsultasi : i = new Intent(this, KonsultasiActivity.class); startActivity(i);break;
            case R.id.card_diskusi : i = new Intent(this, DiskusiActivity.class); startActivity(i);break;
            case R.id.card_belanja : i = new Intent(this, BelanjaActivity.class); startActivity(i);break;
            case R.id.card_pengingat : i = new Intent(this, PengingatActivity.class); startActivity(i);break;
            case R.id.card_edukasi : i = new Intent(this, KategoriEdukasiActivity.class); startActivity(i);break;
            case R.id.card_grafik : i = new Intent(this, LihatGrafikActivity.class); startActivity(i);break;
            default:break;
        }

    }

    //Alert Dialog ketika back button di pencet
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Apakah anda yakin ingin keluar dari Aplikasi ?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        moveTaskToBack(true);
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }

}