package com.example.pet_collar_tracker_v3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.DialogInterface;
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

import java.util.Objects;

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
                else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(LoginActivity.this, "Please enter valid email address.", Toast.LENGTH_SHORT).show();
                }
                else{
                    mAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Task<DataSnapshot> userType = mDb.child("Users").child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).child("usrType").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        if(!task.isSuccessful()){
                                            Log.e("firebase", "Error getting data", task.getException());
                                        }
                                        else {
                                            Log.d("firebase", String.valueOf(Objects.requireNonNull(task.getResult()).getValue()));
                                            Log.d("firebase","Success!");

                                            if(String.valueOf(task.getResult().getValue()).equals("admin")) {
                                                startActivity(new Intent(LoginActivity.this, AdminPanel.class));
                                            }else if(String.valueOf(task.getResult().getValue()).equals("Deleted")){

                                                String uID = mAuth.getCurrentUser().getUid();
                                                mDb.child("Users").child(uID).removeValue();
                                                mAuth.getCurrentUser().delete();


                                                AlertDialog.Builder usrDeletedDialog = new AlertDialog.Builder(LoginActivity.this)
                                                        .setTitle("User Deleted")
                                                        .setMessage("Your user Account is Deleted. Please contact tech support!");
                                                usrDeletedDialog.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {

                                                        dialogInterface.dismiss();

                                                    }
                                                });

                                                AlertDialog alertDialog = usrDeletedDialog.create();
                                                alertDialog.show();
                                            }
                                            else{
                                                Intent userPanelIntent = new Intent(LoginActivity.this, UserPanelActivity.class);

                                                mDb.child("Users").child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).child("usrName").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                                        if (!task.isSuccessful()) {
                                                            Log.e("firebase", "Error getting data", task.getException());
                                                            Toast.makeText(LoginActivity.this, "Please Check your Internet Connectivity.", Toast.LENGTH_SHORT).show();
                                                        }
                                                        else {
                                                            Log.d("firebase", String.valueOf(task.getResult().getValue()));
                                                            userPanelIntent.putExtra("userName", String.valueOf(task.getResult().getValue()));
                                                            //startActivity(userPanelIntent);
                                                        }
                                                    }
                                                });
                                                mDb.child("Users").child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).child("deviceCodes").child("0").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                                        if (!task.isSuccessful()) {
                                                            Log.e("firebase", "Error getting data", task.getException());
                                                            Toast.makeText(LoginActivity.this, "Please Check your Internet Connectivity.", Toast.LENGTH_SHORT).show();
                                                        }
                                                        else {
                                                            Log.d("firebase", String.valueOf(task.getResult().getValue()));
                                                            userPanelIntent.putExtra("deviceCodes", String.valueOf(task.getResult().getValue()));
                                                            startActivity(userPanelIntent);
                                                        }
                                                    }
                                                });
                                                //userPanelIntent.putExtra("deviceCodes",mDb.child("Users").child(mAuth.getCurrentUser().getUid()).child("deviceCodes").child("0").get().toString());
                                                //TODO send user data to the other side - DONE
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
            }
        });

        forgotPwd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,ResetPwdActivity.class));

            }
        });
    }
}