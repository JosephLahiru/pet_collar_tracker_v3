package com.example.pet_collar_tracker_v3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserPanelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_panel);

        Button userDeviceLocationHistoryBtn = (Button) findViewById(R.id.deviceLocationHistoryButton);
        Button userDeviceCurrentLocationBtn = (Button) findViewById(R.id.deviceCurrentLocationButton);
        TextView currentUserTextView = (TextView) findViewById(R.id.currentUserTextView);

        currentUserTextView.setText("Currently logged as Karen"); //TODO get the user list and show user name

        userDeviceLocationHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchUserDeviceCurrentLocationHistoryActivity();
            }
        });

        userDeviceCurrentLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchUserDeviceCurrentLocationActivity();
            }
        });
    }

    public void launchUserDeviceCurrentLocationHistoryActivity(){
        Intent userDeviceLocationHistoryIntent = new Intent(this, UserDeviceLocationHistoryActivity.class);
        startActivity(userDeviceLocationHistoryIntent);
    }

    public void launchUserDeviceCurrentLocationActivity(){
        Intent userDeviceCurrentLocationIntent = new Intent(this, UserDeviceCurrentLocationActivity.class);
        startActivity(userDeviceCurrentLocationIntent);
    }
}