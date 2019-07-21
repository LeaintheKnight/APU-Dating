package com.example.apud;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPAger;

    private Pager mAdapter;

    private ArrayList<Integer> mImages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImages = new ArrayList<Integer>(Arrays.asList(R.drawable.one, R.drawable.two, R.drawable.four, R.drawable.three));

        mViewPAger = (ViewPager) findViewById(R.id.viewPager);
        mViewPAger.setAdapter(new Pager(mImages, this ));

    }

}
