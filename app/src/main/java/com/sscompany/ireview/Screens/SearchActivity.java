package com.sscompany.ireview.Screens;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.sscompany.ireview.Adapters.SearchUserListAdapter;
import com.sscompany.ireview.Models.User;
import com.sscompany.ireview.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {
    private static final int ACTIVITY_NUM = 1;

    private Context mContext = SearchActivity.this;

    //widgets
    private EditText mSearchParam;
    private ListView mListView;

    //vars
    private List<User> mUserList;
    private SearchUserListAdapter mAdapter;

    private DatabaseReference databaseReference;
    String userID;
    ArrayList<String> listFriendIDs;
    ArrayList<String> listFriendsNames;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_friends);

        mSearchParam = (EditText) findViewById(R.id.search);
        mListView = (ListView) findViewById(R.id.listView);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        listFriendIDs = new ArrayList<>();
        listFriendsNames = new ArrayList<>();

        hideSoftKeyboard();
        //setupBottomNavigationView();
        initTextListener();
        getFriendsNamesList();
    }

    private void getFriendsNamesList() {
        databaseReference.child("friendship").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ArrayList<String>> temp = new GenericTypeIndicator<ArrayList<String>>() {
                };
                listFriendIDs = dataSnapshot.getValue(temp);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void initTextListener() {

        mUserList = new ArrayList<>();

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
                searchForMatch(text);
            }
        });
    }


    private void searchForMatch(String keyword) {
        mUserList.clear();
        if (keyword.length() != 0) {
            //...   onDataChange

            //update the users list view
            updateUsersList();

            //...
        }
    }

    private void updateUsersList() {

        mAdapter = new SearchUserListAdapter(SearchActivity.this, R.layout.layout_user_listitem, mUserList);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //navigate to profile activity

            }
        });
    }
}
