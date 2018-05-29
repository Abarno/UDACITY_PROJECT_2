package com.example.abarno.moviesapp_2;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Objects;

public class MovieWishListContentProvider extends ContentProvider {

    public static final int MOVIES = 100;
    public static final int MOVIES_ID = 101;

    private MovieDbHelper movieDbHelper;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH, MOVIES);
        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH + "/#", MOVIES_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        movieDbHelper = new MovieDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        final SQLiteDatabase sqLiteDatabase = movieDbHelper.getReadableDatabase();

        int matchUri = sUriMatcher.match(uri);

        Cursor cursor;

        switch (matchUri){
            case MOVIES:
                cursor = sqLiteDatabase.query(MovieContract.MovieList.TABLE_NAME,
                         null,
                        null,
                        null,
                        null,
                        null,
                        sortOrder);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        cursor.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        final SQLiteDatabase database = movieDbHelper.getWritableDatabase();

        int matchUri = sUriMatcher.match(uri);

        Uri insertUri;

        switch (matchUri){
            case MOVIES:
                long id = database.insert(MovieContract.MovieList.TABLE_NAME, null, contentValues);
                if (id > 0){
                    insertUri = ContentUris.withAppendedId(MovieContract.MovieList.CONTENT_URI, id);
                }
                else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri,null);

        return insertUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
