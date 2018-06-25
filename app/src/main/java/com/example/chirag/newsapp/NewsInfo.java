package com.example.chirag.newsapp;

public class NewsInfo {

    private String mTitle;
    private String mDescription;
    private String mDate;
    private int mImageUrl;

    public NewsInfo(String mTitle, String mDescription, String mDate, int mImageUrl) {
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mDate = mDate;
        this.mImageUrl = mImageUrl;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getmDate() {
        return mDate;
    }

    public int getmImageUrl() {
        return mImageUrl;
    }
}
