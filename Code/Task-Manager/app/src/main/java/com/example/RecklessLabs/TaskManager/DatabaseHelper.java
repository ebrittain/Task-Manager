package com.example.RecklessLabs.TaskManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tasklist.db";

    private static final String TABLE_NAME = "TASKLIST";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_NAME = "NAME";
    private static final String COLUMN_TIME = "TIMEOFF";
    private static final String COLUMN_DESC = "DESCRIPTION";
    private static final String COLUMN_POINTS = "POINTS";

    private Type t = new TypeToken<ArrayList<Long>>(){}.getType();


    private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT NOT NULL, " +
            COLUMN_TIME + " INTEGER, "+
            COLUMN_DESC + " TEXT, "+
            COLUMN_POINTS + " TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name, String desc){
        SQLiteDatabase db = this.getWritableDatabase();
        Gson gson = new Gson();

        ArrayList<Long> points = new ArrayList<>();
        points.add(0L);

        if(!name.isEmpty()) {
            if (desc.isEmpty()) desc = "No Description";
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, name);
            cv.put(COLUMN_TIME, 0);
            cv.put(COLUMN_DESC, desc);
            cv.put(COLUMN_POINTS, gson.toJson(points, t));

            return db.insert(TABLE_NAME, null, cv) != -1;
        }
        else return false;
    }

    public boolean updateData(String oldName, String newName, String desc){
        SQLiteDatabase db = this.getWritableDatabase();

        if(!newName.isEmpty()) {
            if(desc.isEmpty()) desc = "No Description";
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, newName);
            cv.put(COLUMN_DESC, desc);

            return db.update(TABLE_NAME, cv, "NAME = ?", new String[]{oldName}) != -1;
        }
        else return false;
    }

    public boolean deleteData(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "NAME = ?", new String[] {name}) !=-1;
    }

    public boolean resetTime(String name){
        String desc = getDescription(name);

        deleteData(name);
        return insertData(name,desc);
    }

    public boolean stop(String name, long offset){
        SQLiteDatabase db = this.getWritableDatabase();
        Gson gson = new Gson();
        ArrayList<Long> points = getPoints(name);
        Long diff = offset - getTime(name);

        points.add(diff);

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TIME, offset);
        cv.put(COLUMN_POINTS, gson.toJson(points, t));

        return db.update(TABLE_NAME, cv, "NAME = ?", new String[] {name}) !=-1;
    }


    public long getTime(String name){

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor totalCursor = db.rawQuery("SELECT " + COLUMN_TIME + " FROM " + TABLE_NAME + " WHERE NAME = ?", new String[]{name});
        totalCursor.moveToFirst();

        return totalCursor.getLong(0);
    }

    public ArrayList<Long> getPoints(String name){

        SQLiteDatabase db = this.getWritableDatabase();
        Gson gson = new Gson();

        Cursor totalCursor = db.rawQuery("SELECT " + COLUMN_POINTS + " FROM " + TABLE_NAME + " WHERE NAME = ?", new String[]{name});
        totalCursor.moveToFirst();

        return gson.fromJson(totalCursor.getString(0), t);
    }

    public ArrayList<String> getTasks(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> tasks = new ArrayList<>();

        Cursor res = db.rawQuery("SELECT "+ COLUMN_NAME+" FROM "+ TABLE_NAME, null);

        for(res.moveToFirst();!res.isAfterLast();res.moveToNext()){
            tasks.add(res.getString(0));
        }

        return tasks;
    }

    public String getDescription(String name){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor totalCursor = db.rawQuery("SELECT " + COLUMN_DESC + " FROM " + TABLE_NAME + " WHERE NAME = ?", new String[]{name});
        totalCursor.moveToFirst();

        return totalCursor.getString(0);
    }

    public ArrayList<Task> getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Task> tasks = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_NAME, null);

        if(cursor.getCount()==0)return new ArrayList<>();

        while (cursor.moveToNext()){
            Task task = new Task(
                    cursor.getString(1),
                    cursor.getLong(2),
                    cursor.getString(3));

            tasks.add(task);
        }

        return tasks;
    }





}
