package com.example.apud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPAger;

    private Pager mAdapter;

    private FirebaseAuth mAuth;

    private ArrayList<CardDataModel> mContents;

    public ArrayList<String> names = new ArrayList<String>(Arrays.asList());
    public ArrayList<String> images = new ArrayList<String>(Arrays.asList());

    final String user = FirebaseAuth.getInstance().getCurrentUser().getUid();

    private String userSex, oppositeSex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        FirebaseApp.initializeApp(this);

        checkUserSex(new MyCallback() {
            @Override
            public void onCallback(ArrayList<String> value) {
                mContents = new ArrayList<CardDataModel>();
                for(int i =0; i < 7; i++){
                    mContents.add(new CardDataModel());
                }

                int upper = 6;
                int lower = 0;

                for(int i = 0; i < images.size(); i++){
                    if(i != 3) {
                        CardDataModel cardDataModel = new CardDataModel();
                        cardDataModel.images = images.get(i);
                        cardDataModel.names = names.get(i);
                        mContents.set(lower, cardDataModel);
                        mContents.set(upper, cardDataModel);
                        upper--;
                        lower++;
                    }
                    else{
                        CardDataModel cardDataModel = new CardDataModel();
                        cardDataModel.images = images.get(i);
                        cardDataModel.names = names.get(i);
                        mContents.set(3, cardDataModel);
                    }
                }

                mAdapter = new Pager(mContents, MainActivity.this);
                mViewPAger = (ViewPager) findViewById(R.id.viewPager);

                mViewPAger.setAdapter(mAdapter);
                mViewPAger.setCurrentItem(3);
            }
        });

    }

    public void checkUserSex(MyCallback callback){
        setUserGender(new MyCallback() {
            @Override
            public void onCallback(ArrayList<String> value) {
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("Users/" + oppositeSex);
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds: dataSnapshot.getChildren()) {
                            names.add(ds.child("name").getValue().toString());
                            images.add(ds.child("profile-pic").getValue().toString());
                        }
                        callback.onCallback(names);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }
    public void setUserGender(MyCallback callback){
        final FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        DatabaseReference ref1 = database1.getReference("Users");
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //names.add(ds.child("Male").child(user).getKey());
                if (dataSnapshot.child("Male").hasChild(user)){
                    userSex = "Male";
                    oppositeSex = "female";
                }
                else{
                    userSex = "female";
                    oppositeSex = "Male";
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
