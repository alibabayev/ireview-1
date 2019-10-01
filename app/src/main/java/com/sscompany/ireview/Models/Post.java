package com.sscompany.ireview.Models;

import java.util.ArrayList;

/**
 * This class provides structure for uploading books into firebase database.
 */

public class Post
{
    private String item_id;
    private String item_name;
    private String item_owner;
    private String item_cover_photo;
    private String review;
    private float rating;
    private int like_count;
    private ArrayList<String> likes;
    private String user_id;
    private String data_created;
    private String post_id;
    private String post_image;
    private String category;

    public Post(String item_id, String item_name, String item_owner, String item_cover_photo, String review, float rating, int like_count, ArrayList<String> likes, String user_id, String data_created, String post_id, String post_image, String category) {
        this.item_id = item_id;
        this.item_name = item_name;
        this.item_owner = item_owner;
        this.item_cover_photo = item_cover_photo;
        this.review = review;
        this.rating = rating;
        this.like_count = like_count;
        this.likes = likes;
        this.user_id = user_id;
        this.data_created = data_created;
        this.post_id = post_id;
        this.post_image = post_image;
        this.category = category;
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

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
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

    public ArrayList<String> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<String> likes) {
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

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getPost_image() {
        return post_image;
    }

    public void setPost_image(String post_image) {
        this.post_image = post_image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Post{" +
                "item_id='" + item_id + '\'' +
                ", item_name='" + item_name + '\'' +
                ", item_owner='" + item_owner + '\'' +
                ", item_cover_photo='" + item_cover_photo + '\'' +
                ", review='" + review + '\'' +
                ", rating=" + rating +
                ", like_count=" + like_count +
                ", likes=" + likes +
                ", user_id='" + user_id + '\'' +
                ", data_created='" + data_created + '\'' +
                ", post_id='" + post_id + '\'' +
                ", post_image='" + post_image + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}



