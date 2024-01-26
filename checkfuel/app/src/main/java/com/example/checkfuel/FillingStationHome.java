package com.example.checkfuel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FillingStationHome extends AppCompatActivity {

    TextView filling_details, fuel_availability, profile;
    String fillingStationName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filling_station_home);

        filling_details = findViewById(R.id.Fd_Home_Filling_Detail);
        profile = findViewById(R.id.Fd_Home_Profile);
        fuel_availability = findViewById(R.id.Fd_Home_Fuel_Avalibility);

        fillingStationName = getIntent().getStringExtra("FILLING_STATION_NAME");

        filling_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FillingStationHome.this, FillingDetails.class);
                intent.putExtra("FILLING_STATION_NAME", fillingStationName);
                startActivity(intent);
            }
        });

        fuel_availability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),StationFuelList.class));
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),FillingStationProfile.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
