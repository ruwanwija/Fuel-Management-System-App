package com.example.checkfuel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class UserForgotPassword extends AppCompatActivity {

    TextView btnReset;
    EditText editEmail;
    ProgressBar progressBar;
    String strEmail;
    FirebaseAuth mAuth;
    ImageView Back,Home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_forgot_password);


        //Initialization
        btnReset = findViewById(R.id.User_Forgot_Password_Reset);
        editEmail = findViewById(R.id.User_Forgot_Password_Email);
        progressBar = findViewById(R.id.User_Forgot_Password_ProgressBar);

        mAuth=FirebaseAuth.getInstance();

        //Reset Button Listner
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strEmail=editEmail.getText().toString().trim();
                if(!TextUtils.isEmpty(strEmail)){
                    ResetPassword();
                }else {
                    Toast.makeText(UserForgotPassword.this,"Enter email",Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });
        Home=findViewById(R.id.imageView4);
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Back=findViewById(R.id.User_Forgot_Password_Back);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), user_login.class);
                startActivity(intent);
                finish();
            }
        });
        }
    private void ResetPassword(){
        progressBar.setVisibility(View.VISIBLE);
        btnReset.setVisibility(View.INVISIBLE);

        mAuth.sendPasswordResetEmail(strEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(UserForgotPassword.this,"Reset Password link has been sent to your registred Email",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(UserForgotPassword.this,user_login.class);
                startActivity(intent);
                finish();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UserForgotPassword.this,"Error:-"+e.getMessage(),Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.VISIBLE);
                        btnReset.setVisibility(View.INVISIBLE);
                    }
                });
    }

}