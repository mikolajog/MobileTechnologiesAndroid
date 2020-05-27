package com.agh.expenseapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    /*
    Class enabling SQLite database operations
     */


    public DatabaseHelper(Context context) {
        super(context, "Login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creating tables users and balance
        db.execSQL("Create table user(email text primary key, password text)");
        db.execSQL("Create table balance(id integer primary key autoincrement not null, email text,  amount text, purpose txt)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Dropping tables
        db.execSQL("drop table if exists user");
        db.execSQL("drop table if exists balance");
    }


    public boolean insertUser(String email, String password){
        // inserting to user table
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        long ins = db.insert("user", null, contentValues);
        return ins != -1;
    }


    public boolean insertPositiveBalance(String email, String amount, String purpose){
        //inserting to balance table (earning)
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("amount", amount);
        contentValues.put("purpose", purpose);
        long ins = db.insert("balance", null, contentValues);
        return ins != -1;
    }

    //inserting to balance table (spending)
    public boolean insertNegativeBalance(String email, String amount, String purpose){
        //inserting to balance table (spending)
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("amount", "-"+amount);
        contentValues.put("purpose", purpose);
        long ins = db.insert("balance", null, contentValues);
        return ins != -1;
    }


    public Cursor getBalance(String email){
        //getting values from balance for particular user
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM balance WHERE email = '"+email+"'", null);
        return c;
    }


    public void deleteBalance(String email){
        //deleting from balance for particular user
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from "+"balance"+" where email='"+email+"'");
    }


    public String getBalanceSum(String email){
        //deleting from balance sum for particular user
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT sum(amount) FROM balance WHERE email = '"+email+"'", null);
        c.moveToFirst();
        return c.getString(0);
    }



    public Boolean checkIfEmailExists(String email){
        //check if email exists;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where email=?", new String[]{email});
        return cursor.getCount() <= 0;
    }


    public Boolean checkEmailAndPasswordIsCorrect(String email, String password){
        //check email and password
        if(email.isEmpty() || password.isEmpty())
            return false;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where email=? and password=?", new String[]{email, password});
        return cursor.getCount() > 0;
    }

}
