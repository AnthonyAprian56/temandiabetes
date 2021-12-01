package com.example.temandiabetes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ProfilActivity extends AppCompatActivity {
    private TextView email, nama, noTelp, beratBadan, strip1, hbA1c, jenisDiabetes, sistolic, diastolic;
    private CardView logout, asuransi, ajakTeman, tentang, kebijakan, ubahProfilKu;
    private ImageView fotoProfil;
    private RelativeLayout pertanyaanDiskusi;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ubahProfilKu = (CardView) findViewById(R.id.ubah_profil);
        logout = (CardView) findViewById(R.id.btnLogOut);
        asuransi = (CardView) findViewById(R.id.card_asuransi);
        ajakTeman = (CardView) findViewById(R.id.card_ajak_teman);
        tentang = (CardView) findViewById(R.id.card_tentang);
        kebijakan = (CardView) findViewById(R.id.card_kebijakan_privasi);
        email = (TextView) findViewById(R.id.txt_profile_email);
        noTelp = (TextView) findViewById(R.id.txt_profile_NoTelp);
        nama = (TextView) findViewById(R.id.nama_profile_lengkap);
        beratBadan = (TextView) findViewById(R.id.text_profile_berat);
        strip1 = (TextView) findViewById(R.id.strip1);
        hbA1c = (TextView) findViewById(R.id.txt_profile_hba1c);
        jenisDiabetes = (TextView) findViewById(R.id.txt_profile_tipeUser);
        sistolic = (TextView) findViewById(R.id.text_profile_sistolic);
        diastolic = (TextView) findViewById(R.id.text_profile_diastolic);
        fotoProfil = (ImageView) findViewById(R.id.foto_profil2);

        pertanyaanDiskusi = (RelativeLayout) findViewById(R.id.pertanyaan_diskusi);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        email.setText(firebaseUser.getEmail());

        DatabaseReference root2 = db.getReference().child("Data").child("BB").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        DatabaseReference root3 = db.getReference().child("Data").child("Hba1c").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        DatabaseReference root4 = db.getReference().child("Data").child("TekananDarah").child(FirebaseAuth.getInstance().getCurrentUser().getUid());


        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String namaLengkap = snapshot.child("Nama").getValue().toString();
                nama.setText(namaLengkap);

                if(snapshot.child("NoTelp").exists()) {
                    String noTelepon = snapshot.child("NoTelp").getValue().toString();
                    noTelp.setText(noTelepon);
                }else{
                    noTelp.setText("No Telp : -");
                }

                if(snapshot.child("JenisDiabetes").exists()) {
                    String txtJenisDiabetes = snapshot.child("JenisDiabetes").getValue().toString();
                    jenisDiabetes.setText(txtJenisDiabetes);
                }else{
                    jenisDiabetes.setText("-");
                }

                if(snapshot.child("NamaDepan").exists()){
                    fotoProfil.setVisibility(View.VISIBLE);
                    ColorGenerator generator = ColorGenerator.MATERIAL;
                    int color1 = generator.getRandomColor();
                    String namaDepan = snapshot.child("NamaDepan").getValue().toString();
                    TextDrawable drawable2 = TextDrawable.builder().beginConfig().withBorder(4).endConfig().buildRound(namaDepan, color1);
                    fotoProfil.setImageDrawable(drawable2);
                }

                else{
                    fotoProfil.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        root2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              if(snapshot.child("BeratBadan").exists()){
                  String BB = snapshot.child("BeratBadan").getValue().toString();
                  beratBadan.setText(BB);
              }
              else{
                  beratBadan.setVisibility(View.INVISIBLE);
                  strip1.setVisibility(View.VISIBLE);
              }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        root3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("HbA1C").exists()){
                    String hba1c = snapshot.child("HbA1C").getValue().toString();
                    hbA1c.setText(hba1c);
                }
                else{
                    hbA1c.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        root4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("Diastolic").exists()){
                    String txt_diastolic = snapshot.child("Diastolic").getValue().toString();
                    String txt_sistolic = snapshot.child("Sistolic").getValue().toString();
                    sistolic.setText(txt_sistolic);
                    diastolic.setText(txt_diastolic);
                }
                else{
                    sistolic.setVisibility(View.INVISIBLE);
                    diastolic.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        ubahProfilKu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfilActivity.this, UbahProfilActivity.class));
            }
        });



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(ProfilActivity.this, MasukActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        asuransi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfilActivity.this, AsuransiActivity.class));
            }
        });

        ajakTeman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfilActivity.this, AjakTemanActivity.class));
            }
        });

        tentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfilActivity.this, TentangActivity.class));
            }
        });

        kebijakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfilActivity.this, KebijakanPrivasiActivity.class));
            }
        });

        //initialize And Assign variables
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        //beranda dipilih

        bottomNavigationView.setSelectedItemId(R.id.ic_profil);

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

        pertanyaanDiskusi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfilActivity.this, PertanyaanForumActivity.class));
            }
        });

    }


    //kembali ke HOME (MainActivity) ketika back button di pencet
    @Override
    public void onBackPressed()
    {

        Intent intent=new Intent(ProfilActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }
}