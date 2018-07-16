package com.example.chirag.newsapp;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsDataAdapter extends ArrayAdapter {

    /**
     * This method is used to provide Views for an adapter view.
     * @param context - current context.
     * @param objects - objects to represent in ListView.
     */
    public NewsDataAdapter(@NonNull Context context, @NonNull ArrayList<NewsInfo> objects) {
        super(context, 0, objects);
    }

    /**
     * Method describes the translation between the data item and the View to display.
     * getView() is the method that returns the actual view used as a row within the
     * ListView at a particular position. Another method used is getItem() which is
     * already present in the ArrayAdapter class and its task is to simply get the
     * data item associated with the specified position in the data set which is
     * associated with that ArrayAdapter.
     *
     * @param position - item position
     * @param convertView - converts data into View
     * @param parent - contains other views and Its a superclass of all of the layout classes.
     * @return the actual view used as a row within the ListView at a particular position.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
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

            DateConvert dateConvert = new DateConvert();
            String postTime = dateConvert.dateConverter(newsInfo.getmDate());
            newsDate.setText(postTime);

        }
        return listitem;
    }
}
