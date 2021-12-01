package com.example.temandiabetes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class KategoriDokterActivity extends AppCompatActivity implements View.OnClickListener {

    CardView penyakitDalam, dokterUmum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori_dokter);
        getSupportActionBar().setTitle("Pilih Kategori");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        penyakitDalam = (CardView) findViewById(R.id.kategori_penyakit_dalam);
        dokterUmum = (CardView) findViewById(R.id.kategori_dokter_umum);

        penyakitDalam.setOnClickListener(this);
        dokterUmum.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()) {
            case R.id.kategori_penyakit_dalam : i = new Intent(this, PenyakitDalamActivity.class); startActivity(i);break;
            case R.id.kategori_dokter_umum : i = new Intent(this, DokterUmumActivity.class); startActivity(i);break;
            default:break;
        }
    }
}