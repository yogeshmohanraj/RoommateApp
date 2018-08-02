package com.parse.starter;

import android.app.DialogFragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class AddRoommatesActivity extends AppCompatActivity implements AddRoommatesDialog.RoommateDialogListener{

    // variables
    private ArrayList<String> roommateNames = new ArrayList<>();
    RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_roommates);

        // Set up toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar_roommate);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Roommates");

        // Set up floating action button
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show dialog on click
                AddRoommatesDialog addRoommatesDialog = new AddRoommatesDialog();
                addRoommatesDialog.show(getSupportFragmentManager(),"add roommates dialog");
            }
        });

        // Initializes roommateNames with data from Parse
        updateRoommatesArray();

        // Set up Recycler View
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.rm_recycler);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapter(this, roommateNames);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void addUser(String username) {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", username);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    ParseUser currentUser = ParseUser.getCurrentUser();
                    ParseUser newRoommate = objects.get(0);
                    currentUser.getRelation("roommates").add(newRoommate);
                    currentUser.saveInBackground();

                    updateRoommatesArray();
                }
                else {
                    Toast.makeText(AddRoommatesActivity.this, "User does not exist.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void updateRoommatesArray() {
        roommateNames.clear();
        ParseUser currentUser = ParseUser.getCurrentUser();
        ParseRelation<ParseUser> relation = currentUser.getRelation("roommates");
        relation.getQuery().findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    for (ParseUser roommate : objects) {
                        roommateNames.add(roommate.getString("firstName") + " " + roommate.getString("lastName"));
                        adapter.notifyDataSetChanged();
                    }
                }
                else {
                    Log.i("Query", "Empty or Not Working");
                }
            }
        });
    }
}

