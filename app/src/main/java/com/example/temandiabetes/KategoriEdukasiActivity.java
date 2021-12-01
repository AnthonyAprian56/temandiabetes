package com.example.temandiabetes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class KategoriEdukasiActivity extends AppCompatActivity implements View.OnClickListener{

    CardView komplikasi, pengelolaan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori_edukasi);
        getSupportActionBar().setTitle("Pilih Kategori Edukasi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        komplikasi = (CardView) findViewById(R.id.kategori_komplikasi);
        pengelolaan = (CardView) findViewById(R.id.kategori_pengelolaan);

        komplikasi.setOnClickListener(this);
        pengelolaan.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()) {
            case R.id.kategori_komplikasi : i = new Intent(this, KomplikasiActivity.class); startActivity(i);break;
            case R.id.kategori_pengelolaan : i = new Intent(this, PengelolaanActivity.class); startActivity(i);break;
            default:break;
        }
    }
}