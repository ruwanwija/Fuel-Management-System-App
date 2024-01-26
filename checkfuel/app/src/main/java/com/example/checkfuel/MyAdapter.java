package com.example.checkfuel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Fuel> list;

    public MyAdapter(Context context,ArrayList<Fuel> list){
        this.context=context;
        this.list=list;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.activity_fuel_price_comparison,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Fuel fuel=list.get(position);
        holder.station.setText(fuel.getStation());
        holder.fuel.setText(fuel.getFuel());
        holder.price.setText(fuel.getPrice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView station, fuel, price;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            station = itemView.findViewById(R.id.station_name_value);
            fuel = itemView.findViewById(R.id.fuel_name);
            price = itemView.findViewById(R.id.price_value);
        }
    }

}
