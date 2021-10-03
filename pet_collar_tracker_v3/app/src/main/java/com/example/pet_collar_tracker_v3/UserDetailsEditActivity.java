package com.example.pet_collar_tracker_v3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class UserDetailsEditActivity extends AppCompatActivity {

    TextView usrName,usrDeviceCode;
    Button editDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details_edit);



        Intent intent  = getIntent();
        String uID = intent.getStringExtra("usrID");
        String uName = intent.getStringExtra("usrName");
        String uDeviceCodes = intent.getStringExtra("usrDevices");
    }
}