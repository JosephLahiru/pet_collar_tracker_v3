package com.example.pet_collar_tracker_v3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPwdActivity extends AppCompatActivity {

    EditText email;
    Button resetBtn;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd);

        email = (EditText) findViewById(R.id.reset_email);
        resetBtn = (Button) findViewById(R.id.reset_Btn);

        auth = FirebaseAuth.getInstance();

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPwd();
            }
        });

    }

    private void resetPwd() {

        String resetemail = email.getText().toString();

        if (resetemail.isEmpty()){
            Toast.makeText(ResetPwdActivity.this, "Please enter email address.", Toast.LENGTH_SHORT).show();

        }

        else if (!Patterns.EMAIL_ADDRESS.matcher(resetemail).matches()){
            Toast.makeText(ResetPwdActivity.this, "Please enter valid email address.", Toast.LENGTH_SHORT).show();
        }

        auth.sendPasswordResetEmail(resetemail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ResetPwdActivity.this, "Check your email to reset your password!.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(ResetPwdActivity.this, "Something wrong. Please try again!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}