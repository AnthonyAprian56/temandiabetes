package com.example.temandiabetes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class BelanjaActivity extends AppCompatActivity{

    TabLayout tabLayout;
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_belanja);

        tabLayout = findViewById(R.id.tablayout_belanja);
        viewPager = findViewById(R.id.view_pager_belanja);

        prepareViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);


        //Action Bar BelanjaActivity
        getSupportActionBar().setTitle("Belanja");
        //Back Button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);


    }

    private void prepareViewPager(ViewPager viewPager){
        BelanjaAdapter adapter = new BelanjaAdapter(getSupportFragmentManager());

        adapter.addFragment(ObatFragment.newInstance(), "OBAT DAN ALAT");
        adapter.addFragment(AsuransiFragment.newInstance(), "ASURANSI");
        viewPager.setAdapter(adapter);
    }

    private class BelanjaAdapter extends FragmentPagerAdapter {

        ArrayList<String> arrayList = new ArrayList<>();
        List<Fragment> fragmentList = new ArrayList<>();

        public void addFragment(Fragment fragment, String title){
            arrayList.add(title);
            fragmentList.add(fragment);
        }

        public BelanjaAdapter(@NonNull FragmentManager fm) {
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