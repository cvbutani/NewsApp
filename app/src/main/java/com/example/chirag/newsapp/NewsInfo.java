package com.example.chirag.newsapp;

import java.io.Serializable;

public class NewsInfo implements Serializable {

    private String mHeader;
    private String mTitle;
    private String mDescription;
    private String mDate;
    private String mImageUrl;
    private String mWebUrl;
    private String mSectionName;

    public NewsInfo(String mHeader, String mTitle, String mDescription, String mDate, String mImageUrl, String mWebUrl, String mSectionName) {
        this.mHeader = mHeader;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mDate = mDate;
        this.mImageUrl = mImageUrl;
        this.mWebUrl = mWebUrl;
        this.mSectionName = mSectionName;
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

    public String getmWebUrl() {
        return mWebUrl;
    }

    public String getmSectionName() {
        return mSectionName;
    }
}
