package com.sscompany.ireview.Elements;

import android.media.Image;

public class User {

    private String names;
    private String username;
    private Image image;

    public User(String names, String username, Image image) {
        this.names = names;
        this.username = username;
        this.image = image;
    }

    public User(String names, String username) {
        this.names = names;
        this.username = username;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
