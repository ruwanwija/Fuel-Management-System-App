package com.example.checkfuel;

import static android.app.ProgressDialog.show;
import static com.google.android.material.color.utilities.MaterialDynamicColors.error;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;


public class UserProfile extends AppCompatActivity {

    TextView username, userEmail, userContactNo, userPassword;

    ImageView back,home;
    private FirebaseUser user;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // Check if the user is authenticated
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference=FirebaseDatabase.getInstance().getReference("users");
        String userID = user.getUid();
        if (user == null) {
            // If not authenticated, redirect to login screen
            Intent intent = new Intent(this, user_login.class);
            startActivity(intent);
            finish(); // Finish this activity to prevent the user from going back to the profile
        } else {
            // If authenticated, continue with profile display
            reference = FirebaseDatabase.getInstance().getReference("Users");

            // Initialize TextViews
            username = findViewById(R.id.User_Profile_Username);
            userEmail = findViewById(R.id.User_Profile_Email);
            userPassword = findViewById(R.id.User_Profile_Password);
            userContactNo = findViewById(R.id.User_Profile_Contact_No);
            back=findViewById(R.id.U_Profile_Back);
            home=findViewById(R.id.U_Profile_Home);
            // Display user data
            reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User userProfile= (User) snapshot.getValue(User.Class());
                    if(userProfile!=null){
                        String Username=userProfile.getUsername();
                        String UserEmail=userProfile.getEmail();
                        String userpassword=userProfile.getPassword();
                        String usercontactNo=userProfile.getContactNo();

                        username.setText(Username);
                        userEmail.setText(UserEmail);
                        userPassword.setText(userpassword);
                        userContactNo.setText(usercontactNo);



                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(UserProfile.this, "Something went Wrong!!", Toast.LENGTH_SHORT).show();
                }
            });



        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),UserHome.class);
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



}
