package com.example.temandiabetes;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.MenuPopupWindow;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.style.StyleSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;

public class ShowArtikelActivity extends AppCompatActivity {

    private TextView judulArtikel, isiArtikel, namaPengarang, sumber, uriArtikel;
    private ImageView fotoArtikel, fotoPengarang;
    private ImageButton kembali;
    private FirebaseAuth mAuth;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("SimpanArtikel").child(FirebaseAuth.getInstance().getCurrentUser().getUid());


    String url = "";
    String urrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_artikel);

        getSupportActionBar().setTitle("Artikel");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        kembali = (ImageButton)findViewById(R.id.kembali);
        fotoArtikel = (ImageView)findViewById(R.id.foto_artikel1);
        fotoPengarang = (ImageView)findViewById(R.id.foto_pengarang_artikel1);
        judulArtikel = (TextView) findViewById(R.id.judul_artikel1);
        isiArtikel = (TextView) findViewById(R.id.isi_artikel1);
        namaPengarang = (TextView) findViewById(R.id.pengarang_artikel1);
        sumber = (TextView) findViewById(R.id.sumber);
        uriArtikel = (TextView) findViewById(R.id.uri_artikel);


        url = getIntent().getStringExtra("fotoArtikel");
        Glide.with(this).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(fotoArtikel);
        urrl = getIntent().getStringExtra("fotoPengarang");
        Glide.with(this).load(urrl).diskCacheStrategy(DiskCacheStrategy.ALL).into(fotoPengarang);
        judulArtikel.setText(getIntent().getStringExtra("judul"));
        isiArtikel.setText(getIntent().getStringExtra("isi").replace("_b", "\n\n").replace("_c", "\n"));
        namaPengarang.setText(getIntent().getStringExtra("namaPengarang"));
        sumber.setText(getIntent().getStringExtra("sumber"));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.artikel_perbesar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.simpan:
                String txtJudul = judulArtikel.getText().toString();
                String txtIsi = isiArtikel.getText().toString();
                HashMap<String, String> userMap = new HashMap<>();
                userMap.put("JudulArtikel", txtJudul);
                userMap.put("IsiArtikel", txtIsi);
                root.push().setValue(userMap);
                Toast.makeText(this, "Artikel berhasil disimpan", Toast.LENGTH_LONG).show();
                return true;
            case R.id.teks_kecil:
                isiArtikel.setTextSize(20);
                return true;
            case R.id.teks_sedang:
               isiArtikel.setTextSize(23);
                return true;
            case R.id.teks_besar:
                isiArtikel.setTextSize(25);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }


}