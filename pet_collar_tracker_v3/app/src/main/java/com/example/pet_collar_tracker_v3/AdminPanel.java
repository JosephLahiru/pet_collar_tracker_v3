package com.example.pet_collar_tracker_v3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

public class AdminPanel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        Button devManagementBtn = (Button)findViewById(R.id.deviceManagementButton);
        //Button

        devManagementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchDeviceManagerAct();
            }
        });

    }

    public void launchDeviceManagerAct(){
        Intent deviceManagementIntent = new Intent(this, DeviceManagementActivity.class);
        startActivity(deviceManagementIntent);
    }

}