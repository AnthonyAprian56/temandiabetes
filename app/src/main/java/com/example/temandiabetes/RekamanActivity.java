package com.example.temandiabetes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class RekamanActivity extends AppCompatActivity {

    Button btnTambahBeratBadan, btnHba1c, btnOlahraga, btnTekananDarah, btnSimpanBeratBadan;
    Spinner olahraga;
    TextView text_beratBadan, belumAdaBeratBadan, deskripsiBeratBadan, text_kilogram, tanggalBeratBadan;
    TextView text_olahraga, deskripsiOlahraga, tanggalOlahraga, waktuOlahraga, belumAdaOlahraga;
    TextView  text_hba1c, normal, deskripsiHba1c, belumAdaHba1c, tanggalHba1c;
    TextView tekananDarah, tekananDarah2, mmHg, deskripsiTekananDarah, belumAdaTekananDarah, tanggalTekananDarah, atauTekananDarah;
    TextView keteranganGulaDarah;
    TextView titleMakanan, isiMakanan, titleSnack, isiSnack, belumAdaDataMakanan, tanggalDataMakanan, waktuDataMakanan;
    TextView riwayatGulaDarah, lihatGrafik;
    BarChart barChart;
    Button tambahGulaDarah;
    TextView gulaDarah, jenisGulaDarah, targetGulaDarah;
    int mTahun, mBulan, mHari, mNamaHari, mJam, mMenit;
    String gdBangunTidur, gdSetelahSarapan, gdSebelumMakanSiang, gdSetelahMakanSiang, gdSebelumMakanMalam, gdSetelahMakanMalam, gdSebelumTidur;



    private FirebaseAuth mAuth;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekaman);

        getSupportActionBar().setTitle("Rekaman");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        btnOlahraga = (Button) findViewById(R.id.ubah_olahraga);
        text_olahraga = (TextView) findViewById(R.id.jenis_olahraga);
        deskripsiOlahraga = (TextView) findViewById(R.id.deskripsi_olahraga);
        tanggalOlahraga = (TextView) findViewById(R.id.tanggal_olahraga);
        waktuOlahraga = (TextView) findViewById(R.id.waktu_olahraga);
        belumAdaOlahraga = (TextView) findViewById(R.id.belum_ada_olahraga);


        btnTambahBeratBadan = (Button) findViewById(R.id.ubah_beratbadan);
        text_beratBadan = (TextView) findViewById(R.id.input_berat_badan);
        belumAdaBeratBadan = (TextView) findViewById(R.id.belum_ada_BB);
        deskripsiBeratBadan = (TextView) findViewById(R.id.deskripsi_beratbadan);
        text_kilogram = (TextView) findViewById(R.id.kilogram);
        tanggalBeratBadan = (TextView) findViewById(R.id.tanggal_beratbadan);


        btnHba1c = (Button) findViewById(R.id.ubah_hba1c);
        text_hba1c = (TextView) findViewById(R.id.input_hba1c);
        normal = (TextView) findViewById(R.id.normal);
        deskripsiHba1c = (TextView) findViewById(R.id.deskripsi_hba1c);
        belumAdaHba1c = (TextView) findViewById(R.id.belum_ada_Hba1c);
        tanggalHba1c = (TextView) findViewById(R.id.tanggal_hba1c);


        btnTekananDarah = (Button) findViewById(R.id.ubah_tekanan_darah);
        tekananDarah = (TextView) findViewById(R.id.input_tekanan_darah);
        tekananDarah2 = (TextView) findViewById(R.id.input_tekanan_darah2);
        mmHg = (TextView) findViewById(R.id.satuan);
        deskripsiTekananDarah = (TextView) findViewById(R.id.deskripsi_tekanan_darah);
        belumAdaTekananDarah = (TextView) findViewById(R.id.belum_ada_tekanan_darah);
        tanggalTekananDarah = (TextView) findViewById(R.id.tanggal_tekanan_darah);
        atauTekananDarah = (TextView) findViewById(R.id.or);

        gulaDarah = (TextView) findViewById(R.id.gula_darah);
        jenisGulaDarah = (TextView) findViewById(R.id.jenis_gula_darah);
        targetGulaDarah = (TextView) findViewById(R.id.target_gula_darah);
        tambahGulaDarah = (Button) findViewById(R.id.btn_tambah_gula_darah);
        keteranganGulaDarah = (TextView) findViewById(R.id.keterangan_gula_darah);
        riwayatGulaDarah = (TextView) findViewById(R.id.riwayat_gula_darah);
        lihatGrafik = (TextView) findViewById(R.id.lihat_grafik);
        barChart = (BarChart) findViewById(R.id.bar_gula_darah);

        titleMakanan = (TextView) findViewById(R.id.data_makan);
        isiMakanan = (TextView) findViewById(R.id.isi_makan);
        titleSnack = (TextView) findViewById(R.id.data_snack);
        isiSnack = (TextView) findViewById(R.id.isi_snack);
        belumAdaDataMakanan = (TextView) findViewById(R.id.belum_ada_makanan);
        tanggalDataMakanan = (TextView) findViewById(R.id.tanggal_data_makanan);
        waktuDataMakanan = (TextView) findViewById(R.id.waktu_data_makanan);



        DatabaseReference root = db.getReference().child("Data").child("BB").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        DatabaseReference root2 = db.getReference().child("Data").child("Hba1c").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        DatabaseReference root3 = db.getReference().child("Data").child("TekananDarah").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        DatabaseReference root4 = db.getReference().child("Data").child("Olahraga").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        DatabaseReference root5 = db.getReference().child("Data").child("DataGulaDarah").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        DatabaseReference root6 = db.getReference().child("Data").child("GulaDarah").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.child("BeratBadan").exists()){
                        String BB = snapshot.child("BeratBadan").getValue().toString();
                        String tgl = snapshot.child("TanggalInputBeratBadan").getValue().toString();
                        text_beratBadan.setText(BB);
                        tanggalBeratBadan.setText(tgl);
                    }
                    else{
                        deskripsiBeratBadan.setVisibility(View.INVISIBLE);
                        text_kilogram.setVisibility(View.INVISIBLE);
                        text_beratBadan.setVisibility(View.INVISIBLE);
                        tanggalBeratBadan.setVisibility(View.INVISIBLE);
                        belumAdaBeratBadan.setVisibility(View.VISIBLE);
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        root2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("HbA1C").exists()){
                    String hba1c = snapshot.child("HbA1C").getValue().toString();
                    String tglHba1c = snapshot.child("TanggalInputHba1c").getValue().toString();
                    text_hba1c.setText(hba1c);
                    tanggalHba1c.setText(tglHba1c);

                }else{
                    deskripsiHba1c.setVisibility(View.INVISIBLE);
                    normal.setVisibility(View.INVISIBLE);
                    text_hba1c.setVisibility(View.INVISIBLE);
                    tanggalHba1c.setVisibility(View.INVISIBLE);
                    belumAdaHba1c.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        root3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("Diastolic").exists()){
                    String txt_diastolic = snapshot.child("Diastolic").getValue().toString();
                    String txt_sistolic = snapshot.child("Sistolic").getValue().toString();
                    String tglTekananDarah = snapshot.child("TanggalInputTekananDarah").getValue().toString();
                    tekananDarah.setText(txt_sistolic);
                    tekananDarah2.setText(txt_diastolic);
                    tanggalTekananDarah.setText(tglTekananDarah);

                }else{
                    atauTekananDarah.setVisibility(View.INVISIBLE);
                    deskripsiTekananDarah.setVisibility(View.INVISIBLE);
                    mmHg.setVisibility(View.INVISIBLE);
                    tekananDarah.setVisibility(View.INVISIBLE);
                    tekananDarah2.setVisibility(View.INVISIBLE);
                    tanggalTekananDarah.setVisibility(View.INVISIBLE);
                    belumAdaTekananDarah.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        root4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("DescOlahraga").exists()){
                    String txt_olahraga = snapshot.child("Olahraga").getValue().toString();
                    String txt_desc_olahraga = snapshot.child("DescOlahraga").getValue().toString();
                    String tglOlahraga = snapshot.child("TanggalInputOlahraga").getValue().toString();
                    String wktOlahraga = snapshot.child("WaktuInputOlahraga").getValue().toString();
                    text_olahraga.setText(txt_olahraga);
                    deskripsiOlahraga.setText(txt_desc_olahraga);
                    tanggalOlahraga.setText(tglOlahraga);
                    waktuOlahraga.setText(wktOlahraga);

                }else{
                    deskripsiOlahraga.setVisibility(View.INVISIBLE);
                    text_olahraga.setVisibility(View.INVISIBLE);
                    tanggalOlahraga.setVisibility(View.INVISIBLE);
                    waktuOlahraga.setVisibility(View.INVISIBLE);
                    belumAdaOlahraga.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        root6.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                    ArrayList<BarEntry> barEntries = new ArrayList<>();
                    ArrayList<String> xAxisLabel = new ArrayList<>();

                    if(snapshot.child("BangunTidur").child("GulaDarah").exists()){
                        xAxisLabel.add(snapshot.child("BangunTidur").child("WaktuInputGulaDarah").getValue().toString());
                        float f1 = Float.parseFloat(snapshot.child("BangunTidur").child("GulaDarah").getValue().toString());
                        barEntries.add(new BarEntry(0, f1));


                    }else if(!snapshot.child("BangunTidur").child("GulaDarah").exists()){
                        xAxisLabel.add("--");
                        barEntries.add(new BarEntry(0, 0));


                    }if(snapshot.child("1-2JamSetelahSarapan").child("GulaDarah").exists()){
                        xAxisLabel.add(snapshot.child("1-2JamSetelahSarapan").child("WaktuInputGulaDarah").getValue().toString());
                        float f2 = Float.parseFloat(snapshot.child("1-2JamSetelahSarapan").child("GulaDarah").getValue().toString());
                        barEntries.add(new BarEntry(1, f2));


                    }else if(!snapshot.child("1-2JamSetelahSarapan").child("GulaDarah").exists()){
                        xAxisLabel.add("--");
                        barEntries.add(new BarEntry(1, 0));


                    }if (snapshot.child("SebelumMakanSiang").child("GulaDarah").exists()){
                        xAxisLabel.add(snapshot.child("SebelumMakanSiang").child("WaktuInputGulaDarah").getValue().toString());
                        float f3 = Float.parseFloat(snapshot.child("SebelumMakanSiang").child("GulaDarah").getValue().toString());
                        barEntries.add(new BarEntry(2, f3));


                    } else if (!snapshot.child("SebelumMakanSiang").child("GulaDarah").exists()){
                        xAxisLabel.add("--");
                        barEntries.add(new BarEntry(2, 0));


                    }if (snapshot.child("1-2JamSetelahMakanSiang").child("GulaDarah").exists()){
                        xAxisLabel.add(snapshot.child("1-2JamSetelahMakanSiang").child("WaktuInputGulaDarah").getValue().toString());
                        float f4 = Float.parseFloat(snapshot.child("1-2JamSetelahMakanSiang").child("GulaDarah").getValue().toString());
                        barEntries.add(new BarEntry(3, f4));


                    } else if(!snapshot.child("1-2JamSetelahMakanSiang").child("GulaDarah").exists()){
                        xAxisLabel.add("--");
                        barEntries.add(new BarEntry(3, 0));


                    } if (snapshot.child("SebelumMakanMalam").child("GulaDarah").exists()){
                        xAxisLabel.add(snapshot.child("SebelumMakanMalam").child("WaktuInputGulaDarah").getValue().toString());
                        float f5 = Float.parseFloat(snapshot.child("SebelumMakanMalam").child("GulaDarah").getValue().toString());
                        barEntries.add(new BarEntry(4, f5));


                    } else if (!snapshot.child("SebelumMakanMalam").child("GulaDarah").exists()){
                        xAxisLabel.add("--");
                        barEntries.add(new BarEntry(4, 0));


                    } if (snapshot.child("1-2JamSetelahMakanMalam").child("GulaDarah").exists()){
                        xAxisLabel.add(snapshot.child("1-2JamSetelahMakanMalam").child("WaktuInputGulaDarah").getValue().toString());
                        float f6 = Float.parseFloat(snapshot.child("1-2JamSetelahMakanMalam").child("GulaDarah").getValue().toString());
                        barEntries.add(new BarEntry(5, f6));


                    } else if (!snapshot.child("1-2JamSetelahMakanMalam").child("GulaDarah").exists()){
                        xAxisLabel.add("--");
                        barEntries.add(new BarEntry(5, 0));


                    } if (snapshot.child("SebelumTidur").child("GulaDarah").exists()){
                        xAxisLabel.add(snapshot.child("SebelumTidur").child("WaktuInputGulaDarah").getValue().toString());
                        float f7 = Float.parseFloat(snapshot.child("SebelumTidur").child("GulaDarah").getValue().toString());
                        barEntries.add(new BarEntry(6, f7));


                    } else if (!snapshot.child("SebelumTidur").child("GulaDarah").exists()){
                        xAxisLabel.add("--");
                        barEntries.add(new BarEntry(6, 0));


                    }

                    BarDataSet barDataSet = new BarDataSet(barEntries, "Gula Darah");
                    barDataSet.setColors(getResources().getColor(R.color.white1), getResources().getColor(R.color.white2));
                    BarData barData = new BarData(barDataSet);
                    barData.setDrawValues(false);
                    barChart.setClickable(false);
                    barChart.getAxisLeft().setAxisMinimum(0f);
                    barChart.getAxisRight().setAxisMinimum(0f);
                    barChart.setDoubleTapToZoomEnabled(false);
                    barChart.setDoubleTapToZoomEnabled(false);
                    barChart.setTouchEnabled(true);
                    barChart.setClickable(true);
                    barChart.setData(barData);
                    barChart.setDrawGridBackground(false);
                    barDataSet.setHighLightColor(R.color.white3);
                    barChart.setDrawBorders(false);
                    barChart.getXAxis().setDrawGridLines(false);
                    barChart.getAxisLeft().setDrawGridLines(false);
                    barChart.getAxisRight().setDrawGridLines(false);
                    barChart.getAxisRight().setDrawLabels(false);
                    barChart.getAxisRight().setDrawAxisLine(false);
                    barChart.getAxisLeft().setDrawAxisLine(true);
                    barChart.getXAxis().setDrawAxisLine(true);
                    barChart.getDescription().setEnabled(false);
                    barChart.getLegend().setEnabled(false);
                    barChart.getAxisLeft().setTextColor(getResources().getColor(R.color.white));
                    barChart.getXAxis().setTextColor(getResources().getColor(R.color.white));
                    barChart.invalidate();
                    barChart.refreshDrawableState();
                    XAxis xAxis = barChart.getXAxis();
                    xAxis.setGranularity(1f);
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setValueFormatter(new ValueFormatter() {
                        @Override
                        public String getFormattedValue(float value) {
                            return xAxisLabel.get((int) value);
                        }
                    });

                 barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                     @Override
                     public void onValueSelected(Entry e, Highlight h) {
                         float x = e.getX();
                         float y = e.getY();


                         if(x == 0){
                             if(snapshot.child("BangunTidur").child("GulaDarah").exists()){

                                 String gulaDarah2 = snapshot.child("BangunTidur").child("GulaDarah").getValue().toString();
                                 gulaDarah.setText(gulaDarah2);
                                 gulaDarah.setTextColor(getResources().getColor(R.color.white));
                                 keteranganGulaDarah.setText("NORMAL");
                                 jenisGulaDarah.setText("Bangun Tidur");
                                 targetGulaDarah.setText("Target Angka Gula Darah normal: 80 - 130 mg/dl");

                                   if (snapshot.child("BangunTidur").child("MakanMalam").exists()){
                                       float f1 = Float.parseFloat(snapshot.child("BangunTidur").child("GulaDarah").getValue().toString());
                                       String waktu = snapshot.child("BangunTidur").child("WaktuInputGulaDarah").getValue().toString();
                                       String tanggal = snapshot.child("BangunTidur").child("TanggalGulaDarah").getValue().toString();
                                       String makanan = snapshot.child("BangunTidur").child("MakanMalam").getValue().toString();
                                       String snack = snapshot.child("BangunTidur").child("SnackMalam").getValue().toString();


                                     titleSnack.setVisibility(View.VISIBLE);
                                     titleMakanan.setVisibility(View.VISIBLE);
                                     isiSnack.setVisibility(View.VISIBLE);
                                     isiMakanan.setVisibility(View.VISIBLE);
                                     waktuDataMakanan.setVisibility(View.VISIBLE);
                                     tanggalDataMakanan.setVisibility(View.VISIBLE);
                                     belumAdaDataMakanan.setVisibility(View.INVISIBLE);


                                     titleMakanan.setText("Makan Malam:");
                                     titleSnack.setText("Makanan Ringan Malam:");
                                     isiMakanan.setText(makanan);
                                     isiSnack.setText(snack);
                                     waktuDataMakanan.setText(waktu);
                                     tanggalDataMakanan.setText(tanggal);

                                        if (f1 > 130){
                                           gulaDarah.setTextColor(getResources().getColor(R.color.red_td));
                                           keteranganGulaDarah.setText("TINGGI   ");
                                       }

                                       else if (f1 < 80) {
                                           keteranganGulaDarah.setText("RENDAH ");
                                           gulaDarah.setTextColor(getResources().getColor(R.color.red_td));
                                       }

                                 }

                                   else if (!snapshot.child("BangunTidur").child("MakanMalam").exists()){

                                       titleSnack.setVisibility(View.INVISIBLE);
                                       titleMakanan.setVisibility(View.INVISIBLE);
                                       isiSnack.setVisibility(View.INVISIBLE);
                                       isiMakanan.setVisibility(View.INVISIBLE);
                                       waktuDataMakanan.setVisibility(View.INVISIBLE);
                                       tanggalDataMakanan.setVisibility(View.INVISIBLE);
                                       belumAdaDataMakanan.setVisibility(View.VISIBLE);

                                   }



                             } else {
                                 gulaDarah.setText("- ");
                                 gulaDarah.setTextColor(getResources().getColor(R.color.white));
                                 keteranganGulaDarah.setText(" ");
                                 jenisGulaDarah.setText("Anda Belum Memasukan Gula Darah");
                                 targetGulaDarah.setText("Lakukan penambahan gula darah dengan menekan tombol di samping");
                             }

                         } else if(x == 1){
                             if (snapshot.child("1-2JamSetelahSarapan").child("GulaDarah").exists()){
                                 String gulaDarah2 = snapshot.child("1-2JamSetelahSarapan").child("GulaDarah").getValue().toString();
                                 gulaDarah.setText(gulaDarah2);
                                 keteranganGulaDarah.setText("NORMAL");
                                 jenisGulaDarah.setText("1-2 Jam Setelah Sarapan");
                                 targetGulaDarah.setText("Target angka Gula Darah normal: 80 - <180 mg/dl");
                                 gulaDarah.setTextColor(getResources().getColor(R.color.white));

                                    if (snapshot.child("1-2JamSetelahSarapan").child("Sarapan").exists()){
                                        float f2 = Float.parseFloat(snapshot.child("1-2JamSetelahSarapan").child("GulaDarah").getValue().toString());
                                        String waktu = snapshot.child("1-2JamSetelahSarapan").child("WaktuInputGulaDarah").getValue().toString();
                                        String tanggal = snapshot.child("1-2JamSetelahSarapan").child("TanggalGulaDarah").getValue().toString();
                                        String makanan = snapshot.child("1-2JamSetelahSarapan").child("Sarapan").getValue().toString();


                                         titleSnack.setVisibility(View.INVISIBLE);
                                         isiSnack.setVisibility(View.INVISIBLE);
                                         titleMakanan.setVisibility(View.VISIBLE);
                                         isiMakanan.setVisibility(View.VISIBLE);
                                         waktuDataMakanan.setVisibility(View.VISIBLE);
                                         tanggalDataMakanan.setVisibility(View.VISIBLE);
                                         belumAdaDataMakanan.setVisibility(View.INVISIBLE);


                                         titleMakanan.setText("Sarapan:");
                                         isiMakanan.setText(makanan);
                                         waktuDataMakanan.setText(waktu);
                                         tanggalDataMakanan.setText(tanggal);


                                         if (f2 > 180){
                                            gulaDarah.setTextColor(getResources().getColor(R.color.red_td));
                                            keteranganGulaDarah.setText("TINGGI   ");
                                         } else if (f2 < 80) {
                                            keteranganGulaDarah.setText("RENDAH ");
                                            gulaDarah.setTextColor(getResources().getColor(R.color.red_td));
                                         }


                                 }

                                   else if (!snapshot.child("1-2JamSetelahSarapan").child("MakanMalam").exists()){

                                       titleSnack.setVisibility(View.INVISIBLE);
                                       titleMakanan.setVisibility(View.INVISIBLE);
                                       isiSnack.setVisibility(View.INVISIBLE);
                                       isiMakanan.setVisibility(View.INVISIBLE);
                                       waktuDataMakanan.setVisibility(View.INVISIBLE);
                                       tanggalDataMakanan.setVisibility(View.INVISIBLE);
                                       belumAdaDataMakanan.setVisibility(View.VISIBLE);

                                   }



                             } else {
                                 gulaDarah.setText("- ");
                                 keteranganGulaDarah.setText(" ");
                                 gulaDarah.setTextColor(getResources().getColor(R.color.white));
                                 jenisGulaDarah.setText("Anda Belum Memasukan Gula Darah");
                                 targetGulaDarah.setText("Lakukan penambahan gula darah dengan menekan tombol di samping");
                             }

                         } else if(x == 2){
                             if(snapshot.child("SebelumMakanSiang").child("GulaDarah").exists()){
                                 String gulaDarah2 = snapshot.child("SebelumMakanSiang").child("GulaDarah").getValue().toString();
                                 gulaDarah.setText(gulaDarah2);
                                 jenisGulaDarah.setText("Sebelum Makan Siang");
                                 targetGulaDarah.setText("Target Angka Gula Darah normal: 80 - 130 mg/dl");
                                 gulaDarah.setTextColor(getResources().getColor(R.color.white));


                                  if (snapshot.child("SebelumMakanSiang").child("Sarapan").exists()){
                                      float f3 = Float.parseFloat(snapshot.child("SebelumMakanSiang").child("GulaDarah").getValue().toString());
                                      String waktu = snapshot.child("SebelumMakanSiang").child("WaktuInputGulaDarah").getValue().toString();
                                      String tanggal = snapshot.child("SebelumMakanSiang").child("TanggalGulaDarah").getValue().toString();
                                      String makanan = snapshot.child("SebelumMakanSiang").child("Sarapan").getValue().toString();
                                      String snack = snapshot.child("SebelumMakanSiang").child("SnackPagi").getValue().toString();


                                     titleSnack.setVisibility(View.VISIBLE);
                                     titleMakanan.setVisibility(View.VISIBLE);
                                     isiSnack.setVisibility(View.VISIBLE);
                                     isiMakanan.setVisibility(View.VISIBLE);
                                     waktuDataMakanan.setVisibility(View.VISIBLE);
                                     tanggalDataMakanan.setVisibility(View.VISIBLE);
                                     belumAdaDataMakanan.setVisibility(View.INVISIBLE);


                                     titleMakanan.setText("Sarapan:");
                                     titleSnack.setText("Makanan Ringan Pagi:");
                                     isiMakanan.setText(makanan);
                                     isiSnack.setText(snack);
                                     waktuDataMakanan.setText(waktu);
                                     tanggalDataMakanan.setText(tanggal);

                                        if (f3 > 130){
                                          gulaDarah.setTextColor(getResources().getColor(R.color.red_td));
                                          keteranganGulaDarah.setText("TINGGI   ");

                                      } else if (f3 < 80) {
                                          keteranganGulaDarah.setText("RENDAH ");
                                          gulaDarah.setTextColor(getResources().getColor(R.color.red_td));
                                      }


                                 }

                                   else if (!snapshot.child("SebelumMakanSiang").child("Sarapan").exists()){

                                       titleSnack.setVisibility(View.INVISIBLE);
                                       titleMakanan.setVisibility(View.INVISIBLE);
                                       isiSnack.setVisibility(View.INVISIBLE);
                                       isiMakanan.setVisibility(View.INVISIBLE);
                                       waktuDataMakanan.setVisibility(View.INVISIBLE);
                                       tanggalDataMakanan.setVisibility(View.INVISIBLE);
                                       belumAdaDataMakanan.setVisibility(View.VISIBLE);

                                   }



                             } else {
                                 gulaDarah.setText("- ");
                                 keteranganGulaDarah.setText(" ");
                                 gulaDarah.setTextColor(getResources().getColor(R.color.white));
                                 jenisGulaDarah.setText("Anda Belum Memasukan Gula Darah");
                                 targetGulaDarah.setText("Lakukan penambahan gula darah dengan menekan tombol di samping");
                             }

                         } else if(x == 3){
                             if(snapshot.child("1-2JamSetelahMakanSiang").child("GulaDarah").exists()){
                                 String gulaDarah2 = snapshot.child("1-2JamSetelahMakanSiang").child("GulaDarah").getValue().toString();
                                 gulaDarah.setText(gulaDarah2);
                                 keteranganGulaDarah.setText("NORMAL");
                                 jenisGulaDarah.setText("1-2 Jam Setelah Makan Siang");
                                 targetGulaDarah.setText("Target angka Gula Darah normal: 80 - <180 mg/dl");
                                 gulaDarah.setTextColor(getResources().getColor(R.color.white));

                                   if (snapshot.child("1-2JamSetelahMakanSiang").child("MakanSiang").exists()){
                                       float f4 = Float.parseFloat(snapshot.child("1-2JamSetelahMakanSiang").child("GulaDarah").getValue().toString());
                                       String waktu = snapshot.child("1-2JamSetelahMakanSiang").child("WaktuInputGulaDarah").getValue().toString();
                                       String tanggal = snapshot.child("1-2JamSetelahMakanSiang").child("TanggalGulaDarah").getValue().toString();
                                       String makanan = snapshot.child("1-2JamSetelahMakanSiang").child("MakanSiang").getValue().toString();

                                       titleSnack.setVisibility(View.INVISIBLE);
                                       isiSnack.setVisibility(View.INVISIBLE);
                                       titleMakanan.setVisibility(View.VISIBLE);
                                       isiMakanan.setVisibility(View.VISIBLE);
                                       waktuDataMakanan.setVisibility(View.VISIBLE);
                                       tanggalDataMakanan.setVisibility(View.VISIBLE);
                                       belumAdaDataMakanan.setVisibility(View.INVISIBLE);

                                       titleMakanan.setText("Makan Siang:");
                                       isiMakanan.setText(makanan);
                                       waktuDataMakanan.setText(waktu);
                                       tanggalDataMakanan.setText(tanggal);


                                       if (f4 > 180){
                                           gulaDarah.setTextColor(getResources().getColor(R.color.red_td));
                                           keteranganGulaDarah.setText("TINGGI   ");

                                       } else if (f4 < 80) {
                                           keteranganGulaDarah.setText("RENDAH ");
                                           gulaDarah.setTextColor(getResources().getColor(R.color.red_td));
                                       }


                                 }

                                   else if (!snapshot.child("1-2JamSetelahMakanSiang").child("MakanSiang").exists()){

                                       titleSnack.setVisibility(View.INVISIBLE);
                                       titleMakanan.setVisibility(View.INVISIBLE);
                                       isiSnack.setVisibility(View.INVISIBLE);
                                       isiMakanan.setVisibility(View.INVISIBLE);
                                       waktuDataMakanan.setVisibility(View.INVISIBLE);
                                       tanggalDataMakanan.setVisibility(View.INVISIBLE);
                                       belumAdaDataMakanan.setVisibility(View.VISIBLE);

                                   }



                             } else {
                                 gulaDarah.setText("- ");
                                 keteranganGulaDarah.setText(" ");
                                 gulaDarah.setTextColor(getResources().getColor(R.color.white));
                                 jenisGulaDarah.setText("Anda Belum Memasukan Gula Darah");
                                 targetGulaDarah.setText("Lakukan penambahan gula darah dengan menekan tombol di samping");
                             }

                         } else if(x == 4){
                             if (snapshot.child("SebelumMakanMalam").child("GulaDarah").exists()){
                                 String gulaDarah2 = snapshot.child("SebelumMakanMalam").child("GulaDarah").getValue().toString();
                                 gulaDarah.setText(gulaDarah2);
                                 jenisGulaDarah.setText("Sebelum Makan Malam");
                                 targetGulaDarah.setText("Target Angka Gula Darah normal: 80 - 130 mg/dl");
                                 gulaDarah.setTextColor(getResources().getColor(R.color.white));

                                 if (snapshot.child("SebelumMakanMalam").child("MakanSiang").exists()){
                                     float f5 = Float.parseFloat(snapshot.child("SebelumMakanMalam").child("GulaDarah").getValue().toString());
                                     String waktu = snapshot.child("SebelumMakanMalam").child("WaktuInputGulaDarah").getValue().toString();
                                     String tanggal = snapshot.child("SebelumMakanMalam").child("TanggalGulaDarah").getValue().toString();
                                     String makanan = snapshot.child("SebelumMakanMalam").child("MakanSiang").getValue().toString();
                                     String snack = snapshot.child("SebelumMakanMalam").child("SnackSiang").getValue().toString();


                                     titleSnack.setVisibility(View.VISIBLE);
                                     titleMakanan.setVisibility(View.VISIBLE);
                                     isiSnack.setVisibility(View.VISIBLE);
                                     isiMakanan.setVisibility(View.VISIBLE);
                                     waktuDataMakanan.setVisibility(View.VISIBLE);
                                     tanggalDataMakanan.setVisibility(View.VISIBLE);
                                     belumAdaDataMakanan.setVisibility(View.INVISIBLE);


                                     titleMakanan.setText("Makan Siang:");
                                     titleSnack.setText("Makanan Ringan Siang:");
                                     isiMakanan.setText(makanan);
                                     isiSnack.setText(snack);
                                     waktuDataMakanan.setText(waktu);
                                     tanggalDataMakanan.setText(tanggal);

                                     if (f5 > 130){
                                         gulaDarah.setTextColor(getResources().getColor(R.color.red_td));
                                         keteranganGulaDarah.setText("TINGGI   ");

                                     } else if (f5 < 80) {
                                         keteranganGulaDarah.setText("RENDAH ");
                                         gulaDarah.setTextColor(getResources().getColor(R.color.red_td));
                                     }

                                 }

                                   else if (!snapshot.child("SebelumMakanMalam").child("MakanSiang").exists()){

                                       titleSnack.setVisibility(View.INVISIBLE);
                                       titleMakanan.setVisibility(View.INVISIBLE);
                                       isiSnack.setVisibility(View.INVISIBLE);
                                       isiMakanan.setVisibility(View.INVISIBLE);
                                       waktuDataMakanan.setVisibility(View.INVISIBLE);
                                       tanggalDataMakanan.setVisibility(View.INVISIBLE);
                                       belumAdaDataMakanan.setVisibility(View.VISIBLE);

                                   }



                             } else {
                                 gulaDarah.setText("- ");
                                 keteranganGulaDarah.setText(" ");
                                 gulaDarah.setTextColor(getResources().getColor(R.color.white));
                                 jenisGulaDarah.setText("Anda Belum Memasukan Gula Darah");
                                 targetGulaDarah.setText("Lakukan penambahan gula darah dengan menekan tombol di samping");
                             }

                         } else if(x == 5){
                             if(snapshot.child("1-2JamSetelahMakanMalam").child("GulaDarah").exists()){
                                 String gulaDarah2 = snapshot.child("1-2JamSetelahMakanMalam").child("GulaDarah").getValue().toString();
                                 gulaDarah.setText(gulaDarah2);
                                 keteranganGulaDarah.setText("NORMAL");
                                 jenisGulaDarah.setText("1-2 Jam Setelah Makan Malam");
                                 targetGulaDarah.setText("Target angka Gula Darah normal: 80 - <180 mg/dl");
                                 gulaDarah.setTextColor(getResources().getColor(R.color.white));

                                   if (snapshot.child("1-2JamSetelahMakanMalam").child("MakanMalam").exists()){
                                       float f6 = Float.parseFloat(snapshot.child("1-2JamSetelahMakanMalam").child("GulaDarah").getValue().toString());
                                       String waktu = snapshot.child("1-2JamSetelahMakanMalam").child("WaktuInputGulaDarah").getValue().toString();
                                       String tanggal = snapshot.child("1-2JamSetelahMakanMalam").child("TanggalGulaDarah").getValue().toString();
                                       String makanan = snapshot.child("1-2JamSetelahMakanMalam").child("MakanMalam").getValue().toString();

                                       titleSnack.setVisibility(View.INVISIBLE);
                                       isiSnack.setVisibility(View.INVISIBLE);
                                       titleMakanan.setVisibility(View.VISIBLE);
                                       isiMakanan.setVisibility(View.VISIBLE);
                                       waktuDataMakanan.setVisibility(View.VISIBLE);
                                       tanggalDataMakanan.setVisibility(View.VISIBLE);
                                       belumAdaDataMakanan.setVisibility(View.INVISIBLE);

                                       titleMakanan.setText("Makan Malam:");
                                       isiMakanan.setText(makanan);
                                       waktuDataMakanan.setText(waktu);
                                       tanggalDataMakanan.setText(tanggal);


                                       if (f6 > 180){
                                           gulaDarah.setTextColor(getResources().getColor(R.color.red_td));
                                           keteranganGulaDarah.setText("TINGGI   ");

                                       } else if (f6 < 80) {
                                           keteranganGulaDarah.setText("RENDAH ");
                                           gulaDarah.setTextColor(getResources().getColor(R.color.red_td));
                                       }

                                 }

                                   else if (!snapshot.child("1-2JamSetelahMakanMalam").child("MakanMalam").exists()){

                                       titleSnack.setVisibility(View.INVISIBLE);
                                       titleMakanan.setVisibility(View.INVISIBLE);
                                       isiSnack.setVisibility(View.INVISIBLE);
                                       isiMakanan.setVisibility(View.INVISIBLE);
                                       waktuDataMakanan.setVisibility(View.INVISIBLE);
                                       tanggalDataMakanan.setVisibility(View.INVISIBLE);
                                       belumAdaDataMakanan.setVisibility(View.VISIBLE);

                                   }



                             } else {
                                 gulaDarah.setText("- ");
                                 keteranganGulaDarah.setText(" ");
                                 gulaDarah.setTextColor(getResources().getColor(R.color.white));
                                 jenisGulaDarah.setText("Anda Belum Memasukan Gula Darah");
                                 targetGulaDarah.setText("Lakukan penambahan gula darah dengan menekan tombol di samping");
                             }

                         } else if(x == 6){
                             if(snapshot.child("SebelumTidur").child("GulaDarah").exists()){
                                 String gulaDarah2 = snapshot.child("SebelumTidur").child("GulaDarah").getValue().toString();
                                 gulaDarah.setText(gulaDarah2);
                                 keteranganGulaDarah.setText("NORMAL");
                                 jenisGulaDarah.setText("Sebelum Tidur");
                                 targetGulaDarah.setText("Target Angka Gula Darah normal: 80 - 130 mg/dl");
                                 gulaDarah.setTextColor(getResources().getColor(R.color.white));

                                  if (snapshot.child("SebelumTidur").child("MakanMalam").exists()){
                                      float f7 = Float.parseFloat(snapshot.child("SebelumTidur").child("GulaDarah").getValue().toString());
                                      String waktu = snapshot.child("SebelumTidur").child("WaktuInputGulaDarah").getValue().toString();
                                      String tanggal = snapshot.child("SebelumTidur").child("TanggalGulaDarah").getValue().toString();
                                      String makanan = snapshot.child("SebelumTidur").child("MakanMalam").getValue().toString();
                                      String snack = snapshot.child("SebelumTidur").child("SnackMalam").getValue().toString();


                                      titleSnack.setVisibility(View.VISIBLE);
                                      titleMakanan.setVisibility(View.VISIBLE);
                                      isiSnack.setVisibility(View.VISIBLE);
                                      isiMakanan.setVisibility(View.VISIBLE);
                                      waktuDataMakanan.setVisibility(View.VISIBLE);
                                      tanggalDataMakanan.setVisibility(View.VISIBLE);
                                      belumAdaDataMakanan.setVisibility(View.INVISIBLE);


                                      titleMakanan.setText("Makan Malam:");
                                      titleSnack.setText("Makanan Ringan Malam:");
                                      isiMakanan.setText(makanan);
                                      isiSnack.setText(snack);
                                      waktuDataMakanan.setText(waktu);
                                      tanggalDataMakanan.setText(tanggal);

                                      if (f7 > 130){
                                          gulaDarah.setTextColor(getResources().getColor(R.color.red_td));
                                          keteranganGulaDarah.setText("TINGGI   ");

                                      } else if (f7 < 80) {
                                          keteranganGulaDarah.setText("RENDAH ");
                                          gulaDarah.setTextColor(getResources().getColor(R.color.red_td));
                                      }


                                 }

                                   else if (!snapshot.child("SebelumTidur").child("MakanMalam").exists()){

                                       titleSnack.setVisibility(View.INVISIBLE);
                                       titleMakanan.setVisibility(View.INVISIBLE);
                                       isiSnack.setVisibility(View.INVISIBLE);
                                       isiMakanan.setVisibility(View.INVISIBLE);
                                       waktuDataMakanan.setVisibility(View.INVISIBLE);
                                       tanggalDataMakanan.setVisibility(View.INVISIBLE);
                                       belumAdaDataMakanan.setVisibility(View.VISIBLE);

                                   }



                             } else{
                                 gulaDarah.setText("- ");
                                 keteranganGulaDarah.setText(" ");
                                 gulaDarah.setTextColor(getResources().getColor(R.color.white));
                                 jenisGulaDarah.setText("Anda Belum Memasukan Gula Darah");
                                 targetGulaDarah.setText("Lakukan penambahan gula darah dengan menekan tombol di samping");
                             }

                         }
                     }

                     @Override
                     public void onNothingSelected() {

                     }
                 });

            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        tambahGulaDarah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(RekamanActivity.this, R.style.Theme_Dialog);
                View mView = getLayoutInflater().inflate(R.layout.alert_tambah_gula_darah, null);
                final RelativeLayout btnManual = (RelativeLayout) mView.findViewById(R.id.manual);
                final ImageView btnTutup = (ImageView) mView.findViewById(R.id.tutup);
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.BOTTOM;
                lp.windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setAttributes(lp);
                dialog.show();



                btnManual.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(RekamanActivity.this, GulaDarahActivity.class));
                    }
                });


                btnTutup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


            }
        });







        btnTambahBeratBadan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference root = db.getReference().child("Data").child("BB").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(RekamanActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_berat_badan, null);
                final EditText beratBadan = (EditText) mView.findViewById(R.id.edit_berat_badan);
                //final NumberPicker beratBadan = (NumberPicker) mView.findViewById(R.id.edit_berat_badan);
                final RelativeLayout tanggalWaktu = (RelativeLayout) mView.findViewById(R.id.tanggal_waktu_berat_badan);
                final Button btnSimpanBeratBadan = (Button) mView.findViewById(R.id.simpan_berat_badan);
                final TextView tanggalInputBeratBadan = (TextView)mView.findViewById(R.id.tanggal_input_berat_badan);
                final TextView waktuInputBeratBadan = (TextView)mView.findViewById(R.id.waktu_input_berat_badan);
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();


                final Calendar c = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd MMM yyyy");
                String date = dateFormat.format(c.getTime());
                tanggalInputBeratBadan.setText(date);

                String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                waktuInputBeratBadan.setText(currentTime);

                beratBadan.setFocusable(true);
                beratBadan.setFocusableInTouchMode(true);
                beratBadan.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(beratBadan, InputMethodManager.SHOW_FORCED);

                //beratBadan.setMaxValue(80);
                //beratBadan.setMinValue(40);

                tanggalWaktu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar c = Calendar.getInstance();
                        mTahun = c.get(Calendar.YEAR);
                        mBulan = c.get(Calendar.MONTH);
                        mHari = c.get(Calendar.DAY_OF_MONTH);
                        mNamaHari = c.get(Calendar.DAY_OF_WEEK);


                        DatePickerDialog datePickerDialog = new DatePickerDialog(RekamanActivity.this,
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override

                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {
                                        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEE");
                                        SimpleDateFormat simpledateformat2 = new SimpleDateFormat("MMM");
                                        Date d = new Date(year, monthOfYear, dayOfMonth - 1);
                                        String dayOfTheWeek = simpledateformat.format(d);
                                        String monthOfTheYear = simpledateformat2.format(d);

                                        tanggalInputBeratBadan.setText(dayOfTheWeek + " " + dayOfMonth + " " + monthOfTheYear + " " + year);


                                        final Calendar c = Calendar.getInstance();
                                        mJam = c.get(Calendar.HOUR_OF_DAY);
                                        mMenit = c.get(Calendar.MINUTE);

                                        // Launch Time Picker Dialog
                                        TimePickerDialog timePickerDialog = new TimePickerDialog(RekamanActivity.this,
                                                new TimePickerDialog.OnTimeSetListener() {

                                                    @Override
                                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                                          int minute) {

                                                        waktuInputBeratBadan.setText(String.format("%02d:%02d", hourOfDay, minute));
                                                    }
                                                }, mJam, mMenit, true);
                                        timePickerDialog.show();

                                    }
                                }, mTahun, mBulan, mHari);
                        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                        datePickerDialog.show();


                    }
                });


                btnSimpanBeratBadan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String txtBeratBadan = beratBadan.getText().toString();
                        String txtTanggalInputBeratBadan = tanggalInputBeratBadan.getText().toString();
                        String txtWaktuInputBeratBadan = waktuInputBeratBadan.getText().toString();
                        HashMap<String, String> userMap = new HashMap<>();
                        userMap.put("BeratBadan", txtBeratBadan);
                        userMap.put("TanggalInputBeratBadan", txtTanggalInputBeratBadan);
                        userMap.put("WaktuInputBeratBadan", txtWaktuInputBeratBadan);
                        root.setValue(userMap);
                        dialog.dismiss();
                        overridePendingTransition(0, 0);
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                        Toast.makeText(RekamanActivity.this, "Berhasil Simpan Data Berat Badan!", Toast.LENGTH_SHORT).show();
                    }
                });



            }
        });

        btnHba1c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference root = db.getReference().child("Data").child("Hba1c").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(RekamanActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_hba1c, null);
                final EditText hba1c = (EditText) mView.findViewById(R.id.edit_hba1c);
                final Button btnSimpanHba1c = (Button) mView.findViewById(R.id.btn_simpan_hba1c);
                final RelativeLayout tanggalWaktuHba1c = (RelativeLayout) mView.findViewById(R.id.tanggal_waktu_hba1c);
                final TextView tanggalInputHba1c = (TextView)mView.findViewById(R.id.tanggal_input_hba1c);
                final TextView waktuInputHba1c = (TextView)mView.findViewById(R.id.waktu_input_hba1c);
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();


                final Calendar c = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd MMM yyyy");
                String date = dateFormat.format(c.getTime());
                tanggalInputHba1c.setText(date);

                String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                waktuInputHba1c.setText(currentTime);

                hba1c.setFocusable(true);
                hba1c.setFocusableInTouchMode(true);
                hba1c.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(hba1c, InputMethodManager.SHOW_FORCED);

                tanggalWaktuHba1c.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar c = Calendar.getInstance();
                        mTahun = c.get(Calendar.YEAR);
                        mBulan = c.get(Calendar.MONTH);
                        mHari = c.get(Calendar.DAY_OF_MONTH);
                        mNamaHari = c.get(Calendar.DAY_OF_WEEK);


                        DatePickerDialog datePickerDialog = new DatePickerDialog(RekamanActivity.this,
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override

                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {
                                        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEE");
                                        SimpleDateFormat simpledateformat2 = new SimpleDateFormat("MMM");
                                        Date d = new Date(year, monthOfYear, dayOfMonth - 1);
                                        String dayOfTheWeek = simpledateformat.format(d);
                                        String monthOfTheYear = simpledateformat2.format(d);

                                        tanggalInputHba1c.setText(dayOfTheWeek + " " + dayOfMonth + " " + monthOfTheYear + " " + year);

                                        final Calendar c = Calendar.getInstance();
                                        mJam = c.get(Calendar.HOUR_OF_DAY);
                                        mMenit = c.get(Calendar.MINUTE);

                                        // Launch Time Picker Dialog
                                        TimePickerDialog timePickerDialog = new TimePickerDialog(RekamanActivity.this,
                                                new TimePickerDialog.OnTimeSetListener() {

                                                    @Override
                                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                                          int minute) {

                                                        waktuInputHba1c.setText(String.format("%02d:%02d", hourOfDay, minute));
                                                    }
                                                }, mJam, mMenit, true);
                                        timePickerDialog.show();

                                    }
                                }, mTahun, mBulan, mHari);
                        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                        datePickerDialog.show();
                    }
                });


                btnSimpanHba1c.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String txtHba1c = hba1c.getText().toString();
                        String txtTanggalInputHba1c = tanggalInputHba1c.getText().toString();
                        String txtWaktuInputHba1c = waktuInputHba1c.getText().toString();
                        HashMap<String, String> userMap = new HashMap<>();
                        userMap.put("HbA1C", txtHba1c + " %");
                        userMap.put("TanggalInputHba1c", txtTanggalInputHba1c);
                        userMap.put("WaktuInputHba1c", txtWaktuInputHba1c);
                        root.setValue(userMap);
                        dialog.dismiss();
                        overridePendingTransition(0, 0);
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                        Toast.makeText(RekamanActivity.this, "Berhasil Simpan Data Hemoglobin (HBA1C)!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnTekananDarah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference root = db.getReference().child("Data").child("TekananDarah").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(RekamanActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_tekanan_darah, null);
                final EditText sistolic = (EditText) mView.findViewById(R.id.edit_sistolic);
                final EditText diastolic = (EditText) mView.findViewById(R.id.edit_diastolic);
                final Button btnSimpanTekananDarah = (Button) mView.findViewById(R.id.btn_simpan_tekanan_darah);
                final RelativeLayout tanggalWaktuTekananDarah = (RelativeLayout) mView.findViewById(R.id.tanggal_waktu_tekanan_darah);
                final TextView tanggalInputTekananDarah = (TextView)mView.findViewById(R.id.tanggal_input_tekanan_darah);
                final TextView waktuInputTekananDarah = (TextView)mView.findViewById(R.id.waktu_input_tekanan_darah);
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();


                final Calendar c = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd MMM yyyy");
                String date = dateFormat.format(c.getTime());
                tanggalInputTekananDarah.setText(date);

                String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                waktuInputTekananDarah.setText(currentTime);

                sistolic.setFocusable(true);
                sistolic.setFocusableInTouchMode(true);
                sistolic.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(sistolic, InputMethodManager.SHOW_FORCED);

                tanggalWaktuTekananDarah.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar c = Calendar.getInstance();
                        mTahun = c.get(Calendar.YEAR);
                        mBulan = c.get(Calendar.MONTH);
                        mHari = c.get(Calendar.DAY_OF_MONTH);
                        mNamaHari = c.get(Calendar.DAY_OF_WEEK);


                        DatePickerDialog datePickerDialog = new DatePickerDialog(RekamanActivity.this,
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override

                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {

                                        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEE");
                                        SimpleDateFormat simpledateformat2 = new SimpleDateFormat("MMM");
                                        Date d = new Date(year, monthOfYear, dayOfMonth - 1);
                                        String dayOfTheWeek = simpledateformat.format(d);
                                        String monthOfTheYear = simpledateformat2.format(d);

                                        tanggalInputTekananDarah.setText(dayOfTheWeek + " " + dayOfMonth + " " + monthOfTheYear + " " + year);

                                        final Calendar c = Calendar.getInstance();
                                        mJam = c.get(Calendar.HOUR_OF_DAY);
                                        mMenit = c.get(Calendar.MINUTE);

                                        // Launch Time Picker Dialog
                                        TimePickerDialog timePickerDialog = new TimePickerDialog(RekamanActivity.this,
                                                new TimePickerDialog.OnTimeSetListener() {

                                                    @Override
                                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                                          int minute) {

                                                        waktuInputTekananDarah.setText(String.format("%02d:%02d", hourOfDay, minute));
                                                    }
                                                }, mJam, mMenit, true);
                                        timePickerDialog.show();

                                    }
                                }, mTahun, mBulan, mHari);
                        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                        datePickerDialog.show();
                    }
                });


                btnSimpanTekananDarah.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String txtSistolic = sistolic.getText().toString();
                        String txtDiastolic = diastolic.getText().toString();
                        String txtTanggalTekananDarah = tanggalInputTekananDarah.getText().toString();
                        String txtWaktuInputTekananDarah = waktuInputTekananDarah.getText().toString();
                        HashMap<String, String> userMap = new HashMap<>();
                        userMap.put("Sistolic", txtSistolic);
                        userMap.put("Diastolic", txtDiastolic);
                        userMap.put("TanggalInputTekananDarah", txtTanggalTekananDarah);
                        userMap.put("WaktuInputTekananDarah", txtWaktuInputTekananDarah);
                        root.setValue(userMap);
                        dialog.dismiss();
                        overridePendingTransition(0, 0);
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                        Toast.makeText(RekamanActivity.this, "Berhasil Simpan Data Tekanan Darah!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        btnOlahraga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference root = db.getReference().child("Data").child("Olahraga").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(RekamanActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_olahraga, null);
                final Spinner olahraga = (Spinner) mView.findViewById(R.id.edit_olahraga);
                final TextView descOlahraga = (TextView) mView.findViewById(R.id.text_olahraga);
                final Button btnSimpanOlahraga = (Button) mView.findViewById(R.id.btn_simpan_olahraga);
                final RelativeLayout tanggalWaktuOlahraga = (RelativeLayout) mView.findViewById(R.id.tanggal_waktu_olahraga);
                final TextView tanggalInputOlahraga = (TextView)mView.findViewById(R.id.tanggal_input_olahraga);
                final TextView waktuInputOlahraga = (TextView)mView.findViewById(R.id.waktu_input_olahraga);

                final Calendar c = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd MMM yyyy");
                String date = dateFormat.format(c.getTime());
                tanggalInputOlahraga.setText(date);

                String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                waktuInputOlahraga.setText(currentTime);


                olahraga.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        setItemText(position);
                    }

                    void setItemText(int position){
                        switch (position){
                            case 0:
                                descOlahraga.setText("");
                                break;
                            case 1:
                                descOlahraga.setText("Berjalan kaki santai, peregangan otot, senam, yoga");
                                break;
                            case 2:
                                descOlahraga.setText("Jalan cepat, jogging, berenang, bersepeda santai");
                                break;
                            case 3:
                                descOlahraga.setText("Sepakbola, bulutangkis, basket, pilates, tai chi");
                                break;
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });



                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();

                tanggalWaktuOlahraga.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar c = Calendar.getInstance();
                        mTahun = c.get(Calendar.YEAR);
                        mBulan = c.get(Calendar.MONTH);
                        mHari = c.get(Calendar.DAY_OF_MONTH);
                        mNamaHari = c.get(Calendar.DAY_OF_WEEK);


                        DatePickerDialog datePickerDialog = new DatePickerDialog(RekamanActivity.this,
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override

                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {
                                        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEE");
                                        SimpleDateFormat simpledateformat2 = new SimpleDateFormat("MMM");
                                        Date d = new Date(year, monthOfYear, dayOfMonth - 1);
                                        String dayOfTheWeek = simpledateformat.format(d);
                                        String monthOfTheYear = simpledateformat2.format(d);

                                        tanggalInputOlahraga.setText(dayOfTheWeek + " " + dayOfMonth + " " + monthOfTheYear + " " + year);

                                        final Calendar c = Calendar.getInstance();
                                        mJam = c.get(Calendar.HOUR_OF_DAY);
                                        mMenit = c.get(Calendar.MINUTE);

                                        // Launch Time Picker Dialog
                                        TimePickerDialog timePickerDialog = new TimePickerDialog(RekamanActivity.this,
                                                new TimePickerDialog.OnTimeSetListener() {

                                                    @Override
                                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                                          int minute) {

                                                        waktuInputOlahraga.setText(String.format("%02d:%02d", hourOfDay, minute));
                                                    }
                                                }, mJam, mMenit, true);

                                        timePickerDialog.show();
                                    }
                                }, mTahun, mBulan, mHari);
                        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                        datePickerDialog.show();
                    }
                });


                btnSimpanOlahraga.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String txtOlahraga = olahraga.getSelectedItem().toString();
                        String txtDescOlahraga = descOlahraga.getText().toString();
                        String txtTanggalOlahraga = tanggalInputOlahraga.getText().toString();
                        String txtWaktuInputOlahraga = waktuInputOlahraga.getText().toString();
                        HashMap<String, String> userMap = new HashMap<>();
                        userMap.put("Olahraga", txtOlahraga);
                        userMap.put("DescOlahraga", txtDescOlahraga);
                        userMap.put("TanggalInputOlahraga", txtTanggalOlahraga);
                        userMap.put("WaktuInputOlahraga", txtWaktuInputOlahraga);
                        root.setValue(userMap);
                        dialog.dismiss();
                        overridePendingTransition(0, 0);
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                        Toast.makeText(RekamanActivity.this, "Berhasil Simpan Data Olahraga!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        riwayatGulaDarah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RekamanActivity.this, RiwayatGulaDarahActivity.class));
            }
        });

        lihatGrafik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RekamanActivity.this, LihatGrafikActivity.class));
            }
        });




    }

    @Override
    public void onBackPressed()
    {

        Intent intent=new Intent(RekamanActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

}