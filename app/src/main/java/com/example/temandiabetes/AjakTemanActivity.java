package com.example.temandiabetes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AjakTemanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajak_teman);

        getSupportActionBar().setTitle("Ajak Teman");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}