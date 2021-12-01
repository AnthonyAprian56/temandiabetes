package com.example.temandiabetes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class GulaDarahActivity extends AppCompatActivity {

    EditText tanggalGulaDarah, inputGulaDarah;
    //EditText makanan;
    //EditText snackMakan;
    AutoCompleteTextView makanan;
    AutoCompleteTextView snackMakan;
    Spinner jenisPemeriksaan;
    Button selesai;
    RelativeLayout kotakMakan, kotakSnackMakan;
    TextView waktuGulaDarah, titleMakan, titleSnackMalam;
    int mJam, mMenit;
    @TimeFormat private int clockFormat;

    private SimpleDateFormat dateFormat;
    private String date;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference root = db.getReference().child("Data").child("GulaDarah").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("BangunTidur");
    DatabaseReference root1 = db.getReference().child("Data").child("GulaDarah").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("1-2JamSetelahSarapan");
    DatabaseReference root2 = db.getReference().child("Data").child("GulaDarah").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("SebelumMakanSiang");
    DatabaseReference root3 = db.getReference().child("Data").child("GulaDarah").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("1-2JamSetelahMakanSiang");
    DatabaseReference root4 = db.getReference().child("Data").child("GulaDarah").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("SebelumMakanMalam");
    DatabaseReference root5 = db.getReference().child("Data").child("GulaDarah").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("1-2JamSetelahMakanMalam");
    DatabaseReference root6 = db.getReference().child("Data").child("GulaDarah").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("SebelumTidur");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gula_darah);
        getSupportActionBar().setTitle("Masukan Gula Darah");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        tanggalGulaDarah = (EditText) findViewById(R.id.tanggal_gula_darah);
        jenisPemeriksaan = (Spinner) findViewById(R.id.jenis_pemeriksaan);
        kotakMakan = (RelativeLayout) findViewById(R.id.kotak_makan);
        kotakSnackMakan = (RelativeLayout) findViewById(R.id.kotak_snack_makan);
        titleMakan = (TextView) findViewById(R.id.title_makan);
        inputGulaDarah =(EditText) findViewById(R.id.gula_darah1);
        titleSnackMalam = (TextView) findViewById(R.id.title_snack_makan);
        waktuGulaDarah = (TextView) findViewById(R.id.waktu_gula_darah);
        //makanan = (EditText) findViewById(R.id.makan);
        makanan = (AutoCompleteTextView) findViewById(R.id.makan);
        //snackMakan = (EditText) findViewById(R.id.snack_makan);
        snackMakan = (AutoCompleteTextView) findViewById(R.id.snack_makan);
        selesai = (Button) findViewById(R.id.selesai_tambah_gula_darah);


        final Calendar c = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("EEEE, dd MMM yyyy");
        date = dateFormat.format(c.getTime());
        tanggalGulaDarah.setText(date);
        clockFormat = TimeFormat.CLOCK_24H;

        // Get the string array
        String[] countries = getResources().getStringArray(R.array.makanan);
        // Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries);


        makanan.setAdapter(adapter);


        jenisPemeriksaan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setItemText(position);
            }

            void setItemText(int position){
                switch (position){
                    case 0:
                        titleMakan.setVisibility(View.INVISIBLE);
                        titleSnackMalam.setVisibility(View.INVISIBLE);
                        kotakSnackMakan.setVisibility(View.INVISIBLE);
                        kotakMakan.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                    case 7:
                        titleMakan.setVisibility(View.VISIBLE);
                        titleSnackMalam.setVisibility(View.VISIBLE);
                        kotakSnackMakan.setVisibility(View.VISIBLE);
                        kotakMakan.setVisibility(View.VISIBLE);
                        titleMakan.setText("Apa Makan Malam anda sebelumnya ?");
                        titleSnackMalam.setText("Apa Snack Malam anda sebelumnya ?");
                        break;
                    case 2:
                        titleMakan.setText("Apa Sarapan anda sebelumnya ?");
                        titleSnackMalam.setVisibility(View.INVISIBLE);
                        kotakSnackMakan.setVisibility(View.INVISIBLE);
                        titleMakan.setVisibility(View.VISIBLE);
                        kotakMakan.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        titleMakan.setVisibility(View.VISIBLE);
                        kotakMakan.setVisibility(View.VISIBLE);
                        titleSnackMalam.setVisibility(View.VISIBLE);
                        kotakSnackMakan.setVisibility(View.VISIBLE);
                        titleMakan.setText("Apa Sarapan anda sebelumnya ?");
                        titleSnackMalam.setText("Apa Snack Pagi anda sebelumnya ?");
                        break;
                    case 4:
                        titleMakan.setText("Apa Makan Siang anda sebelumnya ?");
                        titleSnackMalam.setVisibility(View.INVISIBLE);
                        kotakSnackMakan.setVisibility(View.INVISIBLE);
                        titleMakan.setVisibility(View.VISIBLE);
                        kotakMakan.setVisibility(View.VISIBLE);
                        break;
                    case 5:
                        titleMakan.setVisibility(View.VISIBLE);
                        kotakMakan.setVisibility(View.VISIBLE);
                        titleSnackMalam.setVisibility(View.VISIBLE);
                        kotakSnackMakan.setVisibility(View.VISIBLE);
                        titleMakan.setText("Apa Makan Siang anda sebelumnya ?");
                        titleSnackMalam.setText("Apa Snack Siang/Sore anda sebelumnya ?");
                        break;
                    case 6:
                        titleMakan.setText("Apa Makan Malam anda sebelumnya ?");
                        titleSnackMalam.setVisibility(View.INVISIBLE);
                        kotakSnackMakan.setVisibility(View.INVISIBLE);
                        titleMakan.setVisibility(View.VISIBLE);
                        kotakMakan.setVisibility(View.VISIBLE);
                        break;


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        waktuGulaDarah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(jenisPemeriksaan.getSelectedItemPosition() == 0){
                    Toast.makeText(GulaDarahActivity.this, "pilih jenis pemeriksaan terlebih dahulu", Toast.LENGTH_SHORT).show();
                    String errorMessage = "text";
                    SetError(errorMessage);

                } else {
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
                        waktuGulaDarah.setText(String.format("%02d : %02d", newHour, newMinute));
                        Toast.makeText(GulaDarahActivity.this, "Berhasil menambah waktu", Toast.LENGTH_SHORT).show();
                    });
                }

            }
        });


        selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(jenisPemeriksaan.getSelectedItemPosition() == 1){

                    String txttglGulaDarah = tanggalGulaDarah.getText().toString();
                    String spnJenisPemeriksaan = jenisPemeriksaan.getSelectedItem().toString();
                    String txtWaktuInputGulaDarah = waktuGulaDarah.getText().toString();
                    String txtInputGulaDarah = inputGulaDarah.getText().toString();
                    String txtMakanMalam = makanan.getText().toString();
                    String txtSnackMakan = snackMakan.getText().toString();

                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("No", "1");
                    userMap.put("TanggalGulaDarah", txttglGulaDarah);
                    userMap.put("JenisPemeriksaan", spnJenisPemeriksaan);
                    userMap.put("MakanMalam", txtMakanMalam);
                    userMap.put("SnackMalam", txtSnackMakan);
                    userMap.put("WaktuInputGulaDarah", txtWaktuInputGulaDarah);
                    userMap.put("GulaDarah", txtInputGulaDarah);

                    root.setValue(userMap);

                    Toast.makeText(GulaDarahActivity.this, "Berhasil Menambah Data", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(GulaDarahActivity.this, RekamanActivity.class));

                } else if(jenisPemeriksaan.getSelectedItemPosition() == 2){

                    String txttglGulaDarah = tanggalGulaDarah.getText().toString();
                    String spnJenisPemeriksaan = jenisPemeriksaan.getSelectedItem().toString();
                    String txtWaktuInputGulaDarah = waktuGulaDarah.getText().toString();
                    String txtInputGulaDarah = inputGulaDarah.getText().toString();
                    String txtSarapan = makanan.getText().toString();

                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("No", "2");
                    userMap.put("TanggalGulaDarah", txttglGulaDarah);
                    userMap.put("JenisPemeriksaan", spnJenisPemeriksaan);
                    userMap.put("Sarapan", txtSarapan);
                    userMap.put("WaktuInputGulaDarah", txtWaktuInputGulaDarah);
                    userMap.put("GulaDarah", txtInputGulaDarah);

                    root1.setValue(userMap);

                    Toast.makeText(GulaDarahActivity.this, "Berhasil Menambah Data", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(GulaDarahActivity.this, RekamanActivity.class));

                } else if(jenisPemeriksaan.getSelectedItemPosition() == 3){
                    String txttglGulaDarah = tanggalGulaDarah.getText().toString();
                    String spnJenisPemeriksaan = jenisPemeriksaan.getSelectedItem().toString();
                    String txtWaktuInputGulaDarah = waktuGulaDarah.getText().toString();
                    String txtInputGulaDarah = inputGulaDarah.getText().toString();
                    String txtSarapan = makanan.getText().toString();
                    String txtSnackMakan = snackMakan.getText().toString();

                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("No", "3");
                    userMap.put("TanggalGulaDarah", txttglGulaDarah);
                    userMap.put("JenisPemeriksaan", spnJenisPemeriksaan);
                    userMap.put("Sarapan", txtSarapan);
                    userMap.put("SnackPagi", txtSnackMakan);
                    userMap.put("WaktuInputGulaDarah", txtWaktuInputGulaDarah);
                    userMap.put("GulaDarah", txtInputGulaDarah);

                    root2.setValue(userMap);
                    Toast.makeText(GulaDarahActivity.this, "Berhasil Menambah Data", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(GulaDarahActivity.this, RekamanActivity.class));

                } else if(jenisPemeriksaan.getSelectedItemPosition() == 4){
                    String txttglGulaDarah = tanggalGulaDarah.getText().toString();
                    String spnJenisPemeriksaan = jenisPemeriksaan.getSelectedItem().toString();
                    String txtWaktuInputGulaDarah = waktuGulaDarah.getText().toString();
                    String txtInputGulaDarah = inputGulaDarah.getText().toString();
                    String txtMakanan = makanan.getText().toString();

                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("No", "4");
                    userMap.put("TanggalGulaDarah", txttglGulaDarah);
                    userMap.put("JenisPemeriksaan", spnJenisPemeriksaan);
                    userMap.put("MakanSiang", txtMakanan);
                    userMap.put("WaktuInputGulaDarah", txtWaktuInputGulaDarah);
                    userMap.put("GulaDarah", txtInputGulaDarah);

                    root3.setValue(userMap);
                    Toast.makeText(GulaDarahActivity.this, "Berhasil Menambah Data", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(GulaDarahActivity.this, RekamanActivity.class));

                } else if(jenisPemeriksaan.getSelectedItemPosition() == 5){

                    String txttglGulaDarah = tanggalGulaDarah.getText().toString();
                    String spnJenisPemeriksaan = jenisPemeriksaan.getSelectedItem().toString();
                    String txtWaktuInputGulaDarah = waktuGulaDarah.getText().toString();
                    String txtInputGulaDarah = inputGulaDarah.getText().toString();
                    String txtMakanan = makanan.getText().toString();
                    String txtSnackMakan = snackMakan.getText().toString();

                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("No", "5");
                    userMap.put("TanggalGulaDarah", txttglGulaDarah);
                    userMap.put("JenisPemeriksaan", spnJenisPemeriksaan);
                    userMap.put("MakanSiang", txtMakanan);
                    userMap.put("SnackSiang", txtSnackMakan);
                    userMap.put("WaktuInputGulaDarah", txtWaktuInputGulaDarah);
                    userMap.put("GulaDarah", txtInputGulaDarah);

                    root4.setValue(userMap);
                    Toast.makeText(GulaDarahActivity.this, "Berhasil Menambah Data", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(GulaDarahActivity.this, RekamanActivity.class));
                }

                else if(jenisPemeriksaan.getSelectedItemPosition() == 6){
                    String txttglGulaDarah = tanggalGulaDarah.getText().toString();
                    String spnJenisPemeriksaan = jenisPemeriksaan.getSelectedItem().toString();
                    String txtWaktuInputGulaDarah = waktuGulaDarah.getText().toString();
                    String txtInputGulaDarah = inputGulaDarah.getText().toString();
                    String txtMakanan = makanan.getText().toString();

                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("No", "6");
                    userMap.put("TanggalGulaDarah", txttglGulaDarah);
                    userMap.put("JenisPemeriksaan", spnJenisPemeriksaan);
                    userMap.put("MakanMalam", txtMakanan);
                    userMap.put("WaktuInputGulaDarah", txtWaktuInputGulaDarah);
                    userMap.put("GulaDarah", txtInputGulaDarah);

                    root5.setValue(userMap);

                    Toast.makeText(GulaDarahActivity.this, "Berhasil Menambah Data", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(GulaDarahActivity.this, RekamanActivity.class));
                }
                else if (jenisPemeriksaan.getSelectedItemPosition() == 7){

                    String txttglGulaDarah = tanggalGulaDarah.getText().toString();
                    String spnJenisPemeriksaan = jenisPemeriksaan.getSelectedItem().toString();
                    String txtWaktuInputGulaDarah = waktuGulaDarah.getText().toString();
                    String txtInputGulaDarah = inputGulaDarah.getText().toString();
                    String txtMakanan = makanan.getText().toString();
                    String txtSnackMakan = snackMakan.getText().toString();

                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("No", "7");
                    userMap.put("TanggalGulaDarah", txttglGulaDarah);
                    userMap.put("JenisPemeriksaan", spnJenisPemeriksaan);
                    userMap.put("MakanMalam", txtMakanan);
                    userMap.put("SnackMalam", txtSnackMakan);
                    userMap.put("WaktuInputGulaDarah", txtWaktuInputGulaDarah);
                    userMap.put("GulaDarah", txtInputGulaDarah);

                    root6.setValue(userMap);

                    Toast.makeText(GulaDarahActivity.this, "Berhasil Menambah Data", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(GulaDarahActivity.this, RekamanActivity.class));
                }


            }
        });
    }

    public void SetError(String errorMessage)
    {
        View view = jenisPemeriksaan.getSelectedView();

        // Set TextView in Secondary Unit spinner to be in error so that red (!) icon
        // appears, and then shake control if in error
        TextView tvListItem = (TextView)view;

        // Set fake TextView to be in error so that the error message appears
        TextView tvInvisibleError = (TextView)findViewById(R.id.tvInvisibleError);

        // Shake and set error if in error state, otherwise clear error
        if(errorMessage != null)
        {
            tvListItem.setError(errorMessage);
            tvListItem.requestFocus();

            tvInvisibleError.requestFocus();
            tvInvisibleError.setError(errorMessage);
        }
        else
        {
            tvListItem.setError(null);
            tvInvisibleError.setError(null);
        }
    }

    //kembali ke HOME (MainActivity) ketika back button di pencet
    @Override
    public void onBackPressed()
    {

        Intent intent=new Intent(GulaDarahActivity.this, RekamanActivity.class);
        startActivity(intent);
        finish();

    }



}