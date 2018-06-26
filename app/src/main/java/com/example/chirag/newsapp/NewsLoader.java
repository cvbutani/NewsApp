package com.example.chirag.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<NewsInfo>> {

    private String mURL;

    public NewsLoader(Context context, String mURL) {
        super(context);
        this.mURL = mURL;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<NewsInfo> loadInBackground() {
        if (mURL == null) {
            return null;
        }
        return NewsQuery.fetchNewsUpdates(mURL);
    }

}
