package com.example.finalprojectjanith02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import static android.hardware.Sensor.TYPE_LIGHT;

public class dashboard extends AppCompatActivity {

    Button imgbtn;
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private SensorEventListener lightEventListener;
    private View malindu;
    private float maxVlue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        malindu = findViewById(R.id.malindu);

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
                malindu.setBackgroundColor(Color.rgb(newValue, newValue, newValue));
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


    public void iwanthelp(View view) {
        Intent intent = new Intent(this, whelp.class);
        startActivity(intent);
    }

    public void mydonations(View view) {
        Intent intent = new Intent(this, mydonations.class);
        startActivity(intent);
    }

    public void users(View view) {
        Intent intent = new Intent(this, users.class);
        startActivity(intent);
    }
    public void samemined(View view) {
        Intent intent = new Intent(this, samemined.class);
        startActivity(intent);
    }

}