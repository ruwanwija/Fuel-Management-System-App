package com.example.checkfuel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.checkfuel.classes.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserList extends AppCompatActivity {

    private RecyclerView recview;
    private AdminUserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        recview=(RecyclerView) findViewById(R.id.recyclerview5);
        recview.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<User> dataList = new ArrayList<>();

        DatabaseReference fillingStationsRef = FirebaseDatabase.getInstance().getReference().child("users");
        fillingStationsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    if(snapshot.child("user_type").getValue().equals("user")) {

                        User model = new User(snapshot.getKey().toString(), snapshot.child("username").getValue().toString(), snapshot.child("email").getValue().toString(), snapshot.child("contactNo").getValue().toString());

                        dataList.add(model);
                    }
                }

                adapter = new AdminUserAdapter(dataList);

                adapter.notifyDataSetChanged();
                recview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}