package com.example.testpageparser;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import java.sql.SQLException;

public class NewsProvider extends ContentProvider{

    public static final Uri CONTENT_URI = Uri.parse("content://com.example.testpageparser.newsprovider/news");

    private SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        db = new DatabaseHelper(getContext()).getWritableDatabase();
        return (db == null) ? false : true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        String orderBy;

        if (TextUtils.isEmpty(sortOrder)){
            orderBy = DatabaseHelper.ID_COLUMN;
        }
        else {
            orderBy = sortOrder;
        }

        Cursor c = db.query(DatabaseHelper.TABLE_NAME, projection, selection, selectionArgs, null, null, orderBy);
        c.setNotificationUri(getContext().getContentResolver(), uri);

        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri url, ContentValues values) {
        long rowId = db.insert(DatabaseHelper.TABLE_NAME, DatabaseHelper.TITLE_COLUMN, values);

        if(rowId > 0)
        {
            Uri uri = ContentUris.withAppendedId(CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(uri, null);
            return uri;
        }else
        {
            try {
                throw new SQLException("Failed to insert row into "+ url);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int retVal = db.delete(DatabaseHelper.TABLE_NAME, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return retVal;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int retVal = db.update(DatabaseHelper.TABLE_NAME, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return retVal;
    }
}
