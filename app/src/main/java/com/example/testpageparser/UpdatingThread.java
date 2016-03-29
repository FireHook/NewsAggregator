package com.example.testpageparser;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.LoaderManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class UpdatingThread extends AsyncTask<Void, Void, Void> {

    ContentResolver contentResolver;

    public UpdatingThread(ContentResolver cr) {
        contentResolver = cr;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        contentResolver.delete(Uri.parse("content://com.example.testpageparser.newsprovider/news"), null, null);
    }

    @Override
    protected Void doInBackground(Void... params) {

        try {

            Document document = Jsoup.connect("http://football.ua/newsarc/").get();
            Elements links = document.select("a.intro-text");
            for(Element e : links)
            {

                Document doc = Jsoup.connect(e.attr("abs:href")).get();

                News news = new News();

                if(e.attr("abs:href").indexOf("http://2016.football.ua/news/") != -1) {
                    news.setContent(doc.select("div.articleText").text());

                    news.setTitle(doc.select("h1").first().text());

                    Element img = doc.select("div.articlePhoto img[src]").first();
                    if (img != null) {
                        news.setImgUrl(img.absUrl("src"));
                    }
                }else {
                    news.setContent(doc.select("div.article-text").text());

                    news.setTitle(doc.select("h1").first().text());

                    Element img = doc.select("div.article-photo img[src]").first();
                    if (img != null) {
                        news.setImgUrl(img.absUrl("src"));
                    }
                }

                ContentValues contentValues = new ContentValues(3);

                contentValues.put(DatabaseHelper.TITLE_COLUMN, news.getTitle());
                contentValues.put(DatabaseHelper.CONTENT_COLUMN, news.getContent());
                contentValues.put(DatabaseHelper.IMG_URL_COLUMN, news.getImgUrl());

                contentResolver.insert(Uri.parse("content://com.example.testpageparser.newsprovider/news"), contentValues);

            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    String AppendElementsText(Elements elements){
        StringBuilder stringBuilder = new StringBuilder();
        for (Element element : elements){
            stringBuilder.append(element.text() + "\n\n");
        }
        return stringBuilder.toString().trim();
    }
}
