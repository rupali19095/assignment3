package com.rupali.androdev.assignment3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Accelerometer extends AppCompatActivity implements SensorEventListener{
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private long lastTime = 0;
    private float lastX, lastY, lastZ;

    TextView xValue,yValue,zValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);

        xValue=findViewById(R.id.xValue);
        yValue=findViewById(R.id.yValue);
        zValue=findViewById(R.id.zValue);

        Log.d("Hello","In onCreate");
        //creating instance of accelerometer
        sensorManager =(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //to use sensor w/o any delay
        sensorManager.registerListener(Accelerometer.this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Log.d("Hey","In On Sensor Changed X:"+sensorEvent.values[0]+"  Y:"+sensorEvent.values[1]+"  Z:"+sensorEvent.values[2]);
        long currentTime = System.currentTimeMillis();
        if ((currentTime - lastTime) > 100) {
            long diffTime = (currentTime - lastTime);
            lastTime = currentTime;
            lastX = sensorEvent.values[0];
            lastY = sensorEvent.values[1];
            lastZ = sensorEvent.values[2];
        }

        xValue.setText("X Value:"+lastX);
        yValue.setText("Y Value:"+lastY);
        zValue.setText("Z Value:"+lastZ);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
