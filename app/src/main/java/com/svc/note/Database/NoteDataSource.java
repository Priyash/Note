package com.svc.note.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PRIYASH on 03-07-2016.
 */
public class NoteDataSource {

    public static final String TAG = NoteDataSource.class.getName();

    private static NoteDataSource noteDataSource;
    private SQLiteOpenHelper dbHelper;
    private SQLiteDatabase database;

    private NoteDataSource(Context context)
    {
        dbHelper =  new DatabaseHandler(context);
    }

    public synchronized static NoteDataSource getInstance(Context context)
    {
        if(noteDataSource==null)
        {
            noteDataSource = new NoteDataSource(context);
        }
        return noteDataSource;
    }

    public synchronized void open()
    {
        Log.i(TAG, "Database opened");
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        Log.i(TAG, "Database closed");
        dbHelper.close();
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    public void setDatabase(SQLiteDatabase database) {
        this.database = database;
    }


    private static final String[] Task_Column = {
            DatabaseHandler.TASK,
            DatabaseHandler.TASK_DESCRIPTION,
            DatabaseHandler.TASK_PRIORITY,
            DatabaseHandler.TASK_DUE_DATE,
            DatabaseHandler.TASK_REMINDER,
            DatabaseHandler.TASK_COMMENTS,
            DatabaseHandler.TASK_LABEL,
            DatabaseHandler.TASK_NOTIFICATION,
            DatabaseHandler.TASK_TAGGED_PEOPLE
    };


    public TaskData createTaskData(TaskData taskData)
    {
        ContentValues values = new ContentValues();
        values.put(DatabaseHandler.TASK,taskData.getTASK());
        values.put(DatabaseHandler.TASK_DESCRIPTION,taskData.getTASK_DESCRIPTION());
        values.put(DatabaseHandler.TASK_PRIORITY,taskData.getTASK_PRIORITY());
        values.put(DatabaseHandler.TASK_DUE_DATE,taskData.getTASK_DUE_DATE());
        values.put(DatabaseHandler.TASK_REMINDER,taskData.getTASK_REMINDER());
        values.put(DatabaseHandler.TASK_COMMENTS,taskData.getTASK_COMMENTS());
        values.put(DatabaseHandler.TASK_LABEL,taskData.getTASK_LABEL());
        values.put(DatabaseHandler.TASK_NOTIFICATION,taskData.getTASK_NOTIFICATION());
        values.put(DatabaseHandler.TASK_TAGGED_PEOPLE,taskData.getTASK_TAGGED_PEOPLE());

        long insertID = database.insertWithOnConflict(DatabaseHandler.TASK_TABLE,null,values,SQLiteDatabase.CONFLICT_IGNORE);
        if(insertID!=0)
        {
            taskData.setROW_ID(insertID);
        }

        return taskData;
    }


    public List<TaskData>getTaskData()
    {
        Cursor cursorTaskData = database.query(DatabaseHandler.TASK_TABLE,Task_Column,null,null,null,null,null);
        List<TaskData>list = cursorToTaskData(cursorTaskData);
        if(list!=null&& list.size()>0)
        {
            return list;
        }
        return null;
    }


    List<TaskData> cursorToTaskData(Cursor cursor)
    {
        List<TaskData>list = new ArrayList<TaskData>();
        if(cursor.getCount()>0)
        {
            while(cursor.moveToNext())
            {
                TaskData taskData = new TaskData();
                taskData.setTASK(cursor.getString(cursor.getColumnIndex(DatabaseHandler.TASK)));
                taskData.setTASK_DESCRIPTION(cursor.getString(cursor.getColumnIndex(DatabaseHandler.TASK_DESCRIPTION)));
                list.add(taskData);
            }
        }
        return list;
    }

    public void deleteAllTask()
    {
        database.delete(DatabaseHandler.TASK_TABLE,null,null);
    }
}
