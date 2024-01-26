package com.example.checkfuel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.checkfuel.classes.Station;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StationFuel extends AppCompatActivity {

    private RecyclerView recview;
    private AdminStationAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_fuel);

        recview=(RecyclerView) findViewById(R.id.recyclerview4);
        recview.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<Station> dataList = new ArrayList<>();

        DatabaseReference fillingStationsRef = FirebaseDatabase.getInstance().getReference().child("users");
        fillingStationsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    if(snapshot.child("user_type").getValue().equals("station")) {

                        Station model = new Station(snapshot.getKey().toString(), snapshot.child("Station").getValue().toString(), snapshot.child("Location").getValue().toString(), snapshot.child("contactNo").getValue().toString(), snapshot.child("email").getValue().toString(), snapshot.child("PemitID").getValue().toString());

                        dataList.add(model);
                    }
                }

                adapter = new AdminStationAdapter(dataList);

                adapter.notifyDataSetChanged();
                recview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}