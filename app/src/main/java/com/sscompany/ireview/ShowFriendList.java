package com.sscompany.ireview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sscompany.ireview.AddElementScreens.AddBook;
import com.sscompany.ireview.Elements.User;

import java.util.ArrayList;
import java.util.List;

public class ShowFriendList extends AppCompatActivity {
    List<User> listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_list);

        User newUser = new User();
        newUser.setUsername("alibabayev");
        newUser.setDisplay_name("Ali Babayev");

        listItem = new ArrayList<>();
        listItem.add(newUser);
        listItem.add(newUser);
        listItem.add(newUser);

        RecyclerView myRV = (RecyclerView) findViewById(R.id.recyclerViewFriendList);
        RecyclerViewAdapterFriendList myAdapter = new RecyclerViewAdapterFriendList(this, listItem);

        myRV.setLayoutManager(new LinearLayoutManager(this));
        myRV.setAdapter(myAdapter);

        /*
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        ImageView imageview = (ImageView) findViewById(R.id.ItemImage);
        imageview.setMinimumWidth(width / 3 - 10);
        */

    }

    public void showFriend(View view) {
        Intent intent = new Intent(getApplicationContext(), FriendsProfile.class);
        intent.putExtra("FRIENDID", view.getId());
        startActivity(intent);
    }
}
