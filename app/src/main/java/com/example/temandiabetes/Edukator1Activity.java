package com.example.temandiabetes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Edukator1Activity extends AppCompatActivity {

    Button tanyaEdukator1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edukator1);

        tanyaEdukator1 = (Button) findViewById(R.id.btn_edukator1);

        tanyaEdukator1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Edukator1Activity.this, "Tenaga Kesehatan sedang tidak aktif", Toast.LENGTH_LONG).show();
            }
        });

    }
}