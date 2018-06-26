package com.example.chirag.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsDataAdapter extends ArrayAdapter {

    NewsDataAdapter(@NonNull Context context, @NonNull ArrayList<NewsInfo> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitem = convertView;

        if (listitem == null) {
            listitem = LayoutInflater.from(getContext()).inflate(R.layout.listview_content, parent,false);
        }

        NewsInfo newsInfo = (NewsInfo) getItem(position);
        if (newsInfo != null) {
            ImageView placeImage = listitem.findViewById(R.id.content_image);
            placeImage.setImageResource(newsInfo.getmImageUrl());

            TextView newsHeading = listitem.findViewById(R.id.content_heading);
            newsHeading.setText(newsInfo.getmTitle());

            TextView newsDescription = listitem.findViewById(R.id.content_description);
            newsDescription.setText(newsInfo.getmDescription());

            TextView newsDate = listitem.findViewById(R.id.content_date);
            newsDate.setText(newsInfo.getmDate());
        }
        return listitem;
    }
}