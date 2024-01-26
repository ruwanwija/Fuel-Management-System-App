package com.example.checkfuel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.checkfuel.FillingStationHome;
import com.example.checkfuel.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateFuelData extends AppCompatActivity {
    private EditText fuelNameEditText;
    private EditText fuelPriceEditText;
    private DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_fuel_data);

        // Initialize Firebase
        FirebaseApp.initializeApp(this);
        databaseRef = FirebaseDatabase.getInstance().getReference()
                .child("Filling_Details")
                .child("ruwan")
                .child("keroseen");

        // Find EditTexts
        fuelNameEditText = findViewById(R.id.Fs_Update_Fuel1_Name);
        fuelPriceEditText = findViewById(R.id.Fs_Update_Fuel1_Price);

        // Load data from Firebase
        loadFirebaseData();

        // Find Submit button and set OnClickListener
        TextView submitButton = findViewById(R.id.Fs_Update_Submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateFirebase();
                navigateToHomePage();
            }
        });
    }

    private void loadFirebaseData() {
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String price = dataSnapshot.child("price").getValue(String.class);

                    fuelNameEditText.setText(name);
                    fuelPriceEditText.setText(price);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    private void updateFirebase() {
        String name = fuelNameEditText.getText().toString();
        String price = fuelPriceEditText.getText().toString();

        databaseRef.child("name").setValue(name);
        databaseRef.child("price").setValue(price);
    }

    private void navigateToHomePage() {
        Intent intent = new Intent(this, FillingStationHome.class); // Replace with your actual home page activity
        startActivity(intent);
        finish(); // Optional, depends on your use case
    }
}
