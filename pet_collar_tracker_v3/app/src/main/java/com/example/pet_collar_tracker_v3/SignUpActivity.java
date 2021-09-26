package com.example.pet_collar_tracker_v3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

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

            String usrName = field_usrName.getText().toString();
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

            mAuth.createUserWithEmailAndPassword(email,pass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                user user = new user(usrName, device_code,"user");

                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()){
                                            Toast.makeText(SignUpActivity.this, "User has been registered successfully", Toast.LENGTH_LONG).show();
                                        }
                                        else{
                                            Toast.makeText(SignUpActivity.this, "user registration failed. Try again!", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                            }
                            else{
                                Toast.makeText(SignUpActivity.this, "user registration failed. Try again!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

        });

    }
}