
package com.example.checkfuel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.checkfuel.R;


public class UpdateProfile extends AppCompatActivity {

    EditText username, password, contactNo, email;
    TextView update;

    ImageView back, home;

    DatabaseReference reference;

    String usernameValue = ""; // Assuming 'usernameValue' is declared and initialized somewhere in your code.
    String Email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        reference = FirebaseDatabase.getInstance().getReference("Users");

        username = findViewById(R.id.U_Update_Name);
        email = findViewById(R.id.User_Update_Email);
        contactNo = findViewById(R.id.U_Update_Contact);
        password = findViewById(R.id.U_Update_Password);
        update = findViewById(R.id.U_Update_Update);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNameChanged() || isEmailChanged() || isContactNoChanged() || isPasswordChanged()) {
                    Toast.makeText(UpdateProfile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UpdateProfile.this, "Profile not Updated", Toast.LENGTH_SHORT).show();
                }
            }
        });

        showData();
    }

    public boolean isNameChanged() {
        String newName = username.getText().toString();
        if (!newName.equals(usernameValue)) {
            reference.child(usernameValue).child("username").setValue(newName);
            usernameValue = newName;
            return true;
        } else {
            return false;
        }
    }

    public boolean isContactNoChanged() {
        String newContactNo = contactNo.getText().toString();
        if (!newContactNo.equals(contactNo)) {
            reference.child(usernameValue).child("contactNo").setValue(newContactNo);
            return true;
        } else {
            return false;
        }
    }

    public boolean isEmailChanged() {
        String newEmail = email.getText().toString();
        if (!newEmail.equals(Email)) {
            reference.child(usernameValue).child("email").setValue(newEmail);
            Email = newEmail;
            return true;
        } else {
            return false;
        }
    }

    public boolean isPasswordChanged() {
        String newPassword = password.getText().toString();
        if (!newPassword.equals(usernameValue)) {
            reference.child(usernameValue).child("password").setValue(newPassword);
            return true;
        } else {
            return false;
        }
    }

    public void showData() {
        Intent intent = getIntent();
        usernameValue = intent.getStringExtra("username");
        Email = intent.getStringExtra("email");
        String contact = intent.getStringExtra("contactNo");
        String pass = intent.getStringExtra("password");

        username.setText(usernameValue);
        email.setText(Email);
        contactNo.setText(contact);
        password.setText(pass);
    }
}
