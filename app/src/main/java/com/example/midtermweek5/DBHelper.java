package com.example.midtermweek5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "student.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE = "students";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE + " (id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT, student_id TEXT, age INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    public boolean addStudent(String name, String sid, int age) {
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("student_id", sid);
        cv.put("age", age);
        return getWritableDatabase().insert(TABLE, null, cv) != -1;
    }

    public int updateStudent(String name, String sid, int age) {
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("age", age);
        return getWritableDatabase().update(TABLE, cv, "student_id=?", new String[]{sid});
    }

    public int deleteStudent(String sid) {
        return getWritableDatabase().delete(TABLE, "student_id=?", new String[]{sid});
    }

    public String getAll() {
        StringBuilder sb = new StringBuilder();
        Cursor c = getReadableDatabase().rawQuery(
                "SELECT name, student_id, age FROM " + TABLE, null);
        while (c.moveToNext()) {
            sb.append(c.getString(0)).append(" | ")
                    .append(c.getString(1)).append(" | ")
                    .append(c.getInt(2)).append("\n");
        }
        c.close();
        return sb.length() == 0 ? "No data" : sb.toString();
    }
}