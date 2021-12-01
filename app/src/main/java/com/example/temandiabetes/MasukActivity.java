package com.example.temandiabetes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MasukActivity extends AppCompatActivity {
    private TextView mEmail, mPass;
    private Button masuk, buat_akun;

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masuk);

        mEmail = findViewById(R.id.txtEmail);
        mPass = findViewById(R.id.txtPassword);
        masuk = findViewById(R.id.btnMasuk);
        buat_akun = findViewById(R.id.btnBuatAkun);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        buat_akun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MasukActivity.this, BuatAkunActivity.class));
            }
        });

        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

    }

    private void loginUser(){
        String email = mEmail.getText().toString();
        String pass = mPass.getText().toString();

        if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            if(!pass.isEmpty()){
                mAuth.signInWithEmailAndPassword(email, pass)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(MasukActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MasukActivity.this, MainActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MasukActivity.this, "Login Gagal", Toast.LENGTH_SHORT).show();
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


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user !=null){
            startActivity(new Intent(MasukActivity.this, MainActivity.class));
            finish();
        }

    }



}