package com.example.finalprojectjanith02;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SimpleCursorAdapter;


public class databasehelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "needhelp.db";
    //create tables
    public static final String TABLE_NAME = "help_table";
    public static final String TABLE1 = "Users_table";
    //create table coloumn
    public static final String COL_1 = "ID";
    public static final String COL_2 = "TYPEOFPETS";
    public static final String COL_3 = "HOWMUTCH";
    public static final String COL_4 = "CITY";
    public static final String COL_5 = "User_ID";
    private static Context context;

    public databasehelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        // Create Database and Database table
        SQLiteDatabase db = this.getWritableDatabase();
        this.context = context;
    }

    @Override
    //create table
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TYPEOFPETS TEXT, HOWMUTCH TEXT, CITY TEXT)"
        );
        db.execSQL("create table " + TABLE1 + "(User_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, address TEXT, phone TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE1);
        onCreate(db);
    }
//set insert data
    public boolean insertData(String typeofpets, String howmutch, String city) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, typeofpets);
        contentValues.put(COL_3, howmutch);
        contentValues.put(COL_4, city);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }
    //set insert data for add user
        public boolean insert(String name, String address, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("address",address);
        contentValues.put("phone",phone);
        long result = db.insert(TABLE1, null,contentValues);
        if (result ==-1)
            return false;
        else
            return true;
    }
    //view all data
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("Select * from " + TABLE_NAME, null);
        return result;
    }
    //view all data to add user
    public Cursor getdata(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("Select * from " + TABLE1, null);
        return result;
    }
//update data
    public boolean updateData(String id, String typeofpets, String howmutch, String city){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,typeofpets);
        contentValues.put(COL_3,howmutch);
        contentValues.put(COL_4,city);
        db.update(TABLE_NAME, contentValues, "id = ?", new String[] {id});
        return true;
    }
    public boolean update1(String id, String name, String address, String phone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_5,id);
        contentValues.put("name",name);
        contentValues.put("address",address);
        contentValues.put("phone",phone);
        db.update(TABLE1, contentValues, "id = ?", new String[] {id});
        return true;
    }
//delete data
    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return  db.delete(TABLE_NAME, "ID = ?", new String[]{id});}
}

