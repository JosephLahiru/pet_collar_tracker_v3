package com.example.pet_collar_tracker_v3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class LoginActivity extends AppCompatActivity {

    EditText fieldEmail, fieldPwd;
    Button Login_btn, forgotPwd_btn;
    private FirebaseAuth mAuth;
    private DatabaseReference mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fieldEmail = findViewById(R.id.login_email);
        fieldPwd = findViewById(R.id.login_password);
        Login_btn = findViewById(R.id.loginButton);
        forgotPwd_btn = findViewById(R.id.forgotPwd);

        mAuth = FirebaseAuth.getInstance();

        mDb = FirebaseDatabase.getInstance().getReference();

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
                            Task<DataSnapshot> userType = mDb.child("Users").child(mAuth.getCurrentUser().getUid()).child("usrType").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    if(!task.isSuccessful()){
                                        Log.e("firebase", "Error getting data", task.getException());
                                    }
                                    else {
                                        Log.d("firebase", String.valueOf(task.getResult().getValue()));
                                        Log.d("firebase","Success!");


                                        if(String.valueOf(task.getResult().getValue()).equals("admin")) {
                                            startActivity(new Intent(LoginActivity.this, AdminPanel.class));
                                        }
                                        else{
                                            startActivity(new Intent(LoginActivity.this, UserPanelActivity.class));
                                        }
                                    }
                                }
                            });


                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Login failed. Please check credentials!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }


}