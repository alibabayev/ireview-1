package com.sscompany.ireview.Screens;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.sscompany.ireview.Adapters.RecyclerItemClickListener;
import com.sscompany.ireview.Adapters.RecyclerViewAdapterFriendList;
import com.sscompany.ireview.Models.Friendships;
import com.sscompany.ireview.Models.User;
import com.sscompany.ireview.R;
import com.sscompany.ireview.Screens.FriendsProfile;

import java.util.ArrayList;
import java.util.List;

public class ShowFriendList extends AppCompatActivity {
    List<User> listFriends;
    List<String> listFriendIDs;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private int count = 0;
    //protected int counterForFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_list);

        listFriends = new ArrayList<>();
        listFriendIDs = new ArrayList<>();
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();


        databaseReference.child("friendship").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ArrayList<String>> temp = new GenericTypeIndicator<ArrayList<String>>() {
                };
                listFriendIDs = dataSnapshot.getValue(temp);

                if (listFriendIDs != null && listFriendIDs.size() > 0)
                    addFriendsToList((int) dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void addToAdapter() {
        System.out.println("CHECK113");
        RecyclerView myRV = (RecyclerView) findViewById(R.id.recyclerViewFriendList);
        RecyclerViewAdapterFriendList myAdapter = new RecyclerViewAdapterFriendList(ShowFriendList.this, listFriends);


        System.out.println("CHECK114");
        myRV.setLayoutManager(new LinearLayoutManager(ShowFriendList.this));
        myRV.setAdapter(myAdapter);
        System.out.println("CHECK115");

        myRV.addOnItemTouchListener(
                new RecyclerItemClickListener(ShowFriendList.this, myRV, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        System.out.println("Name: " + listFriends.get(position).getDisplay_name() + " Owner: " + listFriends.get(position).getUsername());
                        Intent intent = new Intent(getApplicationContext(), FriendsProfile.class);
                        intent.putExtra("FRIENDID", listFriendIDs.get(position));
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }

                    public void onTouch(View view, MotionEvent e, int position) {

                    }
                })
        );
    }


    private void addFriendsToList(final int size) {
        count = 0;
        for (int i = 0; i < listFriendIDs.size(); i++) {
            databaseReference.child("users").child(listFriendIDs.get(i)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User friend = dataSnapshot.getValue(User.class);
                    System.out.println("friendUsername: " + friend.getUsername());
                    listFriends.add(friend);
                    count++;
                    if (count == size) {
                        addToAdapter();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

        }
    }
}
