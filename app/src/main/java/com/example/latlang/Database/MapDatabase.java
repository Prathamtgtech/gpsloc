package com.example.latlang.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MapDatabase extends SQLiteOpenHelper {
    public final static String DBNAME="Map.db";

    public MapDatabase(@Nullable Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "Create TABLE mapdata(_id INTEGER PRIMARY KEY AUTOINCREMENT,Google_Map TEXT,Gmap_Diffrence TEXT,Osm_Map TEXT,Osm_Diffrence TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP Table if exists mapdata");
    }

    public boolean insertData(String Google_Map , double Gmap_Diffrence , String Osm_Map , double Osm_Diffrence ){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("Google_Map",Google_Map);
        values.put("Gmap_Diffrence",Gmap_Diffrence);
        values.put("Osm_Map",Osm_Map);
        values.put("Osm_Diffrence",Osm_Diffrence);
        long result=db.insert("mapdata",null,values);
        if (result==-1) return false;
        else  return true;
    }
}
