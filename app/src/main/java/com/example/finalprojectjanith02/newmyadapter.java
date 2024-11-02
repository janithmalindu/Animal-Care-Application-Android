package com.example.finalprojectjanith02;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class newmyadapter extends RecyclerView.Adapter<newmyadapter.MyViewHolder>{
    private Context context;
    private ArrayList report_id,pets_id,howmut_id,loc_id;

    public newmyadapter(Context context, ArrayList report_id,ArrayList pets_id,ArrayList howmut_id,ArrayList loc_id) {
        this.context = context;
        this.report_id = report_id;
        this.pets_id = pets_id;
        this.howmut_id = howmut_id;
        this.loc_id = loc_id;
    }
    @NonNull
    @Override
    public newmyadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.userentrynew,parent,false);
        return new newmyadapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull newmyadapter.MyViewHolder holder, int position) {
        holder.report_id.setText(String.valueOf(report_id.get(position)));
        holder.pets_id.setText(String.valueOf(pets_id.get(position)));
        holder.howmut_id.setText(String.valueOf(howmut_id.get(position)));
        holder.loc_id.setText(String.valueOf(loc_id.get(position)));

    }

    @Override
    public int getItemCount() {
        return pets_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView report_id,pets_id,howmut_id,loc_id;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            report_id = itemView.findViewById(R.id.reportid);
            pets_id = itemView.findViewById(R.id.pets);
            howmut_id = itemView.findViewById(R.id.howmut);
            loc_id = itemView.findViewById(R.id.loc);
        }
    }
}

