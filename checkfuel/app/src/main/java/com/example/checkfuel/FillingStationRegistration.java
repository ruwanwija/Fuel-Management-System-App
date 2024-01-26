package com.example.checkfuel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class FillingStationRegistration extends AppCompatActivity {

    EditText Editusername,Editpermitid,Editlocaton,Editemail,EditcontactNo,Editstationname,password;
    TextView submit;
    ProgressBar progressBar;

    FirebaseAuth mAuth;

    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;

    double latitude;
    double longitude;

    ImageView back,home;
    String Uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filling_station_registration);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("users");
        Geocoder geocoder = new Geocoder(this);

        Editusername= findViewById(R.id.FS_Profile_Name);
        Editpermitid= findViewById(R.id.FS_Profile_PermitID);
        Editlocaton= findViewById(R.id.FS_Profile_Location);
        Editemail = findViewById(R.id.FS_Profile_Email);
        EditcontactNo = findViewById(R.id.FS_Profile_ContactNo);
        Editstationname = findViewById(R.id.FS_Profile_Filling_Station);
        password = findViewById(R.id.U_Update_Password);
        submit=findViewById(R.id.FS_Reg_Registar);
        progressBar=findViewById(R.id.FS_Reg_ProgressBar);
        home=findViewById(R.id.FS_Profile_Home);
        back=findViewById(R.id.FS_Profile_Back);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.GONE);
                String username,permitID,location,email,contactNo,station,pass;

                username=String.valueOf(Editusername.getText());
                permitID=String.valueOf(Editpermitid.getText());
                location=String.valueOf(Editlocaton.getText());
                email= String.valueOf(Editemail.getText());
                contactNo= String.valueOf(EditcontactNo.getText());
                pass= String.valueOf(password.getText());

                if(TextUtils.isEmpty(username)){
                    Toast.makeText(FillingStationRegistration.this,"Enter Username",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(permitID)){
                    Toast.makeText(FillingStationRegistration.this,"Enter PemitID",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(location)){
                    Toast.makeText(FillingStationRegistration.this,"Enter Location",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(FillingStationRegistration.this,"Enter Email",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(contactNo)){
                    Toast.makeText(FillingStationRegistration.this,"Enter ContactNo",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    Toast.makeText(FillingStationRegistration.this,"Enter Password",Toast.LENGTH_LONG).show();
                    return;
                }
                if(pass.length()<6){
                    Toast.makeText(FillingStationRegistration.this,"Password min length 6",Toast.LENGTH_LONG).show();
                    return;
                }
                try {
                    List<Address> addresses = geocoder.getFromLocationName(location, 1);

                    if (addresses != null && addresses.size() > 0) {
                        latitude = addresses.get(0).getLatitude();
                        longitude = addresses.get(0).getLongitude();
                    }else {
                        Toast.makeText(FillingStationRegistration.this,"Location",Toast.LENGTH_LONG).show();
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), FillingStationLogin.class);
                        startActivity(intent);
                        finish();
                    }
                });

                home.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

                String getName=Editusername.getText().toString();
                String getPermitID=Editpermitid.getText().toString();
                String getLocation=Editlocaton.getText().toString();
                String getEmail=Editemail.getText().toString();
                String getContactNo=EditcontactNo.getText().toString();
                String getPassword=password.getText().toString();

                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("PemitID",getPermitID);
                hashMap.put("Location",getLocation);
                hashMap.put("email",getEmail);
                hashMap.put("contactNo",getContactNo);
                hashMap.put("Station",getName);
                hashMap.put("Password",getPassword);
                hashMap.put("latitude", latitude);
                hashMap.put("longitude", longitude);
                hashMap.put("user_type", "station");
                hashMap.put("active", false);

                mAuth.createUserWithEmailAndPassword(getEmail, getPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            Uid = mAuth.getCurrentUser().getUid();

                            databaseReference.child(Uid).setValue(hashMap);
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(FillingStationRegistration.this,"Register Successful!", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(FillingStationRegistration.this, MainActivity.class));
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(FillingStationRegistration.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}
