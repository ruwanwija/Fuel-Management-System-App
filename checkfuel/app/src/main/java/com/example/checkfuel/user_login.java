package com.example.checkfuel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import android.util.Log;


import java.util.Objects;

public class user_login extends AppCompatActivity {

    EditText user, password;
    TextView login, Userregister, ForgotPassword,FSregister;

    ImageView Back, Home;
    private DatabaseReference mDatabaseRef;

    FirebaseAuth fAuth;

    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        user = findViewById(R.id.U_Login_User);
        password = findViewById(R.id.U_Login_Password);
        login = findViewById(R.id.U_Login_Login);
        ProgressBar progressBar = findViewById(R.id.U_Login_ProgressBar);
        ForgotPassword = findViewById(R.id.U_Login_ForgotPassword);
        Userregister = findViewById(R.id.U_Login_Register);
        FSregister=findViewById(R.id.FS_Login_Register);

        Back = findViewById(R.id.U_Login_Back);
        Home = findViewById(R.id.U_Login_Home);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        fAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validateUser() | !validatePassword()){

                }else{
                    validateFields();
                }
            }
        });

        ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserForgotPassword.class);
                startActivity(intent);
                finish();
            }
        });

        Userregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserRegistration.class);
                startActivity(intent);
                finish();
            }
        });
        FSregister.setOnClickListener(new View.OnClickListener() {
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
    }
    public boolean validatePassword(){
        String val = password.getText().toString();
        if(val.isEmpty()){
            password.setError("Password cannot be empty");
            return false;
        } else {
            user.setError(null);
            return true;
        }
    }

    private void validateFields() {
        //validate fields

        String email, password;

        email = user.getText().toString();
        password = this.password.getText().toString();

        if (!(email.isEmpty())){
            if(!(password.isEmpty())){
                logUser(email,password);
            }else{
                Toast.makeText(user_login.this,"Please Fill Password Field!",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(user_login.this,"Please Fill Email Field!",Toast.LENGTH_SHORT).show();
        }
    }

    private void logUser(String email, String password) {

        fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = fAuth.getCurrentUser();
                    userInterface(user);
                } else {
                    Toast.makeText(user_login.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void userInterface(FirebaseUser user) {
        DatabaseReference myRef = mDatabaseRef.child("users/"+user.getUid());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println(dataSnapshot.child("user_type"));

                SharedPreferences preferences = getSharedPreferences("user_data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("id", dataSnapshot.getKey().toString());
                editor.putString("user_type", dataSnapshot.child("user_type").getValue().toString());

                if(dataSnapshot.child("user_type").getValue().toString().equals("user")){
                    editor.apply();
                    Toast.makeText(user_login.this, "User Login Successfully!!.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(user_login.this,UserHome.class));
                }else if(dataSnapshot.child("user_type").getValue().toString().equals("station")){
                    if(dataSnapshot.child("active").getValue().toString()=="true"){
                        editor.putString("Station", dataSnapshot.child("Station").getValue().toString());
                        editor.apply();
                        Toast.makeText(user_login.this, "Filling Station Login Successfully!!.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(user_login.this,FillingStationHome.class));
                    }else{
                        Toast.makeText(user_login.this, "Activation failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                }else if(dataSnapshot.child("user_type").getValue().toString().equals("admin")){
                    if(dataSnapshot.child("active").getValue().toString()=="true"){
                        editor.apply();
                        Toast.makeText(user_login.this, "Admin Login Successfully!!.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(user_login.this,AdminHome.class));
                    }else{
                        Toast.makeText(user_login.this, "Activation failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}

        });
    }
}
