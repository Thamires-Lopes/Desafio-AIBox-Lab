package com.example.library.persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "libraryOfFavorites.db";
    public static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TABLE = " CREATE TABLE " +
                BookContract.BookColumns.TABLE_BOOK + " (" +
                BookContract.BookColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BookContract.BookColumns.TABLE_BOOK_ISBN + " TEXT NOT NULL" +
                ");";
        db.execSQL(SQL_CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BookContract.BookColumns.TABLE_BOOK);
        onCreate(db);

    }
}