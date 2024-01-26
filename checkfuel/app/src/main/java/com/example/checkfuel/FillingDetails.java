package com.example.checkfuel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.checkfuel.classes.FuelDetails;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class  FillingDetails extends AppCompatActivity {

    ArrayList<FuelDetails> fuelList;
    ArrayAdapter<FuelDetails> adapter;

    EditText price;
    Spinner name,availability;

    TextView add;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filling_details);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Filling_Details");
        SharedPreferences preferences = getSharedPreferences("user_data", Context.MODE_PRIVATE);

        availability = findViewById(R.id.availability);
        name = findViewById(R.id.Fs_Fd_Name);
        price = findViewById(R.id.Fs_Fd_Price);
        add = findViewById(R.id.fuel_add_btn);

        String[] data = new String[] {"Select Availability","Yes","No"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        availability.setAdapter(adapter);

        String[] data1 = new String[] {"Select Fuel","Petrol 92","Petrol 95","Diesel Normal","Super Diesel","Auto Diesel","Keroseen","Auto Gas"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, data1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        name.setAdapter(adapter1);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("FillingDetails", "Submit button clicked");
                if(!name.getSelectedItem().toString().equals("Select Fuel")){
                    if(!availability.getSelectedItem().toString().equals("Select Availability")){

                        String fuelName = name.getSelectedItem().toString();
                        double fuelPrice = Double.parseDouble(price.getText().toString());

                        if (fuelName.isEmpty() || fuelPrice <= 0) {
                            makeToast("Invalid input. Please enter both name and price.");
                        } else {
                            FuelDetails newFuel = new FuelDetails(preferences.getString("Station", null).toString(),fuelName, fuelPrice,availability.getSelectedItem().toString());

                            DatabaseReference fillingStationRef = databaseReference.child(preferences.getString("id", null));
                            String uploadId=databaseReference.push().getKey();
                            DatabaseReference fuelRef = fillingStationRef.child(uploadId);

                            fuelRef.setValue(newFuel)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            makeToast("Data stored");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.e("Firebase", "Error writing document", e);
                                            makeToast("Data not stored");
                                        }
                                    });

                            name.setSelection(0);
                            price.setText("");
                            availability.setSelection(0);
                        }
                    }else {
                        makeToast("Select Availability");
                    }
                }else {
                    makeToast("Select Fuel");
                }
            }
        });
    }

    public void addItem(FuelDetails fuel) {
        fuelList.add(fuel);
        adapter.notifyDataSetChanged();
    }

    public void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
