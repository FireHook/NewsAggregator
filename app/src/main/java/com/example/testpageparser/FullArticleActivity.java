package com.example.testpageparser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.w3c.dom.Text;

public class FullArticleActivity extends Activity {

    TextView Title;
    TextView Content;
    NetworkImageView thumbNail;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_article);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        Title = (TextView) findViewById(R.id.text_title);
        Content= (TextView) findViewById(R.id.text_content);
        thumbNail = (NetworkImageView) findViewById(R.id.thumbnail_full);

        Intent intent = getIntent();
        Title.setText(intent.getStringExtra("Title"));
        Content.setText(intent.getStringExtra("Content"));
        thumbNail.setImageUrl(intent.getStringExtra("ImgUrl"), imageLoader);
    }
}
