package com.example.chirag.newsapp;


import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.chirag.newsapp.Fragments.BusinessFragment;
import com.example.chirag.newsapp.Fragments.EntertainmentFragment;
import com.example.chirag.newsapp.Fragments.HealthFragment;
import com.example.chirag.newsapp.Fragments.ScienceFragment;
import com.example.chirag.newsapp.Fragments.SportFragment;
import com.example.chirag.newsapp.Fragments.TechnologyFragment;
import com.example.chirag.newsapp.Fragments.TopStoriesFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String BUSINESS_URL1 = "https://content.guardianapis.com/search?section=business";
    private static final String BUSINESS_URL3 = "show-fields=all&api-key=00d9a257-1ff3-4d33-bff4-b26e08cd141d";
    String BUSINESS_URL2 = "trade";
    private static String Business_URL;
    Uri.Builder uribuilder;
    Bundle bundle;

    public final String LOG_TAG = getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getMyURL(BUSINESS_URL2);
        onNavigationItemSelected(navigationView.getMenu().getItem(0).setChecked(true));

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != null) {
                    BUSINESS_URL2 = query.replace(" ", "%20%");
                }
                Log.i(LOG_TAG, "QUERY: " + BUSINESS_URL2);
                getMyURL(BUSINESS_URL2);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_top_stories:

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content, new TopStoriesFragment())
                        .commit();
                Toast.makeText(this, "Top Stories", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_business:

                BusinessFragment bf = new BusinessFragment();
                bf.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content, bf)
                        .commit();
                Toast.makeText(this, "Business", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_entertainment:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content, new EntertainmentFragment())
                        .commit();
                Toast.makeText(this, "Entertainment", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_health:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content, new HealthFragment())
                        .commit();
                Toast.makeText(this, "Health", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_science:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content, new ScienceFragment())
                        .commit();
                Toast.makeText(this, "Science", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_sports:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content, new SportFragment())
                        .commit();
                Toast.makeText(this, "Sports", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_tech:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content, new TechnologyFragment())
                        .commit();
                Toast.makeText(this, "Technology", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_share:
                break;

            case R.id.nav_view:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public String getMyURL(String url) {
        Uri baseUri = Uri.parse(BUSINESS_URL1);
        uribuilder = baseUri.buildUpon();
        uribuilder.appendQueryParameter("q", url);
        uribuilder.appendQueryParameter("show-fields", "all");
        uribuilder.appendQueryParameter("api-key", "00d9a257-1ff3-4d33-bff4-b26e08cd141d");

        Log.i(LOG_TAG, "BUSINESS URL: " + uribuilder.toString());
        Business_URL = uribuilder.toString();
        bundle = new Bundle();
        bundle.putString("text", Business_URL);
        return Business_URL;
    }
}
