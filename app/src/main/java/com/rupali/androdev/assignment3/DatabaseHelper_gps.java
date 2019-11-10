package com.rupali.androdev.assignment3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by dell on 11/10/2019.
 */

public class DatabaseHelper_gps extends SQLiteOpenHelper {
    private static final String Table_Name="gps_data";
    private static final String col1="ID";
    private static  final String col2="lattitude_longitude";

    public DatabaseHelper_gps(Context context) {
        super(context,Table_Name,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_Table="CREATE TABLE "+ Table_Name + "(" + col1 +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                col2+" TEXT) ";//+
                //col3+" TEXT) ";
        Log.d("DATABASE ON CREATE","adding COLUMNS "+col2+""+"to "+Table_Name);
        sqLiteDatabase.execSQL(create_Table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Table_Name);
        onCreate(db);
    }
    public boolean addData(String x){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(col2, x);
        Log.d("DATABASE","adding data "+x+""+"to "+Table_Name);
        long result=db.insert(Table_Name,null,cv);
        if (result==-1){
            return false;
        }
        else{
            return true;
        }

    }
    public Cursor getData(){
        SQLiteDatabase db=this.getWritableDatabase();

        Cursor data=db.rawQuery("SELECT * FROM "+Table_Name,null);
        return  data;
    }
}
