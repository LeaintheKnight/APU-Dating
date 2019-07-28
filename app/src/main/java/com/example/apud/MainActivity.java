package com.example.apud;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPAger;

    private Pager mAdapter;

    private FirebaseAuth mAuth;

    private ArrayList<CardDataModel> mContents;

    public ArrayList<String> names = new ArrayList<String>(Arrays.asList("Gavin", "Jim"));

    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        checkUserSex(new MyCallback() {
            @Override
            public void onCallback(ArrayList<String> value) {
                mContents = new ArrayList<CardDataModel>();
                int images[] = {R.drawable.one, R.drawable.two, R.drawable.four, R.drawable.three};


                for(int i = 0; i < names.size(); i++){
                    CardDataModel cardDataModel = new CardDataModel();
                    cardDataModel.images = images[i];
                    cardDataModel.names = names.get(i);
                    mContents.add(cardDataModel);
                }
                mAdapter = new Pager(mContents, MainActivity.this);
                mViewPAger = (ViewPager) findViewById(R.id.viewPager);
                mViewPAger.setAdapter(mAdapter);
            }
        });

    }
    public void checkUserSex(MyCallback callback){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Users/Male");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    names.add(ds.child("name").getValue().toString());
                }
                callback.onCallback(names);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void logoutUser(View view){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, RegistrationOrLoginActivity.class);
        startActivity(intent);
        finish();
        return;

    }

    public interface MyCallback {
        void onCallback(ArrayList<String> value);
    }

}
