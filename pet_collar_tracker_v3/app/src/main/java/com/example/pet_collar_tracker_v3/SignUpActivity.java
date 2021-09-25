package com.example.pet_collar_tracker_v3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    EditText field_usrName, field_email, field_d_code, field_pwd, field_conPwd;
    Button field_signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        field_usrName = findViewById(R.id.signUp_username);
        field_email = findViewById(R.id.signUp_email);
        field_d_code = findViewById(R.id.signUp_device_code);
        field_pwd = findViewById(R.id.signUp_password);
        field_conPwd = findViewById(R.id.signUp_Conf_Password);
        field_signUp = findViewById(R.id.signUp_btn);

        mAuth = FirebaseAuth.getInstance();


        field_signUp.setOnClickListener(view -> {

            String email = this.field_email.getText().toString();
            String device_code = field_d_code.getText().toString();
            String pass = field_pwd.getText().toString();
            String confirmPass = field_conPwd.getText().toString();

            if (email.equals("") || pass.equals("") || confirmPass.equals("") || device_code.equals("")){
                Toast.makeText(SignUpActivity.this, "Please enter values for all fields.", Toast.LENGTH_SHORT).show();
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                Toast.makeText(SignUpActivity.this, "Please enter valid email address.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}