package com.agh.expenseapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, "Login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table user(email text primary key, password text)");
        db.execSQL("Create table balance(id integer primary key autoincrement not null, email text,  amount text, purpose txt)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists user");
        db.execSQL("drop table if exists balance");
    }

    // inserting to user table
    public boolean insertUser(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        long ins = db.insert("user", null, contentValues);
        if(ins==-1){return false;}
        else{return true;}
    }

    //inserting to balance table (earning)
    public boolean insertPositiveBalance(String email, String amount, String purpose){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("amount", amount);
        contentValues.put("purpose", purpose);
        long ins = db.insert("balance", null, contentValues);
        if(ins==-1){return false;}
        else{return true;}
    }

    //inserting to balance table (spending)
    public boolean insertNegativeBalance(String email, String amount, String purpose){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("amount", "-"+amount);
        contentValues.put("purpose", purpose);
        long ins = db.insert("balance", null, contentValues);
        if(ins==-1){return false;}
        else{return true;}
    }

    //getting values from balance for particular user
    public Cursor getBalance(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM balance WHERE email = '"+email+"'", null);
        return c;
    }

    //deleting from balance for particular user
    public void deleteBalance(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from "+"balance"+" where email='"+email+"'");
    }

    //deleting from balance sum  for particular user
    public String getBalanceSum(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT sum(amount) FROM balance WHERE email = '"+email+"'", null);
        c.moveToFirst();
        return c.getString(0);
    }


    //check if email exists;
    public Boolean chkemail(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where email=?", new String[]{email});
        if(cursor.getCount()>0){return false;}
        else{return true;}
    }

    //check email and password
    public Boolean emailpassword(String email, String password){
        if(email.isEmpty() || password.isEmpty())
            return false;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where email=? and password=?", new String[]{email, password});
        return cursor.getCount() > 0;
    }

}
