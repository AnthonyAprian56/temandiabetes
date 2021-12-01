package com.example.temandiabetes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class BuatAkunActivity extends AppCompatActivity {

    public EditText mEmail, mPass, namaLengkap;
    public Button buat_akun, masuk;
    public FirebaseAuth mAuth;
    public FirebaseUser user;
    String id;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_akun);

        mEmail = findViewById(R.id.txtEmail);
        mPass = findViewById(R.id.txtPassword);
        namaLengkap = findViewById(R.id.txtNamaLengkap);
        buat_akun = findViewById(R.id.btnDaftar);
        masuk = findViewById(R.id.btnPindahMasuk);

        mAuth = FirebaseAuth.getInstance();


        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BuatAkunActivity.this, MasukActivity.class));
            }
        });

        buat_akun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });

    }

    private void createUser(){
        String email = mEmail.getText().toString();
        String pass = mPass.getText().toString();


        if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            if(!pass.isEmpty()){
                mAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){

                                    String email = mEmail.getText().toString();
                                    String nama = namaLengkap.getText().toString();
                                    String txtNamaDepan = nama.substring(0, 1);

                                    HashMap<String, String> userMap = new HashMap<>();
                                    userMap.put("Nama", nama);
                                    userMap.put("NamaDepan", txtNamaDepan);
                                    userMap.put("Email", email);
                                    user = mAuth.getCurrentUser();
                                    id = user.getUid();
                                    root.child(id).setValue(userMap);

                                    Toast.makeText(BuatAkunActivity.this, "Berhasil Mendaftar", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(BuatAkunActivity.this, MasukActivity.class));
                                    finish();
                                }
                                else{
                                    Toast.makeText(BuatAkunActivity.this, "User Authentication Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    //Toast.makeText(BuatAkunActivity.this, "Gagal Mendaftar", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(BuatAkunActivity.this, "Gagal Mendaftar", Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                mPass.setError("Password tidak boleh kosong!!!!");
            }
        } else if(email.isEmpty()){
            mEmail.setError("Email Tidak Boleh Kosong");
        }else{
            mEmail.setError("Tolong Masukan Email dengan benar");
        }
    }


}