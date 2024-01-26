package com.example.checkfuel;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.checkfuel.classes.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdminUserAdapter extends RecyclerView.Adapter<AdminUserAdapter.holder>
{
    private ArrayList<User> user;
    private Context context;

    public AdminUserAdapter(ArrayList<User> user){
        this.user = user;
    }

    @NonNull
    @Override
    public AdminUserAdapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.usersinglerow,parent,false);

        return new AdminUserAdapter.holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, @SuppressLint("RecyclerView") int position) {

        int id =position;

        holder.name.setText("User : "+user.get(position).getUsername());
        holder.phone.setText("Phone : "+user.get(position).getPhone());
        holder.email.setText("Email : "+user.get(position).getEmail());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Delete User");
                builder.setMessage("Delete...?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        FirebaseDatabase.getInstance().getReference().child("users").child(user.get(position).getKey()).removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(context,"Successful",Toast.LENGTH_LONG).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.e("Firebase", "Error writing document", e);
                                        Toast.makeText(context,"Unsuccessful",Toast.LENGTH_LONG).show();
                                    }
                                });
                        notifyDataSetChanged();
                        user.remove(id);
                        notifyItemRemoved(id);
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.user.size();
    }

    class holder extends RecyclerView.ViewHolder
    {
        ImageView delete;
        TextView name,phone,permit,email;
        public holder(View itemView)
        {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.nametext);
            phone=(TextView)itemView.findViewById(R.id.phoneText);
            email=(TextView)itemView.findViewById(R.id.emailText);

            delete=itemView.findViewById(R.id.deleteicon);
        }
    }
}