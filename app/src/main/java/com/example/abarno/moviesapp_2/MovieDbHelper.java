package com.example.abarno.moviesapp_2;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MovieDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favouriteList.db";

    private static final int DATABASE_VERSION = 4;

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public MovieDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_TABLE_CREATE = "CREATE TABLE " + MovieContract.MovieList.TABLE_NAME + " ("
                + MovieContract.MovieList._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + MovieContract.MovieList.MOVIE_ID + " INTEGER NOT NULL ,"
                + MovieContract.MovieList.MOVIE_NAME + " TEXT NOT NULL,"
                + MovieContract.MovieList.MOVIE_POSTER + " BLOB NOT NULL,"
                + MovieContract.MovieList.MOVIE_RATING + " INTEGER NOT NULL,"
                + MovieContract.MovieList.MOVIE_WISHLIST + " BOOLEAN, "
                + " UNIQUE (" + MovieContract.MovieList.MOVIE_NAME + ") ON CONFLICT REPLACE"
                + " );";

        sqLiteDatabase.execSQL(SQL_TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieList.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
}
