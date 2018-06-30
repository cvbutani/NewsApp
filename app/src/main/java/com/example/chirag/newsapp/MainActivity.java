package com.example.chirag.newsapp;

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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String BUSINESS_URL1 = "https://content.guardianapis.com/search?";
    String BUSINESS_URL2;
    private static String Business_URL;
    Uri.Builder uribuilder;
    Bundle bundle;
    BusinessFragment bf;
    int id;

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
        getMyURL(null);
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
                createFragment();
                BUSINESS_URL2 = null;
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    private void createFragment() {
        getMyURL(BUSINESS_URL2);
        BusinessFragment fragment = new BusinessFragment();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        id = item.getItemId();

        switch (id) {
            case R.id.nav_top_stories:
                createFragment();
                Toast.makeText(this, "Top Stories", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_business:
                createFragment();
                Toast.makeText(this, "Business", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_entertainment:
                createFragment();
                Toast.makeText(this, "Entertainment", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_health:
                createFragment();
                Toast.makeText(this, "Health", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_science:
                createFragment();
                Toast.makeText(this, "Science", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_sports:
                createFragment();
                Toast.makeText(this, "Sports", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_tech:
                createFragment();
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

    public void getMyURL(String url) {
        Uri baseUri = Uri.parse(BUSINESS_URL1);
        uribuilder = baseUri.buildUpon();
        switch (id) {
            case R.id.nav_business:
                uribuilder.appendQueryParameter("section", "business");
                if (url != null) {
                    uribuilder.appendQueryParameter("q", url);
                }
                break;
            case R.id.nav_entertainment:
                uribuilder.appendQueryParameter("section", "tv-and-radio");
                if (url != null) {
                    uribuilder.appendQueryParameter("q", url);
                }
                break;
            case R.id.nav_health:
                uribuilder.appendQueryParameter("section", "healthcare-network");
                if (url != null) {
                    uribuilder.appendQueryParameter("q", url);
                }
                break;
            case R.id.nav_science:
                uribuilder.appendQueryParameter("section", "science");
                if (url != null) {
                    uribuilder.appendQueryParameter("q", url);
                }
                break;
            case R.id.nav_sports:
                uribuilder.appendQueryParameter("section", "sport");
                if (url != null) {
                    uribuilder.appendQueryParameter("q", url);
                }
                break;
            case R.id.nav_tech:
                uribuilder.appendQueryParameter("section", "technology");
                if (url != null) {
                    uribuilder.appendQueryParameter("q", url);
                }
                break;
            default:
                uribuilder.appendQueryParameter("q", url);
                break;

        }

        uribuilder.appendQueryParameter("show-fields", "all");
        uribuilder.appendQueryParameter("api-key", "00d9a257-1ff3-4d33-bff4-b26e08cd141d");
        Business_URL = uribuilder.toString();
        bundle = new Bundle();
        bundle.putString("text", Business_URL);
    }
}
