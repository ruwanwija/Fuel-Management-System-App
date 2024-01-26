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

import com.example.checkfuel.classes.Station;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdminStationAdapter extends RecyclerView.Adapter<AdminStationAdapter.holder>
{
    private ArrayList<Station> station;
    private Context context;

    public AdminStationAdapter(ArrayList<Station> station){
        this.station = station;
    }

    @NonNull
    @Override
    public AdminStationAdapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stationsinglerow,parent,false);

        return new AdminStationAdapter.holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, @SuppressLint("RecyclerView") int position) {

        int id =position;

        holder.station.setText("Station : "+station.get(position).getStation());
        holder.location.setText("Location : "+station.get(position).getLocation());
        holder.permit.setText("Permit : "+station.get(position).getPermit());
        holder.phone.setText("Phone : "+station.get(position).getPhone());
        holder.email.setText("Email : "+station.get(position).getEmail());

        holder.feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), Feedback.class);
                i.putExtra("id", station.get(position).getKey() );
                view.getContext().startActivity(i);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Delete Station");
                builder.setMessage("Delete...?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        FirebaseDatabase.getInstance().getReference().child("users").child(station.get(position).getKey()).removeValue()
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
                        station.remove(id);
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
        return this.station.size();
    }

    class holder extends RecyclerView.ViewHolder
    {
        ImageView delete;
        Button feedback;
        TextView station,location,permit,phone,email;
        public holder(View itemView)
        {
            super(itemView);
            station=(TextView)itemView.findViewById(R.id.stationText);
            location=(TextView)itemView.findViewById(R.id.locationText);
            permit=(TextView)itemView.findViewById(R.id.permitText);
            phone=(TextView)itemView.findViewById(R.id.phoneText);
            email=(TextView)itemView.findViewById(R.id.emailText);

            delete=itemView.findViewById(R.id.deleteicon);
            feedback=itemView.findViewById(R.id.feedback);
        }
    }
}