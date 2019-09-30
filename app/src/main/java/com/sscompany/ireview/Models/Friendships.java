package com.sscompany.ireview.Models;

import java.util.ArrayList;

public class Friendships {

    private ArrayList<String> friends;

    public Friendships() {
        friends = new ArrayList<>();
    }

    public Friendships(ArrayList<String> friends){
        this.friends = friends;
    }

    public ArrayList<String> getFriendship() {
        return friends;
    }

    public void setFriendship(ArrayList<String> friends) {
        this.friends = friends;
    }
}
