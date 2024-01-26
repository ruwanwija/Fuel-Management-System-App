package com.example.checkfuel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.checkfuel.classes.Feedbacks;
import com.example.checkfuel.classes.FuelDetails;
import com.example.checkfuel.classes.Station;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Feedback extends AppCompatActivity {

    private RecyclerView recview;
    private FeedbackAdapter adapter;
    EditText feedback;
    TextView submit;
    String id;

    private FirebaseDatabase firebaseDatabase;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Feedback");

        feedback=findViewById(R.id.U_Feedback_Feedback);
        submit=findViewById(R.id.U_Feedback_Submit);

        Intent intent = getIntent();
        id=intent.getStringExtra("id");
        SharedPreferences preferences = getSharedPreferences("user_data", Context.MODE_PRIVATE);

        if(preferences.getString("user_type",null).toString().equals("admin")){
            feedback.setVisibility(View.GONE);
            submit.setVisibility(View.GONE);
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getFeedback = feedback.getText().toString();

                if (TextUtils.isEmpty(getFeedback)) {
                    Toast.makeText(Feedback.this, "Please fill in feedback", Toast.LENGTH_LONG).show();
                    return;
                }

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("feedback", getFeedback);

                String feedbackKey = databaseReference.push().getKey();

                databaseReference.child(id).child(feedbackKey).setValue(hashMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(Feedback.this, "Submitted Feedback", Toast.LENGTH_SHORT).show();
                                feedback.setText("");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Feedback.this, "Feedback Fail: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        recview=(RecyclerView) findViewById(R.id.recyclerview3);
        recview.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Feedback").child(id);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Feedbacks> dataList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Feedbacks model= new Feedbacks(snapshot.getKey().toString(),snapshot.child("feedback").getValue().toString());

                    dataList.add(model);
                }

                adapter = new FeedbackAdapter(dataList);

                adapter.notifyDataSetChanged();
                recview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }


}