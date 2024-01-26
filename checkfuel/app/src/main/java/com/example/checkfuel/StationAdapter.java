package com.example.checkfuel;

import android.annotation.SuppressLint;
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

import com.example.checkfuel.classes.FuelDetails;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class StationAdapter extends RecyclerView.Adapter<StationAdapter.holder>
{
    private ArrayList<FuelDetails> fuel;
    private Context context;
    SharedPreferences preferences;

    public StationAdapter(ArrayList<FuelDetails> fuelDetails,SharedPreferences preferences){
        this.fuel = fuelDetails;
        this.preferences=preferences;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);

        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {

        int id =position;

        holder.price.setText("Fuel Price : "+fuel.get(position).getPrice());
        holder.name.setText("Fuel : "+fuel.get(position).getName());
        holder.station.setText("Station : "+fuel.get(position).getStation());
        holder.availability.setText("Availability : "+fuel.get(position).getAvailability());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), FillingDetailsEdit.class);
                i.putExtra("price", fuel.get(id).getPrice()+"" );
                i.putExtra("name", fuel.get(id).getName() );
                i.putExtra("availability", fuel.get(id).getAvailability() );
                i.putExtra("id", fuel.get(id).getKey() );
                view.getContext().startActivity(i);
            }
        });


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Delete Challenges");
                builder.setMessage("Delete...?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        FirebaseDatabase.getInstance().getReference().child("Filling_Details").child(preferences.getString("id", null))
                                .child(fuel.get(position).getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
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
                        fuel.remove(id);
                        notifyItemRemoved(id);
                        notifyDataSetChanged();
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
        return this.fuel.size();
    }

    class holder extends RecyclerView.ViewHolder
    {
        ImageView edit,delete;
        TextView name,station,price,availability;
        public holder(View itemView)
        {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.nametext);
            station=(TextView)itemView.findViewById(R.id.stationtext);
            price=(TextView)itemView.findViewById(R.id.pricetext);
            availability=(TextView)itemView.findViewById(R.id.availabilitytext);

            edit=itemView.findViewById(R.id.editicon);
            delete=itemView.findViewById(R.id.deleteicon);
        }
    }
}