package com.example.finalprojectjanith02;

import static android.hardware.Sensor.TYPE_LIGHT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class mydonations extends AppCompatActivity {
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private SensorEventListener lightEventListener;
    private View donations;
    private float maxVlue;
    RecyclerView recyclerView;
    ArrayList<String> reports,pets,howmut,loc;
    newmyadapter adapter;
    databasehelper myDb;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mydonations);
        donations = findViewById(R.id.donations);
        myDb = new databasehelper(this);
        reports = new ArrayList<>();
        pets = new ArrayList<>();
        howmut = new ArrayList<>();
        loc = new ArrayList<>();
        recyclerView = findViewById(R.id.newrecyclerview);
        adapter = new newmyadapter(this,reports,pets,howmut,loc);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displaydatanew();

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(TYPE_LIGHT);

        if (lightSensor == null) {
            Toast.makeText(this, "This device has no light sensor :(", Toast.LENGTH_SHORT).show();
            finish();
        }
        maxVlue = lightSensor.getMaximumRange();

        lightEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float value = sensorEvent.values[0];
                int newValue = (int) (255f * value / maxVlue);
                donations.setBackgroundColor(Color.rgb(newValue, newValue, newValue));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }


    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(lightEventListener, lightSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(lightEventListener);
    }
    private void displaydatanew() {
        Cursor cursor = myDb.getAllData();
        if (cursor.getCount()==0){
            Toast.makeText(this, "No Reports", Toast.LENGTH_SHORT).show();
        }
        else
        {
            while
            (cursor.moveToNext())
            {
                reports.add(cursor.getString(0));
                pets.add(cursor.getString(1));
                howmut.add(cursor.getString(2));
                loc.add(cursor.getString(3));
            }
        }
    }
    public void newdonate(View view) {
        Intent intent = new Intent(this, donate.class);
        startActivity(intent);
    }

}