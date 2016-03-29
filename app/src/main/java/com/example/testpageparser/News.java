package com.example.testpageparser;

import java.util.Date;

public class News {
    private String Title;
    private String Content;
    private String ImgUrl;
    private Date Date;

    public News() {
    }

    public News(String title, String content, String imgUrl, java.util.Date date) {
        Title = title;
        Content = content;
        ImgUrl = imgUrl;
        Date = date;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
