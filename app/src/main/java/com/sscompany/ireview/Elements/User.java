package com.sscompany.ireview.Elements;

import android.media.Image;

public class User {

    private String user_id;
    private String email;
    private String username;
    private String display_name;
    private long phone_number;


    public User(String user_id, String email, String username, String display_name, long phone_number) {
        this.user_id = user_id;
        this.email = email;
        this.username = username;
        this.display_name = display_name;
        this.phone_number = phone_number;
    }

    public User() {

    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public long getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(long phone_number) {
        this.phone_number = phone_number;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", display_name='" + display_name + '\'' +
                ", phone_number=" + phone_number +
                '}';
    }
}
