package com.example.finalprojectjanith02;

import static android.hardware.Sensor.TYPE_LIGHT;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
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

public class login extends AppCompatActivity {

    EditText username,password;
    Button nlogin;
    dbhelper DB;

    private SensorManager sensorManager;
    private Sensor lightSensor;
    private SensorEventListener lightEventListener;
    private View login;
    private float maxVlue;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.login);
//identyfying inputs and buttons
        username = findViewById(R.id.newusername);
        password = findViewById(R.id.newpassword);
        nlogin =  findViewById(R.id.newsignin);
        DB = new dbhelper(this);
//after click log in and check database
        nlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
//user not fill the fields show this message
                if (user.equals("")||pass.equals(""))
                    Toast.makeText(com.example.finalprojectjanith02.login.this, "Please enter all the field", Toast.LENGTH_SHORT).show();
                else {
                    Boolean checkuserpass = DB.checkusernamepassword(user, pass);
                    // user enterd inputs are correct then go to the dashbord and show log in successfully
                    if (checkuserpass == true) {
                        Toast.makeText(com.example.finalprojectjanith02.login.this, "Login successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), dashboard.class);
                        startActivity(intent);
                    }
                    //inputs are wrong then show inputs are invalid
                    else{
                        Toast.makeText(com.example.finalprojectjanith02.login.this, "Invalid inputs", Toast.LENGTH_SHORT).show();
                    }
                }
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
                login.setBackgroundColor(Color.rgb(newValue, newValue, newValue));
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

    public void SignIn(View view) {
        Intent intent = new Intent(this, mainActivity.class);
        startActivity(intent);
    }
}