package com.rupali.androdev.assignment3;

import android.content.Intent;
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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Accelerometer extends AppCompatActivity implements SensorEventListener{
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private long lastTime = 0;
    private float lastX, lastY, lastZ;
    private DatabaseHelper1 db;
    private Button save_data;
    TextView xValue,yValue,zValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);

        xValue=findViewById(R.id.xValue);
        yValue=findViewById(R.id.yValue);
        zValue=findViewById(R.id.zValue);
        db=new DatabaseHelper1(this);
        save_data=findViewById(R.id.save_acc);

        Log.d("Hello","In onCreate");
        //creating instance of accelerometer
        sensorManager =(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //to use sensor w/o any delay
        sensorManager.registerListener(Accelerometer.this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        save_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("IN SAVE DATA","hello");
                Intent i=new Intent(getApplicationContext(),ListSavedData1.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        long currentTime = System.currentTimeMillis();
        if ((currentTime - lastTime) > 2000) {
            long diffTime = (currentTime - lastTime);
            Log.d("Hey","In On Sensor Changed X:"+sensorEvent.values[0]+"  Y:"+sensorEvent.values[1]+"  Z:"+sensorEvent.values[2]);
            lastTime = currentTime;
            lastX = sensorEvent.values[0];
            lastY = sensorEvent.values[1];
            lastZ = sensorEvent.values[2];
            AddData();
        }

        xValue.setText("X Value:"+lastX);
        yValue.setText("Y Value:"+lastY);
        zValue.setText("Z Value:"+lastZ);
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    public  void AddData(){
        String save_x=xValue.toString();
        String save_y=yValue.toString();
        String save_z=zValue.toString();
        boolean insertdata=db.addData(save_x,save_y,save_z);
        if(insertdata){
            Toast.makeText(this,"Data inserted",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"Data NOT inserted",Toast.LENGTH_SHORT).show();
        }
    }
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

}
