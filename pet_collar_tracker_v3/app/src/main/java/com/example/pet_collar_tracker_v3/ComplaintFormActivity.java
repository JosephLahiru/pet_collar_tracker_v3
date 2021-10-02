package com.example.pet_collar_tracker_v3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ComplaintFormActivity extends AppCompatActivity {

    EditText nameEditTxt, deviceNoEditTxt, contactNoEditTxt, emailEditTxt, issueEditText;
    Button submitButton;
    DatabaseReference dbRef;
    Complains complains;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_form);

        nameEditTxt = (EditText) findViewById(R.id.editTextTextPersonName);
        deviceNoEditTxt = (EditText) findViewById(R.id.editTextTextDeviceNo);
        contactNoEditTxt = (EditText) findViewById(R.id.editTextTextContactNo);
        emailEditTxt = (EditText) findViewById(R.id.editTextTexEmail);
        issueEditText = (EditText) findViewById(R.id.editTextTextIssue);

        submitButton = (Button) findViewById(R.id.submitButton);

        complains = new Complains();

        dbRef= FirebaseDatabase.getInstance().getReference().child("Complains");

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                complains.setName(nameEditTxt.getText().toString().trim());
                complains.setDeviceNo(deviceNoEditTxt.getText().toString().trim());
                complains.setContactNo(contactNoEditTxt.getText().toString().trim());
                complains.setEmail(emailEditTxt.getText().toString().trim());
                complains.setIssue(issueEditText.getText().toString().trim());

                dbRef.push().setValue(complains);

                Toast.makeText(ComplaintFormActivity.this, "Complain submission success.", Toast.LENGTH_SHORT).show();
                nameEditTxt.getText().clear();
                deviceNoEditTxt.getText().clear();
                contactNoEditTxt.getText().clear();
                emailEditTxt.getText().clear();
                issueEditText.getText().clear();
            }
        });
    }
}