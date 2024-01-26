package com.example.checkfuel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FillingStationProfile extends AppCompatActivity {

    TextView name, Email, ContactNo, station,location,permitID;

    ImageView back, home;

    private FirebaseUser fillingstation;

    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filling_station_profile);

        fillingstation = FirebaseAuth.getInstance().getCurrentUser();

        if (fillingstation == null) {
            // If not authenticated, redirect to login screen
            Intent intent = new Intent(this, FillingStationLogin.class);
            startActivity(intent);
            finish();
        } else {
            // If authenticated, continue with profile display
            reference = FirebaseDatabase.getInstance().getReference("Filling_Station");
            name = findViewById(R.id.FS_Profile_Name);
            Email = findViewById(R.id.FS_Profile_Email);
            station=findViewById(R.id.FS_Profile_Filling_Station);
            permitID=findViewById(R.id.FS_Profile_PermitID);
            location=findViewById(R.id.FS_Profile_Location);
            ContactNo = findViewById(R.id.FS_Profile_ContactNo);
            back=findViewById(R.id.FS_Profile_Back);
            home=findViewById(R.id.FS_Profile_Home);

            showFillingStationData();
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),FillingStationHome.class);
                startActivity(intent);
                finish();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void showFillingStationData() {
        // Retrieve data from Intent
        Intent intent = getIntent();
        String receivedUsername = intent.getStringExtra("username");
        String receivedEmail = intent.getStringExtra("email");
        String receivedLocation=intent.getStringExtra("Location");
        String receivedPermitId=intent.getStringExtra("PermitID");
        String receivedStation=intent.getStringExtra("Station");
        String receivedContactNo = intent.getStringExtra("contactNo");

        // Set data to TextViews
        name.setText(receivedUsername);
        Email.setText(receivedEmail);
        ContactNo.setText(receivedContactNo);
        station.setText(receivedStation);
        permitID.setText(receivedPermitId);
        location.setText(receivedLocation);
    }

}