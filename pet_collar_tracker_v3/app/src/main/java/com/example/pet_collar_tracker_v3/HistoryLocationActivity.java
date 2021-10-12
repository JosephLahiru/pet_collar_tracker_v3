package com.example.pet_collar_tracker_v3;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
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

public class HistoryLocationActivity extends FragmentActivity {

    boolean DEBUG = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_location);

        TableLayout tl = (TableLayout) findViewById(R.id.tableLayout3);
        EditText deviceIdEditTxt = (EditText) findViewById(R.id.editTextCollarId);
        Button deviceIdButton = (Button) findViewById(R.id.chooseCollarButton);
        TextView pastDataTxt = (TextView) findViewById(R.id.retrieveDataTextView);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Location");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {

                    deviceIdButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String deviceId = deviceIdEditTxt.getText().toString();
                            boolean isNumber = TextUtils.isDigitsOnly(deviceId);

                            if(isNumber){

                                if(Long.parseLong(deviceId) < dataSnapshot.getChildrenCount()){
                                    pastDataTxt.setText("RETRIEVE PAST DATA : Dev" + deviceId);

                                    //TODO fix the limitation error - if the count went higher than 4 - DONE
                                    //TODO limit input for only numbers - DONE
                                    //Toast.makeText(HistoryLocationActivity.this, Long.toString(dataSnapshot.getChildrenCount()), Toast.LENGTH_SHORT).show();

                                    String databaseLatitudeString = dataSnapshot.child("dev" + deviceId).child("latitude").getValue().toString().substring(1, dataSnapshot.child("dev" + deviceId).child("latitude").getValue().toString().length() - 1);
                                    String databaseLongitudeString = dataSnapshot.child("dev" + deviceId).child("longitude").getValue().toString().substring(1, dataSnapshot.child("dev" + deviceId).child("longitude").getValue().toString().length() - 1);
                                    String databaseTimeString = dataSnapshot.child("dev" + deviceId).child("time").getValue().toString().substring(1, dataSnapshot.child("dev" + deviceId).child("time").getValue().toString().length() - 1);

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

                                        tr_head[j] = new TableRow(HistoryLocationActivity.this);
                                        tr_head[j].setId(j + 1);
                                        tr_head[j].setLayoutParams(new TableLayout.LayoutParams(
                                                TableLayout.LayoutParams.MATCH_PARENT,
                                                TableLayout.LayoutParams.WRAP_CONTENT
                                        ));

                                        for (int h = 0; h < data.length; h++) {
                                            textArray[h] = new TextView(HistoryLocationActivity.this);
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
                                }else{
                                    Toast.makeText(HistoryLocationActivity.this, "There are only " + dataSnapshot.getChildrenCount() + " devices in the system.\n Index starts from 0.", Toast.LENGTH_SHORT).show();
                                }

                            }
                            else{
                                Toast.makeText(HistoryLocationActivity.this, "Enter Integers only !!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (Exception e) {
                    if(DEBUG) {
                        Toast.makeText(HistoryLocationActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
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