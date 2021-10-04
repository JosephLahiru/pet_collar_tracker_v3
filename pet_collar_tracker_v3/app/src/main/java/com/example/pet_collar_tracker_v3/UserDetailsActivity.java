package com.example.pet_collar_tracker_v3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class UserDetailsActivity extends AppCompatActivity {

    TextView usrName,usrEmail,usrDeviceCode;
    ArrayList<UserList> userDetailList;
    Button editDetails,deleteUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        usrName = findViewById(R.id.usrDetails_usrName);
        usrEmail = findViewById(R.id.usrDetails_email);
        usrDeviceCode = findViewById(R.id.usrDetails_deviceCode);
        editDetails = findViewById(R.id.usrDetails_EditBtn);

        deleteUser = findViewById(R.id.usrDetails_DeleteBtn);

        Intent intent  = getIntent();
        String uID = intent.getStringExtra("usrID");
        String uName = intent.getStringExtra("usrName");
        String uDeviceCodes = intent.getStringExtra("usrDevices");

//        admin.auth().getUser(uid);
        String currentUser = FirebaseAuth.getInstance().getUid();
        Log.d("CurrentUser",currentUser);


//        usrName.setText(user.usrName);
//        usrEmail.setText();
        String uEmail = null;



        usrName.setText(uName);
        usrDeviceCode.setText(uDeviceCodes);
        usrEmail.setText(uEmail);

        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(UserDetailsActivity.this)
                        .setTitle("Are you Sure?")
                        .setMessage("Deleting user account will result completely removing user account from the system and result cannot be undone!");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference("Users").child("usrType").setValue("Deleted");
                        Toast.makeText(UserDetailsActivity.this, "User Deleted.", Toast.LENGTH_SHORT).show();

                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

            }
        });

        editDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDetailsActivity.this,UserDetailsEditActivity.class);
                String.valueOf(intent.putExtra("usrID",uID));
                String.valueOf(intent.putExtra("usrName",uName));
                String.valueOf(intent.putExtra("usrDevices",uDeviceCodes));
                startActivity(intent);

            }
        });

    }
}