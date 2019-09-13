package com.sscompany.ireview;

public class FeedItem
{
    private String user_id;
    private String username;
    private String profile_picture;
    private String cover_photo;
    private String item_name;
    private String item_owner;
    private String date;
    private float rating;
    private String review;
    private int likes;
    private String post_id;

    public FeedItem(String user_id, String username, String profile_picture, String cover_photo, String item_name, String item_owner, String date, float rating, String review, int likes, String post_id) {
        this.user_id = user_id;
        this.username = username;
        this.profile_picture = profile_picture;
        this.cover_photo = cover_photo;
        this.item_name = item_name;
        this.item_owner = item_owner;
        this.date = date;
        this.rating = rating;
        this.review = review;
        this.likes = likes;
        this.post_id = post_id;
    }

    public FeedItem() {

    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getCover_photo() {
        return cover_photo;
    }

    public void setCover_photo(String cover_photo) {
        this.cover_photo = cover_photo;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    @Override
    public String
    toString() {
        return "FeedItem{" +
                "user_id='" + user_id + '\'' +
                ", username='" + username + '\'' +
                ", profile_picture='" + profile_picture + '\'' +
                ", cover_photo='" + cover_photo + '\'' +
                ", item_name='" + item_name + '\'' +
                ", item_owner='" + item_owner + '\'' +
                ", date='" + date + '\'' +
                ", rating=" + rating +
                ", review='" + review + '\'' +
                ", likes=" + likes +
                ", post_id='" + post_id + '\'' +
                '}';
    }
}
