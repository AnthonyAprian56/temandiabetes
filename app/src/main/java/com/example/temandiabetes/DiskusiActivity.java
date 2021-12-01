package com.example.temandiabetes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DiskusiActivity extends AppCompatActivity {

    private EditText pencarianForumDiskusi;
    private RelativeLayout tanyakan;
    private RecyclerView recyclerView;

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("Pertanyaan");
    private AdapterDiskusi adapter;
    private ArrayList<Model> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diskusi);

        getSupportActionBar().setTitle("Forum Diskusi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        tanyakan = (RelativeLayout) findViewById(R.id.buat_tanya);
        pencarianForumDiskusi = findViewById(R.id.pencariaan_forum_diskusi);
        recyclerView = findViewById(R.id.pertanyaan);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new AdapterDiskusi(this, list);

        recyclerView.setAdapter(adapter);

        tanyakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DiskusiActivity.this, BuatPertanyaanActivity.class));
            }
        });

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Model model = dataSnapshot.getValue(Model.class);
                    list.add(model);

                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        pencarianForumDiskusi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

    }

        private void filter(String text){
            ArrayList<Model> filteredlist = new ArrayList<>();

            for (Model model : list){
                if(model.getJudulPertanyaan().toLowerCase().contains(text.toLowerCase())) {
                    filteredlist.add(model);
                }
            }
            adapter.filterlist(filteredlist);
        }

    //kembali ke HOME (MainActivity) ketika back button di pencet
    @Override
    public void onBackPressed()
    {

        Intent intent=new Intent(DiskusiActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }


}