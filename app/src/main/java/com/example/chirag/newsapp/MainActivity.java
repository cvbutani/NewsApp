package com.example.chirag.newsapp;

import android.app.Fragment;
import android.app.FragmentManager;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content, new BusinessFragment())
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
}
