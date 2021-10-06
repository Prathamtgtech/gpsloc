package com.example.latlang.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
        String sql = "Create TABLE mapdata(_id INTEGER PRIMARY KEY AUTOINCREMENT,G_LAT TEXT,G_LANG TEXT,M_LAT TEXT,M_LANG TEXT,DIFF TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP Table if exists mapdata");
    }

    public boolean insertData(double g_LAT , double g_LANG , double m_LAT , double m_LANG, double dIFF ){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("G_LAT",g_LAT);
        values.put("G_LANG",g_LANG);
        values.put("M_LAT",m_LAT);
        values.put("M_LANG",m_LANG);
        values.put("DIFF",dIFF);
        long result=db.insert("mapdata",null,values);
        if (result==-1) return false;
        else  return true;
    }

    public Cursor FetchCustData(){
        SQLiteDatabase db=this.getWritableDatabase();
        String qry="Select * from mapdata";
        Cursor cursor=db.rawQuery(qry,null);
        return cursor;
    }

}
