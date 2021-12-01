package com.example.temandiabetes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class DokterActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    public DokterActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dokter);

        getSupportActionBar().setTitle("Tanya Dokter");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        tabLayout = findViewById(R.id.tablayout_belanja);
        viewPager = findViewById(R.id.view_pager_belanja);

        prepareViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

        private void prepareViewPager(ViewPager viewPager){
            DokterActivity.DokterAdapter2 adapter = new DokterActivity.DokterAdapter2(getSupportFragmentManager());

            adapter.addFragment(PenyakitDalamFragment.newInstance(), "PENYAKIT DALAM");
            adapter.addFragment(DokterUmumFragment.newInstance(), "DOKTER UMUM");
            viewPager.setAdapter(adapter);
        }

        private class DokterAdapter2 extends FragmentPagerAdapter {

            ArrayList<String> arrayList = new ArrayList<>();
            List<Fragment> fragmentList = new ArrayList<>();

            public void addFragment(Fragment fragment, String title){
                arrayList.add(title);
                fragmentList.add(fragment);
            }

            public DokterAdapter2(@NonNull FragmentManager fm) {
                super(fm);
            }

            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return arrayList.get(position);
            }
        }

}