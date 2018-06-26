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

    private static final String NEWS_URL = "https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=ad77c422f08443c99f25b369d06844d3";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

//        ArrayList<NewsInfo> news = new ArrayList<>();

//        news.add(new NewsInfo("India plans to participate in oil block auctions in UAE, says oil minister",
//                "We are in talks that India could bid in the next licensing round of UAE with some Middle Eastern nation companies like Mubadala,\" Dharmendra Pradhan, India's oil minister said.",
//                "25th June 2018",
//                R.drawable.asas));
//
//        news.add(new NewsInfo("Govt working to boost Air India's operational efficiencies: Goyal",
//                "The government is giving a new thrust to strengthen Air India's management practices and operational efficiencies while the disinvestment plan would depend on various circumstances, Finance Minister Piyush Goyal said today.\n" +
//                        "\n" +
//                        "The strategic disinvestment plan for debt-laden Air India did not take off after it failed to attract any bidder when the deadline for submitting preliminary bids ended on May 31. ",
//                "25th June 2018",
//                R.drawable.asas));
//
//        news.add(new NewsInfo("IDBI-LIC stake deal: FinMin official says boards to take a call",
//                "The government today sounded evasive about the media reports that it was planning to ask LIC to take a controlling stake in the crippled IDBI Bank, saying the boards of the respective entities will take a call on the matter.\n" +
//                        "\n" +
//                        "\"Both IDBI Bank and LIC are independent organisations. We have left all the decisions to bank boards and we are not going to micromanage them,\" a senior finance ministry official told reporters on the sidelines of the two-day annual summit of the Asian Infrastructure Investment Bank. ",
//                "25th June 2018",
//                R.drawable.asas));
//
//        news.add(new NewsInfo("10 Richest Cricketers in the World Right Now",
//                "Rahul Dravid, often hailed as “The Wall”, has earned his spot amongst the cricketing greats. During his illustrious 16 year (1996-2012) career, Dravid mainly played in the test and ODI formats ( he played just 1 international T20) of the game and even donned the hat of the captain of the Indian team. But his most distinctive performances have invariably been in the test cricket. He also played in the India Premiere League for the Royal Challengers Bangalore in the years 2008, 2009 and 2010.  Post retirement from the international cricket, he played in the 2012 IPL captaining the Rajasthan Royals. He also served as the mentor for Delhi Daredevils till the 2017 season. He has received the Padma Shri and Padma Bhushan awards for his contributions to the sport by the Indian Government. He currently serves as the head coach for India’s U-19 and ‘A’ teams as well as the Overseas Batting Consultant for the Indian team. Along with all that on his plate, he is involved in mentoring future Olympians and Paralympians for India through his Athlete Mentorship programme.",
//                "25th June 2018",
//                R.drawable.asas));

        newsDataAdapter = new NewsDataAdapter(BusinessActivity.this, new ArrayList<NewsInfo>());

        ListView listView = findViewById(R.id.list_view);

        listView.setAdapter(newsDataAdapter);

        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

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
