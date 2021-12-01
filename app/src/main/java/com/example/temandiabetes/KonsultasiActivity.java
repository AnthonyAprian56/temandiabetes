package com.example.temandiabetes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class KonsultasiActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView tanyaDokter, tanyaEdukator, tanyaDietisen, riwayatKonsultasi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konsultasi);
        getSupportActionBar().setTitle("Konsultasi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        //membuat card
        tanyaDokter = (CardView) findViewById(R.id.tanyadokter);
        tanyaEdukator = (CardView) findViewById(R.id.tanyaedukator);
        tanyaDietisen = (CardView) findViewById(R.id.tanyadietisen);
        riwayatKonsultasi = (CardView) findViewById(R.id.riwayatkonsultasi);

        tanyaDokter.setOnClickListener(this);
        tanyaEdukator.setOnClickListener(this);
        tanyaDietisen.setOnClickListener(this);
        riwayatKonsultasi.setOnClickListener(this);

        //initialize And Assign variables
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);


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
                        startActivity(new Intent(getApplicationContext()
                                ,MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.ic_profil:

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
            case R.id.tanyadokter : i = new Intent(this, KategoriDokterActivity.class); startActivity(i);break;
            case R.id.tanyaedukator : i = new Intent(this, TanyaEdukatorActivity.class); startActivity(i);break;
            case R.id.tanyadietisen : i = new Intent(this, TanyaDietisenActivity.class); startActivity(i);break;
            case R.id.riwayatkonsultasi : i = new Intent(this, RiwayatKonsultasiActivity.class); startActivity(i);break;
            default:break;
        }

    }
}