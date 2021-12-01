package com.example.temandiabetes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TanyaDietisenActivity extends AppCompatActivity implements View.OnClickListener{
    private CardView gizi1, gizi2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tanya_dietisen);

        getSupportActionBar().setTitle("Tanya Dietisen");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        gizi1 = (CardView) findViewById(R.id.gizi_1);
        gizi2 = (CardView) findViewById(R.id.gizi_2);

        gizi1.setOnClickListener(this);
        gizi2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()) {
            case R.id.gizi_1 : i = new Intent(this, Dietisen1Activity.class); startActivity(i);break;
            case R.id.gizi_2 : i = new Intent(this, Dietisen2Activity.class); startActivity(i);break;
            default:break;

        }
    }
}