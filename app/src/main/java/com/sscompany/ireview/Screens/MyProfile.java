package com.sscompany.ireview.Screens;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import static com.sscompany.ireview.Screens.Homepage.profileOfMineOrFriend;

import com.sscompany.ireview.Adapters.RecyclerItemClickListener;
import com.sscompany.ireview.Adapters.RecyclerViewAdapter;
import com.sscompany.ireview.Models.*;
import com.sscompany.ireview.R;

public class MyProfile extends AppCompatActivity {

    //private RecyclerView mRecyclerView;
    //private RecyclerView.Adapter mAdapter;
    //private RecyclerView.LayoutManager mLayoutManager;
    private List<InterfaceItem> listItem;

    private Context mContext;

    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);

        mContext = MyProfile.this;
        /*
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewCategories);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        String[] str = {"Books", "Movies", "Restaurants", "Places", "TV Shows", "Songs", "Video Games", "Custom"};
        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(str);
        mRecyclerView.setAdapter(mAdapter);
        */

        listItem = new ArrayList<>();

        listItem.add(new Book(
                        "Book 1",
                        "Author",
                        "Drama",
                        ""
                )
        );

        listItem.add(new Book(
                        "Book 2",
                        "Author",
                        "Drama",
                        ""
                )
        );

        listItem.add(new Book(
                        "Book 3",
                        "Author",
                        "Drama",
                        ""
                )
        );

        listItem.add(new Book(
                        "Book 4",
                        "Author",
                        "Drama",
                        ""
                )
        );

        listItem.add(new Book(
                        "Book 5",
                        "Author",
                        "Drama",
                        ""
                )
        );

        listItem.add(new Book(
                        "Book 6",
                        "Author",
                        "Drama",
                        ""
                )
        );



        RecyclerView itemRecyclerView1 = (RecyclerView) findViewById(R.id.recyclerViewCategoryBooks);
        RecyclerViewAdapter myAdapter1 = new RecyclerViewAdapter(this, listItem);
        GridLayoutManager gr1 = new GridLayoutManager(this, 3);
        itemRecyclerView1.setLayoutManager(gr1);
        itemRecyclerView1.setAdapter(myAdapter1);
        itemRecyclerView1.setNestedScrollingEnabled(false);

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

        final RecyclerView recyclerView = findViewById(R.id.recyclerViewCategoryBooks);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(mContext, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position)
                    {
                        System.out.println("Name: " + listItem.get(position).getName() + " Owner: " + listItem.get(position).getOwner());
                        // do whatever
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

    }

}
