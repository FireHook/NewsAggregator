package com.example.testpageparser;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "mydatabase12.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "t_News";
    public static final String ID_COLUMN = "_id";
    public static final String TITLE_COLUMN = "n_Title";
    public static final String CONTENT_COLUMN = "n_Content";
    public static final String IMG_URL_COLUMN = "n_ImgUrl";

    private String columnNames[] = { ID_COLUMN, TITLE_COLUMN, CONTENT_COLUMN, IMG_URL_COLUMN };

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME
                + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + TITLE_COLUMN + " TEXT, "
                + CONTENT_COLUMN + " TEXT, " + IMG_URL_COLUMN + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
