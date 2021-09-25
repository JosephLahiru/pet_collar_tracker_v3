package com.example.pet_collar_tracker_v3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText userName, Pwd;
    Button Login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

<<<<<<< Updated upstream
=======
        userName = findViewById(R.id.username_data);
        Pwd = findViewById(R.id.user_password);
        Login_btn = findViewById(R.id.sign_in_button);


        Login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userName.equals("") || Pwd.equals("")) {
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
>>>>>>> Stashed changes
    }


}