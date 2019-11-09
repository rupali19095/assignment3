package com.rupali.androdev.assignment3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.w3c.dom.Text;

import static android.os.Build.ID;

/**
 * Created by dell on 11/6/2019.
 */

public class DatabaseHelper1 extends SQLiteOpenHelper {
    private static final String Table_Name="Accelerometer_data";
    private static final String col1="ID";
    private static  final String col2="x_cor";
    private static  final String col3="y_cor";
    private static  final String col4="z_cor";

    public DatabaseHelper1(Context context) {
        super(context,Table_Name,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_Table="CREATE TABLE "+ Table_Name +"(ID INTEGER PRIMARY KEY AUTOINCREMENT,"+
                col2+"Text, "+
                col3+"Text, "+
                col4+"Text)";
        Log.d("DATABASE ON CREATE","adding COLUMNS "+col2+""+col3+""+col4+"to "+Table_Name);
        sqLiteDatabase.execSQL(create_Table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Table_Name);
        onCreate(db);
    }
    public boolean addData(String x,String y,String z){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(col2,x);
        cv.put(col3,y);
        cv.put(col4,z);
        Log.d("DATABASE","adding data "+x+""+y+""+z+"to "+Table_Name);
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
