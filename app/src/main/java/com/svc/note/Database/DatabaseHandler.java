package com.svc.note.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by PRIYASH on 03-07-2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Note.db";
    private static final int DATABASE_VERSION = 2;
    private static final String tag = DatabaseHandler.class.getName();


    public static final String TASK_TABLE = "Task_Table";

    public static final String TASK_ID = "Task_ID";
    public static final String TASK = "Task";
    public static final String TASK_DESCRIPTION = "Description";
    public static final String TASK_PRIORITY = "Priority";
    public static final String TASK_DUE_DATE = "Due_Date";
    public static final String TASK_REMINDER = "Reminder";
    public static final String TASK_COMMENTS = "Comments";
    public static final String TASK_LABEL = "Label";
    public static final String TASK_NOTIFICATION = "Notification";
    public static final String TASK_TAGGED_PEOPLE = "Tagged_People";

    private static final String SQL_CREATE_TABLE_TASK = "CREATE TABLE " + TASK_TABLE  + "("
            + TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , "
            + TASK + " TEXT , "
            + TASK_DESCRIPTION + " TEXT , "
            + TASK_PRIORITY + " TEXT, "
            + TASK_DUE_DATE + " TEXT, "
            + TASK_REMINDER + " TEXT, "
            + TASK_COMMENTS + " TEXT, "
            + TASK_LABEL + " TEXT , "
            + TASK_NOTIFICATION + " TEXT , "
            + TASK_TAGGED_PEOPLE + " TEXT "
            + ")";



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d("TABLE",SQL_CREATE_TABLE_TASK);
        db.execSQL(SQL_CREATE_TABLE_TASK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SQL_CREATE_TABLE_TASK);
        onCreate(db);

    }
}
