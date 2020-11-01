package com.plamy.taskforcer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class TaskDatabase {
    public static final String TAG = "TaskDatabase";
    private static TaskDatabase database;
    public static String DATABASE_NAME = "task.db";
    public static String TABLE_TASK_INFO = "TASK_INFO";
    public static int DATABASE_VERSION = 1;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Context context;

    private TaskDatabase(Context context) {
        this.context = context;
    }

    public static TaskDatabase getInstance(Context context) {
        if (database == null) {
            database = new TaskDatabase(context);
        }
        return database;
    }

    public boolean open() {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return true;
    }

    public void close() {
        db.close();
        database = null;
    }

    public Cursor rawQuery(String SQL) {
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(SQL, null);
        } catch(Exception ex) {
            Log.e(TAG, "Exception in executeQuery", ex);

        }
        return cursor;
    }

    public boolean execSQL(String SQL) {
        try {
            db.execSQL(SQL);
        } catch(Exception ex) {
            Log.e(TAG, "Exception in executeQuery", ex);
            return false;
        }
        return true;
    }

    private class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            dropTable(db);
            createTable(db);

            // TEST
            insertRecord( db, "title_test1", "2020-00-00", "2020-12-31",
                    "content_test1", 0);
            insertRecord( db, "title_test211", "2020-00-00", "2020-12-31",
                    "content_test1", 0);
            insertRecord( db, "title_tes12t1", "2020-00-00", "2020-12-31",
                    "content_test1", 0);
            insertRecord( db, "title_tes12t1", "2020-00-00", "2020-12-31",
                    "content_test1", 0);
            insertRecord( db, "title_tes12t1", "2020-00-00", "2020-12-31",
                    "content_test1", 0);
            insertRecord( db, "title_t41est1", "2020-00-00", "2020-12-31",
                    "content_test1", 1);
            insertRecord( db, "title_t124est1", "2020-00-00", "2020-12-31",
                    "content_test1", 0);
            insertRecord( db, "title_te214st1", "2020-00-00", "2020-12-31",
                    "content_test1", 1);
            insertRecord( db, "title_t124124est1", "2020-00-00", "2020-12-31",
                    "content_test1", 0);
            insertRecord( db, "title_214test1", "2020-00-00", "2020-12-31",
                    "content_test1", 0);
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            TODO : onUpgrade
//            if (oldVersion < 2) {   // version 1
//
//            }
        }

        private void insertRecord(SQLiteDatabase db, String title, String startDate, String endDate,
                                  String content, int completedFlag) {
            String CREATE_SQL = "insert into " + TABLE_TASK_INFO
                    + "(TITLE, CONTENT, START_DATE, END_DATE, COMPLETED_FLAG)"
                    + " values ('" + title + "', '" + startDate + "', '" + endDate + "', '"
                    + content + "', '" + completedFlag + "');";
            try {
                db.execSQL(CREATE_SQL);
            } catch(Exception ex) {
                Log.e(TAG, "Exception in executing insert SQL.", ex);
            }
        }

        private void createTable(SQLiteDatabase db) {
            String CREATE_SQL = "create table " + TABLE_TASK_INFO + "("
                    + "  _id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, "
                    + "  TITLE TEXT, "
                    + "  CONTENT TEXT, "
                    + "  START_DATE TEXT, "
                    + "  END_DATE TEXT, "
                    + "  COMPLETED_FLAG INTEGER "
                    + ")";
            try {
                db.execSQL(CREATE_SQL);
            } catch(Exception ex) {
                Log.e(TAG, "Exception in CREATE_SQL", ex);
            }
        }

        private void dropTable(SQLiteDatabase db) {
            String DROP_SQL = "drop table if exists " + TABLE_TASK_INFO;
            try {
                db.execSQL(DROP_SQL);
            } catch(Exception ex) {
                Log.e(TAG, "Exception in DROP_SQL", ex);
            }
        }
    }

    public void insertRecord(String title, String startDate, String endDate, String content,
                             int completedFlag) {
        String CREATE_SQL = "insert into " + TABLE_TASK_INFO
                + "(TITLE, CONTENT, START_DATE, END_DATE, COMPLETED_FLAG)"
                + " values ('" + title + "', '" + startDate + "', '" + endDate + "', '"
                + content + "', '" + completedFlag + "');";
        try {
            db.execSQL(CREATE_SQL);
        } catch(Exception ex) {
            Log.e(TAG, "Exception in executing insert SQL.", ex);
        }
    }

    public ArrayList<TaskInfo> selectAll() {
        ArrayList<TaskInfo> result = new ArrayList<TaskInfo>();

        try {
            Cursor cursor = db.rawQuery(
                    "select TITLE, CONTENT, START_DATE, END_DATE, COMPLETED_FLAG from "
                            + TABLE_TASK_INFO, null);
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                String title = cursor.getString(0);
                String startDate = cursor.getString(1);
                String endDate = cursor.getString(2);
                String content = cursor.getString(3);
                int completedFlag = cursor.getInt(4);

                TaskInfo info = new TaskInfo(title, startDate, endDate, content, completedFlag);
                result.add(info);
            }

        } catch(Exception ex) {
            Log.e(TAG, "Exception in executing select SQL.", ex);
        }

        return result;

    }
}
