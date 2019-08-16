package com.example.apud;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Pager extends PagerAdapter {

    private ArrayList<CardDataModel> contents;
    private Context context;

    public Pager(ArrayList<CardDataModel> contents, Context context) {
        this.contents = contents;
        this.context = context;
    }

    @Override
    public int getCount() {
        return contents.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_matches, container, false);
        container.addView(view);

        ImageView imageView = (ImageView) view.findViewById(R.id.profilePic);
        //imageView.setImageResource(contents.get(position).getImages());
        Glide.with(Pager.this.context).load(contents.get(position).getImages()).into(imageView);


        TextView textView = (TextView) view.findViewById(R.id.userName);
        textView.setText(contents.get(position).getNames());

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       container.removeView((View) object);
    }
}
