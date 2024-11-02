package com.example.finalprojectjanith02;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myadapter extends RecyclerView.Adapter<myadapter.MyViewHolder> {
    private Context context;
    private ArrayList id_id,name_id,address_id;

    public myadapter(Context context, ArrayList id_id, ArrayList name_id, ArrayList address_id) {
        this.context = context;
        this.id_id = id_id;
        this.name_id = name_id;
        this.address_id = address_id;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.userentry,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.id_id.setText(String.valueOf(id_id.get(position)));
        holder.name_id.setText(String.valueOf(name_id.get(position)));
        holder.address_id.setText(String.valueOf(address_id.get(position)));

    }

    @Override
    public int getItemCount() {
        return name_id.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView id_id,name_id,address_id;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id_id = itemView.findViewById(R.id.textid);
            name_id = itemView.findViewById(R.id.textname);
            address_id = itemView.findViewById(R.id.textaddress);
        }
    }
}
