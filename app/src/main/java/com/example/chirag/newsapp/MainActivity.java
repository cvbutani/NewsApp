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

import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.chirag.newsapp.Constants.ApiRequestConstant;
import com.example.chirag.newsapp.Fragments.BusinessFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static String NEWS_URL;
    private Bundle bundle;
    int id;

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
                    NEWS_URL = query.replace(" ", ApiRequestConstant.INPUT_REPLACEMENT);
                }
                createFragment();
                NEWS_URL = null;
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
        id = item.getItemId();

        switch (id) {
            case R.id.nav_top_stories:
                setActionBarTitle(getString(R.string.top_stories));
                createFragment();
                break;
            case R.id.nav_business:
                setActionBarTitle(getString(R.string.business));
                createFragment();
                break;
            case R.id.nav_entertainment:
                setActionBarTitle(getString(R.string.entertainment));
                createFragment();
                break;
            case R.id.nav_health:
                setActionBarTitle(getString(R.string.health));
                createFragment();
                break;
            case R.id.nav_science:
                setActionBarTitle(getString(R.string.science));
                createFragment();
                break;
            case R.id.nav_sports:
                setActionBarTitle(getString(R.string.sports));
                createFragment();
                break;
            case R.id.nav_tech:
                setActionBarTitle(getString(R.string.technology));
                createFragment();
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

    private void createFragment() {
        getMyURL(NEWS_URL);
        BusinessFragment fragment = new BusinessFragment();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
    }

    public void getMyURL(String url) {
        Uri baseUri = Uri.parse(ApiRequestConstant.SCHEME_PART);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        switch (id) {
            case R.id.nav_business:
                uriBuilder.appendQueryParameter(ApiRequestConstant.SCHEME_PART_SECTION, ApiRequestConstant.RESOURCE_SECTION_BUSINESS);
                if (url != null) {
                    uriBuilder.appendQueryParameter(ApiRequestConstant.SCHEME_PART_QUERY, url);
                }
                break;
            case R.id.nav_entertainment:
                uriBuilder.appendQueryParameter(ApiRequestConstant.SCHEME_PART_SECTION, ApiRequestConstant.RESOURCE_SECTION_ENTERTAINMENT);
                if (url != null) {
                    uriBuilder.appendQueryParameter(ApiRequestConstant.SCHEME_PART_QUERY, url);
                }
                break;
            case R.id.nav_health:
                uriBuilder.appendQueryParameter(ApiRequestConstant.SCHEME_PART_SECTION, ApiRequestConstant.RESOURCE_SECTION_HEALTH);
                if (url != null) {
                    uriBuilder.appendQueryParameter(ApiRequestConstant.SCHEME_PART_QUERY, url);
                }
                break;
            case R.id.nav_science:
                uriBuilder.appendQueryParameter(ApiRequestConstant.SCHEME_PART_SECTION, ApiRequestConstant.RESOURCE_SECTION_SCIENCE);
                if (url != null) {
                    uriBuilder.appendQueryParameter(ApiRequestConstant.SCHEME_PART_QUERY, url);
                }
                break;
            case R.id.nav_sports:
                uriBuilder.appendQueryParameter(ApiRequestConstant.SCHEME_PART_SECTION, ApiRequestConstant.RESOURCE_SECTION_SPORT);
                if (url != null) {
                    uriBuilder.appendQueryParameter(ApiRequestConstant.SCHEME_PART_QUERY, url);
                }
                break;
            case R.id.nav_tech:
                uriBuilder.appendQueryParameter(ApiRequestConstant.SCHEME_PART_SECTION, ApiRequestConstant.RESOURCE_SECTION_TECHNOLOGY);
                if (url != null) {
                    uriBuilder.appendQueryParameter(ApiRequestConstant.SCHEME_PART_QUERY, url);
                }
                break;
            default:
                uriBuilder.appendQueryParameter(ApiRequestConstant.SCHEME_PART_QUERY, url);
                break;
        }

        uriBuilder.appendQueryParameter(ApiRequestConstant.SCHEME_PART_SHOW_FIELDS, ApiRequestConstant.RESOURCE_FIELDS);
        uriBuilder.appendQueryParameter(ApiRequestConstant.SCHEME_PART_API, ApiRequestConstant.API_KEY);
        String final_url = uriBuilder.toString();
        bundle = new Bundle();
        bundle.putString(ApiRequestConstant.BUNDLE_STRING_EXTRA, final_url);
    }

    public void setActionBarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

}
