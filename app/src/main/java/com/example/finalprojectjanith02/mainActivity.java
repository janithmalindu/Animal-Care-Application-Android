package com.example.finalprojectjanith02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import static android.hardware.Sensor.TYPE_LIGHT;

import java.util.regex.Pattern;

public class mainActivity extends AppCompatActivity {
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])"+  /*at least one number*/
                    "(?=.*[a-z])"+  /*at least one lawercase letter*/
                    "(?=.*[A-Z])"+  /*at least one upper case letter*/
                    "(?=.*[@#$%^&*+=])"+  /*at least one special character*/
                    "(?=\\S+$)"+
                    ".{6,}" +  /*minimum 6 character*/
                    "$");

    Button imgbtn,newsignin,Login;
    EditText username,enterpassword,confirmpassword;

    dbhelper DB;
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private SensorEventListener lightEventListener;
    private View janith;
    private float maxVlue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        janith = findViewById(R.id.janith);

        username = (EditText) findViewById(R.id.newsername);
        enterpassword = (EditText) findViewById(R.id.enterpassword);
        confirmpassword = (EditText) findViewById(R.id.confirmpassword);
        newsignin = (Button) findViewById(R.id.newsignin);
        DB = new dbhelper(this);
//click  sign in button and get inputs form textinputs
        newsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = enterpassword.getText().toString();
                String conpass = confirmpassword.getText().toString();


//user not fill in the filed display this message
               if (user.equals("")||pass.equals("")||conpass.equals(""))
                    Toast.makeText(mainActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                //user fill the filed correctly save to databse and show registerd successfully and go login page
                else if (!PASSWORD_PATTERN.matcher(pass).matches()) {
                    enterpassword.setError("please use at least one lowercase letter,uppercase letter,number and special character");
                    
                } else{
                    if (pass.equals(conpass)){
                        Boolean checkuser = DB.checkusername(user);
                        if (checkuser==false){
                            Boolean insert = DB.insertdata(user, pass);
                            if (insert==true){
                                Toast.makeText(mainActivity.this, "Registerd Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), login.class);
                                startActivity(intent);
                                //or some error show register faild
                            }else {
                                Toast.makeText(mainActivity.this, "Registration Faild", Toast.LENGTH_SHORT).show();
                            }
                        }
                        //user unable to create same both accounts.then show this message
                        else {
                            Toast.makeText(mainActivity.this, "User already exsist please Log in", Toast.LENGTH_SHORT).show();
                        }
                        //user inputs are not maching to both passwords then show this message
                    }else{
                        Toast.makeText(mainActivity.this, "Password not matching", Toast.LENGTH_SHORT).show();
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
                janith.setBackgroundColor(Color.rgb(newValue, newValue, newValue));
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


        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(mainActivity.this, splash.class);
                startActivity(i);
                finish();
            }
        },10000000);
    }
    public void Login(View view) {
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }

    public void setJanith(View janith) {
        this.janith = janith;
    }
}