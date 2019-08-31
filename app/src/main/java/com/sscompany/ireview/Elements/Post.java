package com.sscompany.ireview.Elements;

import java.util.ArrayList;

public class Post
{
    private String item_id;
    private String caption;
    private int rating;
    private int like_count;
    private ArrayList likes;
    private String user_id;
    private String data_created;

    public Post(String item_id, String caption, int rating, int like_count, ArrayList likes, String user_id, String data_created) {
        this.item_id = item_id;
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

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
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
                ", caption='" + caption + '\'' +
                ", rating=" + rating +
                ", like_count=" + like_count +
                ", likes=" + likes +
                ", user_id='" + user_id + '\'' +
                ", data_created='" + data_created + '\'' +
                '}';
    }
}



