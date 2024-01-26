package com.example.checkfuel;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class FillingStationLogin extends AppCompatActivity {

    EditText user, permitID;
    TextView login, register, ForgotPassword;

    ImageView Back, Home;
    private static final String TAG = "FillingStationLogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filling_station_login);

        user = findViewById(R.id.FS_Login_Name);
        login = findViewById(R.id.FS_Login_Login);
        register = findViewById(R.id.FS_Login_Register);
        permitID = findViewById(R.id.FS_Login_PermitID);
        Home = findViewById(R.id.FS_Login_Home);
        Back = findViewById(R.id.FS_Login_Back);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateUser() | !validatePermitId()) {

                } else {
                    checkuser();
                }
            }
        });
        ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FillingStationRegistration.class);
                startActivity(intent);
                finish();
            }
        });
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    public boolean validateUser(){
        String val = user.getText().toString();
        if(val.isEmpty()){
            user.setError("Username cannot be empty");
            return false;
        } else {
            user.setError(null);
            return true;
        }
    }public boolean validatePermitId(){
        String val = permitID.getText().toString();
        if(val.isEmpty()){
            permitID.setError("PermitID cannot be empty");
            return false;
        } else {
            permitID.setError(null);
            return true;
        }
    }
    public void checkuser() {
        String fillingStationUsername = user.getText().toString().trim();
        String fillingStationPermitId = permitID.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Filling_Station").child(fillingStationUsername);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String permitIDFromDB = snapshot.child("PermitID").getValue(String.class);

                    if (permitIDFromDB.equals(fillingStationPermitId)) {
                        String nameFromDB = snapshot.child("username").getValue(String.class);
                        String emailFromDB = snapshot.child("email").getValue(String.class);
                        String contactNoFromDB = snapshot.child("contactNo").getValue(String.class);
                        String locationFromDB = snapshot.child("Location").getValue(String.class);
                        Double latitudeFromDB = snapshot.child("latitude").getValue(Double.class);
                        Double longitudeFromDB = snapshot.child("longitude").getValue(Double.class);
                        String stationFromDB = snapshot.child("station").getValue(String.class);

                        Intent intent = new Intent(FillingStationLogin.this, FillingStationHome.class);
                        intent.putExtra("username", nameFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("contactNo", contactNoFromDB);
                        intent.putExtra("PermitID", permitIDFromDB);
                        intent.putExtra("Location", locationFromDB);
                        intent.putExtra("latitude", latitudeFromDB);
                        intent.putExtra("longitude", longitudeFromDB);
                        intent.putExtra("Station", stationFromDB);

                        startActivity(intent);

                        Intent intent2 = new Intent(FillingStationLogin.this, FillingStationHome.class);
                        intent2.putExtra("FILLING_STATION_NAME", nameFromDB);
                        Log.d("FillingStationLogin", "Filling station name: " + nameFromDB);
                        startActivity(intent2);
                    } else {
                        permitID.setError("Invalid PermitID");
                        permitID.requestFocus();
                    }
                } else {
                    user.setError("User does not exist");
                    user.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Database Error: " + error.getMessage());
            }
        });
    }


}
