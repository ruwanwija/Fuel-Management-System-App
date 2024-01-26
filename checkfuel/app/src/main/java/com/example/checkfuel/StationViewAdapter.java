package com.example.checkfuel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.checkfuel.R;
import com.example.checkfuel.StationFuel;
import com.example.checkfuel.classes.Station;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class StationViewAdapter extends RecyclerView.Adapter<StationViewAdapter.holder>
{
    private ArrayList<Station> station;
    private Context context;

    public StationViewAdapter(ArrayList<Station> station){
        this.station = station;
    }

    @NonNull
    @Override
    public StationViewAdapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlestation,parent,false);

        return new StationViewAdapter.holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {

        int id =position;

        holder.station.setText("Station Name : "+station.get(position).getStation());
        holder.location.setText("Station Location: "+station.get(position).getLocation());
        holder.permit.setText("Station Permit ID : "+station.get(position).getPermit());
        holder.phone.setText("Contact No : "+station.get(position).getPhone());
        holder.email.setText("Email : "+station.get(position).getEmail());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), StationFuelData.class);
                i.putExtra("id", station.get(id).getKey() );
                view.getContext().startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.station.size();
    }

    class holder extends RecyclerView.ViewHolder
    {
        Button view;
        TextView station,location,permit,phone,email;
        public holder(View itemView)
        {
            super(itemView);
            station=(TextView)itemView.findViewById(R.id.stationText);
            location=(TextView)itemView.findViewById(R.id.locationText);
            permit=(TextView)itemView.findViewById(R.id.permitText);
            phone=(TextView)itemView.findViewById(R.id.phoneText);
            email=(TextView)itemView.findViewById(R.id.emailText);

            view=itemView.findViewById(R.id.view_station);
        }
    }
}