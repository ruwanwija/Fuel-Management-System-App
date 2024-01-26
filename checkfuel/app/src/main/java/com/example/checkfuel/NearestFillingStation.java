package com.example.checkfuel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import android.Manifest;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.location.Location;
import android.location.LocationManager;
import android.widget.TextView;


public class NearestFillingStation extends AppCompatActivity implements OnMapReadyCallback {

    ImageView Back, Home;
    TextView list;
    GoogleMap googleMap;
    double my_longitude;
    double my_latitude;

    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearest_filling_station);
        list = findViewById(R.id.stationsList);

        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), StationsList.class);
                i.putExtra("my_latitude", my_latitude + "");
                i.putExtra("my_longitude", my_longitude + "");
                startActivity(i);
            }
        });

        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            my_latitude = location.getLatitude();
                            my_longitude = location.getLongitude();
                        }
                    }
                });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.general_map_view);
        mapFragment.getMapAsync(this);

        Home = findViewById(R.id.general_map_view_home);
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Back = findViewById(R.id.general_map_view_back);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserHome.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private List<FillingStationInfo> getFillingStationsFromDatabase() {
        List<FillingStationInfo> fillingStations = new ArrayList<>();

        DatabaseReference fillingStationsRef = FirebaseDatabase.getInstance().getReference().child("users");

        fillingStationsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.child("user_type").getValue().equals("station")) {
                        String Station = snapshot.child("Station").getValue(String.class);
                        Double latitude = snapshot.child("latitude").getValue(Double.class);
                        Double longitude = snapshot.child("longitude").getValue(Double.class);

                        if (calculateDistance(latitude, longitude, my_latitude, my_longitude) < 20) {
                            LatLng location = new LatLng(latitude, longitude);
                            googleMap.addMarker(new MarkerOptions().position(location).title(Station));
                        }
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error here
            }
        });

        return fillingStations;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;

        // Set the initial camera position to Sri Lanka
        LatLng sriLanka = new LatLng(7.8731, 80.7718);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sriLanka, 7));
        googleMap.getUiSettings().setMapToolbarEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);

        // Retrieve and add filling stations to the map
        List<FillingStationInfo> fillingStations = getFillingStationsFromDatabase();

        if (googleMap != null) {
            for (FillingStationInfo station : fillingStations) {
                LatLng location = station.getLocation();
                String username = station.getUsername();

                googleMap.addMarker(new MarkerOptions().position(location).title(username));
            }
        }
    }

    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return 6371 * c; // Distance in kilometers
    }
}
