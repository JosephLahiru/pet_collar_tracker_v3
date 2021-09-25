package com.example.pet_collar_tracker_v3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText email, Pwd;
    Button Login_btn, forgotPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        email = findViewById(R.id.login_email);
        Pwd = findViewById(R.id.login_password);
        Login_btn = findViewById(R.id.loginButton);
        forgotPwd = findViewById(R.id.forgotPwd);


        Login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.equals("") || Pwd.equals("")) {
                    Toast.makeText(LoginActivity.this, "Please enter values for all fields.", Toast.LENGTH_SHORT).show();
                }
                /*else {
                    Boolean checkUserPass = database.authUser(user, pass);
                    if (checkUserPass) {
                        Intent intent = new Intent(getApplicationContext(), chat.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "Invalid credentials.", Toast.LENGTH_SHORT).show();
                    }*/
            }
        });

    }


}