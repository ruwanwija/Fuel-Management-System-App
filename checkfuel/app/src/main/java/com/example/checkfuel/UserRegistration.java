package com.example.checkfuel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import java.util.HashMap;

public class UserRegistration extends AppCompatActivity {

    private EditText username, contactNo, password,email;
    private TextView submit;
    FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabaseRef;
    private ImageView back, home;
    String Uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        mAuth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("users");

        username = findViewById(R.id.U_Update_Name);
        contactNo = findViewById(R.id.U_Update_Contact);
        email = findViewById(R.id.User_Update_Email);
        password = findViewById(R.id.U_Update_Password);
        submit = findViewById(R.id.U_Update_Update);
        progressBar = findViewById(R.id.U_Reg_ProgressBar);
        back = findViewById(R.id.U_Update_Back);
        home = findViewById(R.id.U_Update_Home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), user_login.class);
                startActivity(intent);
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String getName = username.getText().toString();
                String getContactNo = contactNo.getText().toString();
                String getemail= email.getText().toString();
                String getPassword = password.getText().toString();

                if (TextUtils.isEmpty(getName) || TextUtils.isEmpty(getContactNo) || TextUtils.isEmpty(getPassword) || TextUtils.isEmpty(getemail)) {
                    Toast.makeText(UserRegistration.this, "Please fill in all fields", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                mAuth.createUserWithEmailAndPassword(getemail, getPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            Uid = mAuth.getCurrentUser().getUid();
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("username", getName);
                            hashMap.put("contactNo", getContactNo);
                            hashMap.put("email",getemail);
                            hashMap.put("password", getPassword);
                            hashMap.put("user_type", "user");
                            hashMap.put("active", true);

                            mDatabaseRef.child(Uid).setValue(hashMap);
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(UserRegistration.this,"Register Successful!", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(UserRegistration.this, MainActivity.class));
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(UserRegistration.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
