package com.example.temandiabetes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Obat1Activity extends AppCompatActivity {

    private Button pesan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obat1);

        getSupportActionBar().setTitle("DNURSE SINOCARE TWIST LANCET 28G BOX 50PCS");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pesan=findViewById(R.id.tombol_pesan);

        pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAlertDialog();
            }


        });
    }

    private void CreateAlertDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Apakah Yakin Ingin Memesan Barang Ini ?");
        builder.setPositiveButton("Pesan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.goapotik.com/produk/sinocare-twist-lancet-28g-box-50-pcs"));
                startActivity(intent);
            }
        });

        builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Obat1Activity.this, "Berhasil Membatalkan", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create();
        builder.show();
    }

}