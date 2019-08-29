package com.sscompany.ireview;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sscompany.ireview.AddElementScreens.AddElement;
import com.sscompany.ireview.Elements.*;
import com.sscompany.ireview.Settings.*;
import com.sscompany.ireview.LoginRelatedPages.*;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class Homepage extends AppCompatActivity
{
    private DrawerLayout mDrawerLayout;
    public static String profileOfMineOrFriend = "";
    private ListView listView;
    private ArrayList<NewsFeedItem> newsFeedItems = new ArrayList<>();

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        mAuth = FirebaseAuth.getInstance();

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        TextView navTitle = (TextView) findViewById(R.id.navHeaderTitle);
                        navTitle.setText(mAuth.getCurrentUser().getEmail());
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        //Navigation view
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        if(menuItem.getTitle().toString().matches("My Profile")) {
                            profileOfMineOrFriend = "Mine";
                            Intent intent = new Intent(getApplicationContext(), MyProfile.class);
                            startActivity(intent);
                        }
                        else if(menuItem.getTitle().toString().matches("My Friends")) {
                            Intent intent = new Intent(getApplicationContext(), ShowFriendList.class);
                            startActivity(intent);
                        }
                        else if(menuItem.getTitle().toString().matches("My Favorite Reviewers")) {
                            Intent intent = new Intent(getApplicationContext(), ShowFriendList.class);
                            startActivity(intent);
                        }
                        else if(menuItem.getTitle().toString().matches("Add Element")) {
                            Intent intent = new Intent(getApplicationContext(), AddElement.class);
                            startActivity(intent);
                        }
                        else if(menuItem.getTitle().toString().matches("Settings")) {
                            Intent intent = new Intent(getApplicationContext(), Settings.class);
                            startActivity(intent);
                        }
                        else if(menuItem.getTitle().toString().matches("Show Items")) {
                            //Intent intent = new Intent(getApplicationContext(), ShowItems.class);
                            //startActivity(intent);
                        }
                        else if(menuItem.getTitle().toString().matches("Log out")) {
                            if(mAuth.getCurrentUser() != null) {
                                Toast.makeText(getApplicationContext(), "Logged out", Toast.LENGTH_SHORT).show();
                                mAuth.signOut();
                                Intent intent = new Intent(getApplicationContext(), LoginPage.class);
                                startActivity(intent);
                            }
                        }
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();
                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        return true;
                    }
                });


        //News Feed

        /*ParseQuery<ParseObject> postQuery = ParseQuery.getQuery("Post");

        postQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null)
                {
                    System.out.println("YESSSSSSSSSSSSSSSSSS");
                    ParseUser current = ParseUser.getCurrentUser();
                    String postingUserId = current.getObjectId();
                    for (ParseObject obj : objects)
                    {
                        System.out.println("nOOOOOOOOOOOOOOOO");
                        String itemId = obj.get("itemId").toString();

                        NewsFeedItem newsFeedItem = new NewsFeedItem(obj.getObjectId());
                        newsFeedItems.add(newsFeedItem);
                    }

                    listView = findViewById(R.id.news_feed_list);
                    NewsFeedAdapter newsFeedAdapter = new NewsFeedAdapter(Homepage.this, newsFeedItems);
                    listView.setAdapter(newsFeedAdapter);
                } else {
                    e.printStackTrace();
                }
            }
        });*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // ------------------------- Firebase -------------------------------

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null)
        {
            Intent intent = new Intent(getApplicationContext(), LoginPage.class);
            startActivity(intent);
            finish();
        }
    }

}
