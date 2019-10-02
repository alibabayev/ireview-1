package com.sscompany.ireview.Models;

import java.util.ArrayList;

/**
 * This class provides structure for uploading items for my profile into firebase database.
 */

public class MyItem
{
    private String item_id;
    private String item_name;
    private String item_type;
    private String item_owner;
    private String item_detail;
    private String item_cover_photo;
    private String review;
    private float rating;
    private String user_id;
    private String date_created;
    private String post_id;
    private String post_image;
    private String category;

    public MyItem(String item_id, String item_name, String item_type, String item_owner, String item_detail, String item_cover_photo, String review, float rating, String user_id, String date_created, String post_id, String post_image, String category)
    {
        this.item_id = item_id;
        this.item_name = item_name;
        this.item_type = item_type;
        this.item_owner = item_owner;
        this.item_detail = item_detail;
        this.item_cover_photo = item_cover_photo;
        this.review = review;
        this.rating = rating;
        this.user_id = user_id;
        this.date_created = date_created;
        this.post_id = post_id;
        this.post_image = post_image;
        this.category = category;
    }

    public MyItem() {

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

    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public String getItem_owner() {
        return item_owner;
    }

    public void setItem_owner(String item_owner) {
        this.item_owner = item_owner;
    }

    public String getItem_detail() {
        return item_detail;
    }

    public void setItem_detail(String item_detail) {
        this.item_detail = item_detail;
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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
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
        return "MyItem{" +
                "item_id='" + item_id + '\'' +
                ", item_name='" + item_name + '\'' +
                ", item_type='" + item_type + '\'' +
                ", item_owner='" + item_owner + '\'' +
                ", item_detail='" + item_detail + '\'' +
                ", item_cover_photo='" + item_cover_photo + '\'' +
                ", review='" + review + '\'' +
                ", rating=" + rating +
                ", user_id='" + user_id + '\'' +
                ", date_created='" + date_created + '\'' +
                ", post_id='" + post_id + '\'' +
                ", post_image='" + post_image + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}