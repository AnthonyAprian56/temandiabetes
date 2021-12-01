package com.example.temandiabetes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TanyaEdukatorActivity extends AppCompatActivity implements View.OnClickListener{
    private CardView edukator1, edukator2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tanya_edukator);

        getSupportActionBar().setTitle("Tanya Edukator");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        edukator1 = (CardView) findViewById(R.id.edukator_1);
        edukator2 = (CardView) findViewById(R.id.edukator_2);

        edukator1.setOnClickListener(this);
        edukator2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()) {
            case R.id.edukator_1 : i = new Intent(this, Edukator1Activity.class); startActivity(i);break;
            case R.id.edukator_2 : i = new Intent(this, Edukator2Activity.class); startActivity(i);break;
            default:break;

        }

    }
}