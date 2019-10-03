package com.sscompany.ireview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sscompany.ireview.Adapters.AdapterForItemList;
import com.sscompany.ireview.Models.Book;
import com.sscompany.ireview.Models.Song;
import com.sscompany.ireview.Models.VideoGame;
import com.sscompany.ireview.Models.InterfaceItem;
import com.sscompany.ireview.Models.Movie;
import com.sscompany.ireview.Models.Place;
import com.sscompany.ireview.Models.TVShow;
import com.sscompany.ireview.Models.Website;
import com.sscompany.ireview.Screens.Homepage;

import java.util.ArrayList;

//This is the version I uploaded for the test of github on my laptop
public class AddElement extends AppCompatActivity implements AdapterForItemList.ItemClickListener
{
    private String category;
    private ArrayList<InterfaceItem> items = new ArrayList<>();

    private Context mContext;

    private SearchView searchView;
    private AdapterForItemList myAdapter;

    private DatabaseReference myRef;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_element);

        //Initializing mContext
        mContext = AddElement.this;

        //Initializing myRef
        myRef = FirebaseDatabase.getInstance().getReference();

        //Initializing category
        category = "Books";

        //Initializing and setting searchView
        initSearchView();

        //Initializing recyclerView
        recyclerView = findViewById(R.id.alreadyAdded);

        //Initializing book button and performing click in order to add books to recyclerView
        Button book = findViewById(R.id.categoryButtonBooks);
        book.performClick();
    }

    private void initSearchView()
    {
        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setQueryHint("Search in " + category);
        searchView.setIconifiedByDefault(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getData(newText);
                return false;
            }
        });
    }

    private void getData(String query)
    {
        ArrayList<InterfaceItem> output = new ArrayList<>();
        ArrayList<InterfaceItem> filteredOutput = new ArrayList<>();

        for(int i = 0; i < items.size(); i++)
            output.add(items.get(i));

        if (searchView != null)
        {
            for(InterfaceItem item: output)
            {
                if(item.getName().toLowerCase().contains(query.toLowerCase()))
                {
                    filteredOutput.add(item);
                }
            }
        }
        else
            filteredOutput = output;

        myAdapter = new AdapterForItemList(AddElement.this, filteredOutput);
        myAdapter.setClickListener(AddElement.this);
        recyclerView.setAdapter(myAdapter);
    }

    /**
     * Add Manually Button is clicked
     *
     * @param view
     */
    public void addManually(View view)
    {
        Intent intent = new Intent(mContext, AddItem.class);

        intent.putExtra("category", category);
        intent.putExtra("action", "add");

        startActivity(intent);
    }

    /**
     * Back Button is clicked
     *
     * @param view
     */
    public void back(View view)
    {
        onBackPressed();
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(getApplicationContext(), Homepage.class);
        startActivity(intent);
        finish();
    }

    private void getItemsFromDatabase()
    {
        String databaseClass = category;

        searchView.setQueryHint("Search in " + databaseClass);

        myRef.child(databaseClass)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        for(DataSnapshot ds : dataSnapshot.getChildren())
                        {
                            InterfaceItem item = new Book();
                            if(category.equals("Books"))
                                item = ds.getValue(Book.class);
                            else if(category.equals("Movies"))
                                item = ds.getValue(Movie.class);
                            else if(category.equals("Songs"))
                                item = ds.getValue(Song.class);
                            else if(category.equals("TV Shows"))
                                item = ds.getValue(TVShow.class);
                            else if(category.equals("Places"))
                                item = ds.getValue(Place.class);
                            else if(category.equals("Video Games"))
                                item = ds.getValue(VideoGame.class);
                            else if(category.equals("Websites"))
                                item = ds.getValue(Website.class);

                            items.add(item);
                        }

                        recyclerView = (RecyclerView) findViewById(R.id.alreadyAdded);
                        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                        myAdapter = new AdapterForItemList(AddElement.this, items);
                        myAdapter.setClickListener(AddElement.this);
                        recyclerView.setAdapter(myAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    public void book(View view)
    {
        items = new ArrayList<>();

        category = "Books";

        getItemsFromDatabase();
    }

    public void movie(View view)
    {
        items = new ArrayList<>();

        category = "Movies";

        getItemsFromDatabase();
    }

    public void place(View view)
    {
        items = new ArrayList<>();

        category = "Places";

        getItemsFromDatabase();
    }

    public void song(View view)
    {
        items = new ArrayList<>();

        category = "Songs";

        getItemsFromDatabase();
    }

    public void videoGame(View view)
    {
        items = new ArrayList<>();

        category = "Video Games";

        getItemsFromDatabase();
    }

    public void tv_show(View view)
    {
        items = new ArrayList<>();

        category = "TV Shows";

        getItemsFromDatabase();
    }

    public void website(View view)
    {
        items = new ArrayList<>();

        category = "Websites";

        getItemsFromDatabase();
    }

    public void onItemClick(View view, int position)
    {

    }
}
