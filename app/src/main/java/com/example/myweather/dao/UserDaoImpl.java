package com.example.myweather.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.myweather.entity.User;
import com.example.myweather.util.MyHelper;

import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private Context c;
    public UserDaoImpl(Context c) {
        this.c = c;
    }


    @Override
    public void addUser(User user) {

        MyHelper myHelper = new MyHelper(c);
        SQLiteDatabase db = myHelper.getWritableDatabase();

        String s1 = user.getUsername().toLowerCase();
        String s2 = user.getPassword();

        ContentValues cv = new ContentValues();
        cv.put("username",s1);
        cv.put("password",s2);
        db.insert("userinfo",null,cv);


        db.close();
    }

    @Override
    public boolean checkUser(String username, String password) {

        MyHelper myHelper = new MyHelper(c);
        SQLiteDatabase db = myHelper.getReadableDatabase();


        String sql = "SELECT * from userinfo where username= '"+username.toLowerCase() + "' AND `password` = '" + password+"'";
        Log.e("sql",""+sql);
//        String sql = "SELECT * from userinfo where username=boris AND password = 1111111";
        Cursor cursor = db.rawQuery(sql,null);

        if (cursor.getCount() > 0) {
            Log.e("login:","ok");
            return true;
        } else {
            Log.e("login:","false");
            return false;
        }
    }

    @Override
    public boolean validateUsername(String username) {

        MyHelper myHelper = new MyHelper(c);
        SQLiteDatabase db = myHelper.getReadableDatabase();

        String sql = "SELECT * from userinfo where username= '"+username.toLowerCase()+"'";
        Log.e("sql",""+sql);

        Cursor cursor = db.rawQuery(sql,null);

        if (cursor.getCount() > 0) {
            Log.e("login:","duplicate");
            return false;
        } else {
            Log.e("login:","unique");
            return true;
        }
    }

    @Override
    public Boolean checkSavedCity(String username, String city) {
        MyHelper myHelper = new MyHelper(c);
        SQLiteDatabase db = myHelper.getReadableDatabase();

        String sql = "SELECT * from savedcity where username= '"+username.toLowerCase() + "' AND `city` = '" + city+"'";

        Cursor cursor = db.rawQuery(sql,null);

        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public void saveCity(String username, String city) {

        MyHelper myHelper = new MyHelper(c);
        SQLiteDatabase db = myHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("city",city);
        cv.put("username",username);

        if(!checkSavedCity(username, city)){
            db.insert("savedcity",null,cv);
            db.close();
        }
    }

    @Override
    public void removeCity(String username, String city) {

        MyHelper myHelper = new MyHelper(c);
        SQLiteDatabase db = myHelper.getWritableDatabase();
//        String sql = "delete from savedcity where username = '"+ username + "'";

        String sql = "delete from savedcity where username= '"+username + "' AND `city` = '" + city+"'";

        try {
            db.execSQL(sql);
        }
        catch (Exception e) {
            Toast.makeText(c, "Remove not successful", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    @Override
    public List<String> getCitylist(String username) {
        List<String> citylist = new ArrayList<String>();

        MyHelper myHelper = new MyHelper(c);
        SQLiteDatabase db = myHelper.getReadableDatabase();
        String sql = "select city from savedcity where username = '"+username+"'";
        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()){
            String s0 = cursor.getString(0);
            citylist.add(s0);
        }

        return citylist;

    }
}
