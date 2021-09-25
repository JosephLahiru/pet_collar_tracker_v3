package com.example.pet_collar_tracker_v3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText fieldEmail, fieldPwd;
    Button Login_btn, forgotPwd_btn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        fieldEmail = findViewById(R.id.login_email);
        fieldPwd = findViewById(R.id.login_password);
        Login_btn = findViewById(R.id.loginButton);
        forgotPwd_btn = findViewById(R.id.forgotPwd);

        mAuth = FirebaseAuth.getInstance();

        Login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = fieldEmail.getText().toString().trim();
                String pwd = fieldPwd.getText().toString().trim();

                if (email.equals("") || pwd.equals("")) {
                    Toast.makeText(LoginActivity.this, "Please enter values for all fields.", Toast.LENGTH_SHORT).show();
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(LoginActivity.this, "Please enter valid email address.", Toast.LENGTH_SHORT).show();
                }

                mAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //redirect to user profile
                            startActivity(new Intent(LoginActivity.this,AdminPanel.class));
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Login failed. Please check credentials!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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