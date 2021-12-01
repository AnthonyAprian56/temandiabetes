package com.example.temandiabetes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class ShowDokterActivity extends AppCompatActivity {

    private TextView namaDokter, spesialisDokter, statusDokter, pengalamanKerjaDokter, jenisKelamin, lulusanDokter, tempatKerja;
    private ImageView gambarDokter,online;
    private RelativeLayout kembali;
    private Button tanya;

    String url = "";

    String text = "Mohon maaf, saat ini dokter sedang tidak aktif.";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_dokter);


        gambarDokter = (ImageView)findViewById(R.id.gambar_dokter);
        namaDokter = (TextView) findViewById(R.id.nama_dokter);
        spesialisDokter = (TextView) findViewById(R.id.spesialis_dokter);
        statusDokter = (TextView) findViewById(R.id.status_dokter);
        pengalamanKerjaDokter = (TextView) findViewById(R.id.pengalaman_kerja_dokter);
        jenisKelamin = (TextView) findViewById(R.id.jenis_kelamin_dokter);
        lulusanDokter = (TextView) findViewById(R.id.lulusan_dokter);
        tempatKerja = (TextView) findViewById(R.id.tempat_kerja_dokter);
        tanya = (Button) findViewById(R.id.btn_tanya);
        online = (ImageView) findViewById(R.id.online1);
        kembali = (RelativeLayout) findViewById(R.id.icon_kembali_dokter);


        url = getIntent().getStringExtra("foto");
        Glide.with(this).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(gambarDokter);
        namaDokter.setText(getIntent().getStringExtra("nama"));
        spesialisDokter.setText(getIntent().getStringExtra("spesialis"));
        statusDokter.setText(getIntent().getStringExtra("status"));
        pengalamanKerjaDokter.setText(getIntent().getStringExtra("pengalaman"));
        jenisKelamin.setText(getIntent().getStringExtra("jeniskelamin"));
        lulusanDokter.setText(getIntent().getStringExtra("lulusan"));
        tempatKerja.setText(getIntent().getStringExtra("tempatkerja"));

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (statusDokter.getText().toString().equals(text)){
            tanya.setBackgroundColor(Color.parseColor("#A7A7A7"));
            online.setVisibility(View.INVISIBLE);
            statusDokter.setTextColor(Color.parseColor("#EF434F"));
        } else {
            tanya.setBackgroundColor(Color.parseColor("#3C9CDC"));
            online.setImageDrawable(ContextCompat.getDrawable(ShowDokterActivity.this, R.drawable.online));
            online.setVisibility(View.VISIBLE);
            statusDokter.setTextColor(Color.parseColor("#000000"));
        }

        tanya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(statusDokter.getText().toString().equals(text)){
                    Toast.makeText(ShowDokterActivity.this, "Mohon maaf, dokter sedang tidak aktif!", Toast.LENGTH_SHORT).show();


                } else {
                    CreateAlertDialog();
                }


            }
        });


    }

    private void CreateAlertDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Apakah yakin ingin booking dokter ini ?");
        builder.setPositiveButton("Booking", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.dkonsul.com/"));
                startActivity(intent);
            }
        });

        builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ShowDokterActivity.this, "Berhasil Membatalkan", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create();
        builder.show();
    }







}