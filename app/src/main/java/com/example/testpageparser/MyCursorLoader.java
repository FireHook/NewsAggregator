package com.example.testpageparser;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

public class MyCursorLoader extends CursorLoader
{
    ContentResolver contentResolver;
    String[] projection;

    public MyCursorLoader(Context context, ContentResolver cr)
    {
        super(context);
        contentResolver = cr;
        projection = new String[] { "rowid " + DatabaseHelper.ID_COLUMN, DatabaseHelper.TITLE_COLUMN, DatabaseHelper.CONTENT_COLUMN, DatabaseHelper.IMG_URL_COLUMN };
    }

    @Override
    public Cursor loadInBackground() {
        Cursor cursor = contentResolver.query(Uri.parse("content://com.example.testpageparser.newsprovider/news"), projection,
                null, null, null);
        return cursor;
    }
}
