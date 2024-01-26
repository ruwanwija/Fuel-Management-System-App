package com.example.checkfuel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.checkfuel.classes.FuelDetails;
import com.example.checkfuel.classes.Station;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StationsList extends AppCompatActivity {

    private RecyclerView recview;
    private StationViewAdapter adapter;
    double my_longitude=0;
    double my_latitude=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stations_list);

        recview=(RecyclerView) findViewById(R.id.recyclerview1);
        recview.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<Station> dataList = new ArrayList<>();

        Intent intent = getIntent();
        my_latitude=Double.parseDouble(intent.getStringExtra("my_latitude"));
        my_longitude=Double.parseDouble(intent.getStringExtra("my_longitude"));

        DatabaseReference fillingStationsRef = FirebaseDatabase.getInstance().getReference().child("users");
        fillingStationsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    if(snapshot.child("user_type").getValue().equals("station")) {
                        Double latitude = snapshot.child("latitude").getValue(Double.class);
                        Double longitude = snapshot.child("longitude").getValue(Double.class);

                        if(calculateDistance(latitude,longitude,my_latitude,my_longitude)<20) {

                            Station model = new Station(snapshot.getKey().toString(), snapshot.child("Station").getValue().toString(), snapshot.child("Location").getValue().toString(), snapshot.child("contactNo").getValue().toString(), snapshot.child("email").getValue().toString(), snapshot.child("PemitID").getValue().toString());

                            dataList.add(model);
                        }
                    }
                }

                adapter = new StationViewAdapter(dataList);

                adapter.notifyDataSetChanged();
                recview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

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