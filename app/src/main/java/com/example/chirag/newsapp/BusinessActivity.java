package com.example.chirag.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class BusinessActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsInfo>> {

    private NewsDataAdapter newsDataAdapter;

    private static final int NEWS_LOADER_ID = 1;

    private static final String NEWS_URL = "https://content.guardianapis.com/search?section=tv-and-radio&api-key=00d9a257-1ff3-4d33-bff4-b26e08cd141d";

    private static final String NEWS_URL_NEW = "https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=ad77c422f08443c99f25b369d06844d3";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

        newsDataAdapter = new NewsDataAdapter(BusinessActivity.this, new ArrayList<NewsInfo>());

        ListView listView = findViewById(R.id.list);

        listView.setAdapter(newsDataAdapter);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }

        boolean isConnected = (activeNetwork != null) && (activeNetwork.isConnectedOrConnecting());

        if (isConnected) {
            LoaderManager loadManager = getLoaderManager();
            loadManager.initLoader(NEWS_LOADER_ID, null, this);
        }
    }

    @Override
    public Loader<List<NewsInfo>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(this, NEWS_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<NewsInfo>> loader, List<NewsInfo> data) {
        newsDataAdapter.clear();
        if (data != null && !data.isEmpty()) {
            newsDataAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<NewsInfo>> loader) {
        newsDataAdapter.clear();
    }
}
