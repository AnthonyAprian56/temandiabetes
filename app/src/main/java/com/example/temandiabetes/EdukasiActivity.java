package com.example.temandiabetes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class EdukasiActivity extends AppCompatActivity {


    TabLayout tabLayout;
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edukasi);

        tabLayout = findViewById(R.id.tablayout_edukasi);
        viewPager = findViewById(R.id.view_pager_edukasi);

        prepareViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        getSupportActionBar().setTitle("Edukasi Diabetes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);


    }

    private void prepareViewPager(ViewPager viewPager){
        EdukasiActivity.EdukasiAdapter adapter = new EdukasiActivity.EdukasiAdapter(getSupportFragmentManager());

        adapter.addFragment(KomplikasiDiabetesFragment.newInstance(), "KOMPLIKASI");
        adapter.addFragment(PengelolaanDiabetesFragment.newInstance(), "PENGELOLAAN");
        viewPager.setAdapter(adapter);
    }

    private class EdukasiAdapter extends FragmentPagerAdapter {

        ArrayList<String> arrayList = new ArrayList<>();
        List<Fragment> fragmentList = new ArrayList<>();

        public void addFragment(Fragment fragment, String title){
            arrayList.add(title);
            fragmentList.add(fragment);
        }

        public EdukasiAdapter(@NonNull FragmentManager fm) {
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