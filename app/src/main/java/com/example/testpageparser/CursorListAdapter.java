package com.example.testpageparser;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class CursorListAdapter extends CursorAdapter{

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CursorListAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_row, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        NetworkImageView thumbNail = (NetworkImageView) view.findViewById(R.id.thumbnail);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView article = (TextView) view.findViewById(R.id.article);
        TextView tvListUrl = (TextView) view.findViewById(R.id.tvListUrl);

        thumbNail.setImageUrl(cursor.getString(cursor.getColumnIndexOrThrow("n_ImgUrl")), imageLoader);

        title.setText(cursor.getString(cursor.getColumnIndexOrThrow("n_Title")));

        article.setText(cursor.getString(cursor.getColumnIndexOrThrow("n_Content")));

        tvListUrl.setText(cursor.getString(cursor.getColumnIndexOrThrow("n_ImgUrl")));

    }
}
