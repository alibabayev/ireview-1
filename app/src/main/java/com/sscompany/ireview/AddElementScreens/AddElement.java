package com.sscompany.ireview.AddElementScreens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.sscompany.ireview.AdapterForItemList;
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
    final ArrayList<Item> itemBooks = new ArrayList<>();
    final ArrayList<Item> itemMovies = new ArrayList<>();
    final ArrayList<Item> itemMusics = new ArrayList<>();
    final ArrayList<Item> itemTVShows = new ArrayList<>();
    final ArrayList<Item> itemPlaces = new ArrayList<>();
    final ArrayList<Item> itemGames = new ArrayList<>();
    final ArrayList<Item> itemWebsites = new ArrayList<>();
    Movie newMovie;
    Book newBook;
    Music newMusic;
    Place newPlace;
    TVShow newTVShow;
    VideoGame newVideoGame;
    Website newWebsite;
    SearchView searchView;
    ListView listView;
    AdapterForItemList myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_element);

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
                        //bookNames.add(book.getString("name"));
                        //bookAuthors.add(book.getString("author"));
                        newBook = new Book(book.getString("name"),book.getString("author"), book.getString("genre"));
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
        ArrayList<Item> output = new ArrayList<>();
        ArrayList<Item> filteredOutput = new ArrayList<>();

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
        else if(category.equals("tvshow"))
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
            for(Item item: output)
            {
                if(item.getTitle().toLowerCase().contains(query.toLowerCase()))
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

    public void addManually(View view)
    {
        if (category.equals("music"))
        {
            Intent intent = new Intent(getApplicationContext(), AddMusic.class);
            startActivity(intent);
        }
        else if (category.equals("book"))
        {
            Intent intent = new Intent(getApplicationContext(), AddBook.class);
            startActivity(intent);
        }
        else if(category.equals("movie"))
        {
            Intent intent = new Intent(getApplicationContext(), AddMovie.class);
            startActivity(intent);
        }
        else if(category.equals("tvshow"))
        {
            Intent intent = new Intent(getApplicationContext(), AddTVShow.class);
            startActivity(intent);
        }
        else if(category.equals("game"))
        {
            Intent intent = new Intent(getApplicationContext(), AddVideoGame.class);
            startActivity(intent);
        }
        else if(category.equals("place"))
        {
            Intent intent = new Intent(getApplicationContext(), AddPlace.class);
            startActivity(intent);
        }
        else if(category.equals("website"))
        {
            Intent intent = new Intent(getApplicationContext(), AddWebsite.class);
            startActivity(intent);
        }

    }

    public void back(View view)
    {
        Intent intent = new Intent(getApplicationContext(), Homepage.class);
        startActivity(intent);
    }

    public void book(View view)
    {
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

        books.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> bookList, ParseException e) {
                if (e == null) {

                    for(ParseObject book: bookList)
                    {
                        //bookNames.add(book.getString("name"));
                        //bookAuthors.add(book.getString("author"));
                        newBook = new Book(book.getString("name"),book.getString("author"), book.getString("genre"));
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
                        //bookNames.add(book.getString("name"));
                        //bookAuthors.add(book.getString("author"));
                        if(movie.getString("director") == null && movie.getString("leadActors") == null)
                        {
                            newMovie = new Movie(movie.getString("name"), movie.getString("genre"));
                        }
                        else if(movie.getString("director") == null)
                        {
                            newMovie = new Movie(movie.getString("name"), movie.getString("leadActors"), movie.getString("genre"), 1);
                        }
                        else if(movie.getString("leadActors") == null)
                        {
                            newMovie = new Movie(movie.getString("name"), movie.getString("director"), movie.getString("genre"));
                        }
                        else {
                            newMovie = new Movie(movie.getString("name"), movie.getString("director"), movie.getString("leadActors"), movie.getString("genre"));
                        }

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
                        newPlace = new Place(place.getString("name"), place.getString("address"), place.getString("typeOfPlace"));
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
                        newMusic = new Music(music.getString("name"), music.getString("artist"), music.getString("genre"), music.getString("language"));
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

    public void videoGame(View view)
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

        games.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> gameList, ParseException e) {
                if (e == null) {

                    for(ParseObject game: gameList)
                    {

                        if(game.getString("developer") == null && game.getString("mode") == null)
                        {
                            newVideoGame = new VideoGame(game.getString("name"), game.getString("genre"));
                        }
                        else if(game.getString("developer") == null)
                        {
                            newVideoGame = new VideoGame(game.getString("name"), game.getString("genre"), game.getString("mode"), 1);
                        }
                        else if(game.getString("mode") == null)
                        {
                            newVideoGame = new VideoGame(game.getString("name"), game.getString("genre"), game.getString("developer"));
                        }
                        else {
                            newVideoGame = new VideoGame(game.getString("name"), game.getString("genre"), game.getString("developer"), game.getString("mode"));
                        }

                        itemGames.add(newVideoGame);
                    }

                    listView = (ListView) findViewById(R.id.alreadyAdded);

                    myAdapter = new AdapterForItemList(AddElement.this, itemGames);

                    listView.setAdapter(myAdapter);

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }

    public void tvshow(View view)
    {
        category = "tvshow";

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

                        if(tvshow.getString("host") == null && tvshow.getString("favoriteEpisode") == null)
                        {
                            newTVShow = new TVShow(tvshow.getString("title"), tvshow.getString("genre"));
                        }
                        else if(tvshow.getString("host") == null)
                        {
                            newTVShow = new TVShow(tvshow.getString("title"), tvshow.getString("genre"), tvshow.getString("favoriteEpisode"), 1);
                        }
                        else if(tvshow.getString("favoriteEpisode") == null)
                        {
                            newTVShow = new TVShow(tvshow.getString("title"), tvshow.getString("host"), tvshow.getString("genre"));
                        }
                        else {
                            newTVShow = new TVShow(tvshow.getString("title"), tvshow.getString("host"), tvshow.getString("genre"), tvshow.getString("favoriteEpisode"));
                        }

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
                        newWebsite = new Website(website.getString("name"), website.getString("use"));
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
