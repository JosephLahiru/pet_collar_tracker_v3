package com.example.pet_collar_tracker_v3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

public class UserDetailsEditActivity extends AppCompatActivity {

    TextView usrName,usrDeviceCode,usrEmail;
    Button editDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details_edit);

        usrName = (EditText) findViewById(R.id.usrDetailsEdit_usrName);
        usrDeviceCode = (EditText) findViewById(R.id.usrDetailsEdit_deviceCode);
        editDetails = (Button)findViewById(R.id.usrDetailsEdit_UpdateBtn);
        usrEmail = (TextView) findViewById(R.id.usrDetailsEdit_email); 


        Intent intent  = getIntent();
        String uID = intent.getStringExtra("usrID");
        String uName = intent.getStringExtra("usrName");
        String uDeviceCodes = intent.getStringExtra("usrDevices");
        String uEmail = intent.getStringExtra("usrEmail");

        usrName.setText(uName);
        usrDeviceCode.setText(uDeviceCodes);
        usrEmail.setText(uEmail);


        editDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String editUsrName = usrName.getText().toString();
                String editusrDeviceCode = usrDeviceCode.getText().toString();

                if (editUsrName.equals("") || editusrDeviceCode.equals("")) {
                    Toast.makeText(UserDetailsEditActivity.this, "Please enter values for all fields.", Toast.LENGTH_SHORT).show();
                }
                else {

                    FirebaseDatabase.getInstance().getReference().child("Users").child(uID).child("usrName").setValue(editUsrName);
                    FirebaseDatabase.getInstance().getReference().child("Users").child(uID).child("deviceCodes").child("0").setValue(editusrDeviceCode);
                    Toast.makeText(UserDetailsEditActivity.this, "User Details Updated.", Toast.LENGTH_SHORT).show();


                }
            }
        });
    }
}