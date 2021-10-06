package com.example.pet_collar_tracker_v3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity<options> extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button liveLocationBtn = (Button) findViewById(R.id.liveLocationButton);
        Button pastLocationsBtn = (Button) findViewById(R.id.pastLocationButton);

        liveLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLiveLocationAct();
            }
        });

        pastLocationsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPastLocationsAct();
            }
        });

    }
    public void  openLiveLocationAct(){
        Intent liveLocationIntent = new Intent(this, MapsActivity.class);
        startActivity(liveLocationIntent);
    }

    public void openPastLocationsAct(){
        Intent pastLocationsIntent = new Intent(this, PastLocationsActivity.class);
        startActivity(pastLocationsIntent);
    }




}