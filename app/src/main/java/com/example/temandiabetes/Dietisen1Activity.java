package com.example.temandiabetes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Dietisen1Activity extends AppCompatActivity {

    Button tanyaDietisen1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dietisen1);

        tanyaDietisen1 = (Button) findViewById(R.id.btn_dietisen1);

        tanyaDietisen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Dietisen1Activity.this, "Tenaga Kesehatan sedang tidak aktif", Toast.LENGTH_LONG).show();
            }
        });


    }
}