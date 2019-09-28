package com.sscompany.ireview.Screens;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import static com.sscompany.ireview.Screens.Homepage.profileOfMineOrFriend;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sscompany.ireview.Adapters.RecyclerItemClickListener;
import com.sscompany.ireview.Adapters.RecyclerViewAdapter;
import com.sscompany.ireview.Models.*;
import com.sscompany.ireview.R;

public class MyProfile extends AppCompatActivity {

    //private RecyclerView mRecyclerView;
    //private RecyclerView.Adapter mAdapter;
    //private RecyclerView.LayoutManager mLayoutManager;
    private List<InterfaceItem> bookList;
    private List<InterfaceItem> movieList;
    private List<InterfaceItem> musicList;
    private List<InterfaceItem> tvShowList;
    private List<InterfaceItem> placeList;
    private List<InterfaceItem> gameList;
    private List<InterfaceItem> websiteList;


    private Context mContext;

    private GestureDetector mGestureDetector;

    private String userId;

    //Firebase Database
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);

        //Initializing mContext
        mContext = MyProfile.this;

        //Initializing myRef
        myRef = FirebaseDatabase.getInstance().getReference();

        //Initializing currentUser's userId
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //Getting Books of currentUser and adding them to the bookList

        bookList = new ArrayList<>();

        myRef.child("user_items")
                .child(userId)
                .child("books")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        for (DataSnapshot sampleDataSnapshot : dataSnapshot.getChildren())
                        {
                            Book book = sampleDataSnapshot.getValue(Book.class);
                            bookList.add(book);
                            if(dataSnapshot.getChildrenCount() == 0)
                            {
                                findViewById(R.id.no_book).setVisibility(View.VISIBLE);
                            }
                        }

                        if(dataSnapshot.getChildrenCount() == 0)
                        {
                            findViewById(R.id.no_book).setVisibility(View.VISIBLE);
                        }

                        RecyclerView itemRecyclerView1 = (RecyclerView) findViewById(R.id.recyclerViewBooks);
                        RecyclerViewAdapter myAdapter1 = new RecyclerViewAdapter(mContext, bookList);
                        GridLayoutManager gr1 = new GridLayoutManager(mContext, 3);
                        itemRecyclerView1.setLayoutManager(gr1);
                        itemRecyclerView1.setAdapter(myAdapter1);
                        itemRecyclerView1.setNestedScrollingEnabled(false);
                        itemRecyclerView1.setFocusable(false);

                        RecyclerView recyclerView = findViewById(R.id.recyclerViewBooks);
                        recyclerView.addOnItemTouchListener(
                                new RecyclerItemClickListener(mContext, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                                    @Override public void onItemClick(View view, int position)
                                    {
                                        System.out.println("Name: " + bookList.get(position).getName() + " Owner: " + bookList.get(position).getOwner());
                                        // do whatever
                                    }

                                    @Override public void onLongItemClick(View view, int position) {
                                        // do whatever
                                    }
                                })
                        );
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {

                    }
                });

        //Getting Movies of currentUser and adding them to the movieList

        movieList = new ArrayList<>();

        myRef.child("user_items")
                .child(userId)
                .child("movies")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        for (DataSnapshot sampleDataSnapshot : dataSnapshot.getChildren())
                        {
                            Movie movie = sampleDataSnapshot.getValue(Movie.class);
                            movieList.add(movie);
                        }

                        if(dataSnapshot.getChildrenCount() == 0)
                        {
                            findViewById(R.id.no_movie).setVisibility(View.VISIBLE);
                        }

                        RecyclerView itemRecyclerView1 = (RecyclerView) findViewById(R.id.recyclerViewMovies);
                        RecyclerViewAdapter myAdapter1 = new RecyclerViewAdapter(mContext, movieList);
                        GridLayoutManager gr1 = new GridLayoutManager(mContext, 3);
                        itemRecyclerView1.setLayoutManager(gr1);
                        itemRecyclerView1.setAdapter(myAdapter1);
                        itemRecyclerView1.setNestedScrollingEnabled(false);
                        itemRecyclerView1.setFocusable(false);

                        RecyclerView recyclerView = findViewById(R.id.recyclerViewMovies);
                        recyclerView.addOnItemTouchListener(
                                new RecyclerItemClickListener(mContext, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                                    @Override public void onItemClick(View view, int position)
                                    {
                                        System.out.println("Name: " + movieList.get(position).getName() + " Owner: " + movieList.get(position).getOwner());
                                        // do whatever
                                    }

                                    @Override public void onLongItemClick(View view, int position) {
                                        // do whatever
                                    }
                                })
                        );
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {

                    }
                });


        //Getting Musics of currentUser and adding them to the musicList

        musicList = new ArrayList<>();

        myRef.child("user_items")
                .child(userId)
                .child("musics")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        for (DataSnapshot sampleDataSnapshot : dataSnapshot.getChildren())
                        {
                            Music music = sampleDataSnapshot.getValue(Music.class);
                            musicList.add(music);
                        }

                        if(dataSnapshot.getChildrenCount() == 0)
                        {
                            findViewById(R.id.no_music).setVisibility(View.VISIBLE);
                        }

                        RecyclerView itemRecyclerView1 = (RecyclerView) findViewById(R.id.recyclerViewMusics);
                        RecyclerViewAdapter myAdapter1 = new RecyclerViewAdapter(mContext, musicList);
                        GridLayoutManager gr1 = new GridLayoutManager(mContext, 3);
                        itemRecyclerView1.setLayoutManager(gr1);
                        itemRecyclerView1.setAdapter(myAdapter1);
                        itemRecyclerView1.setNestedScrollingEnabled(false);
                        itemRecyclerView1.setFocusable(false);

                        RecyclerView recyclerView = findViewById(R.id.recyclerViewMusics);
                        recyclerView.addOnItemTouchListener(
                                new RecyclerItemClickListener(mContext, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                                    @Override public void onItemClick(View view, int position)
                                    {
                                        System.out.println("Name: " + musicList.get(position).getName() + " Owner: " + musicList.get(position).getOwner());
                                        // do whatever
                                    }

                                    @Override public void onLongItemClick(View view, int position) {
                                        // do whatever
                                    }
                                })
                        );
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {

                    }
                });


        //Getting Places of currentUser and adding them to the placeList

        placeList = new ArrayList<>();

        myRef.child("user_items")
                .child(userId)
                .child("places")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        for (DataSnapshot sampleDataSnapshot : dataSnapshot.getChildren())
                        {
                            Place place = sampleDataSnapshot.getValue(Place.class);
                            bookList.add(place);
                        }

                        if(dataSnapshot.getChildrenCount() == 0)
                        {
                            findViewById(R.id.no_place).setVisibility(View.VISIBLE);
                        }

                        RecyclerView itemRecyclerView1 = (RecyclerView) findViewById(R.id.recyclerViewPlaces);
                        RecyclerViewAdapter myAdapter1 = new RecyclerViewAdapter(mContext, placeList);
                        GridLayoutManager gr1 = new GridLayoutManager(mContext, 3);
                        itemRecyclerView1.setLayoutManager(gr1);
                        itemRecyclerView1.setAdapter(myAdapter1);
                        itemRecyclerView1.setNestedScrollingEnabled(false);
                        itemRecyclerView1.setFocusable(false);

                        RecyclerView recyclerView = findViewById(R.id.recyclerViewPlaces);
                        recyclerView.addOnItemTouchListener(
                                new RecyclerItemClickListener(mContext, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                                    @Override public void onItemClick(View view, int position)
                                    {
                                        System.out.println("Name: " + placeList.get(position).getName() + " Owner: " + placeList.get(position).getOwner());
                                        // do whatever
                                    }

                                    @Override public void onLongItemClick(View view, int position) {
                                        // do whatever
                                    }
                                })
                        );
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {

                    }
                });


        //Getting TV Shows of currentUser and adding them to the tvShowList

        tvShowList = new ArrayList<>();

        myRef.child("user_items")
                .child(userId)
                .child("tv_shows")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        for (DataSnapshot sampleDataSnapshot : dataSnapshot.getChildren())
                        {
                            TVShow tvShow = sampleDataSnapshot.getValue(TVShow.class);
                            tvShowList.add(tvShow);
                        }

                        if(dataSnapshot.getChildrenCount() == 0)
                        {
                            findViewById(R.id.no_tvshow).setVisibility(View.VISIBLE);
                        }

                        RecyclerView itemRecyclerView1 = (RecyclerView) findViewById(R.id.recyclerViewTVShows);
                        RecyclerViewAdapter myAdapter1 = new RecyclerViewAdapter(mContext, tvShowList);
                        GridLayoutManager gr1 = new GridLayoutManager(mContext, 3);
                        itemRecyclerView1.setLayoutManager(gr1);
                        itemRecyclerView1.setAdapter(myAdapter1);
                        itemRecyclerView1.setNestedScrollingEnabled(false);
                        itemRecyclerView1.setFocusable(false);

                        RecyclerView recyclerView = findViewById(R.id.recyclerViewTVShows);
                        recyclerView.addOnItemTouchListener(
                                new RecyclerItemClickListener(mContext, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                                    @Override public void onItemClick(View view, int position)
                                    {
                                        System.out.println("Name: " + tvShowList.get(position).getName() + " Owner: " + tvShowList.get(position).getOwner());
                                        // do whatever
                                    }

                                    @Override public void onLongItemClick(View view, int position) {
                                        // do whatever
                                    }
                                })
                        );
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {

                    }
                });

        //Getting Games of currentUser and adding them to the gameList

        gameList = new ArrayList<>();

        myRef.child("user_items")
                .child(userId)
                .child("games")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        for (DataSnapshot sampleDataSnapshot : dataSnapshot.getChildren())
                        {
                            Game game = sampleDataSnapshot.getValue(Game.class);
                            gameList.add(game);
                        }

                        if(dataSnapshot.getChildrenCount() == 0)
                        {
                            findViewById(R.id.no_game).setVisibility(View.VISIBLE);
                        }

                        RecyclerView itemRecyclerView1 = (RecyclerView) findViewById(R.id.recyclerViewGames);
                        RecyclerViewAdapter myAdapter1 = new RecyclerViewAdapter(mContext, gameList);
                        GridLayoutManager gr1 = new GridLayoutManager(mContext, 3);
                        itemRecyclerView1.setLayoutManager(gr1);
                        itemRecyclerView1.setAdapter(myAdapter1);
                        itemRecyclerView1.setNestedScrollingEnabled(false);
                        itemRecyclerView1.setFocusable(false);

                        RecyclerView recyclerView = findViewById(R.id.recyclerViewGames);
                        recyclerView.addOnItemTouchListener(
                                new RecyclerItemClickListener(mContext, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                                    @Override public void onItemClick(View view, int position)
                                    {
                                        System.out.println("Name: " + gameList.get(position).getName() + " Owner: " + gameList.get(position).getOwner());
                                        // do whatever
                                    }

                                    @Override public void onLongItemClick(View view, int position) {
                                        // do whatever
                                    }
                                })
                        );
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {

                    }
                });

        //Getting Websites of currentUser and adding them to the websiteList

        websiteList = new ArrayList<>();

        myRef.child("user_items")
                .child(userId)
                .child("websites")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        for (DataSnapshot sampleDataSnapshot : dataSnapshot.getChildren())
                        {
                            Website website = sampleDataSnapshot.getValue(Website.class);
                            websiteList.add(website);
                        }

                        if(dataSnapshot.getChildrenCount() == 0)
                        {
                            findViewById(R.id.no_website).setVisibility(View.VISIBLE);
                        }

                        RecyclerView itemRecyclerView1 = (RecyclerView) findViewById(R.id.recyclerViewWebsites);
                        RecyclerViewAdapter myAdapter1 = new RecyclerViewAdapter(mContext, websiteList);
                        GridLayoutManager gr1 = new GridLayoutManager(mContext, 3);
                        itemRecyclerView1.setLayoutManager(gr1);
                        itemRecyclerView1.setAdapter(myAdapter1);
                        itemRecyclerView1.setNestedScrollingEnabled(false);
                        itemRecyclerView1.setFocusable(false);

                        RecyclerView recyclerView = findViewById(R.id.recyclerViewWebsites);
                        recyclerView.addOnItemTouchListener(
                                new RecyclerItemClickListener(mContext, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                                    @Override public void onItemClick(View view, int position)
                                    {
                                        System.out.println("Name: " + websiteList.get(position).getName() + " Owner: " + websiteList.get(position).getOwner());
                                        // do whatever
                                    }

                                    @Override public void onLongItemClick(View view, int position) {
                                        // do whatever
                                    }
                                })
                        );
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {

                    }
                });

        /*
        RecyclerView itemRecyclerView1 = (RecyclerView) findViewById(R.id.recyclerViewCategoryBooks);
        RecyclerViewAdapter myAdapter1 = new RecyclerViewAdapter(this, listItem);
        GridLayoutManager gr1 = new GridLayoutManager(this, 3);
        itemRecyclerView1.setLayoutManager(gr1);
        itemRecyclerView1.setAdapter(myAdapter1);
        itemRecyclerView1.setNestedScrollingEnabled(false);
        itemRecyclerView1.setFocusable(false);

        RecyclerView itemRecyclerView2 = (RecyclerView) findViewById(R.id.recyclerViewCategoryMovies);
        RecyclerViewAdapter myAdapter2 = new RecyclerViewAdapter(this, listItem);
        itemRecyclerView2.setLayoutManager(new GridLayoutManager(this, 3));
        itemRecyclerView2.setAdapter(myAdapter2);
        itemRecyclerView2.setNestedScrollingEnabled(false);


        RecyclerView itemRecyclerView3 = (RecyclerView) findViewById(R.id.recyclerViewCategoryRestaurants);
        RecyclerViewAdapter myAdapter3 = new RecyclerViewAdapter(this, listItem);
        itemRecyclerView3.setLayoutManager(new GridLayoutManager(this, 3));
        itemRecyclerView3.setAdapter(myAdapter3);
        itemRecyclerView3.setNestedScrollingEnabled(false);


        RecyclerView itemRecyclerView4 = (RecyclerView) findViewById(R.id.recyclerViewCategoryPlaces);
        RecyclerViewAdapter myAdapter4 = new RecyclerViewAdapter(this, listItem);
        itemRecyclerView4.setLayoutManager(new GridLayoutManager(this, 3));
        itemRecyclerView4.setAdapter(myAdapter4);
        itemRecyclerView4.setNestedScrollingEnabled(false);


        RecyclerView itemRecyclerView5 = (RecyclerView) findViewById(R.id.recyclerViewCategoryTvShows);
        RecyclerViewAdapter myAdapter5 = new RecyclerViewAdapter(this, listItem);
        itemRecyclerView5.setLayoutManager(new GridLayoutManager(this, 3));
        itemRecyclerView5.setAdapter(myAdapter5);
        itemRecyclerView5.setNestedScrollingEnabled(false);


        RecyclerView itemRecyclerView6 = (RecyclerView) findViewById(R.id.recyclerViewCategorySongs);
        RecyclerViewAdapter myAdapter6 = new RecyclerViewAdapter(this, listItem);
        itemRecyclerView6.setLayoutManager(new GridLayoutManager(this, 3));
        itemRecyclerView6.setAdapter(myAdapter6);
        itemRecyclerView6.setNestedScrollingEnabled(false);


        RecyclerView itemRecyclerView7 = (RecyclerView) findViewById(R.id.recyclerViewCategoryVideoGames);
        RecyclerViewAdapter myAdapter7 = new RecyclerViewAdapter(this, listItem);
        itemRecyclerView7.setLayoutManager(new GridLayoutManager(this, 3));
        itemRecyclerView7.setAdapter(myAdapter7);
        itemRecyclerView7.setNestedScrollingEnabled(false);

        RecyclerView itemRecyclerView8 = (RecyclerView) findViewById(R.id.recyclerViewCategoryCustom);
        RecyclerViewAdapter myAdapter8 = new RecyclerViewAdapter(this, listItem);
        itemRecyclerView8.setLayoutManager(new GridLayoutManager(this, 3));
        itemRecyclerView8.setAdapter(myAdapter8);
        itemRecyclerView8.setNestedScrollingEnabled(false);

         */

    }

}
