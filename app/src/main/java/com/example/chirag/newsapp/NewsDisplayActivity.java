package com.example.chirag.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class NewsDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.news_display);

        if (!getIntent().hasExtra("info")) return;
        if (getIntent().hasExtra("header"))setTitle(getIntent().getStringExtra("header"));
        final NewsInfo news = (NewsInfo) getIntent().getSerializableExtra("info");


        ImageView image = findViewById(R.id.news_display_image);
        if (news.getmImageUrl() != null) {
            Picasso.get().load(news.getmImageUrl()).into(image);
        } else {
            image.setVisibility(View.GONE);
        }

        TextView title = findViewById(R.id.news_display_title);
        title.setText(news.getmTitle());

        TextView date = findViewById(R.id.news_display_date);
        date.setText(news.getmDate());

        TextView description = findViewById(R.id.news_display_description);
        description.setText(news.getmDescription());

        Button readMore = findViewById(R.id.news_display_button);
        readMore.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = news.getmWebUrl();
                Uri webLink = Uri.parse(name);
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webLink);
                startActivity(webIntent);
            }
        });
    }
}