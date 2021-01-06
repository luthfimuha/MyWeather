package com.example.myweather.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyHelper extends SQLiteOpenHelper {

    public MyHelper(@Nullable Context context) {
        super(context, "myweather.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table userinfo(_id integer primary key autoincrement, username text, password text)";
        db.execSQL(sql);
        sql = "CREATE TABLE savedcity (\n" +
                "    _id integer primary key autoincrement," +
                "    city text," +
                "    username text);";

        db.execSQL(sql);

        Log.e("demo", "table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        Log.e("demo", "getWritableDatabase");
        return super.getWritableDatabase();
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        Log.e("demo", "getReadableDatabase");
        return super.getReadableDatabase();
    }

}
