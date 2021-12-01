package com.example.temandiabetes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class UbahProfilActivity extends AppCompatActivity {

    EditText namaProfilLengkap, tanggalLahir, noTelp, alamat;
    Spinner jenisKelamin, tipeUser, jenisDiabetes;
    Button simpanProfil;
    int mTahun, mBulan, mHari;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference root = db.getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_profil);

        getSupportActionBar().setTitle("Ubah Profil Ku");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        namaProfilLengkap = (EditText) findViewById(R.id.edit_nama_lengkap);
        tanggalLahir = (EditText) findViewById(R.id.edit_tanggal_lahir);
        noTelp = (EditText) findViewById(R.id.edit_no_telp);
        alamat = (EditText) findViewById(R.id.edit_alamat);
        jenisKelamin = (Spinner) findViewById(R.id.spinner_jenis_kelamin);
        tipeUser = (Spinner) findViewById(R.id.spinner_tipe_user);
        jenisDiabetes = (Spinner) findViewById(R.id.spinner_jenis_diabetes);
        simpanProfil = (Button) findViewById(R.id.simpan_profil);


        tanggalLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mTahun = c.get(Calendar.YEAR);
                mBulan = c.get(Calendar.MONTH);
                mHari = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(UbahProfilActivity.this, AlertDialog.THEME_HOLO_LIGHT,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {


                                tanggalLahir.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mTahun, mBulan, mHari);
                datePickerDialog.show();

            }
        });




        simpanProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtNama = namaProfilLengkap.getText().toString();
                String txtNamaDepan = txtNama.substring(0, 1);
                String txtNoTelp = noTelp.getText().toString();
                String txtAlamat = alamat.getText().toString();
                String tglLahir = tanggalLahir.getText().toString();
                String spnJenisKelamin = jenisKelamin.getSelectedItem().toString();
                String spnTipeUser = tipeUser.getSelectedItem().toString();
                String spnJenisDiabetes = jenisDiabetes.getSelectedItem().toString();

                HashMap<String, String> userMap = new HashMap<>();

                userMap.put("Nama", txtNama);
                userMap.put("NamaDepan", txtNamaDepan);
                userMap.put("NoTelp", txtNoTelp);
                userMap.put("Alamat", txtAlamat);
                userMap.put("TanggalLahir", tglLahir);
                userMap.put("JenisKelamin", spnJenisKelamin);
                userMap.put("TipeUser", spnTipeUser);
                userMap.put("JenisDiabetes", spnJenisDiabetes);

                root.setValue(userMap);
                overridePendingTransition(0, 0);
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                Toast.makeText(UbahProfilActivity.this, "Data Berhasil Di Simpan", Toast.LENGTH_SHORT).show();

            }
        });



        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String nama = snapshot.child("Nama").getValue().toString();
                namaProfilLengkap.setText(nama);

                if (snapshot.child("TanggalLahir").exists()){
                    String txt_tanggal_lahir = snapshot.child("TanggalLahir").getValue().toString();
                    tanggalLahir.setText(txt_tanggal_lahir);
                }
                else{
                    tanggalLahir.setText("");
                }


                if(snapshot.child("JenisDiabetes").exists()){
                    String txt_jenisDiabetes = snapshot.child("JenisDiabetes").getValue().toString();
                    if (txt_jenisDiabetes.equals("Pre-Diabetes")){
                        jenisDiabetes.setSelection(1);
                    } else if(txt_jenisDiabetes.equals("Diabetes Type 1")){
                        jenisDiabetes.setSelection(2);
                    } else if(txt_jenisDiabetes.equals("Diabetes Type 2")){
                        jenisDiabetes.setSelection(3);
                    } else{
                        jenisDiabetes.setSelection(0);
                    }

                }else{
                    jenisDiabetes.setSelection(0);
                }


                if(snapshot.child("TipeUser").exists()){
                    String txt_tipeUser = snapshot.child("TipeUser").getValue().toString();
                    if (txt_tipeUser.equals("Non-Diabetesi")){
                        tipeUser.setSelection(1);
                    } else{
                        tipeUser.setSelection(0);
                    }

                }else{
                    tipeUser.setSelection(0);
                }


                if(snapshot.child("JenisKelamin").exists()){
                    String txt_jenisKelamin = snapshot.child("JenisKelamin").getValue().toString();
                    if (txt_jenisKelamin.equals("Laki-Laki")){
                        jenisKelamin.setSelection(1);
                    } else if (txt_jenisKelamin.equals("Wanita")){
                        jenisKelamin.setSelection(2);
                    } else{
                        jenisKelamin.setSelection(0);
                    }

                }else{
                    jenisKelamin.setSelection(0);
                }

                if(snapshot.child("NoTelp").exists()){
                    String noTelepon = snapshot.child("NoTelp").getValue().toString();

                    noTelp.setText(noTelepon);



                }else{
                    noTelp.setText("");
                }

                if(snapshot.child("Alamat").exists()){
                    String text_alamat = snapshot.child("Alamat").getValue().toString();
                    alamat.setText(text_alamat);
                }
                else{
                    alamat.setText("");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

}