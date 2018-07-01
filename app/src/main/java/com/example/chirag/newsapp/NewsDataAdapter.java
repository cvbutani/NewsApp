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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsDataAdapter extends ArrayAdapter {

    public NewsDataAdapter(@NonNull Context context, @NonNull ArrayList<NewsInfo> objects) {
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
            if (newsInfo.getmImageUrl() != null) {
                placeImage.setVisibility(View.VISIBLE);
                Picasso.get().load(newsInfo.getmImageUrl()).into(placeImage);
            } else {
                placeImage.setVisibility(View.GONE);
            }

            TextView newsHeading = listitem.findViewById(R.id.content_title);
            newsHeading.setText(newsInfo.getmTitle());

            TextView newsDescription = listitem.findViewById(R.id.content_header);
            newsDescription.setText(newsInfo.getmHeader());

            TextView newsDate = listitem.findViewById(R.id.content_date);
            newsDate.setText(newsInfo.getmDate());
        }
        return listitem;
    }
}
