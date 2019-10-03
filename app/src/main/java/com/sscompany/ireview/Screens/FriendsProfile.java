package com.sscompany.ireview.Screens;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.icu.util.Freezable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.sscompany.ireview.Models.*;
import com.sscompany.ireview.R;

import java.util.ArrayList;
import java.util.List;

import static com.sscompany.ireview.Screens.Homepage.profileOfMineOrFriend;

public class FriendsProfile extends AppCompatActivity
{
    private List<MyItem> myItemsList;
    private List<MyItem> myItemsList1;
    private List<MyItem> myItemsList2;
    private List<MyItem> myItemsList3;
    private List<MyItem> myItemsList4;
    private List<MyItem> myItemsList5;
    private List<MyItem> myItemsList6;

    private DatabaseReference myRef;
    private boolean isSpeakButtonLongPressed = false;
    private Context mContext;
    String friendID;
    String userID;
    ImageView friendProfileCoverPicture;
    TextView name_and_surnameTextView;
    TextView followerCounterTextView;
    TextView followingCounterTextView;

    private AlertDialog.Builder ImageDialog;
    private AlertDialog alertDialog;
    private String whereComeFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_profile);

        //Initializing mContext
        mContext = FriendsProfile.this;


        myRef = FirebaseDatabase.getInstance().getReference();

        myRef = FirebaseDatabase.getInstance().getReference();
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        friendID = getIntent().getStringExtra("FRIENDID");
        whereComeFrom = getIntent().getStringExtra("WHERECOMEFROM");

        friendProfileCoverPicture = findViewById(R.id.FriendProfileCoverPicture);

        name_and_surnameTextView = findViewById(R.id.FriendNameText);
        followerCounterTextView = findViewById(R.id.FollowerCountText);
        followingCounterTextView = findViewById(R.id.FollowingsCountText);


        myRef.child("user_account_settings")
                .child(friendID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String imageID = dataSnapshot.getValue(UserAccountSettings.class).getProfile_photo();
                        String textName = dataSnapshot.getValue(UserAccountSettings.class).getDisplay_name();
                        name_and_surnameTextView.setText(textName);
                        setFriendProfilePicture(imageID);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        updateFollowingNumber();
        checkFriendship();
        setItemRecyclerViews();

        if (profileOfMineOrFriend.equals("Mine")) {

        }
    }

    private void updateFollowingNumber() {
        myRef.child("friendship").child(friendID).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int followingCount = (int) dataSnapshot.getChildrenCount();
                followingCounterTextView.setText(Integer.toString(followingCount));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void checkFriendship() {
        myRef.child("friendship").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ArrayList<String>> temp = new GenericTypeIndicator<ArrayList<String>>() {
                };
                ArrayList<String> friendshipList = dataSnapshot.getValue(temp);
                if (friendshipList.indexOf(friendID) != -1) {
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

        myRef.child("friendship").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ArrayList<String>> temp = new GenericTypeIndicator<ArrayList<String>>() {
                };
                ArrayList<String> friendshipList = dataSnapshot.getValue(temp);
                if (friendshipList == null || friendshipList.size() == 0) {
                    friendshipList = new ArrayList<String>();
                }

                friendshipList.add(friendID);

                myRef.child("friendship").child(userID).setValue(friendshipList);
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
        myRef.child("friendship").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ArrayList<String>> temp = new GenericTypeIndicator<ArrayList<String>>() {
                };
                ArrayList<String> friendshipList = dataSnapshot.getValue(temp);
                if (friendshipList.indexOf(friendID) != -1)
                    friendshipList.remove(friendshipList.indexOf(friendID));

                myRef.child("friendship").child(userID).setValue(friendshipList);
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

        //Setting recyclerView for song
        setSongRecyclerView();

        //Setting recyclerView for places
        setPlaceRecyclerView();

        //Setting recyclerView for tv shows
        setTVShowRecyclerView();

        //Setting recyclerView for video games
        setVideoGameRecyclerView();

        //Setting recyclerView for websites
        setWebsiteRecyclerView();
    }

    private void setBookRecyclerView()
    {
        myItemsList = new ArrayList<>();

        myRef.child("user_items")
                .child(friendID)
                .child("Books")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        for (DataSnapshot sampleDataSnapshot : dataSnapshot.getChildren())
                        {
                            MyItem myItem = sampleDataSnapshot.getValue(MyItem.class);
                            myItemsList.add(myItem);
                        }

                        if(dataSnapshot.getChildrenCount() == 0)
                        {
                            findViewById(R.id.no_book).setVisibility(View.VISIBLE);
                        }

                        RecyclerView itemRecyclerView1 = (RecyclerView) findViewById(R.id.recyclerViewBooks);
                        RecyclerViewAdapter myAdapter1 = new RecyclerViewAdapter(mContext, myItemsList);
                        GridLayoutManager gr1 = new GridLayoutManager(mContext, 3);
                        itemRecyclerView1.setLayoutManager(gr1);
                        itemRecyclerView1.setAdapter(myAdapter1);
                        itemRecyclerView1.setNestedScrollingEnabled(false);

                        final RecyclerView recyclerView = findViewById(R.id.recyclerViewBooks);
                        recyclerView.addOnItemTouchListener(
                                new RecyclerItemClickListener(mContext, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position)
                                    {
                                        final Intent intent = new Intent(mContext, ShowItem.class);

                                        intent.putExtra("category", myItemsList.get(position).getCategory());
                                        intent.putExtra("item_name", myItemsList.get(position).getItem_name());
                                        intent.putExtra("item_type", myItemsList.get(position).getItem_type());
                                        intent.putExtra("item_owner", myItemsList.get(position).getItem_owner());
                                        intent.putExtra("item_detail", myItemsList.get(position).getItem_detail());
                                        intent.putExtra("cover_photo", myItemsList.get(position).getItem_cover_photo());
                                        intent.putExtra("post_image", myItemsList.get(position).getPost_image());
                                        intent.putExtra("review", myItemsList.get(position).getReview());
                                        intent.putExtra("date", myItemsList.get(position).getDate_created());
                                        intent.putExtra("rating", myItemsList.get(position).getRating());
                                        intent.putExtra("post_id", myItemsList.get(position).getPost_id());
                                        intent.putExtra("user_id", myItemsList.get(position).getUser_id());

                                        myRef.child("user_account_settings")
                                                .child(myItemsList.get(position).getUser_id())
                                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        UserAccountSettings userAccountSettings = dataSnapshot.getValue(UserAccountSettings.class);
                                                        intent.putExtra("profile_picture", userAccountSettings.getProfile_photo());
                                                        intent.putExtra("username", userAccountSettings.getUsername());

                                                        startActivity(intent);
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });

                                    }

                                    @Override
                                    public void onLongItemClick(View view, int position)
                                    {
                                        //isSpeakButtonLongPressed = true;

                                        /*ImageView imageView = (ImageView) ((LinearLayout)((CardView)recyclerView.getChildAt(position)).getChildAt(0)).getChildAt(0);
                                        imageView.invalidate();
                                        BitmapDrawable dr = (BitmapDrawable)((ImageView)imageView).getDrawable();
                                        Bitmap bitmap = dr.getBitmap();

                                        showCoverPhotoDialog(bitmap);*/
                                    }

                                    @Override
                                    public void onTouch(View view, MotionEvent e, int position)
                                    {
                                        view.onTouchEvent(e);

                                        // We're only interested in when the button is released.
                                        if (e.getAction() == MotionEvent.ACTION_UP)
                                        {
                                            // We're only interested in anything if our speak button is currently pressed.
                                            if (isSpeakButtonLongPressed)
                                            {
                                                alertDialog.cancel();
                                                isSpeakButtonLongPressed = false;
                                            }
                                        }
                                    }


                                })
                        );
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {

                    }
                });
    }

    private void setMovieRecyclerView()
    {
        myItemsList1 = new ArrayList<>();

        myRef.child("user_items")
                .child(friendID)
                .child("Movies")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        for (DataSnapshot sampleDataSnapshot : dataSnapshot.getChildren())
                        {
                            MyItem movie = sampleDataSnapshot.getValue(MyItem.class);
                            myItemsList1.add(movie);
                        }

                        if(dataSnapshot.getChildrenCount() == 0)
                        {
                            findViewById(R.id.no_movie).setVisibility(View.VISIBLE);
                        }

                        RecyclerView itemRecyclerView1 = (RecyclerView) findViewById(R.id.recyclerViewMovies);
                        RecyclerViewAdapter myAdapter1 = new RecyclerViewAdapter(mContext, myItemsList1);
                        GridLayoutManager gr1 = new GridLayoutManager(mContext, 3);
                        itemRecyclerView1.setLayoutManager(gr1);
                        itemRecyclerView1.setAdapter(myAdapter1);
                        itemRecyclerView1.setNestedScrollingEnabled(false);

                        RecyclerView recyclerView = findViewById(R.id.recyclerViewMovies);
                        recyclerView.addOnItemTouchListener(
                                new RecyclerItemClickListener(mContext, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position)
                                    {
                                        final Intent intent = new Intent(mContext, ShowItem.class);

                                        intent.putExtra("category", myItemsList1.get(position).getCategory());
                                        intent.putExtra("item_name", myItemsList1.get(position).getItem_name());
                                        intent.putExtra("item_type", myItemsList1.get(position).getItem_type());
                                        intent.putExtra("item_owner", myItemsList1.get(position).getItem_owner());
                                        intent.putExtra("item_detail", myItemsList1.get(position).getItem_detail());
                                        intent.putExtra("cover_photo", myItemsList1.get(position).getItem_cover_photo());
                                        intent.putExtra("post_image", myItemsList1.get(position).getPost_image());
                                        intent.putExtra("review", myItemsList1.get(position).getReview());
                                        intent.putExtra("date", myItemsList1.get(position).getDate_created());
                                        intent.putExtra("rating", myItemsList1.get(position).getRating());
                                        intent.putExtra("post_id", myItemsList1.get(position).getPost_id());

                                        myRef.child("user_account_settings")
                                                .child(myItemsList1.get(position).getUser_id())
                                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        UserAccountSettings userAccountSettings = dataSnapshot.getValue(UserAccountSettings.class);
                                                        intent.putExtra("profile_picture", userAccountSettings.getProfile_photo());
                                                        intent.putExtra("username", userAccountSettings.getUsername());

                                                        startActivity(intent);
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });

                                    }


                                    @Override public void onLongItemClick(View view, int position) {
                                        // do whatever
                                    }

                                    @Override
                                    public void onTouch(View view, MotionEvent e, int position)
                                    {
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
                        );
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {

                    }
                });
    }

    private void setSongRecyclerView()
    {
        myItemsList2 = new ArrayList<>();

        myRef.child("user_items")
                .child(friendID)
                .child("Songs")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        for (DataSnapshot sampleDataSnapshot : dataSnapshot.getChildren())
                        {
                            MyItem song = sampleDataSnapshot.getValue(MyItem.class);
                            myItemsList2.add(song);
                        }

                        if(dataSnapshot.getChildrenCount() == 0)
                        {
                            findViewById(R.id.no_song).setVisibility(View.VISIBLE);
                        }

                        RecyclerView itemRecyclerView1 = (RecyclerView) findViewById(R.id.recyclerViewSongs);
                        RecyclerViewAdapter myAdapter1 = new RecyclerViewAdapter(mContext, myItemsList2);
                        GridLayoutManager gr1 = new GridLayoutManager(mContext, 3);
                        itemRecyclerView1.setLayoutManager(gr1);
                        itemRecyclerView1.setAdapter(myAdapter1);
                        itemRecyclerView1.setNestedScrollingEnabled(false);

                        RecyclerView recyclerView = findViewById(R.id.recyclerViewSongs);
                        recyclerView.addOnItemTouchListener(
                                new RecyclerItemClickListener(mContext, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position)
                                    {
                                        final Intent intent = new Intent(mContext, ShowItem.class);

                                        intent.putExtra("category", myItemsList2.get(position).getCategory());
                                        intent.putExtra("item_name", myItemsList2.get(position).getItem_name());
                                        intent.putExtra("item_type", myItemsList2.get(position).getItem_type());
                                        intent.putExtra("item_owner", myItemsList2.get(position).getItem_owner());
                                        intent.putExtra("item_detail", myItemsList2.get(position).getItem_detail());
                                        intent.putExtra("cover_photo", myItemsList2.get(position).getItem_cover_photo());
                                        intent.putExtra("post_image", myItemsList2.get(position).getPost_image());
                                        intent.putExtra("review", myItemsList2.get(position).getReview());
                                        intent.putExtra("date", myItemsList2.get(position).getDate_created());
                                        intent.putExtra("rating", myItemsList2.get(position).getRating());
                                        intent.putExtra("post_id", myItemsList2.get(position).getPost_id());

                                        myRef.child("user_account_settings")
                                                .child(myItemsList2.get(position).getUser_id())
                                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        UserAccountSettings userAccountSettings = dataSnapshot.getValue(UserAccountSettings.class);
                                                        intent.putExtra("profile_picture", userAccountSettings.getProfile_photo());
                                                        intent.putExtra("username", userAccountSettings.getUsername());

                                                        startActivity(intent);
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });

                                    }


                                    @Override public void onLongItemClick(View view, int position) {
                                        // do whatever
                                    }

                                    @Override
                                    public void onTouch(View view, MotionEvent e, int position)
                                    {
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
                        );
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {

                    }
                });
    }

    private void setPlaceRecyclerView()
    {
        myItemsList3 = new ArrayList<>();

        myRef.child("user_items")
                .child(friendID)
                .child("Places")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        for (DataSnapshot sampleDataSnapshot : dataSnapshot.getChildren())
                        {
                            MyItem place = sampleDataSnapshot.getValue(MyItem.class);
                            myItemsList3.add(place);
                        }

                        if(dataSnapshot.getChildrenCount() == 0)
                        {
                            findViewById(R.id.no_place).setVisibility(View.VISIBLE);
                        }

                        RecyclerView itemRecyclerView1 = (RecyclerView) findViewById(R.id.recyclerViewPlaces);
                        RecyclerViewAdapter myAdapter1 = new RecyclerViewAdapter(mContext, myItemsList3);
                        GridLayoutManager gr1 = new GridLayoutManager(mContext, 3);
                        itemRecyclerView1.setLayoutManager(gr1);
                        itemRecyclerView1.setAdapter(myAdapter1);
                        itemRecyclerView1.setNestedScrollingEnabled(false);

                        RecyclerView recyclerView = findViewById(R.id.recyclerViewPlaces);
                        recyclerView.addOnItemTouchListener(
                                new RecyclerItemClickListener(mContext, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position)
                                    {
                                        final Intent intent = new Intent(mContext, ShowItem.class);

                                        intent.putExtra("category", myItemsList3.get(position).getCategory());
                                        intent.putExtra("item_name", myItemsList3.get(position).getItem_name());
                                        intent.putExtra("item_type", myItemsList3.get(position).getItem_type());
                                        intent.putExtra("item_owner", myItemsList3.get(position).getItem_owner());
                                        intent.putExtra("item_detail", myItemsList3.get(position).getItem_detail());
                                        intent.putExtra("cover_photo", myItemsList3.get(position).getItem_cover_photo());
                                        intent.putExtra("post_image", myItemsList3.get(position).getPost_image());
                                        intent.putExtra("review", myItemsList3.get(position).getReview());
                                        intent.putExtra("date", myItemsList3.get(position).getDate_created());
                                        intent.putExtra("rating", myItemsList3.get(position).getRating());
                                        intent.putExtra("post_id", myItemsList3.get(position).getPost_id());

                                        myRef.child("user_account_settings")
                                                .child(myItemsList3.get(position).getUser_id())
                                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        UserAccountSettings userAccountSettings = dataSnapshot.getValue(UserAccountSettings.class);
                                                        intent.putExtra("profile_picture", userAccountSettings.getProfile_photo());
                                                        intent.putExtra("username", userAccountSettings.getUsername());

                                                        startActivity(intent);
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });

                                    }


                                    @Override public void onLongItemClick(View view, int position) {
                                        // do whatever
                                    }

                                    @Override
                                    public void onTouch(View view, MotionEvent e, int position)
                                    {
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
                        );
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {

                    }
                });
    }

    private void setTVShowRecyclerView()
    {
        myItemsList4 = new ArrayList<>();

        myRef.child("user_items")
                .child(friendID)
                .child("TV Shows")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        for (DataSnapshot sampleDataSnapshot : dataSnapshot.getChildren())
                        {
                            MyItem tvShow = sampleDataSnapshot.getValue(MyItem.class);
                            myItemsList4.add(tvShow);
                        }

                        if(dataSnapshot.getChildrenCount() == 0)
                        {
                            findViewById(R.id.no_tvshow).setVisibility(View.VISIBLE);
                        }

                        RecyclerView itemRecyclerView1 = (RecyclerView) findViewById(R.id.recyclerViewTVShows);
                        RecyclerViewAdapter myAdapter1 = new RecyclerViewAdapter(mContext, myItemsList4);
                        GridLayoutManager gr1 = new GridLayoutManager(mContext, 3);
                        itemRecyclerView1.setLayoutManager(gr1);
                        itemRecyclerView1.setAdapter(myAdapter1);
                        itemRecyclerView1.setNestedScrollingEnabled(false);

                        RecyclerView recyclerView = findViewById(R.id.recyclerViewTVShows);
                        recyclerView.addOnItemTouchListener(
                                new RecyclerItemClickListener(mContext, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position)
                                    {
                                        final Intent intent = new Intent(mContext, ShowItem.class);

                                        intent.putExtra("category", myItemsList4.get(position).getCategory());
                                        intent.putExtra("item_name", myItemsList4.get(position).getItem_name());
                                        intent.putExtra("item_type", myItemsList4.get(position).getItem_type());
                                        intent.putExtra("item_owner", myItemsList4.get(position).getItem_owner());
                                        intent.putExtra("item_detail", myItemsList4.get(position).getItem_detail());
                                        intent.putExtra("cover_photo", myItemsList4.get(position).getItem_cover_photo());
                                        intent.putExtra("post_image", myItemsList4.get(position).getPost_image());
                                        intent.putExtra("review", myItemsList4.get(position).getReview());
                                        intent.putExtra("date", myItemsList4.get(position).getDate_created());
                                        intent.putExtra("rating", myItemsList4.get(position).getRating());
                                        intent.putExtra("post_id", myItemsList4.get(position).getPost_id());

                                        myRef.child("user_account_settings")
                                                .child(myItemsList4.get(position).getUser_id())
                                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        UserAccountSettings userAccountSettings = dataSnapshot.getValue(UserAccountSettings.class);
                                                        intent.putExtra("profile_picture", userAccountSettings.getProfile_photo());
                                                        intent.putExtra("username", userAccountSettings.getUsername());

                                                        startActivity(intent);
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });

                                    }


                                    @Override public void onLongItemClick(View view, int position) {
                                        // do whatever
                                    }

                                    @Override
                                    public void onTouch(View view, MotionEvent e, int position)
                                    {
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
                        );
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {

                    }
                });

    }

    private void setVideoGameRecyclerView()
    {
        myItemsList5 = new ArrayList<>();

        myRef.child("user_items")
                .child(friendID)
                .child("Video Games")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        for (DataSnapshot sampleDataSnapshot : dataSnapshot.getChildren())
                        {
                            MyItem videoGame = sampleDataSnapshot.getValue(MyItem.class);
                            myItemsList5.add(videoGame);
                        }

                        if(dataSnapshot.getChildrenCount() == 0)
                        {
                            findViewById(R.id.no_video_game).setVisibility(View.VISIBLE);
                        }

                        RecyclerView itemRecyclerView1 = (RecyclerView) findViewById(R.id.recyclerViewVideoGames);
                        RecyclerViewAdapter myAdapter1 = new RecyclerViewAdapter(mContext, myItemsList5);
                        GridLayoutManager gr1 = new GridLayoutManager(mContext, 3);
                        itemRecyclerView1.setLayoutManager(gr1);
                        itemRecyclerView1.setAdapter(myAdapter1);
                        itemRecyclerView1.setNestedScrollingEnabled(false);

                        RecyclerView recyclerView = findViewById(R.id.recyclerViewVideoGames);
                        recyclerView.addOnItemTouchListener(
                                new RecyclerItemClickListener(mContext, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position)
                                    {
                                        final Intent intent = new Intent(mContext, ShowItem.class);

                                        intent.putExtra("category", myItemsList5.get(position).getCategory());
                                        intent.putExtra("item_name", myItemsList5.get(position).getItem_name());
                                        intent.putExtra("item_type", myItemsList5.get(position).getItem_type());
                                        intent.putExtra("item_owner", myItemsList5.get(position).getItem_owner());
                                        intent.putExtra("item_detail", myItemsList5.get(position).getItem_detail());
                                        intent.putExtra("cover_photo", myItemsList5.get(position).getItem_cover_photo());
                                        intent.putExtra("post_image", myItemsList5.get(position).getPost_image());
                                        intent.putExtra("review", myItemsList5.get(position).getReview());
                                        intent.putExtra("date", myItemsList5.get(position).getDate_created());
                                        intent.putExtra("rating", myItemsList5.get(position).getRating());
                                        intent.putExtra("post_id", myItemsList5.get(position).getPost_id());

                                        myRef.child("user_account_settings")
                                                .child(myItemsList5.get(position).getUser_id())
                                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        UserAccountSettings userAccountSettings = dataSnapshot.getValue(UserAccountSettings.class);
                                                        intent.putExtra("profile_picture", userAccountSettings.getProfile_photo());
                                                        intent.putExtra("username", userAccountSettings.getUsername());

                                                        startActivity(intent);
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });

                                    }


                                    @Override public void onLongItemClick(View view, int position) {
                                        // do whatever
                                    }

                                    @Override
                                    public void onTouch(View view, MotionEvent e, int position)
                                    {
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
                        );
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {

                    }
                });
    }

    private void setWebsiteRecyclerView()
    {
        myItemsList6 = new ArrayList<>();

        myRef.child("user_items")
                .child(friendID)
                .child("Websites")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        for (DataSnapshot sampleDataSnapshot : dataSnapshot.getChildren())
                        {
                            MyItem website = sampleDataSnapshot.getValue(MyItem.class);
                            myItemsList6.add(website);
                        }

                        if(dataSnapshot.getChildrenCount() == 0)
                        {
                            findViewById(R.id.no_website).setVisibility(View.VISIBLE);
                        }

                        RecyclerView itemRecyclerView1 = (RecyclerView) findViewById(R.id.recyclerViewWebsites);
                        RecyclerViewAdapter myAdapter1 = new RecyclerViewAdapter(mContext, myItemsList6);
                        GridLayoutManager gr1 = new GridLayoutManager(mContext, 3);
                        itemRecyclerView1.setLayoutManager(gr1);
                        itemRecyclerView1.setAdapter(myAdapter1);
                        itemRecyclerView1.setNestedScrollingEnabled(false);

                        RecyclerView recyclerView = findViewById(R.id.recyclerViewWebsites);
                        recyclerView.addOnItemTouchListener(
                                new RecyclerItemClickListener(mContext, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position)
                                    {
                                        final Intent intent = new Intent(mContext, ShowItem.class);

                                        intent.putExtra("category", myItemsList6.get(position).getCategory());
                                        intent.putExtra("item_name", myItemsList6.get(position).getItem_name());
                                        intent.putExtra("item_type", myItemsList6.get(position).getItem_type());
                                        intent.putExtra("item_owner", myItemsList6.get(position).getItem_owner());
                                        intent.putExtra("item_detail", myItemsList6.get(position).getItem_detail());
                                        intent.putExtra("cover_photo", myItemsList6.get(position).getItem_cover_photo());
                                        intent.putExtra("post_image", myItemsList6.get(position).getPost_image());
                                        intent.putExtra("review", myItemsList6.get(position).getReview());
                                        intent.putExtra("date", myItemsList6.get(position).getDate_created());
                                        intent.putExtra("rating", myItemsList6.get(position).getRating());
                                        intent.putExtra("post_id", myItemsList6.get(position).getPost_id());

                                        myRef.child("user_account_settings")
                                                .child(myItemsList6.get(position).getUser_id())
                                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        UserAccountSettings userAccountSettings = dataSnapshot.getValue(UserAccountSettings.class);
                                                        intent.putExtra("profile_picture", userAccountSettings.getProfile_photo());
                                                        intent.putExtra("username", userAccountSettings.getUsername());

                                                        startActivity(intent);
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });

                                    }


                                    @Override
                                    public void onLongItemClick(View view, int position) {
                                        // do whatever
                                    }

                                    @Override
                                    public void onTouch(View view, MotionEvent e, int position)
                                    {
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
                        );
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {

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
        Intent intent;
        if(whereComeFrom.equals("SearchActivity")) {
             intent = new Intent(getApplicationContext(), SearchActivity.class);
        }else {
            intent = new Intent(getApplicationContext(), ShowFriendList.class);
        }
        startActivity(intent);
    }
}
