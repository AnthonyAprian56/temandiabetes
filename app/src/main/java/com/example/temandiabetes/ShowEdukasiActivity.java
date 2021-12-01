package com.example.temandiabetes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class ShowEdukasiActivity extends AppCompatActivity {

    private ImageView gambarEdukasi;
    private TextView isiEdukasi, judulEdukasi;

    String url = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_edukasi);

        getSupportActionBar().setTitle("Edukasi Diabetes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        gambarEdukasi = (ImageView) findViewById(R.id.gambar_edukasi);
        isiEdukasi = (TextView) findViewById(R.id.isi_edukasi);
        judulEdukasi = (TextView) findViewById(R.id.juduL_edukasi);

        url = getIntent().getStringExtra("gambarkonten");
        Glide.with(this).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(gambarEdukasi);
        isiEdukasi.setText(getIntent().getStringExtra("konten").replace("_b", "\n\n"));
        judulEdukasi.setText(getIntent().getStringExtra("judul"));
    }
}