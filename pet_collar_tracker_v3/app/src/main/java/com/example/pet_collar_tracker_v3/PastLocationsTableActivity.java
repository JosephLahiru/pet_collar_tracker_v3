package com.example.pet_collar_tracker_v3;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
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

public class PastLocationsTableActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_locations_table);

        TableLayout tl = (TableLayout)findViewById(R.id.tableLayout1);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Location");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {

//                    String[] latitude, longitude, id, time;

                    String databaseLatitudeString = dataSnapshot.child("latitude").getValue().toString().substring(1, dataSnapshot.child("latitude").getValue().toString().length() - 1);
                    String databaseLongitudeString = dataSnapshot.child("longitude").getValue().toString().substring(1, dataSnapshot.child("longitude").getValue().toString().length() - 1);
                    String databaseIdString = dataSnapshot.child("id").getValue().toString().substring(1, dataSnapshot.child("id").getValue().toString().length()-1);
                    String databaseTimeString = dataSnapshot.child("time").getValue().toString().substring(1, dataSnapshot.child("time").getValue().toString().length()-1);

                    String[] stringLat = databaseLatitudeString.split(", ");
                    Arrays.sort(stringLat);

                    String[] stringLong = databaseLongitudeString.split(", ");
                    Arrays.sort(stringLong);

                    String[] stringId = databaseIdString.split(", ");
                    Arrays.sort(stringId);

                    String[] stringTime = databaseTimeString.split(", ");
                    Arrays.sort(stringTime);

                    String[] latitude = Arrays.copyOfRange(stringLat, stringLat.length-13, stringLat.length-1);
                    String[] longitude = Arrays.copyOfRange(stringLong, stringLong.length-13, stringLong.length-1);
                    String[] id = Arrays.copyOfRange(stringId, stringId.length-13, stringId.length-1);
                    String[] time = Arrays.copyOfRange(stringTime, stringTime.length-13, stringTime.length-1);

                    //TODO Optimize here
//                    if(stringLat.length < 13){
//                        latitude = Arrays.copyOfRange(stringLat, 0, stringLat.length-1);
//                        longitude = Arrays.copyOfRange(stringLong, 0, stringLong.length-1);
//                        id = Arrays.copyOfRange(stringId, 0, stringId.length-1);
//                        time = Arrays.copyOfRange(stringTime, 0, stringTime.length-1);
//
//                    }else{
//                        latitude = Arrays.copyOfRange(stringLat, stringLat.length-13, stringLat.length-1);
//                        longitude = Arrays.copyOfRange(stringLong, stringLong.length-13, stringLong.length-1);
//                        id = Arrays.copyOfRange(stringId, stringId.length-13, stringId.length-1);
//                        time = Arrays.copyOfRange(stringTime, stringTime.length-13, stringTime.length-1);
//                    }

                    TextView[] textArray = new TextView[latitude.length];
                    TableRow[] tr_head = new TableRow[latitude.length];

                    cleanTable(tl);

                    for(int i=0; i<latitude.length; i++){
                        String curr_lat = latitude[i].split("=")[1];
                        String curr_long = longitude[i].split("=")[1];
                        String curr_id = id[i].split("=")[1];
                        String curr_time = time[i].split("=")[1];

                        String[] data = new String[]{curr_id, curr_long, curr_lat, curr_time};

                        tr_head[i] = new TableRow(PastLocationsTableActivity.this);
                        tr_head[i].setId(i+1);
                        tr_head[i].setLayoutParams(new TableLayout.LayoutParams(
                                TableLayout.LayoutParams.MATCH_PARENT,
                                TableLayout.LayoutParams.WRAP_CONTENT
                        ));

                        for(int h = 0; h < data.length; h++) {
                            textArray[h] = new TextView(PastLocationsTableActivity.this);
                            textArray[h].setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            textArray[h].setId(i + 111 + h);
                            textArray[h].setText(data[h]);
                            tr_head[i].addView(textArray[h]);
                        }

                        tl.addView(tr_head[i], new TableLayout.LayoutParams(
                                TableLayout.LayoutParams.MATCH_PARENT,
                                TableLayout.LayoutParams.WRAP_CONTENT
                        ));
                    }

                } catch (Exception e) {
                    Toast.makeText(PastLocationsTableActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
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