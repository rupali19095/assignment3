package com.rupali.androdev.assignment3;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class microphone extends AppCompatActivity {

    private Button stop_record;
    private Button play;
    private Button  record;
    private Button stop_play;
    MediaRecorder mediaRecorder;
    String path_to_save= "";
    MediaPlayer mediaPlayer;
    final int Request_permission_code=1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        play=findViewById(R.id.play);
        stop_play=findViewById(R.id.stop_play);
        stop_record=findViewById(R.id.stop_record);
        record=findViewById(R.id.record);



        if(checkPermissionFromDevice()){
            record.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    path_to_save= Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+ UUID.randomUUID().toString()+"audio_record.3gp";
                    setUpRecorder();
                    try{
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    play.setEnabled(false);
                    stop_play.setEnabled(false);

                    Toast.makeText(microphone.this,"Recording",Toast.LENGTH_SHORT).show();

                }
            });

            stop_record.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mediaRecorder.stop();
                    stop_record.setEnabled(false);
                    play.setEnabled(true);
                    stop_play.setEnabled(true);
                }
            });

            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    stop_play.setEnabled(true);
                    stop_record.setEnabled(false);
                    record.setEnabled(false);

                    mediaPlayer=new MediaPlayer();
                    try {
                        mediaPlayer.setDataSource(path_to_save);
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mediaPlayer.start();
                    Toast.makeText(microphone.this,"Playing..",Toast.LENGTH_SHORT).show();
                }
            });
            stop_play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    stop_record.setEnabled(false);
                    record.setEnabled(true);
                    stop_play.setEnabled(false);
                    play.setEnabled(true);

                    if(mediaPlayer!=null){
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        setUpRecorder();
                    }
                }
            });

        }
        else{
            request_permission();

        }


    }

    private void setUpRecorder() {
        mediaRecorder=new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(path_to_save);
    }

    private void request_permission() {
        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO,
        }, Request_permission_code);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case Request_permission_code: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show();
                else{
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();

                }

            }
            break;
        }
    }

    private boolean checkPermissionFromDevice(){
        int write_external_storage_result= ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int record_audio_result=ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO);
        return write_external_storage_result== PackageManager.PERMISSION_GRANTED&&
                record_audio_result==PackageManager.PERMISSION_GRANTED;
    }

}

