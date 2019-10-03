package com.sscompany.ireview.Screens;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.sscompany.ireview.Adapters.RecyclerItemClickListener;
import com.sscompany.ireview.Adapters.RecyclerViewAdapterFriendList;
import com.sscompany.ireview.Adapters.SearchUserListAdapter;
import com.sscompany.ireview.Models.User;
import com.sscompany.ireview.Models.UserAccountSettings;
import com.sscompany.ireview.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {
    private static final int ACTIVITY_NUM = 1;

    private Context mContext = SearchActivity.this;

    //widgets
    private EditText mSearchParam;
    //private ListView mListView;

    private SearchUserListAdapter mAdapter;

    private DatabaseReference databaseReference;
    String userID;
    List<User> listUsers;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_friends);

        mSearchParam = (EditText) findViewById(R.id.search);
        //mListView = (ListView) findViewById(R.id.listView);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        listUsers = new ArrayList<>();

        hideSoftKeyboard();
        //setupBottomNavigationView();
        initTextListener();
    }


    private void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void initTextListener() {
        listUsers.clear();
        mSearchParam.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = mSearchParam.getText().toString().toLowerCase(Locale.getDefault());
                listUsers.clear();
                searchForMatch(text);
            }
        });
    }
    private int size;
    private String keyword1;
    private int count = 0;

    private void searchForMatch(String keyword) {
        count = 0;
        RecyclerView rv = findViewById(R.id.recyclerViewFriendList);
        TextView textView = findViewById(R.id.NoUserText);
        keyword1 = keyword;
        listUsers.clear();

        if (keyword.length() != 0) {
            rv.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
            //...   onDataChange
            databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    size = (int) dataSnapshot.getChildrenCount();
                    for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                        User user = singleSnapshot.getValue(User.class);
                        String userNameLower = user.getDisplay_name().toLowerCase();
                        System.out.println("friendUsername: " + user.getUsername());
                        if (userNameLower.contains(keyword1))
                            listUsers.add(user);

                        count++;
                        if (count == size) {
                            if (listUsers != null && listUsers.size() > 0) {
                                addToAdapter();
                            } else {
                                RecyclerView rv = findViewById(R.id.recyclerViewFriendList);
                                TextView textView = findViewById(R.id.NoUserText);

                                rv.setVisibility(View.GONE);
                                textView.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            //update the users list view
            //updateUsersList();

            //...
        }
        else{
            rv.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }
    }

    private void addToAdapter() {
        System.out.println("CHECK113");
        RecyclerView myRV = findViewById(R.id.recyclerViewFriendList);
        RecyclerViewAdapterFriendList myAdapter = new RecyclerViewAdapterFriendList(SearchActivity.this, listUsers);

        System.out.println("CHECK114");
        myRV.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
        myRV.setAdapter(myAdapter);
        System.out.println("CHECK115");

        myRV.addOnItemTouchListener(
                new RecyclerItemClickListener(SearchActivity.this, myRV, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                       //System.out.println("Name: " + listUsers.get(position).getDisplay_name() + " Owner: " + listUsers.get(position).getUsername());
                        Intent intent = new Intent(getApplicationContext(), FriendsProfile.class);
                        intent.putExtra("FRIENDID", listUsers.get(position).getUser_id());
                        intent.putExtra("WHERECOMEFROM", "SearchActivity");
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
}
