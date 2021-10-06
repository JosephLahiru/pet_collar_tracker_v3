package com.example.pet_collar_tracker_v3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class UserManagement extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference db;
    UserList_Adaptor userList_adaptor;
    ArrayList<UserList> list;
    FloatingActionButton addUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);

        recyclerView = findViewById(R.id.userList);
        addUser = findViewById(R.id.usrManage_addUser);
        db = FirebaseDatabase.getInstance().getReference("Users");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        userList_adaptor = new UserList_Adaptor(this, list);
        recyclerView.setAdapter(userList_adaptor);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    UserList userList = new UserList();
                    UserList userList = dataSnapshot.getValue(UserList.class);
                    String usrID = dataSnapshot.getKey();
                    userList.setUsrID(usrID);
//                    userList.setUsrName(dataSnapshot.child("usrName").getValue(String.class));
//                    userList.setUsrType(dataSnapshot.child("usrType").getValue(String.class));
//                    userList.setDeviceCodes((List<String>) dataSnapshot.child("deviceCodes").getValue());
                    Log.d("firebase", String.valueOf(Objects.requireNonNull(usrID)));

                    if (userList.getUsrType() != null && (userList.getUsrType().equals("admin") || userList.getUsrType().equals("Deleted"))){
                        Log.d("firebase", String.valueOf(Objects.requireNonNull(dataSnapshot.getValue())));
                    }
                    else {
                        Log.d("firebase", String.valueOf(Objects.requireNonNull(dataSnapshot.getValue())));
                        list.add(userList);


                    }
                }

                userList_adaptor.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUp = new Intent(UserManagement.this,SignUpActivity.class);
                startActivity(signUp);
            }
        });

    }
}