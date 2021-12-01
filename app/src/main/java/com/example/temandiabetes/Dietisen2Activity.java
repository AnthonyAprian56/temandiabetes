package com.example.temandiabetes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Dietisen2Activity extends AppCompatActivity {

    Button tanyaDietisen2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dietisen2);

        tanyaDietisen2 = (Button) findViewById(R.id.btn_dietisen2);

        tanyaDietisen2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Dietisen2Activity.this, "Tenaga Kesehatan sedang tidak aktif", Toast.LENGTH_LONG).show();
            }
        });

    }
}