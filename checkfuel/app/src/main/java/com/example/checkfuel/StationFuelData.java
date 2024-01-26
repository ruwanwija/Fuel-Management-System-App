package com.example.checkfuel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.checkfuel.classes.FuelDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StationFuelData extends AppCompatActivity {

    private RecyclerView recview;
    private StationFuelAdapter adapter;
    String id;

    TextView feedback;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_fuel_data);

        recview=(RecyclerView) findViewById(R.id.recyclerview2);
        recview.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<FuelDetails> dataList = new ArrayList<>();

        feedback = findViewById(R.id.view_feedback_btn);

        Intent intent = getIntent();
        id=intent.getStringExtra("id");

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), Feedback.class);
                i.putExtra("id", id );
                view.getContext().startActivity(i);
            }
        });

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Filling_Details").child(id);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    FuelDetails model= new FuelDetails(snapshot.getKey().toString(),snapshot.child("station").getValue().toString(),snapshot.child("name").getValue().toString(),Double.parseDouble(snapshot.child("price").getValue().toString()),snapshot.child("availability").getValue().toString());

                    dataList.add(model);
                }

                adapter = new StationFuelAdapter(dataList);

                adapter.notifyDataSetChanged();
                recview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }
}