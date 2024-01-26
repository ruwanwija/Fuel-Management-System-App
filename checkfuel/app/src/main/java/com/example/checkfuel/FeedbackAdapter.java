package com.example.checkfuel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.checkfuel.classes.Feedbacks;

import java.util.ArrayList;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.holder>
{
    private ArrayList<Feedbacks> feedback;
    private Context context;

    public FeedbackAdapter(ArrayList<Feedbacks> feedback){
        this.feedback = feedback;
    }

    @NonNull
    @Override
    public FeedbackAdapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlefeedback,parent,false);

        return new FeedbackAdapter.holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        holder.feedback.setText("Feedback : "+feedback.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return this.feedback.size();
    }

    class holder extends RecyclerView.ViewHolder
    {
        TextView feedback;
        public holder(View itemView)
        {
            super(itemView);
            feedback=(TextView)itemView.findViewById(R.id.feedbacktext);
        }
    }
}