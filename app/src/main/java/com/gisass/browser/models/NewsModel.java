package com.gisass.browser.models;

import androidx.databinding.BaseObservable;

public class NewsModel extends BaseObservable {
    private int newsIcon;
    private String news;
    private String time;

    public int getNewsIcon() {
        return newsIcon;
    }

    public void setNewsIcon(int newsIcon) {
        this.newsIcon = newsIcon;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
