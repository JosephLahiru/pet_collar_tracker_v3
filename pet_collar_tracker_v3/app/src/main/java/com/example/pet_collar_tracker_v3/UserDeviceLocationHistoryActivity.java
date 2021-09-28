package com.example.pet_collar_tracker_v3;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Arrays;

public class UserDeviceLocationHistoryActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_device_location_history);

        TableLayout tl = (TableLayout) findViewById(R.id.tableLayout4);
        TextView pastDataTxt = (TextView) findViewById(R.id.retrieveUserDataTextView);

        Intent intent  = getIntent();
        String devID = intent.getStringExtra("deviceCodes");

        int deviceId = Integer.parseInt(devID.split("v")[1]);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Location");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    pastDataTxt.setText("RETRIEVE PAST DATA : Dev" + deviceId);

                    String databaseLatitudeString = dataSnapshot.child("Dev" + deviceId).child("latitude").getValue().toString().substring(1, dataSnapshot.child("Dev" + deviceId).child("latitude").getValue().toString().length() - 1);
                    String databaseLongitudeString = dataSnapshot.child("Dev" + deviceId).child("longitude").getValue().toString().substring(1, dataSnapshot.child("Dev" + deviceId).child("longitude").getValue().toString().length() - 1);
                    String databaseTimeString = dataSnapshot.child("Dev" + deviceId).child("time").getValue().toString().substring(1, dataSnapshot.child("Dev" + deviceId).child("time").getValue().toString().length() - 1);

                    String[] stringLat = databaseLatitudeString.split(", ");
                    Arrays.sort(stringLat);

                    String[] stringLong = databaseLongitudeString.split(", ");
                    Arrays.sort(stringLong);

                    String[] stringTime = databaseTimeString.split(", ");
                    Arrays.sort(stringTime);

                    String[] latitude = Arrays.copyOfRange(stringLat, stringLat.length - 25, stringLat.length - 1);
                    String[] longitude = Arrays.copyOfRange(stringLong, stringLong.length - 25, stringLong.length - 1);
                    String[] time = Arrays.copyOfRange(stringTime, stringTime.length - 25, stringTime.length - 1);

                    TextView[] textArray = new TextView[latitude.length];
                    TableRow[] tr_head = new TableRow[latitude.length];

                    cleanTable(tl);

                    for (int j = 0; j < latitude.length; j++) {
                        String curr_lat = latitude[j].split("=")[1];
                        String curr_long = longitude[j].split("=")[1];
                        String curr_time = time[j].split("=")[1];

                        String[] data = new String[]{"", curr_long, curr_lat, curr_time};

                        tr_head[j] = new TableRow(UserDeviceLocationHistoryActivity.this);
                        tr_head[j].setId(j + 1);
                        tr_head[j].setLayoutParams(new TableLayout.LayoutParams(
                                TableLayout.LayoutParams.MATCH_PARENT,
                                TableLayout.LayoutParams.WRAP_CONTENT
                        ));

                        for (int h = 0; h < data.length; h++) {
                            textArray[h] = new TextView(UserDeviceLocationHistoryActivity.this);
                            textArray[h].setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            textArray[h].setId(j + 111 + h);
                            textArray[h].setText(data[h]);
                            //textArray[h].setWidth(120);
                            textArray[h].setTextSize(15);
                            tr_head[j].addView(textArray[h]);
                        }

                        tl.addView(tr_head[j], new TableLayout.LayoutParams(
                                TableLayout.LayoutParams.MATCH_PARENT,
                                TableLayout.LayoutParams.WRAP_CONTENT
                        ));
                    }
                } catch (Exception e) {
                    Toast.makeText(UserDeviceLocationHistoryActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void cleanTable(TableLayout table) {

        int childCount = table.getChildCount();

        if (childCount > 1) {
            table.removeViews(1, childCount - 1);
        }
    }
}