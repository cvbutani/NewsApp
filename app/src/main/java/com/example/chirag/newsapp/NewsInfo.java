package com.example.chirag.newsapp;

public class NewsInfo {

    private String mHeader;
    private String mTitle;
    private String mDescription;
    private String mDate;
    private String mImageUrl;

    public NewsInfo(String mHeader, String mTitle, String mDescription, String mDate, String mImageUrl) {
        this.mHeader = mHeader;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mDate = mDate;
        this.mImageUrl = mImageUrl;
    }

    public String getmHeader() {
        return mHeader;
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

    public String getmImageUrl() {
        return mImageUrl;
    }
}
