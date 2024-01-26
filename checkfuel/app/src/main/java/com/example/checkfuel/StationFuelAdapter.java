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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.checkfuel.classes.FuelDetails;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class StationFuelAdapter extends RecyclerView.Adapter<StationFuelAdapter.holder>
{
    private ArrayList<FuelDetails> fuel;
    private Context context;

    public StationFuelAdapter(ArrayList<FuelDetails> fuelDetails){
        this.fuel = fuelDetails;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlefuelstation,parent,false);

        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {

        int id =position;

        holder.price.setText("Fuel Price : "+fuel.get(position).getPrice());
        holder.name.setText("Fuel : "+fuel.get(position).getName());
        holder.station.setText("Station : "+fuel.get(position).getStation());
        holder.availability.setText("Availability : "+fuel.get(position).getAvailability());

    }

    @Override
    public int getItemCount() {
        return this.fuel.size();
    }

    class holder extends RecyclerView.ViewHolder
    {
        TextView name,station,price,availability;
        public holder(View itemView)
        {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.nametext);
            station=(TextView)itemView.findViewById(R.id.stationtext);
            price=(TextView)itemView.findViewById(R.id.pricetext);
            availability=(TextView)itemView.findViewById(R.id.availabilitytext);
        }
    }
}