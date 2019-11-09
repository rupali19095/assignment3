package com.rupali.androdev.assignment3;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListSavedData1 extends AppCompatActivity {
    DatabaseHelper1 db1;
    private ListView lv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_saved_data1);
        lv1=findViewById(R.id.listview);
        db1=new DatabaseHelper1(this);
         
        display_data();
    }

    private void display_data() {
        Cursor data= db1.getData();
        ArrayList list_data=new ArrayList();
        while(data.moveToNext()){
            list_data.add(data.getString(1));
        }
        ListAdapter adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,list_data);
        lv1.setAdapter(adapter);
    }
}
