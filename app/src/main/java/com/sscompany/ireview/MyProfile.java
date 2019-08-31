package com.sscompany.ireview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import static com.sscompany.ireview.Homepage.profileOfMineOrFriend;

import com.sscompany.ireview.Elements.*;

public class MyProfile extends AppCompatActivity {

    //private RecyclerView mRecyclerView;
    //private RecyclerView.Adapter mAdapter;
    //private RecyclerView.LayoutManager mLayoutManager;
    List<InterfaceItem> listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);
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

        Book newBook = new Book();

        newBook.setName("The Martian");
        newBook.setGenre("Drama");
        newBook.setOwner("Book");

        listItem.add(newBook);
        listItem.add(newBook);
        listItem.add(newBook);
        listItem.add(newBook);
        listItem.add(newBook);
        listItem.add(newBook);

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


        if(profileOfMineOrFriend.equals("Mine")){

        }
    }

    public void showItem(View view) {
        Intent intent = new Intent(getApplicationContext(), ShowBookProperties.class);
        startActivity(intent);
    }
}
