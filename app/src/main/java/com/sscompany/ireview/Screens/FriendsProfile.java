package com.sscompany.ireview.Screens;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.sscompany.ireview.Adapters.RecyclerItemClickListener;
import com.sscompany.ireview.Adapters.RecyclerViewAdapter;
import com.sscompany.ireview.AddElement;
import com.sscompany.ireview.AddItem;
import com.sscompany.ireview.Models.*;
import com.sscompany.ireview.R;

import java.util.ArrayList;
import java.util.List;

import static com.sscompany.ireview.Screens.Homepage.profileOfMineOrFriend;

public class FriendsProfile extends AppCompatActivity {

    private List<InterfaceItem> bookList;
    private List<InterfaceItem> movieList;
    private List<InterfaceItem> musicList;
    private List<InterfaceItem> tvShowList;
    private List<InterfaceItem> placeList;
    private List<InterfaceItem> gameList;
    private List<InterfaceItem> websiteList;

    private DatabaseReference databaseReference;
    private boolean isSpeakButtonLongPressed = false;
    private Context mContext;
    String friendID;
    String userID;
    ImageView friendProfileCoverPicture;

    private AlertDialog.Builder ImageDialog;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_profile);

        //Initializing mContext
        mContext = FriendsProfile.this;

        bookList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        friendID = getIntent().getStringExtra("FRIENDID");
        friendProfileCoverPicture = findViewById(R.id.FriendProfileCoverPicture);

        databaseReference.child("user_account_settings")
                .child(friendID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String imageID = dataSnapshot.getValue(UserAccountSettings.class).getProfile_photo();

                        setFriendProfilePicture(imageID);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        checkFriendship();
        setItemRecyclerViews();

        if (profileOfMineOrFriend.equals("Mine")) {

        }
    }

    private void checkFriendship() {
        databaseReference.child("friendship").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ArrayList<String>> temp = new GenericTypeIndicator<ArrayList<String>>() {};
                ArrayList<String> friendshipList = dataSnapshot.getValue(temp);
                if(friendshipList.indexOf(friendID) != -1) {
                    ImageView img1 = findViewById(R.id.AddFriendButton);
                    ImageView img2 = findViewById(R.id.RemoveFriendButton);

                    img1.setVisibility(View.INVISIBLE);
                    img2.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void clickToFollow(View view) {
        final ArrayList<String> friendIDList = new ArrayList<>();
        ImageView img1 = (ImageView) view;
        ImageView img2 = findViewById(R.id.RemoveFriendButton);

        databaseReference.child("friendship").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ArrayList<String>> temp = new GenericTypeIndicator<ArrayList<String>>() {};
                ArrayList<String> friendshipList = dataSnapshot.getValue(temp);
                friendshipList.add(friendID);

                databaseReference.child("friendship").child(userID).setValue(friendshipList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        img1.setVisibility(View.INVISIBLE);
        img2.setVisibility(View.VISIBLE);
    }


    public void clickToUnfollow(View view) {
        ImageView img1 = (ImageView) view;
        ImageView img2 = findViewById(R.id.AddFriendButton);
        databaseReference.child("friendship").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ArrayList<String>> temp = new GenericTypeIndicator<ArrayList<String>>() {};
                ArrayList<String> friendshipList = dataSnapshot.getValue(temp);
                if(friendshipList.indexOf(friendID) != -1)
                    friendshipList.remove(friendshipList.indexOf(friendID));

                databaseReference.child("friendship").child(userID).setValue(friendshipList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        img1.setVisibility(View.INVISIBLE);
        img2.setVisibility(View.VISIBLE);
    }


    private void setFriendProfilePicture(String imageID) {
        System.out.println("String ImageID = " + imageID);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.image_placeholder);

        Glide.with(mContext).applyDefaultRequestOptions(requestOptions)
                .load(imageID).into(friendProfileCoverPicture);
    }

    private void setItemRecyclerViews() {
        //Setting recyclerView for books
        setBookRecyclerView();

        //Setting recyclerView for movies
        setMovieRecyclerView();

        //Setting recyclerView for music
        setMusicRecyclerView();

        //Setting recyclerView for places
        setPlaceRecyclerView();

        //Setting recyclerView for tv shows
        setTVShowRecyclerView();

        //Setting recyclerView for games
        setGameRecyclerView();

        //Setting recyclerView for websites
        setWebsiteRecyclerView();
    }

    private void setBookRecyclerView() {
        bookList = new ArrayList<>();

        databaseReference.child("user_items")
                .child(friendID)
                .child("books")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot sampleDataSnapshot : dataSnapshot.getChildren()) {
                            Book book = sampleDataSnapshot.getValue(Book.class);
                            bookList.add(book);
                        }

                        if (dataSnapshot.getChildrenCount() == 0) {
                            findViewById(R.id.no_book).setVisibility(View.VISIBLE);
                        }

                        RecyclerView itemRecyclerView1 = (RecyclerView) findViewById(R.id.recyclerViewCategoryBooks);
                        RecyclerViewAdapter myAdapter1 = new RecyclerViewAdapter(mContext, bookList);
                        GridLayoutManager gr1 = new GridLayoutManager(mContext, 3);
                        itemRecyclerView1.setLayoutManager(gr1);
                        itemRecyclerView1.setAdapter(myAdapter1);
                        itemRecyclerView1.setNestedScrollingEnabled(false);

                        /*final RecyclerView recyclerView = findViewById(R.id.recyclerViewCategoryBooks);
                        recyclerView.addOnItemTouchListener(
                                new RecyclerItemClickListener(mContext, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        System.out.println("Name: " + bookList.get(position).getName() + " Owner: " + bookList.get(position).getOwner());
                                        // do whatever
                                        Intent intent = new Intent(mContext, AddItem.class);

                                        intent.putExtra("action", "edit");
                                        intent.putExtra("category", "book");
                                        intent.putExtra("first_row", bookList.get(position).getName());
                                        intent.putExtra("second_row", bookList.get(position).getOwner());
                                        intent.putExtra("third_row", ((Book) bookList.get(position)).getGenre());
                                        intent.putExtra("fourth_row", "");
                                        intent.putExtra("cover_photo", bookList.get(position).getCover_photo());

                                        startActivity(intent);
                                    }

                                   @Override
                                    public void onLongItemClick(View view, int position) {
                                        isSpeakButtonLongPressed = true;
                                        ImageView imageView;
                                        imageView = (ImageView) ((LinearLayout) ((CardView) recyclerView.getChildAt(position)).getChildAt(0)).getChildAt(0);
                                        showCoverPhotoDialog(imageView);
                                    }

                                })
                        );*/
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void setMovieRecyclerView() {
        movieList = new ArrayList<>();

        databaseReference.child("user_items")
                .child(friendID)
                .child("movies")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot sampleDataSnapshot : dataSnapshot.getChildren()) {
                            Movie movie = sampleDataSnapshot.getValue(Movie.class);
                            movieList.add(movie);
                        }

                        if (dataSnapshot.getChildrenCount() == 0) {
                            findViewById(R.id.no_movie).setVisibility(View.VISIBLE);
                        }

                        RecyclerView itemRecyclerView1 = (RecyclerView) findViewById(R.id.recyclerViewCategoryMovies);
                        RecyclerViewAdapter myAdapter1 = new RecyclerViewAdapter(mContext, movieList);
                        GridLayoutManager gr1 = new GridLayoutManager(mContext, 3);
                        itemRecyclerView1.setLayoutManager(gr1);
                        itemRecyclerView1.setAdapter(myAdapter1);
                        itemRecyclerView1.setNestedScrollingEnabled(false);

                        /*RecyclerView recyclerView = findViewById(R.id.recyclerViewCategoryMovies);
                        recyclerView.addOnItemTouchListener(
                                new RecyclerItemClickListener(mContext, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        System.out.println("Name: " + movieList.get(position).getName() + " Owner: " + movieList.get(position).getOwner());
                                        // do whatever
                                        Intent intent = new Intent(mContext, AddItem.class);

                                        intent.putExtra("action", "edit");
                                        intent.putExtra("category", "movie");
                                        intent.putExtra("first_row", movieList.get(position).getName());
                                        intent.putExtra("second_row", movieList.get(position).getOwner());
                                        intent.putExtra("third_row", ((Movie) movieList.get(position)).getGenre());
                                        intent.putExtra("fourth_row", ((Movie) movieList.get(position)).getLead_actors());
                                        intent.putExtra("cover_photo", movieList.get(position).getCover_photo());

                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onLongItemClick(View view, int position) {
                                        // do whatever
                                    }

                                    @Override
                                    public void onTouch(View view, MotionEvent e, int position) {
                                        view.onTouchEvent(e);
                                        // We're only interested in when the button is released.
                                        if (e.getAction() == MotionEvent.ACTION_UP) {
                                            // We're only interested in anything if our speak button is currently pressed.
                                            if (isSpeakButtonLongPressed) {
                                                Log.d("MyProfile", "Released");
                                                System.out.println("Item Released");
                                                isSpeakButtonLongPressed = false;
                                            }
                                        }
                                    }
                                })
                        );*/
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void setMusicRecyclerView() {
        musicList = new ArrayList<>();

        databaseReference.child("user_items")
                .child(friendID)
                .child("musics")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot sampleDataSnapshot : dataSnapshot.getChildren()) {
                            Music music = sampleDataSnapshot.getValue(Music.class);
                            musicList.add(music);
                        }

                        if (dataSnapshot.getChildrenCount() == 0) {
                            findViewById(R.id.no_music).setVisibility(View.VISIBLE);
                        }

                        RecyclerView itemRecyclerView1 = (RecyclerView) findViewById(R.id.recyclerViewCategoryMusics);
                        RecyclerViewAdapter myAdapter1 = new RecyclerViewAdapter(mContext, musicList);
                        GridLayoutManager gr1 = new GridLayoutManager(mContext, 3);
                        itemRecyclerView1.setLayoutManager(gr1);
                        itemRecyclerView1.setAdapter(myAdapter1);
                        itemRecyclerView1.setNestedScrollingEnabled(false);

                        /*RecyclerView recyclerView = findViewById(R.id.recyclerViewCategoryMusics);
                        recyclerView.addOnItemTouchListener(
                                new RecyclerItemClickListener(mContext, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        System.out.println("Name: " + musicList.get(position).getName() + " Owner: " + musicList.get(position).getOwner());
                                        // do whatever
                                        Intent intent = new Intent(mContext, AddItem.class);

                                        intent.putExtra("action", "edit");
                                        intent.putExtra("category", "music");
                                        intent.putExtra("first_row", musicList.get(position).getName());
                                        intent.putExtra("second_row", musicList.get(position).getOwner());
                                        intent.putExtra("third_row", ((Music) musicList.get(position)).getGenre());
                                        intent.putExtra("fourth_row", ((Music) musicList.get(position)).getLanguage());
                                        intent.putExtra("cover_photo", musicList.get(position).getCover_photo());

                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onLongItemClick(View view, int position) {
                                        // do whatever
                                    }

                                    @Override
                                    public void onTouch(View view, MotionEvent e, int position) {
                                        view.onTouchEvent(e);
                                        // We're only interested in when the button is released.
                                        if (e.getAction() == MotionEvent.ACTION_UP) {
                                            // We're only interested in anything if our speak button is currently pressed.
                                            if (isSpeakButtonLongPressed) {
                                                Log.d("MyProfile", "Released");
                                                System.out.println("Item Released");
                                                isSpeakButtonLongPressed = false;
                                            }
                                        }
                                    }
                                })
                        );*/
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void setPlaceRecyclerView() {
        placeList = new ArrayList<>();

        databaseReference.child("user_items")
                .child(friendID)
                .child("places")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot sampleDataSnapshot : dataSnapshot.getChildren()) {
                            Place place = sampleDataSnapshot.getValue(Place.class);
                            bookList.add(place);
                        }

                        if (dataSnapshot.getChildrenCount() == 0) {
                            findViewById(R.id.no_place).setVisibility(View.VISIBLE);
                        }

                        RecyclerView itemRecyclerView1 = (RecyclerView) findViewById(R.id.recyclerViewCategoryPlaces);
                        RecyclerViewAdapter myAdapter1 = new RecyclerViewAdapter(mContext, placeList);
                        GridLayoutManager gr1 = new GridLayoutManager(mContext, 3);
                        itemRecyclerView1.setLayoutManager(gr1);
                        itemRecyclerView1.setAdapter(myAdapter1);
                        itemRecyclerView1.setNestedScrollingEnabled(false);

                        /*RecyclerView recyclerView = findViewById(R.id.recyclerViewCategoryPlaces);
                        recyclerView.addOnItemTouchListener(
                                new RecyclerItemClickListener(mContext, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        System.out.println("Name: " + placeList.get(position).getName() + " Owner: " + placeList.get(position).getOwner());
                                        // do whatever
                                        Intent intent = new Intent(mContext, AddItem.class);

                                        intent.putExtra("action", "edit");
                                        intent.putExtra("category", "place");
                                        intent.putExtra("first_row", placeList.get(position).getName());
                                        intent.putExtra("second_row", ((Place) placeList.get(position)).getPlace_type());
                                        intent.putExtra("third_row", ((Place) placeList.get(position)).getAddress());
                                        intent.putExtra("fourth_row", "");
                                        intent.putExtra("cover_photo", placeList.get(position).getCover_photo());

                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onLongItemClick(View view, int position) {
                                        // do whatever
                                    }

                                    @Override
                                    public void onTouch(View view, MotionEvent e, int position) {
                                        view.onTouchEvent(e);
                                        // We're only interested in when the button is released.
                                        if (e.getAction() == MotionEvent.ACTION_UP) {
                                            // We're only interested in anything if our speak button is currently pressed.
                                            if (isSpeakButtonLongPressed) {
                                                Log.d("MyProfile", "Released");
                                                System.out.println("Item Released");
                                                isSpeakButtonLongPressed = false;
                                            }
                                        }
                                    }
                                })
                        );*/
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void setTVShowRecyclerView() {
        tvShowList = new ArrayList<>();

        databaseReference.child("user_items")
                .child(friendID)
                .child("tv_shows")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot sampleDataSnapshot : dataSnapshot.getChildren()) {
                            TVShow tvShow = sampleDataSnapshot.getValue(TVShow.class);
                            tvShowList.add(tvShow);
                        }

                        if (dataSnapshot.getChildrenCount() == 0) {
                            findViewById(R.id.no_tvshow).setVisibility(View.VISIBLE);
                        }

                        RecyclerView itemRecyclerView1 = (RecyclerView) findViewById(R.id.recyclerViewCategoryTvShows);
                        RecyclerViewAdapter myAdapter1 = new RecyclerViewAdapter(mContext, tvShowList);
                        GridLayoutManager gr1 = new GridLayoutManager(mContext, 3);
                        itemRecyclerView1.setLayoutManager(gr1);
                        itemRecyclerView1.setAdapter(myAdapter1);
                        itemRecyclerView1.setNestedScrollingEnabled(false);

                        /*RecyclerView recyclerView = findViewById(R.id.recyclerViewCategoryTvShows);
                        recyclerView.addOnItemTouchListener(
                                new RecyclerItemClickListener(mContext, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        System.out.println("Name: " + tvShowList.get(position).getName() + " Owner: " + tvShowList.get(position).getOwner());
                                        // do whatever
                                        Intent intent = new Intent(mContext, AddItem.class);

                                        intent.putExtra("action", "edit");
                                        intent.putExtra("category", "tv_show");
                                        intent.putExtra("first_row", tvShowList.get(position).getName());
                                        intent.putExtra("second_row", tvShowList.get(position).getOwner());
                                        intent.putExtra("third_row", ((TVShow) tvShowList.get(position)).getGenre());
                                        intent.putExtra("fourth_row", "");
                                        intent.putExtra("cover_photo", tvShowList.get(position).getCover_photo());

                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onLongItemClick(View view, int position) {
                                        // do whatever
                                    }

                                    @Override
                                    public void onTouch(View view, MotionEvent e, int position) {
                                        view.onTouchEvent(e);
                                        // We're only interested in when the button is released.
                                        if (e.getAction() == MotionEvent.ACTION_UP) {
                                            // We're only interested in anything if our speak button is currently pressed.
                                            if (isSpeakButtonLongPressed) {
                                                Log.d("MyProfile", "Released");
                                                System.out.println("Item Released");
                                                isSpeakButtonLongPressed = false;
                                            }
                                        }
                                    }
                                })
                        );*/
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

    private void setGameRecyclerView() {
        gameList = new ArrayList<>();

        databaseReference.child("user_items")
                .child(friendID)
                .child("games")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot sampleDataSnapshot : dataSnapshot.getChildren()) {
                            Game game = sampleDataSnapshot.getValue(Game.class);
                            gameList.add(game);
                        }

                        if (dataSnapshot.getChildrenCount() == 0) {
                            findViewById(R.id.no_game).setVisibility(View.VISIBLE);
                        }

                        RecyclerView itemRecyclerView1 = (RecyclerView) findViewById(R.id.recyclerViewCategoryVideoGames);
                        RecyclerViewAdapter myAdapter1 = new RecyclerViewAdapter(mContext, gameList);
                        GridLayoutManager gr1 = new GridLayoutManager(mContext, 3);
                        itemRecyclerView1.setLayoutManager(gr1);
                        itemRecyclerView1.setAdapter(myAdapter1);
                        itemRecyclerView1.setNestedScrollingEnabled(false);

                        /*RecyclerView recyclerView = findViewById(R.id.recyclerViewCategoryVideoGames);
                        recyclerView.addOnItemTouchListener(
                                new RecyclerItemClickListener(mContext, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        System.out.println("Name: " + gameList.get(position).getName() + " Owner: " + gameList.get(position).getOwner());
                                        // do whatever
                                        Intent intent = new Intent(mContext, AddItem.class);

                                        intent.putExtra("action", "edit");
                                        intent.putExtra("category", "game");
                                        intent.putExtra("first_row", gameList.get(position).getName());
                                        intent.putExtra("second_row", gameList.get(position).getOwner());
                                        intent.putExtra("third_row", ((Game) gameList.get(position)).getGame_type());
                                        intent.putExtra("fourth_row", "");
                                        intent.putExtra("cover_photo", gameList.get(position).getCover_photo());

                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onLongItemClick(View view, int position) {
                                        // do whatever
                                    }

                                    @Override
                                    public void onTouch(View view, MotionEvent e, int position) {
                                        view.onTouchEvent(e);
                                        // We're only interested in when the button is released.
                                        if (e.getAction() == MotionEvent.ACTION_UP) {
                                            // We're only interested in anything if our speak button is currently pressed.
                                            if (isSpeakButtonLongPressed) {
                                                Log.d("MyProfile", "Released");
                                                System.out.println("Item Released");
                                                isSpeakButtonLongPressed = false;
                                            }
                                        }
                                    }
                                })
                        );*/
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void setWebsiteRecyclerView() {
        websiteList = new ArrayList<>();

        databaseReference.child("user_items")
                .child(friendID)
                .child("websites")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot sampleDataSnapshot : dataSnapshot.getChildren()) {
                            Website website = sampleDataSnapshot.getValue(Website.class);
                            websiteList.add(website);
                        }

                        if (dataSnapshot.getChildrenCount() == 0) {
                            findViewById(R.id.no_website).setVisibility(View.VISIBLE);
                        }

                        RecyclerView itemRecyclerView1 = (RecyclerView) findViewById(R.id.recyclerViewCategoryWebsites);
                        RecyclerViewAdapter myAdapter1 = new RecyclerViewAdapter(mContext, websiteList);
                        GridLayoutManager gr1 = new GridLayoutManager(mContext, 3);
                        itemRecyclerView1.setLayoutManager(gr1);
                        itemRecyclerView1.setAdapter(myAdapter1);
                        itemRecyclerView1.setNestedScrollingEnabled(false);

                        /*RecyclerView recyclerView = findViewById(R.id.recyclerViewCategoryWebsites);
                        recyclerView.addOnItemTouchListener(
                                new RecyclerItemClickListener(mContext, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        System.out.println("Name: " + websiteList.get(position).getName() + " Owner: " + websiteList.get(position).getOwner());
                                        // do whatever

                                        Intent intent = new Intent(mContext, AddItem.class);

                                        intent.putExtra("action", "edit");
                                        intent.putExtra("category", "website");
                                        intent.putExtra("first_row", websiteList.get(position).getName());
                                        intent.putExtra("second_row", ((Website) websiteList.get(position)).getUse());
                                        intent.putExtra("third_row", "");
                                        intent.putExtra("fourth_row", "");
                                        intent.putExtra("cover_photo", websiteList.get(position).getCover_photo());

                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onLongItemClick(View view, int position) {
                                        // do whatever
                                    }

                                    @Override
                                    public void onTouch(View view, MotionEvent e, int position) {
                                        view.onTouchEvent(e);
                                        // We're only interested in when the button is released.
                                        if (e.getAction() == MotionEvent.ACTION_UP) {
                                            // We're only interested in anything if our speak button is currently pressed.
                                            if (isSpeakButtonLongPressed) {
                                                Log.d("MyProfile", "Released");
                                                System.out.println("Item Released");
                                                isSpeakButtonLongPressed = false;
                                            }
                                        }
                                    }
                                })
                        );*/
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void showCoverPhotoDialog(ImageView v) {
        Bitmap bitmap;
        ImageDialog = new AlertDialog.Builder(mContext);
        ImageView showImage = new ImageView(mContext);
        v.invalidate();
        BitmapDrawable dr = (BitmapDrawable) ((ImageView) v).getDrawable();
        bitmap = dr.getBitmap();

        //*********************************************************************************************************
        //MAX Width  MAX Height
        //*********************************************************************************************************

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int maxHeight = displayMetrics.heightPixels - 150;
        int maxWidth = displayMetrics.widthPixels - 150;

        int inWidth = bitmap.getWidth();
        int inHeight = bitmap.getHeight();
        int outWidth = inWidth;
        int outHeight = inHeight;
        if (inHeight > maxHeight) {
            outHeight = maxHeight;
            outWidth = (int) ((double) (maxHeight * inWidth) / (double) inHeight);
        } else if (inWidth > maxWidth) {
            outWidth = maxWidth;
            outHeight = (int) ((double) (maxWidth * inHeight) / (double) inWidth);
        }

        bitmap = Bitmap.createScaledBitmap(bitmap, outWidth, outHeight, false);

        inWidth = bitmap.getWidth();
        inHeight = bitmap.getHeight();
        outWidth = inWidth;
        outHeight = inHeight;

        if (inHeight > maxHeight) {
            outHeight = maxHeight;
            outWidth = (int) ((double) (maxHeight * inWidth) / (double) inHeight);
        } else if (inWidth > maxWidth) {
            outWidth = maxWidth;
            outHeight = (int) ((double) (maxWidth * inHeight) / (double) inWidth);
        }

        bitmap = Bitmap.createScaledBitmap(bitmap, outWidth, outHeight, false);

        //*********************************************************************************************************
        //MIN Width  MIN Height
        //*********************************************************************************************************

        DisplayMetrics displayMetrics1 = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics1);
        int minHeight = displayMetrics.heightPixels - 200;
        int minWidth = displayMetrics.widthPixels - 200;

        inWidth = bitmap.getWidth();
        inHeight = bitmap.getHeight();
        outWidth = inWidth;
        outHeight = inHeight;
        if (inHeight < minHeight) {
            outHeight = minHeight;
            outWidth = (int) ((double) (minHeight * inWidth) / (double) inHeight);
        } else if (inWidth < minWidth) {
            outWidth = minWidth;
            outHeight = (int) ((double) (minWidth * inHeight) / (double) inWidth);
        }

        if (outWidth <= maxWidth && outHeight <= maxHeight) {
            bitmap = Bitmap.createScaledBitmap(bitmap, outWidth, outHeight, false);
        }

        inWidth = bitmap.getWidth();
        inHeight = bitmap.getHeight();
        outWidth = inWidth;
        outHeight = inHeight;

        if (inHeight < minHeight) {
            outHeight = minHeight;
            outWidth = (int) ((double) (minHeight * inWidth) / (double) inHeight);
        } else if (inWidth < minWidth) {
            outWidth = minWidth;
            outHeight = (int) ((double) (minWidth * inHeight) / (double) inWidth);
        }

        if (outWidth <= maxWidth && outHeight <= maxHeight) {
            bitmap = Bitmap.createScaledBitmap(bitmap, outWidth, outHeight, false);
        }

        showImage.setImageBitmap(bitmap);
        ImageDialog.setView(showImage);
        ImageDialog.setCancelable(true);
        alertDialog = ImageDialog.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        alertDialog.show();

        alertDialog.getWindow().setLayout(bitmap.getWidth(), bitmap.getHeight());
    }


    public void showItem(View view) {
        Intent intent = new Intent(getApplicationContext(), ShowBookProperties.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), ShowFriendList.class);
        startActivity(intent);
    }
}
