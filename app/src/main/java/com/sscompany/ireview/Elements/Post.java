package com.sscompany.ireview.Elements;

import java.util.ArrayList;

public class Post
{
    private String item_id;
    private String item_name;
    private String item_owner;
    private String item_cover_photo;
    private String caption;
    private float rating;
    private int like_count;
    private ArrayList likes;
    private String user_id;
    private String data_created;

    public Post(String item_id, String item_name, String item_owner, String item_cover_photo, String caption, float rating, int like_count, ArrayList likes, String user_id, String data_created)
    {
        this.item_id = item_id;
        this.item_name = item_name;
        this.item_owner = item_owner;
        this.item_cover_photo = item_cover_photo;
        this.caption = caption;
        this.rating = rating;
        this.like_count = like_count;
        this.likes = likes;
        this.user_id = user_id;
        this.data_created = data_created;
    }

    public Post() {

    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_owner() {
        return item_owner;
    }

    public void setItem_owner(String item_owner) {
        this.item_owner = item_owner;
    }

    public String getItem_cover_photo() {
        return item_cover_photo;
    }

    public void setItem_cover_photo(String item_cover_photo) {
        this.item_cover_photo = item_cover_photo;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public ArrayList getLikes() {
        return likes;
    }

    public void setLikes(ArrayList likes) {
        this.likes = likes;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getData_created() {
        return data_created;
    }

    public void setData_created(String data_created) {
        this.data_created = data_created;
    }

    @Override
    public String toString() {
        return "Post{" +
                "item_id='" + item_id + '\'' +
                ", item_name='" + item_name + '\'' +
                ", item_owner='" + item_owner + '\'' +
                ", item_cover_photo='" + item_cover_photo + '\'' +
                ", caption='" + caption + '\'' +
                ", rating=" + rating +
                ", like_count=" + like_count +
                ", likes=" + likes +
                ", user_id='" + user_id + '\'' +
                ", data_created='" + data_created + '\'' +
                '}';
    }
}



