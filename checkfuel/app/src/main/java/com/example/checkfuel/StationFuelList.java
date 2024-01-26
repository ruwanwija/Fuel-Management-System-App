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
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StationFuelList extends AppCompatActivity {

    private RecyclerView recview;
    private StationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_fuel_list);
        SharedPreferences preferences = getSharedPreferences("user_data", Context.MODE_PRIVATE);

        recview=(RecyclerView) findViewById(R.id.recyclerview);
        recview.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<FuelDetails> dataList = new ArrayList<>();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Filling_Details").child(preferences.getString("id", null));
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    FuelDetails model= new FuelDetails(snapshot.getKey().toString(),snapshot.child("station").getValue().toString(),snapshot.child("name").getValue().toString(),Double.parseDouble(snapshot.child("price").getValue().toString()),snapshot.child("availability").getValue().toString());

                    dataList.add(model);
                }

                adapter = new StationAdapter(dataList,preferences);

                adapter.notifyDataSetChanged();
                recview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }
}