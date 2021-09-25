package com.example.pet_collar_tracker_v3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LocationManagementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_management);

        Button deviceLiveLocationBtn = (Button) findViewById(R.id.deviceLiveLocationsButton);
        Button deviceHistoryLocationBtn = (Button) findViewById(R.id.deviceHistoryLocationButton);

        deviceLiveLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchLiveLocationAct();
            }
        });

        deviceHistoryLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHistoryLocationAct();
            }
        });
    }

    public void launchLiveLocationAct(){
        Intent liveLocationIntent = new Intent(this, MapsActivity.class);
        startActivity(liveLocationIntent);
    }
    public void launchHistoryLocationAct(){
        Intent historyLocationIntent = new Intent(this, HistoryLocationActivity.class);
        startActivity(historyLocationIntent);
    }
}