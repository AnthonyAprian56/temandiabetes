package com.example.temandiabetes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class PengingatActivity extends AppCompatActivity  {

    EditText namaObat;
    TextView sesudahMakan, sebelumMakan, waktuMinumObat, tanggalMinumObat;
    CheckBox boxSebelumMakan, boxSesudahMakan;
    Button btnTambahPengingat;
    boolean mSebelumMakan, mSesudahMakan;
    int mTahun, mBulan, mHari, mNamaHari, mJam, mMenit;
    @TimeFormat
    private int clockFormat;

    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("Pengingat").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
    private AdapterPengingat adapter;
    private ArrayList<Model> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengingat);

        getSupportActionBar().setTitle("Pengingat Obat");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        clockFormat = TimeFormat.CLOCK_24H;


        btnTambahPengingat = (Button)findViewById(R.id.btn_tambah_pengingat);


        recyclerView = findViewById(R.id.recyclerview3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        list = new ArrayList<>();
        adapter = new AdapterPengingat(this, list);

        recyclerView.setAdapter(adapter);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Model model = dataSnapshot.getValue(Model.class);
                    list.add(model);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        btnTambahPengingat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(PengingatActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_tambah_obat, null);
                final AutoCompleteTextView namaObat = (AutoCompleteTextView) mView.findViewById(R.id.input_nama_obat);
                final CheckBox boxSebelumMakan = (CheckBox)mView.findViewById(R.id.sebelum_makan);
                final CheckBox boxSesudahMakan = (CheckBox)mView.findViewById(R.id.sesudah_makan);
                final TextView sebelumMakan = (TextView)mView.findViewById(R.id.txt_sebelum_makan);
                final TextView sesudahMakan = (TextView)mView.findViewById(R.id.txt_sesudah_makan);
                final TextView waktuMinumObat = (TextView)mView.findViewById(R.id.input_waktu_obat);
                final TextView tanggalMinumObat = (TextView)mView.findViewById(R.id.tanggal);
                final Button selesai = (Button) mView.findViewById(R.id.selesai);
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();

                // Get the string array
                String[] countries = getResources().getStringArray(R.array.obat);
                // Create the adapter and set it to the AutoCompleteTextView
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(PengingatActivity.this, android.R.layout.simple_list_item_1, countries);
                namaObat.setAdapter(adapter);

                final Calendar c = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMM yyyy");
                String date = dateFormat.format(c.getTime());
                tanggalMinumObat.setText(date);

                boxSebelumMakan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(((CheckBox) v).isChecked()){
                            sebelumMakan.setEnabled(true);
                            sesudahMakan.setEnabled(false);
                            sebelumMakan.getText();
                            mSebelumMakan = true;
                        }
                        else{
                            sesudahMakan.setEnabled(true);
                            mSebelumMakan = false;
                        }
                    }
                });

                boxSesudahMakan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(((CheckBox) v).isChecked()){
                            sesudahMakan.setEnabled(true);
                            sebelumMakan.setEnabled(false);
                            sesudahMakan.getText();
                            mSesudahMakan = true;
                        }
                        else{
                            sebelumMakan.setEnabled(true);
                            mSesudahMakan = false;
                        }
                    }
                });


                selesai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String txtObat = namaObat.getText().toString();
                        String txtSudahMakan = sesudahMakan.getText().toString();
                        String txtSebelumMakan = sebelumMakan.getText().toString();
                        String txtWaktuMinumObat = waktuMinumObat.getText().toString();
                        String txtTanggalMinumObat = tanggalMinumObat.getText().toString();
                        HashMap<String, String> userMap = new HashMap<>();
                        userMap.put("Obat", txtObat);
                        userMap.put("Waktu", txtWaktuMinumObat);
                        userMap.put("Tanggal", txtTanggalMinumObat);

                        if(boxSebelumMakan.isChecked()){
                            userMap.put("Aturan", txtSebelumMakan);
                            root.push().setValue(userMap);
                            dialog.dismiss();
                        }else if(boxSesudahMakan.isChecked()){
                            userMap.put("Aturan", txtSudahMakan);
                            root.push().setValue(userMap);
                            dialog.dismiss();
                        }else if(txtObat.isEmpty()){
                            namaObat.setError("Tidak Boleh Kosong");
                        }else if(!boxSebelumMakan.isChecked() || !boxSesudahMakan.isChecked()){
                            boxSebelumMakan.setError("Harus di pilih");
                            boxSesudahMakan.setError("Harus di pilih");
                        }

                        overridePendingTransition(0, 0);
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);

                    }

                });


                tanggalMinumObat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar c = Calendar.getInstance();
                        mTahun = c.get(Calendar.YEAR);
                        mBulan = c.get(Calendar.MONTH);
                        mHari = c.get(Calendar.DAY_OF_MONTH);
                        mNamaHari = c.get(Calendar.DAY_OF_WEEK);


                        DatePickerDialog datePickerDialog = new DatePickerDialog(PengingatActivity.this,
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {
                                        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEE");
                                        SimpleDateFormat simpledateformat2 = new SimpleDateFormat("MMM");
                                        Date d = new Date(year, monthOfYear, dayOfMonth - 1);
                                        String dayOfTheWeek = simpledateformat.format(d);
                                        String monthOfTheYear = simpledateformat2.format(d);

                                        tanggalMinumObat.setText(dayOfTheWeek + " " + dayOfMonth + "/" + monthOfTheYear + "/" + year);

                                    }
                                }, mTahun, mBulan, mHari);
                        datePickerDialog.show();

                    }
                });

                waktuMinumObat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar = Calendar.getInstance();
                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        int minute = calendar.get(Calendar.MINUTE);

                        MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                                .setTimeFormat(clockFormat)
                                .setHour(hour)
                                .setMinute(minute)
                                .setInputMode(MaterialTimePicker.INPUT_MODE_KEYBOARD)
                                .build();

                        materialTimePicker.show(getSupportFragmentManager(), "fragment_tag");

                        materialTimePicker.addOnPositiveButtonClickListener(dialog -> {
                            int newHour = materialTimePicker.getHour();
                            int newMinute = materialTimePicker.getMinute();
                            waktuMinumObat.setText(String.format("%02d : %02d", newHour, newMinute));
                        });
                    }
                });

            }
        });

    }
}