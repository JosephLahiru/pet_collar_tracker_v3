package com.example.pet_collar_tracker_v3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class UserDetailsActivity extends AppCompatActivity {

    TextView usrName,usrEmail,usrDeviceCode;
    Button editDetails;
    ArrayList<UserList> userDetaillist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        usrName = findViewById(R.id.usrDetails_usrName);
        usrEmail = findViewById(R.id.usrDetails_email);
        usrDeviceCode = findViewById(R.id.usrDetails_deviceCode);
        editDetails = findViewById(R.id.usrDetails_EditBtn);

        Intent intent  = getIntent();
        String uID = intent.getStringExtra("usrID");
        String uName = intent.getStringExtra("usrName");
        String uDeviceCodes = intent.getStringExtra("usrDevices");

//        admin.auth().getUser(uid);
        String currentUser = FirebaseAuth.getInstance().getUid();
        Log.d("CurrentUser",currentUser);


//        usrName.setText(user.usrName);
//        usrEmail.setText();

    }
}