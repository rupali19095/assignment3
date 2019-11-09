package com.rupali.androdev.assignment3;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button b1;
    Button b2;
    Button b3;
    Button b4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1=findViewById(R.id.accel);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent
                        (getApplicationContext(),Accelerometer.class);
                MainActivity.this.startActivity(intent);
            }
        });

        b2=findViewById(R.id.wifi);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),WifiScannerActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
        b3=findViewById(R.id.gps);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),GPS_location.class);
                MainActivity.this.startActivity(i);
            }
        });
        b4=findViewById(R.id.micro);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),microphone.class);
                MainActivity.this.startActivity(i);
            }
        });

    }
}
