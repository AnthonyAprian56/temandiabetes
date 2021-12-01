package com.example.temandiabetes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class LihatGrafikActivity extends AppCompatActivity {

    private TextView tanggalAwal, tamggalAkhir, kotak1, kotak2, kotak3, kotak4;
    private RelativeLayout kembali;
    private MaterialButton btnTampilkan;
    private LineChart grafikGulaDarah;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("Data").child("GrafikGaris");
    final Calendar myCalendar = Calendar.getInstance();
    final Calendar myCalendar2 = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_grafik);

        getSupportActionBar().hide();


        grafikGulaDarah = (LineChart) findViewById(R.id.grafik_gula_darah);
        tanggalAwal = (TextView) findViewById(R.id.tanggal_awal);
        tamggalAkhir = (TextView) findViewById(R.id.tanggal_akhir);
        btnTampilkan = (MaterialButton) findViewById(R.id.btn_tampilkan);
        kembali = (RelativeLayout) findViewById(R.id.icon_kembali_grafik);
        kotak1 = (TextView) findViewById(R.id.kotak_tanggal1);
        kotak2 = (TextView) findViewById(R.id.kotak_tanggal2);
        kotak3 = (TextView) findViewById(R.id.kotak_tanggal3);
        kotak4 = (TextView) findViewById(R.id.kotak_tanggal4);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });


        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();


            }

        };

        DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar2.set(Calendar.YEAR, year);
                myCalendar2.set(Calendar.MONTH, monthOfYear);
                myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel2();


            }

        };



        tanggalAwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(LihatGrafikActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        tamggalAkhir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(LihatGrafikActivity.this, date2, myCalendar2
                        .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                        myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnTampilkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tanggalAwal.getText().toString().isEmpty() || tamggalAkhir.getText().toString().isEmpty() ){

                    tanggalAwal.setError("Tidak Boleh Kosong");
                    Toast.makeText(LihatGrafikActivity.this, "Silahkan Memilih Tanggal Awal atau Akhir", Toast.LENGTH_LONG).show();
                    tamggalAkhir.setError("Tidak Boleh Kosong");

                } else {

                    root.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            LineDataSet lineDataSet1 = new LineDataSet(dataValues3(), "Minggu, 14 Mov 2021");
                            LineDataSet lineDataSet2 = new LineDataSet(dataValues4(), "Senin, 15 Nov 2021");
                            LineDataSet lineDataSet3 = new LineDataSet(dataValues5(), "Selasa, 16 Nov 2021");
                            LineDataSet lineDataSet4 = new LineDataSet(dataValues6(), "Rabu, 17 Nov 2021");
                            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                            ArrayList<String> xAxisLabel = new ArrayList<>();

                            lineDataSet1.setLineWidth(2);
                            lineDataSet2.setLineWidth(2);
                            lineDataSet3.setLineWidth(2);
                            lineDataSet4.setLineWidth(2);

                            dataSets.add(lineDataSet1);
                            dataSets.add(lineDataSet2);
                            dataSets.add(lineDataSet3);
                            dataSets.add(lineDataSet4);

                            LineData data = new LineData(dataSets);
                            grafikGulaDarah.setData(data);
                            grafikGulaDarah.invalidate();
                            lineDataSet1.setColors(getResources().getColor(R.color.black));
                            lineDataSet2.setColors(getResources().getColor(R.color.red_td));
                            lineDataSet3.setColors(getResources().getColor(R.color.purple_500));
                            lineDataSet4.setColors(getResources().getColor(R.color.teal_700));

                            kotak1.setVisibility(View.VISIBLE);
                            kotak2.setVisibility(View.VISIBLE);
                            kotak3.setVisibility(View.VISIBLE);
                            kotak4.setVisibility(View.VISIBLE);

                            lineDataSet1.setDrawCircles(false);
                            lineDataSet1.setCircleColor(Color.BLACK);
                            lineDataSet1.setCircleRadius(7);

                            lineDataSet2.setDrawCircles(false);
                            lineDataSet2.setCircleColor(Color.RED);
                            lineDataSet2.setCircleRadius(7);

                            lineDataSet3.setDrawCircles(false);
                            lineDataSet3.setCircleColor(R.color.purple_500);
                            lineDataSet3.setCircleRadius(7);

                            lineDataSet4.setDrawCircles(false);
                            lineDataSet4.setCircleColor(R.color.teal_700);
                            lineDataSet4.setCircleRadius(7);

                            CustomMPLineChartMarkerView mv = new CustomMPLineChartMarkerView(LihatGrafikActivity.this);
                            mv.setChartView(grafikGulaDarah);
                            grafikGulaDarah.setMarker(mv);

                            data.setDrawValues(false);
                            grafikGulaDarah.setClickable(true);
                            grafikGulaDarah.invalidate();
                            grafikGulaDarah.getAxisLeft().setAxisMinimum(0f);
                            grafikGulaDarah.getAxisRight().setAxisMinimum(0f);
                            grafikGulaDarah.setDoubleTapToZoomEnabled(false);
                            grafikGulaDarah.setTouchEnabled(true);
                            grafikGulaDarah.setClickable(true);
                            grafikGulaDarah.setData(data);
                            grafikGulaDarah.setDrawGridBackground(false);
                            lineDataSet1.setHighLightColor(R.color.white3);
                            lineDataSet2.setHighLightColor(R.color.white3);
                            grafikGulaDarah.setDrawBorders(false);
                            grafikGulaDarah.getXAxis().setDrawGridLines(false);
                            grafikGulaDarah.getAxisLeft().setDrawGridLines(false);
                            grafikGulaDarah.getAxisRight().setDrawGridLines(false);
                            grafikGulaDarah.getAxisRight().setDrawLabels(false);
                            grafikGulaDarah.getAxisLeft().setDrawLabels(false);
                            grafikGulaDarah.getAxisRight().setDrawAxisLine(false);
                            grafikGulaDarah.getAxisLeft().setDrawAxisLine(false);
                            grafikGulaDarah.getAxisLeft().setDrawAxisLine(false);
                            grafikGulaDarah.getXAxis().setDrawAxisLine(false);
                            grafikGulaDarah.getDescription().setEnabled(false);
                            grafikGulaDarah.getAxisLeft().setTextColor(getResources().getColor(R.color.black));
                            grafikGulaDarah.getXAxis().setTextColor(getResources().getColor(R.color.black));
                            grafikGulaDarah.invalidate();
                            grafikGulaDarah.refreshDrawableState();
                            grafikGulaDarah.setExtraLeftOffset(36);
                            grafikGulaDarah.setExtraRightOffset(36);
                            grafikGulaDarah.getLegend().setEnabled(false);


                           kotak1.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   if(lineDataSet1.isDrawCirclesEnabled()){
                                       lineDataSet1.setDrawCircles(false);
                                       lineDataSet2.setVisible(true);
                                       lineDataSet3.setVisible(true);
                                       lineDataSet4.setVisible(true);
                                       lineDataSet1.setLineWidth(2);
                                       kotak1.setBackgroundResource(R.drawable.kotak_tanggal1);
                                       kotak1.setTextColor(Color.parseColor("#000000"));
                                       grafikGulaDarah.notifyDataSetChanged();
                                       grafikGulaDarah.invalidate();
                                   } else{
                                       lineDataSet1.setDrawCircles(true);
                                       lineDataSet2.setDrawCircles(false);
                                       lineDataSet3.setDrawCircles(false);
                                       lineDataSet4.setDrawCircles(false);
                                       lineDataSet2.setVisible(false);
                                       lineDataSet3.setVisible(false);
                                       lineDataSet4.setVisible(false);
                                       lineDataSet1.setLineWidth(4);
                                       kotak1.setBackgroundResource(R.color.black);
                                       kotak1.setTextColor(Color.parseColor("#FFFFFF"));
                                       grafikGulaDarah.notifyDataSetChanged();
                                       grafikGulaDarah.invalidate();
                                   }


                               }
                           });

                           kotak2.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   if(lineDataSet2.isDrawCirclesEnabled()){
                                       lineDataSet2.setDrawCircles(false);
                                       lineDataSet1.setVisible(true);
                                       lineDataSet3.setVisible(true);
                                       lineDataSet4.setVisible(true);
                                       lineDataSet2.setLineWidth(2);
                                       kotak2.setBackgroundResource(R.drawable.kotak_tanggal2);
                                       kotak2.setTextColor(Color.parseColor("#000000"));
                                       grafikGulaDarah.notifyDataSetChanged();
                                       grafikGulaDarah.invalidate();
                                   } else{
                                       lineDataSet2.setDrawCircles(true);
                                       lineDataSet1.setDrawCircles(false);
                                       lineDataSet3.setDrawCircles(false);
                                       lineDataSet4.setDrawCircles(false);
                                       lineDataSet1.setVisible(false);
                                       lineDataSet3.setVisible(false);
                                       lineDataSet4.setVisible(false);
                                       lineDataSet2.setLineWidth(4);
                                       kotak2.setBackgroundResource(R.color.red_td);
                                       kotak2.setTextColor(Color.parseColor("#FFFFFF"));
                                       grafikGulaDarah.notifyDataSetChanged();
                                       grafikGulaDarah.invalidate();
                                   }
                               }
                           });

                           kotak3.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   if(lineDataSet3.isDrawCirclesEnabled()){
                                       lineDataSet3.setDrawCircles(false);
                                       lineDataSet1.setVisible(true);
                                       lineDataSet2.setVisible(true);
                                       lineDataSet4.setVisible(true);
                                       lineDataSet3.setLineWidth(2);
                                       kotak3.setBackgroundResource(R.drawable.kotak_tanggal3);
                                       kotak3.setTextColor(Color.parseColor("#000000"));
                                       grafikGulaDarah.notifyDataSetChanged();
                                       grafikGulaDarah.invalidate();

                                   } else{
                                       lineDataSet3.setDrawCircles(true);
                                       lineDataSet1.setDrawCircles(false);
                                       lineDataSet2.setDrawCircles(false);
                                       lineDataSet4.setDrawCircles(false);
                                       lineDataSet1.setVisible(false);
                                       lineDataSet2.setVisible(false);
                                       lineDataSet4.setVisible(false);
                                       lineDataSet3.setLineWidth(4);
                                       kotak3.setBackgroundResource(R.color.purple_500);
                                       kotak3.setTextColor(Color.parseColor("#FFFFFF"));
                                       grafikGulaDarah.notifyDataSetChanged();
                                       grafikGulaDarah.invalidate();
                                   }
                               }
                           });

                           kotak4.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   if(lineDataSet4.isDrawCirclesEnabled()){
                                       lineDataSet4.setDrawCircles(false);
                                       lineDataSet1.setVisible(true);
                                       lineDataSet2.setVisible(true);
                                       lineDataSet3.setVisible(true);
                                       lineDataSet4.setLineWidth(2);
                                       kotak4.setBackgroundResource(R.drawable.kotak_tanggal4);
                                       kotak4.setTextColor(Color.parseColor("#000000"));
                                       grafikGulaDarah.notifyDataSetChanged();
                                       grafikGulaDarah.invalidate();
                                   } else{
                                       lineDataSet4.setDrawCircles(true);
                                       lineDataSet1.setDrawCircles(false);
                                       lineDataSet2.setDrawCircles(false);
                                       lineDataSet3.setDrawCircles(false);
                                       lineDataSet1.setVisible(false);
                                       lineDataSet2.setVisible(false);
                                       lineDataSet3.setVisible(false);
                                       lineDataSet4.setLineWidth(4);
                                       kotak4.setBackgroundResource(R.color.teal_700);
                                       kotak4.setTextColor(Color.parseColor("#FFFFFF"));
                                       grafikGulaDarah.notifyDataSetChanged();
                                       grafikGulaDarah.invalidate();
                                   }
                               }
                           });

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }






                    });



                }
            }
        });







    }

    private void updateLabel() {
        String myFormat = "EEEE, dd MMM YY"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        tanggalAwal.setText(sdf.format(myCalendar.getTime()));

    }

    private void updateLabel2() {
        String myFormat = "EEEE, dd MMM YY"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        tamggalAkhir.setText(sdf.format(myCalendar2.getTime()));
    }



    private ArrayList<Entry> dataValues3(){
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        dataVals.add(new Entry(0, 139));
        dataVals.add(new Entry(1, 170));
        dataVals.add(new Entry(2, 140));
        dataVals.add(new Entry(3, 150));
        dataVals.add(new Entry(4, 190));
        dataVals.add(new Entry(5, 200));
        dataVals.add(new Entry(6, 110));

        return  dataVals;
    }

    private ArrayList<Entry> dataValues4(){
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        dataVals.add(new Entry(0, 120));
        dataVals.add(new Entry(1, 161));
        dataVals.add(new Entry(2, 142));
        dataVals.add(new Entry(3, 171));
        dataVals.add(new Entry(4, 130));
        dataVals.add(new Entry(5, 125));
        dataVals.add(new Entry(6, 170));

        return  dataVals;
    }

    private ArrayList<Entry> dataValues5(){
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        dataVals.add(new Entry(0, 180));
        dataVals.add(new Entry(1, 190));
        dataVals.add(new Entry(2, 120));
        dataVals.add(new Entry(3, 130));
        dataVals.add(new Entry(4, 140));
        dataVals.add(new Entry(5, 150));
        dataVals.add(new Entry(6, 160));

        return  dataVals;
    }

    private ArrayList<Entry> dataValues6(){
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        dataVals.add(new Entry(0, 130));
        dataVals.add(new Entry(1, 140));
        dataVals.add(new Entry(2, 100));
        dataVals.add(new Entry(3, 120));
        dataVals.add(new Entry(4, 180));
        dataVals.add(new Entry(5, 190));
        dataVals.add(new Entry(6, 120));

        return  dataVals;
    }









}