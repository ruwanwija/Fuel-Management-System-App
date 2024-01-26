package com.example.checkfuel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.firebase.auth.FirebaseUser;

public class JourneyManagementSystem extends AppCompatActivity {

    ImageView Back, Home;
    EditText fuel,consumption;

    TextView submit_btn,textView,find_btn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_management_system);
        fuel=findViewById(R.id.fuel);
        consumption=findViewById(R.id.consumption);
        submit_btn=findViewById(R.id.submit_btn);
        find_btn=findViewById(R.id.find_btn);
        textView=findViewById(R.id.textView);

        find_btn.setVisibility(View.GONE);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!fuel.getText().equals("")) {
                    if (!consumption.getText().equals("")) {
                        textView.setText("Max : "+(Double.parseDouble(consumption.getText().toString())*Double.parseDouble(fuel.getText().toString()))+" km ");
                        if((Double.parseDouble(consumption.getText().toString())*Double.parseDouble(fuel.getText().toString()))<31){
                            find_btn.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Toast.makeText(JourneyManagementSystem.this, "Enter Fuel consumption",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(JourneyManagementSystem.this, "Enter fuel quantity",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        find_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NearestFillingStation.class);
                startActivity(intent);
                finish();
            }
        });

        Home=findViewById(R.id.User_Journey_Management_System_Home);
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Back=findViewById(R.id.User_Journey_Management_System_Back);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserHome.class);
                startActivity(intent);
                finish();
            }
        });



    }
}