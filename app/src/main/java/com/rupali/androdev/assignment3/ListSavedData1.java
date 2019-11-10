package com.rupali.androdev.assignment3;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class ListSavedData1 extends AppCompatActivity {
    DatabaseHelper1 db1;
    private ListView lv1;
    Button export;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_saved_data1);
        lv1=findViewById(R.id.listview);
        db1=new DatabaseHelper1(this);
        export=findViewById(R.id.export_data1);

        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                export(view);
            }
        });

        display_data();
    }

    private void display_data() {
        Cursor data= db1.getData();
        ArrayList list_data=new ArrayList();
        while(data.moveToNext()){
                list_data.add(data.getString(1));
                list_data.add(data.getString(2));
                list_data.add(data.getString(3));
        }
        ListAdapter adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,list_data);
        lv1.setAdapter(adapter);
    }
    public void export(View v){
        StringBuilder export_list=new StringBuilder();
        int i=0;
        while(i< lv1.getCount()){
            export_list.append(lv1.getAdapter().getItem(i).toString());
            i++;
        }

        Log.d("export list",export_list.toString());
        try{
            FileOutputStream out=openFileOutput("data_acc.csv", Context.MODE_PRIVATE);
            out.write((export_list.toString()).getBytes());

            Context context=getApplicationContext();
            File filelocation= new File(getFilesDir(),"data_acc.csv");
            Uri path= FileProvider.getUriForFile(context,"com.rupali.androidev.assignment3.fileprovider",filelocation);
            Intent fileintent=new Intent(Intent.ACTION_SEND);
            fileintent.setType("text/csv");
            fileintent.putExtra(Intent.EXTRA_SUBJECT,"Data");
            fileintent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fileintent.putExtra(Intent.EXTRA_STREAM,path);
            startActivity(Intent.createChooser(fileintent,"Send mail"));


        }
        catch(Exception e){
                        e.printStackTrace();
        }
    }
}
