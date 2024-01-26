package com.example.checkfuel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class UserHome extends AppCompatActivity {

    TextView nearest_filling_station,journey_management_system,profile;

    double latitude = 7.8731; // Default value, change if needed
    double longitude = 80.7718; // Default value, change if needed


    ImageView Back,Home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);


        journey_management_system = findViewById(R.id.User_Home_Journey_Management_System);
        profile=findViewById(R.id.User_Home_Profile);
        nearest_filling_station = findViewById(R.id.User_Home_Nearest_Filling_Station);


        nearest_filling_station.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserHome.this, NearestFillingStation.class);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                startActivity(intent);

            }
        });
        journey_management_system.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),JourneyManagementSystem.class);
                startActivity(intent);
                finish();
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usernameFromIntent = getIntent().getStringExtra("username");
                String emailFromIntent = getIntent().getStringExtra("email");
                String contactNoFromIntent = getIntent().getStringExtra("contactNo");
                String passwordFromIntent = getIntent().getStringExtra("password");

                Intent intent = new Intent(UserHome.this, UserProfile.class);
                intent.putExtra("username", usernameFromIntent);
                intent.putExtra("email", emailFromIntent);
                intent.putExtra("contactNo", contactNoFromIntent);
                intent.putExtra("password", passwordFromIntent);
                startActivity(intent);
            }
        });

        Home=findViewById(R.id.User_Home_Home);
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Back=findViewById(R.id.User_Home_Back);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), user_login.class);
                startActivity(intent);
                finish();
            }
        });
    }


}