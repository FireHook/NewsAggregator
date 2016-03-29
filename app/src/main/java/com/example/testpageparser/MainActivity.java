package com.example.testpageparser;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, SwipeRefreshLayout.OnRefreshListener {

    public static final String LOG_TAG = "ProjectLog";
    ListView lv_News;
    CursorListAdapter cursorListAdapter;
    ContentResolver contentResolver;
    LoaderManager loaderManager;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        cursorListAdapter = new CursorListAdapter(this, null, true);

        lv_News = (ListView) findViewById(R.id.lst_News);
        lv_News.setAdapter(cursorListAdapter);
        lv_News.setOnItemClickListener(itemClickListener);
        registerForContextMenu(lv_News);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        contentResolver = this.getContentResolver();

        loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(0, null, this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(R.string.menu_item_sources).setIntent(new Intent(this, PreferenceSourcesActivity.class));
        menu.add(R.string.menu_item_time).setIntent(new Intent(this, PreferenceTimeActivity.class));

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onRefresh() {
        new UpdatingThread(contentResolver).execute();
        swipeRefreshLayout.setRefreshing(false);
    }

    public AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            TextView title = (TextView) view.findViewById(R.id.title);
            TextView content = (TextView) view.findViewById(R.id.article);
            TextView image = (TextView) view.findViewById(R.id.tvListUrl);

            Intent intent = new Intent(getApplicationContext(), FullArticleActivity.class);
            intent.putExtra("Title", title.getText());
            intent.putExtra("Content", content.getText());
            intent.putExtra("ImgUrl", image.getText());
            startActivity(intent);
        }
    };

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new MyCursorLoader(this, contentResolver);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        cursorListAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {    }
}