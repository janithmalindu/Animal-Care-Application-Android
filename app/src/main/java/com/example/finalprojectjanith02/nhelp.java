package com.example.finalprojectjanith02;

import static android.hardware.Sensor.TYPE_LIGHT;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class nhelp extends AppCompatActivity {
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private SensorEventListener lightEventListener;
    private View nhelp;
    private float maxVlue;
    databasehelper myDb;
    EditText typeofpets,city, howmutch,updatedata;
    Button save, viewdata, btnupdatedata, deletedata;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhelp);
        nhelp = findViewById(R.id.nhelp);
        myDb = new databasehelper(this);

        typeofpets = findViewById(R.id.typeofpets);
        city = findViewById(R.id.city);
        howmutch = findViewById(R.id.howmutch);
        updatedata = findViewById(R.id.updatedata);
        save = findViewById(R.id.savenew);
        viewdata = findViewById(R.id.viewdata);
        btnupdatedata = findViewById(R.id.btnupdate);
        deletedata = findViewById(R.id.deletedata);

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
                nhelp.setBackgroundColor(Color.rgb(newValue, newValue, newValue));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        addData();
        viewAll();
        updateData();
        deleteData();

    }
    public void addData() {
        save.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isInserted = myDb.insertData(typeofpets.getText().toString(),howmutch.getText().toString(),
                                city.getText().toString());
                        if (isInserted == true)
                            Toast.makeText(nhelp.this,"Data Inserted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(nhelp.this,"Data not Inserted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    //Implement onClickListener method to handle VIEW DATA button functionality.
    public void viewAll(){
        viewdata.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Cursor results = myDb.getAllData();
                        if (results.getCount()==0){
                            showMessage("Error Message :", "No data found");
                            return;
                        }
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
    //Show message using alert dialog box
    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    //Implement onClickListener method to handle UPDATE DATA button functionality.
    public void updateData() {
        btnupdatedata.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isUpdate = myDb.updateData(updatedata.getText().toString(), typeofpets.getText().toString(),
                                howmutch.getText().toString(), city.getText().toString());
                        if (isUpdate == true)
                            Toast.makeText(nhelp.this, "Data Updated",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(nhelp.this, "Data not Updated",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    //Implement onClickListener method to handle DELETE DATA button functionality.
    public void deleteData(){
        deletedata.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Integer deletedatarows = myDb.deleteData(updatedata.getText().toString());
                        if (deletedatarows>0)
                            Toast.makeText(nhelp.this, "Data Deleted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(nhelp.this, "Data not Deleted",Toast.LENGTH_LONG).show();
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

    public void location(View view) {
        Intent intent = new Intent(this,location.class);
        startActivity(intent);
    }
    public void doner(View view) {
        Intent intent = new Intent(this, donate.class);
        startActivity(intent);
    }
}
