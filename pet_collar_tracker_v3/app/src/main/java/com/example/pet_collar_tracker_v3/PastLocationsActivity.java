package com.example.pet_collar_tracker_v3;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ThrowOnExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

public class PastLocationsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    boolean DEBUG = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_locations);

        Button gotoPastTable = (Button)findViewById(R.id.showTableButton);

        gotoPastTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openPastTableAct(); }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Location");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    mMap.clear();

                    String databaseLatitudeString = dataSnapshot.child("latitude").getValue().toString().substring(1, dataSnapshot.child("latitude").getValue().toString().length() - 1);
                    String databaseLongitudeString = dataSnapshot.child("longitude").getValue().toString().substring(1, dataSnapshot.child("longitude").getValue().toString().length() - 1);

                    String[] stringLat = databaseLatitudeString.split(", ");
                    Arrays.sort(stringLat);
                    String[] latitude = Arrays.copyOfRange(stringLat, stringLat.length-13, stringLat.length-1);

                    String[] stringLong = databaseLongitudeString.split(", ");
                    Arrays.sort(stringLong);
                    String[] longitude = Arrays.copyOfRange(stringLong, stringLong.length-13, stringLong.length-1);

                    for(int i=0; i<latitude.length; i++){
                        String curr_lat = latitude[i].split("=")[1];
                        String curr_long = longitude[i].split("=")[1];

                        LatLng latLng = new LatLng(Double.parseDouble(curr_lat), Double.parseDouble(curr_long));

                        mMap.addMarker(new MarkerOptions().position(latLng).title("Live Location at : " + latitude + " , " + longitude));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                    }

                } catch (Exception e) {
                    if(DEBUG) {
                        Toast.makeText(PastLocationsActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            long MIN_DIST = 5;
            long MIN_TIME = 1000;
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DIST, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DIST, locationListener);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void openPastTableAct(){
        Intent pastLocationTableIntent = new Intent(this, PastLocationsTableActivity.class);
        startActivity(pastLocationTableIntent);
    }
}