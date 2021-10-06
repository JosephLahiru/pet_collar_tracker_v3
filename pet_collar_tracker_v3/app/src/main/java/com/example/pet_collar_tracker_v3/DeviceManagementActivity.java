package com.example.pet_collar_tracker_v3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class DeviceManagementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_management);

        TableLayout tl = (TableLayout)findViewById(R.id.tableLayout2);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Devices");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try{
                    String databaseDevicesString = snapshot.getValue().toString().substring(1, snapshot.getValue().toString().length() - 1);

                    //Toast.makeText(DeviceManagementActivity.this, databaseDevicesString, Toast.LENGTH_SHORT).show();

                    String[] Devices = databaseDevicesString.split(", ");

                    TextView[] textArray = new TextView[Devices.length];
                    TableRow[] tr_head = new TableRow[Devices.length];

                    cleanTable(tl);

                    for(int i=0; i<Devices.length; i++){
                        String curr_dev = Devices[i].split("=")[0];

                        String[] data = new String[]{curr_dev};

                        tr_head[i] = new TableRow(DeviceManagementActivity.this);
                        tr_head[i].setId(i+1);
                        tr_head[i].setLayoutParams(new TableLayout.LayoutParams(
                                TableLayout.LayoutParams.MATCH_PARENT,
                                TableLayout.LayoutParams.WRAP_CONTENT
                        ));

                        for(int h = 0; h < data.length; h++) {
                            textArray[h] = new TextView(DeviceManagementActivity.this);
                            textArray[h].setTextSize(30);
                            textArray[h].setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            textArray[h].setId(i + 111 + h);
                            textArray[h].setText(data[h]);
//                            textArray[h].setGravity(View.TEXT_ALIGNMENT_CENTER);
                            textArray[h].setWidth(1070);
                            tr_head[i].addView(textArray[h]);
                        }

                        tl.addView(tr_head[i], new TableLayout.LayoutParams(
                                TableLayout.LayoutParams.MATCH_PARENT,
                                TableLayout.LayoutParams.WRAP_CONTENT
                        ));
                    }

                }catch (Exception e){
                    //Toast.makeText(DeviceManagementActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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