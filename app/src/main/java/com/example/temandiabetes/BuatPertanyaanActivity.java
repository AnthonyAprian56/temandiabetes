package com.example.temandiabetes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class BuatPertanyaanActivity extends AppCompatActivity {

    ImageView profilTanya;
    TextView namaTanya;
    EditText judulPertanyaan, isiPertanyaan;
    Spinner tipe, kategori;
    Button kirimPertanyaan;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root1 = db.getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
    private DatabaseReference root2 = db.getReference().child("Pertanyaan");
    private DatabaseReference root3 = db.getReference().child("PertanyaanDiskusiProfil").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_pertanyaan);

        getSupportActionBar().setTitle("Buat Pertanyaan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);



        profilTanya = (ImageView) findViewById(R.id.buat_tanya_foto);
        namaTanya = (TextView) findViewById(R.id.buat_tanya_nama);
        judulPertanyaan = (EditText) findViewById(R.id.buat_tanya_judul);
        isiPertanyaan = (EditText) findViewById(R.id.buat_tanya_isi);
        tipe = (Spinner) findViewById(R.id.tipe_pertanyaan);
        kategori = (Spinner) findViewById(R.id.kategori_pertanyaan);
        kirimPertanyaan = (Button) findViewById(R.id.btn_kirim);



        //Gambar Profil
        root1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("NamaDepan").exists()){
                    profilTanya.setVisibility(View.VISIBLE);
                    ColorGenerator generator = ColorGenerator.MATERIAL;
                    int color1 = generator.getRandomColor();
                    String namaDepan = snapshot.child("NamaDepan").getValue().toString();
                    TextDrawable drawable2 = TextDrawable.builder().beginConfig().withBorder(4).endConfig().buildRound(namaDepan, color1);
                    profilTanya.setImageDrawable(drawable2);
                }

                else{
                    profilTanya.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Nama Penanya
        root1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String namaLengkap = snapshot.child("Nama").getValue().toString();
                namaTanya.setText(namaLengkap);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        //Tombol KIrim Pertanyaan kedalam database
        kirimPertanyaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tipe.getSelectedItemPosition() == 0 || kategori.getSelectedItemPosition() == 0){
                    String errorMessage = "test";
                    SetError(errorMessage);
                    Toast.makeText(BuatPertanyaanActivity.this, "Silahkan pilih tipe atau kategori ", Toast.LENGTH_SHORT).show();
                } else{

                    String txtNamaPenanya = namaTanya.getText().toString();
                    String txtNamaDepan = txtNamaPenanya.substring(0, 1);
                    String txtJudulPertanyaan = judulPertanyaan.getText().toString();
                    String txtIsiPertanyaan = isiPertanyaan.getText().toString();
                    String txtTipe = tipe.getSelectedItem().toString();
                    String txtKategori = kategori.getSelectedItem().toString();

                    HashMap<String, String> userMap = new HashMap<>();

                    userMap.put("NamaPenanya", txtNamaPenanya);
                    userMap.put("NamaDepanPenanya", txtNamaDepan);
                    userMap.put("JudulPertanyaan", txtJudulPertanyaan);
                    userMap.put("IsiPertanyaan", txtIsiPertanyaan);
                    userMap.put("TipePertanyaan", txtTipe);
                    userMap.put("KategoriPertanyaan", txtKategori);

                    if(txtJudulPertanyaan.isEmpty()){
                        judulPertanyaan.requestFocus();
                        judulPertanyaan.setError("Tidak Boleh Kosong, Judul Pertanyaan wajib di isi");

                    } else if (txtIsiPertanyaan.isEmpty()){
                        isiPertanyaan.setError("Tidak Boleh Kosong, Isi Pertanyaan wajib di Isi");

                    } else{
                        userMap.put("NamaPenanya", txtNamaPenanya);
                        root2.push().setValue(userMap);
                        root3.push().setValue(userMap);
                    }

                    Toast.makeText(BuatPertanyaanActivity.this, "Berhasil Mengirim Pertanyaan", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(BuatPertanyaanActivity.this, DiskusiActivity.class));

                }


            }
        });


    }

    public void SetError(String errorMessage)
    {
        View view = tipe.getSelectedView();

        // Set TextView in Secondary Unit spinner to be in error so that red (!) icon
        // appears, and then shake control if in error
        TextView tvListItem = (TextView)view;

        // Set fake TextView to be in error so that the error message appears
        TextView tvInvisibleError = (TextView)findViewById(R.id.tvInvisibleError);

        // Shake and set error if in error state, otherwise clear error
        if(errorMessage != null)
        {
            tvListItem.setError(errorMessage);
            tvListItem.requestFocus();

            tvInvisibleError.requestFocus();
            tvInvisibleError.setError(errorMessage);
        }
        else
        {
            tvListItem.setError(null);
            tvInvisibleError.setError(null);
        }
    }

}