package com.parse.starter;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.ParseUser;

import java.util.ArrayList;

public class HomepageActivity extends AppCompatActivity implements MeTab.OnFragmentInteractionListener, TasksTab.OnFragmentInteractionListener, PaymentsTab.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        // Set the Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_icon_24px);
        // Remove shadows from under toolbar
        getSupportActionBar().setElevation(0);

        // Set Tabs
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        // Manage Drawer Clicks
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // set item as highlighted when tapped
                item.setChecked(true);

                // Swap fragments
                int itemId = item.getItemId();
                if (itemId == R.id.nav_roommates) {
                    Intent intent = new Intent(getApplicationContext(), AddRoommatesActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                }
                if (itemId == R.id.nav_logout) {
                    ParseUser.logOut();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Set Action Buttons on Toolbar
        getMenuInflater().inflate(R.menu.homepage_menu, menu);

        // Set SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // initialize the drawer
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
