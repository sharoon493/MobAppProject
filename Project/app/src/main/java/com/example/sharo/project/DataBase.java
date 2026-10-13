package com.example.sharo.project;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Time;
import java.util.Date;

public class DataBase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "database";
    private static final int DATABASE_VERSION = 1;

    int Record_id =getRecord_id();

    private int getRecord_id(){
        SQLiteDatabase db;
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Note ", null);

        return cursor.getCount();

    }

    Cursor getRecords() {
        SQLiteDatabase db;
        db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Note Order by R_id DESC ", null);

        return cursor;
    }

    Cursor SearchRecords(String key1) {
        SQLiteDatabase db;
        db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Note WHERE content LIKE key1 " , null);

        return cursor;
    }

    void DeleteRecords(int R_id) {
        SQLiteDatabase db;
        db = getWritableDatabase();
        Log.d("Mytag","DELETE work2.1");
        db.delete("Note", "R_id = "+ R_id  , null );
//        String query_ = "DELETE FROM Note WHERE title = "+title+" AND content = "+content+";";
//        db.execSQL(query_);
        Log.d("Mytag","DELETE work2.2");
    }

    void insertNameAndScore(String name, String content,int color) {
        SQLiteDatabase db;
        db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("R_id", Record_id);
        cv.put("title", name);
        cv.put("content", content);
        cv.put("date", ((Date)new java.sql.Date((long)System.currentTimeMillis())).toString());
        cv.put("time", ((Time)new Time((long)System.currentTimeMillis())).toString());
        cv.put("color", color);
        Log.d("Mytag",Record_id+"");
        Record_id++;

        db.insert("Note", null, cv);
    }

    void UpdateRecord(String name, String content,int color,int R_id) {
        SQLiteDatabase db;
        db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("title", name);
        cv.put("content", content);
        cv.put("color", color);

        db.update("Note",cv,"R_id = "+ R_id, null);
    }

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d("Mytag", "Inside onCreate");
        String query = "CREATE TABLE MySaved (Req_id Integer PRIMARY KEY,UserName Text, Req_Descr Text, date Date, time Time" +
                ",Req_Reason Text,MobileNumber Text,City Text,Country Text,Image Text)";

        String query2 = "CREATE TABLE My (Req_id Integer PRIMARY KEY,UserName Text, Req_Descr Text, date Date, time Time" +
                ",Req_Reason Text,MobileNumber Text,City Text,Country Text,Image Text)";
        db.execSQL(query);
        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("onUpgrade", "onUpgrade called");

        switch (oldVersion) {

        }
    }
}