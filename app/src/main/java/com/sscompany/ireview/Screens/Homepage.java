package com.sscompany.ireview.Screens;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sscompany.ireview.Adapters.FeedAdapter;
import com.sscompany.ireview.AddElement;
import com.sscompany.ireview.FeedItem;
import com.sscompany.ireview.Models.Post;
import com.sscompany.ireview.Models.UserAccountSettings;
import com.sscompany.ireview.R;
import com.sscompany.ireview.Settings.*;
import com.sscompany.ireview.LoginRelatedPages.*;

import android.content.Context;
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

import java.util.ArrayList;

public class Homepage extends AppCompatActivity
{
    private static final String TAG = "Homepage";

    private Context mContext;

    private DrawerLayout mDrawerLayout;
    public static String profileOfMineOrFriend = "";
    private ListView listView;
    private ArrayList<FeedItem> feedItems = new ArrayList<>();
    private static FeedAdapter feedAdapter;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        mContext = Homepage.this;

        listView = findViewById(R.id.news_feed_list);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

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
        Query query = databaseReference
                .child("posts");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren())
                {
                    Post post = singleSnapshot.getValue(Post.class);

                    final FeedItem feedItem = new FeedItem();

                    feedItem.setCover_photo(post.getItem_cover_photo());
                    feedItem.setDate(post.getData_created());
                    feedItem.setItem_name(post.getItem_name());
                    feedItem.setItem_owner(post.getItem_owner());
                    feedItem.setLikes(post.getLike_count());
                    feedItem.setRating(post.getRating());
                    feedItem.setReview(post.getReview());
                    feedItem.setUser_id(post.getUser_id());
                    feedItem.setPost_id(post.getPost_id());
                    feedItem.setPost_image(post.getPost_image());

                    Query query = FirebaseDatabase.getInstance().getReference()
                            .child("user_account_settings")
                            .child(post.getUser_id());

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot)
                        {
                            System.out.println();

                            System.out.println("datasnapshot: " + dataSnapshot.getChildrenCount());

                            String username = dataSnapshot.getValue(UserAccountSettings.class).getUsername();
                            String profile_picture = dataSnapshot.getValue(UserAccountSettings.class).getProfile_photo();

                            feedItem.setUsername(username);
                            feedItem.setProfile_picture(profile_picture);

                            Log.d(TAG, "onDataChange: found user: "
                                    + dataSnapshot.getValue(UserAccountSettings.class).getUsername());

                            feedItems.add(feedItem);

                            feedAdapter = new FeedAdapter(feedItems, mContext);

                            listView.setAdapter(feedAdapter);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
