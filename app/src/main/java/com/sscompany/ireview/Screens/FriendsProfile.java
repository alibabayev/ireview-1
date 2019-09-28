package com.sscompany.ireview.Screens;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sscompany.ireview.Adapters.RecyclerViewAdapter;
import com.sscompany.ireview.Models.*;
import com.sscompany.ireview.R;

import java.util.ArrayList;
import java.util.List;

import static com.sscompany.ireview.Screens.Homepage.profileOfMineOrFriend;

public class FriendsProfile extends AppCompatActivity {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    //private RecyclerView fRecyclerView;
    //private RecyclerView.Adapter fAdapter;
    //private RecyclerView.LayoutManager fLayoutManager;
    List<InterfaceItem> flistItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_profile);

        /*flistItem = new ArrayList<>();
        Book newBook = new Book();
        newBook.setName("The Martian");
        newBook.setGenre("Drama");
        newBook.setOwner("Book");
        flistItem.add(newBook);
        flistItem.add(newBook);
        flistItem.add(newBook);*/
        databaseReference.child("users").child(getIntent().getStringExtra("FRIENDID")).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );

        RecyclerView itemRecyclerView1 = (RecyclerView) findViewById(R.id.recyclerViewCategoryBooks);
        RecyclerViewAdapter myAdapter1 = new RecyclerViewAdapter(this, flistItem);
        GridLayoutManager gr1 = new GridLayoutManager(this, 3);
        itemRecyclerView1.setLayoutManager(gr1);
        itemRecyclerView1.setAdapter(myAdapter1);
        itemRecyclerView1.setNestedScrollingEnabled(false);

        RecyclerView itemRecyclerView2 = (RecyclerView) findViewById(R.id.recyclerViewCategoryMovies);
        RecyclerViewAdapter myAdapter2 = new RecyclerViewAdapter(this, flistItem);
        itemRecyclerView2.setLayoutManager(new GridLayoutManager(this, 3));
        itemRecyclerView2.setAdapter(myAdapter2);
        itemRecyclerView2.setNestedScrollingEnabled(false);


        RecyclerView itemRecyclerView3 = (RecyclerView) findViewById(R.id.recyclerViewCategoryRestaurants);
        RecyclerViewAdapter myAdapter3 = new RecyclerViewAdapter(this, flistItem);
        itemRecyclerView3.setLayoutManager(new GridLayoutManager(this, 3));
        itemRecyclerView3.setAdapter(myAdapter3);
        itemRecyclerView3.setNestedScrollingEnabled(false);


        RecyclerView itemRecyclerView4 = (RecyclerView) findViewById(R.id.recyclerViewCategoryPlaces);
        RecyclerViewAdapter myAdapter4 = new RecyclerViewAdapter(this, flistItem);
        itemRecyclerView4.setLayoutManager(new GridLayoutManager(this, 3));
        itemRecyclerView4.setAdapter(myAdapter4);
        itemRecyclerView4.setNestedScrollingEnabled(false);


        RecyclerView itemRecyclerView5 = (RecyclerView) findViewById(R.id.recyclerViewCategoryTvShows);
        RecyclerViewAdapter myAdapter5 = new RecyclerViewAdapter(this, flistItem);
        itemRecyclerView5.setLayoutManager(new GridLayoutManager(this, 3));
        itemRecyclerView5.setAdapter(myAdapter5);
        itemRecyclerView5.setNestedScrollingEnabled(false);


        RecyclerView itemRecyclerView6 = (RecyclerView) findViewById(R.id.recyclerViewCategorySongs);
        RecyclerViewAdapter myAdapter6 = new RecyclerViewAdapter(this, flistItem);
        itemRecyclerView6.setLayoutManager(new GridLayoutManager(this, 3));
        itemRecyclerView6.setAdapter(myAdapter6);
        itemRecyclerView6.setNestedScrollingEnabled(false);


        RecyclerView itemRecyclerView7 = (RecyclerView) findViewById(R.id.recyclerViewCategoryVideoGames);
        RecyclerViewAdapter myAdapter7 = new RecyclerViewAdapter(this, flistItem);
        itemRecyclerView7.setLayoutManager(new GridLayoutManager(this, 3));
        itemRecyclerView7.setAdapter(myAdapter7);
        itemRecyclerView7.setNestedScrollingEnabled(false);

        RecyclerView itemRecyclerView8 = (RecyclerView) findViewById(R.id.recyclerViewCategoryCustom);
        RecyclerViewAdapter myAdapter8 = new RecyclerViewAdapter(this, flistItem);
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
