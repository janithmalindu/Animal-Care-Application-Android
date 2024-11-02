package com.example.finalprojectjanith02;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class samemined extends AppCompatActivity {

    databasehelper myDb;
    EditText name,address, phone,newupdate;
    Button newinsert, newview, updatebutton;


    //Implement onCreate method to call database helper constructor
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_samemined);
        myDb = new databasehelper(this);

        name = findViewById(R.id.enteryourname);
        address = findViewById(R.id.enteryouraddress);
        phone = findViewById(R.id.enteryourphonenumber);
        newupdate = findViewById(R.id.editTextTextPersonName4);
        newinsert = findViewById(R.id.newinsert);
        updatebutton = findViewById(R.id.updatebutton);

        addData();
        update1();
    }
    //add data
    public void addData() {
        newinsert.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isInserted = myDb.insert(name.getText().toString(),address.getText().toString(),
                                phone.getText().toString());
                        if (isInserted == true)
                            Toast.makeText(samemined.this,"Data Inserted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(samemined.this,"Data not Inserted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    //Show message using alert dialog box
    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void update1() {
        updatebutton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isUpdate = myDb.update1(newupdate.getText().toString(), name.getText().toString(),
                                address.getText().toString(), phone.getText().toString());
                        if (isUpdate == true)
                            Toast.makeText(samemined.this, "Data Updated",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(samemined.this, "Data not Updated",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
}