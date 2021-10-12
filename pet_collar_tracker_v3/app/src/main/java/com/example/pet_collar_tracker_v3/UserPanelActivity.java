package com.example.pet_collar_tracker_v3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class UserPanelActivity extends AppCompatActivity {

    String devID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_panel);

        Button userDeviceLocationHistoryBtn = (Button) findViewById(R.id.deviceLocationHistoryButton);
        Button userDeviceCurrentLocationBtn = (Button) findViewById(R.id.deviceCurrentLocationButton);
        Button userComplaintFormButton = (Button) findViewById(R.id.complaintFormButton);
        TextView currentUserTextView = (TextView) findViewById(R.id.currentUserTextView);

        Intent intent  = getIntent();
        String uName = intent.getStringExtra("userName");
        devID = intent.getStringExtra("deviceCodes");

        Toast.makeText(UserPanelActivity.this, devID + " selected.", Toast.LENGTH_SHORT).show();

        currentUserTextView.setText("Currently logged as " + uName); //TODO get the user list and show user name - Done

        userDeviceLocationHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchUserDeviceCurrentLocationHistoryActivity(devID);
            }
        });

        userDeviceCurrentLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchUserDeviceCurrentLocationActivity(devID);
            }
        });

        userComplaintFormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userComplaintIntent = new Intent(UserPanelActivity.this, ComplaintFormActivity.class);
                startActivity(userComplaintIntent);
            }
        });
    }

    public void launchUserDeviceCurrentLocationHistoryActivity(String devID){
        Intent userDeviceLocationHistoryIntent = new Intent(this, UserDeviceLocationHistoryActivity.class);
        userDeviceLocationHistoryIntent.putExtra("deviceCodes", devID);
        startActivity(userDeviceLocationHistoryIntent);
    }

    public void launchUserDeviceCurrentLocationActivity(String devID){
        Intent userDeviceCurrentLocationIntent = new Intent(this, UserDeviceCurrentLocationActivity.class);
        userDeviceCurrentLocationIntent.putExtra("deviceCodes", devID);
        startActivity(userDeviceCurrentLocationIntent);
    }
}