package com.example.finalprojectjanith02;

import static android.hardware.Sensor.TYPE_LIGHT;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class donate extends AppCompatActivity {
    //sensor part

    private SensorManager sensorManager;
    private Sensor lightSensor;
    private SensorEventListener lightEventListener;
    private View kumara;
    private float maxVlue;
    //database part
    databasehelper myDb;
    EditText updatedata1, to, from;
    Button viewdata, savemydonations, viewmap;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        kumara = findViewById(R.id.kumara);
        myDb = new databasehelper(this);
//identyfying text inputs and buttons
        updatedata1 = findViewById(R.id.updatedata1);
        to = findViewById(R.id.to);
        from = findViewById(R.id.from);
        viewdata = findViewById(R.id.viewdata1);
        savemydonations = findViewById(R.id.savemydonations);
        viewmap = findViewById(R.id.mapview);

// set view map button to get inputs from text inputs.
        viewmap.setOnClickListener(view -> {
            String reporterlocation = to.getText().toString();
            String donaterlocation = from.getText().toString();

            if (reporterlocation.equals("") || donaterlocation.equals("")){
                Toast.makeText(this, "Please enter your location and reporter location", Toast.LENGTH_SHORT).show();
            }else{
                //if inputs are given then get directions
                getDirections(reporterlocation, donaterlocation);
            }
        });


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
                kumara.setBackgroundColor(Color.rgb(newValue, newValue, newValue));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        viewdata();
        deleteData();

    }
    private void getDirections(String from, String to){
        //go directions
        try {
            Uri uri = Uri.parse("https://www.google.com/maps/dir/" + from + "/" + to);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            //your device havn't map application will go to the play store and install it
        } catch (ActivityNotFoundException exception) {
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps&hl=en&gl=US");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
    // click view data button and get data from database
    public void viewdata(){
        viewdata.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Cursor results = myDb.getAllData();
                        //data is not in database show error message
                        if (results.getCount()==0){
                            showMessage("Error Message :", "No data found");
                            return;
                        }
                        //set how to view of list
                        StringBuffer buffer = new StringBuffer();
                        while(results.moveToNext()){
                            buffer.append("ID :" +results.getString(0) + "\n");
                            buffer.append("TYPE_OF_PETS :" +results.getString(1) + "\n");
                            buffer.append("HOW_MUTCH :" +results.getString(2) + "\n");
                            buffer.append("CITY :" +results.getString(3) + "\n\n");

                            showMessage("List of need help list :", buffer.toString());
                        }
                    }
                }
        );
    }
    //alert dialog box
    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    //click save my donation button will delete reporters data form database
    public void deleteData(){
        savemydonations.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Integer deletedatarows = myDb.deleteData(updatedata1.getText().toString());
                        if (deletedatarows>0)
                            Toast.makeText(donate.this, "Thank You So Mutch",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(donate.this, "Data not Updated",Toast.LENGTH_LONG).show();
                    }
                }
        );
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
    public void report(View view) {
        Intent intent = new Intent(this, nhelp.class);
        startActivity(intent);
    }

}