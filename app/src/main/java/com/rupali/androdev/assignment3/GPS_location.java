package com.rupali.androdev.assignment3;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GPS_location extends AppCompatActivity {
    private Button start,stop;
    private TextView txt;
    private BroadcastReceiver broadcastReceiver;
    private DatabaseHelper_gps db1;
    Button save_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_location);
        start=findViewById(R.id.button);
        stop=findViewById(R.id.btn);
        txt=findViewById(R.id.coordinates);
        db1=new DatabaseHelper_gps(this);
        save_data=findViewById(R.id.save_data_gps);

        if(!runtime_permissions())
        {
            enable_buttons();
        }
        save_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ListSavedDataGPS.class);
                startActivity(i);
            }
        });
    }

    private void enable_buttons() {
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),GPS_Service.class);
                startService(i);
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),GPS_Service.class);
                stopService(i);
            }
        });

    }

    private boolean runtime_permissions() {
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},100);

            return true;
        }
        return false;
    }
    public  void onRequestPermissionsResult(int requestCode,String [] persmissions,int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, persmissions, grantResults);
        if (requestCode==100)
        {
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                enable_buttons();
            }
            else
            {
                runtime_permissions();
            }
        }
    }
    public void onResume(){
        super.onResume();
        if(broadcastReceiver==null){
            broadcastReceiver= new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    txt.append("\n"+ intent.getExtras().get("latitude_longitude"));
                    AddData();
                }
            };
        }
        registerReceiver(broadcastReceiver,new IntentFilter("Location update"));
    }
    public void onDestroy(){
        super.onDestroy();
        if(broadcastReceiver!=null){
            unregisterReceiver(broadcastReceiver);
        }
    }
    public void AddData() {
        String save_data = txt.getText().toString();
        boolean  insertdata = db1.addData(save_data);
        if (insertdata) {
            Toast.makeText(this, "Data inserted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Data Not inserted", Toast.LENGTH_SHORT).show();
        }
    }


}


