package com.example.finalprojectjanith02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class users extends AppCompatActivity {

    databasehelper myDb;
    RecyclerView recyclerView;
    ArrayList<String>id,name,address;
    myadapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        myDb = new databasehelper(this);
        id = new ArrayList<>();
        name = new ArrayList<>();
        address = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview);
        adapter = new myadapter(this,id,name,address);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displaydata();

    }

    private void displaydata() {
        Cursor cursor = myDb.getdata();
        if (cursor.getCount()==0){
            Toast.makeText(this, "No users", Toast.LENGTH_SHORT).show();
        }
        else
        {
            while
            (cursor.moveToNext())
            {
                id.add(cursor.getString(0));
                name.add(cursor.getString(1));
                address.add(cursor.getString(2));
            }
        }
    }


}