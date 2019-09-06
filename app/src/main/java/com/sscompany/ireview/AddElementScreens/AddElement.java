package com.sscompany.ireview.AddElementScreens;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.sscompany.ireview.AdapterForItemList;
import com.sscompany.ireview.AddItem;
import com.sscompany.ireview.Elements.*;
import com.sscompany.ireview.Homepage;
import com.sscompany.ireview.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class AddElement extends AppCompatActivity
{
    private String category;
    private final ArrayList<InterfaceItem> itemBooks = new ArrayList<>();
    private final ArrayList<InterfaceItem> itemMovies = new ArrayList<>();
    private final ArrayList<InterfaceItem> itemMusics = new ArrayList<>();
    private final ArrayList<InterfaceItem> itemTVShows = new ArrayList<>();
    private final ArrayList<InterfaceItem> itemPlaces = new ArrayList<>();
    private final ArrayList<InterfaceItem> itemGames = new ArrayList<>();
    private final ArrayList<InterfaceItem> itemWebsites = new ArrayList<>();

    private Context mContext;

    private Movie newMovie;
    private Book newBook;
    private Music newMusic;
    private Place newPlace;
    private TVShow newTVShow;
    private Game newVideoGame;
    private Website newWebsite;
    private SearchView searchView;
    private ListView listView;
    private AdapterForItemList myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_element);

        mContext = AddElement.this;

        category = "book";
        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setQueryHint("Search in " + category.substring(0, 1).toUpperCase() + category.substring(1) + "s");
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

        ParseQuery<ParseObject> books = ParseQuery.getQuery("Book");
        final List<String> bookNames = new ArrayList<>();
        final List<String> bookAuthors = new ArrayList<>();

        books.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> bookList, ParseException e) {
                if (e == null) {

                    for(ParseObject book: bookList)
                    {
                        newBook = new Book();
                        newBook.setName(book.getString("name"));
                        newBook.setOwner(book.getString("author"));
                        newBook.setGenre(book.getString("genre"));
                        itemBooks.add(newBook);
                    }

                    listView = (ListView) findViewById(R.id.alreadyAdded);

                    myAdapter = new AdapterForItemList(AddElement.this, itemBooks);

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddElement.this, android.R.layout.simple_list_item_1, bookNames);

                    listView.setAdapter(myAdapter);

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }

    private void getData(String query)
    {
        ArrayList<InterfaceItem> output = new ArrayList<>();
        ArrayList<InterfaceItem> filteredOutput = new ArrayList<>();

        if(category.equals("book"))
            for(int i = 0; i < itemBooks.size(); i++)
            {
                output.add(itemBooks.get(i));
            }
        else if(category.equals("movie"))
            for(int i = 0; i < itemMovies.size(); i++)
            {
                output.add(itemMovies.get(i));
            }
        else if(category.equals("music"))
            for(int i = 0; i < itemMusics.size(); i++)
            {
                output.add(itemMusics.get(i));
            }
        else if(category.equals("tv_show"))
            for(int i = 0; i < itemTVShows.size(); i++)
            {
                output.add(itemTVShows.get(i));
            }
        else if(category.equals("place"))
            for(int i = 0; i < itemMovies.size(); i++)
            {
                output.add(itemMovies.get(i));
            }
        else if(category.equals("game"))
            for(int i = 0; i < itemGames.size(); i++)
            {
                output.add(itemGames.get(i));
            }
        else if(category.equals("website"))
            for(int i = 0; i < itemWebsites.size(); i++)
            {
                output.add(itemWebsites.get(i));
            }

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

        listView.setAdapter(myAdapter);

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
        Intent intent = new Intent(getApplicationContext(), Homepage.class);
        startActivity(intent);
    }


    public void book(View view)
    {
        category = "book";


        /*
        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setQueryHint("Search in " + category.substring(0, 1).toUpperCase() + category.substring(1) + "s");
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

        ParseQuery<ParseObject> books = ParseQuery.getQuery("Book");

        books.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> bookList, ParseException e) {
                if (e == null) {

                    for(ParseObject book: bookList)
                    {
                        //bookNames.add(book.getString("name"));
                        //bookAuthors.add(book.getString("author"));
                        newBook = new Book();
                        newBook.setOwner(book.getString("author"));
                        newBook.setName(book.getString("name"));
                        newBook.setGenre(book.getString("genre"));

                        itemBooks.add(newBook);
                    }

                    listView = (ListView) findViewById(R.id.alreadyAdded);

                    myAdapter = new AdapterForItemList(AddElement.this, itemBooks);

                    listView.setAdapter(myAdapter);

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
        */

    }

    public void movie(View view)
    {
        category = "movie";

        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setQueryHint("Search in " + category.substring(0, 1).toUpperCase() + category.substring(1) + "s");
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

        ParseQuery<ParseObject> movies = ParseQuery.getQuery("Movie");
        //final List<String> bookNames = new ArrayList<>();
        //final List<String> bookAuthors = new ArrayList<>();

        movies.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> movieList, ParseException e) {
                if (e == null) {

                    for(ParseObject movie: movieList)
                    {
                        newMovie = new Movie();
                        newMovie.setName(movie.getString("name"));
                        newMovie.setOwner(movie.getString("director"));

                        itemMovies.add(newMovie);
                    }

                    listView = (ListView) findViewById(R.id.alreadyAdded);

                    myAdapter = new AdapterForItemList(AddElement.this, itemMovies);

                    listView.setAdapter(myAdapter);

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }

    public void place(View view)
    {
        category = "place";

        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setQueryHint("Search in " + category.substring(0, 1).toUpperCase() + category.substring(1) + "s");
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

        ParseQuery<ParseObject> places = ParseQuery.getQuery("Place");

        places.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> placeList, ParseException e) {
                if (e == null) {

                    for(ParseObject place: placeList)
                    {
                        newPlace = new Place();
                        itemPlaces.add(newPlace);
                    }

                    listView = (ListView) findViewById(R.id.alreadyAdded);

                    myAdapter = new AdapterForItemList(AddElement.this, itemPlaces);

                    listView.setAdapter(myAdapter);

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }

    public void music(View view)
    {
        category = "music";

        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setQueryHint("Search in " + category.substring(0, 1).toUpperCase() + category.substring(1) + "s");
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

        ParseQuery<ParseObject> musics = ParseQuery.getQuery("Music");

        musics.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> musicList, ParseException e) {
                if (e == null) {

                    for(ParseObject music: musicList)
                    {
                        newMusic = new Music();
                        itemMusics.add(newMusic);
                    }

                    listView = (ListView) findViewById(R.id.alreadyAdded);

                    myAdapter = new AdapterForItemList(AddElement.this, itemMusics);

                    listView.setAdapter(myAdapter);

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }

    public void game(View view)
    {
        category = "game";

        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setQueryHint("Search in " + category.substring(0, 1).toUpperCase() + category.substring(1) + "s");
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

        ParseQuery<ParseObject> games = ParseQuery.getQuery("Game");
    }

    public void tv_show(View view)
    {
        category = "tv_show";

        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setQueryHint("Search in " + category.substring(0, 1).toUpperCase() + category.substring(1) + "s");
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

        ParseQuery<ParseObject> tvshows = ParseQuery.getQuery("TVShow");

        tvshows.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> tvshowList, ParseException e) {
                if (e == null) {

                    for(ParseObject tvshow: tvshowList)
                    {

                        newTVShow = new TVShow();

                        itemTVShows.add(newTVShow);
                    }

                    listView = (ListView) findViewById(R.id.alreadyAdded);

                    myAdapter = new AdapterForItemList(AddElement.this, itemTVShows);

                    listView.setAdapter(myAdapter);

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }

    public void website(View view) {
        category = "website";

        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setQueryHint("Search in " + category.substring(0, 1).toUpperCase() + category.substring(1) + "s");
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

        ParseQuery<ParseObject> websites = ParseQuery.getQuery("Website");

        websites.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> websiteList, ParseException e) {
                if (e == null) {

                    for(ParseObject website: websiteList)
                    {
                        newWebsite = new Website();
                        itemWebsites.add(newWebsite);
                    }

                    listView = (ListView) findViewById(R.id.alreadyAdded);

                    myAdapter = new AdapterForItemList(AddElement.this, itemWebsites);

                    listView.setAdapter(myAdapter);

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }
}
