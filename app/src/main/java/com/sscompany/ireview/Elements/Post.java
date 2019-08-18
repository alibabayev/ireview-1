package com.sscompany.ireview.Elements;

import java.util.ArrayList;

public class Post
{

    private int numberOfComments;
    private ArrayList<String> comments = new ArrayList<>();
    private int numberOfLikes;
    private ArrayList<String> peopleLikedIds = new ArrayList<>();
    private String postingUserId;
    //private String itemTitle;
    //private String itemPublisher;
    //private int coverImage;
    private String itemId;
    private String caption;
    private String userReview;
    private int numberOfStarByUser;
    private String postingDate;

    public Post()
    {
        setNumberOfComments(0);
        setNumberOfLikes(0);
    }

    public void setNumberOfComments(int numberOfComments) {
        this.numberOfComments = numberOfComments;
    }

    public void setNumberOfLikes(int numberOfLikes) {
        this.numberOfLikes = numberOfLikes;
    }

    /*

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public void setItemPublisher(String itemPublisher) {
        this.itemPublisher = itemPublisher;
    }

    public void setCoverImage(int coverImage) {
        this.coverImage = coverImage;
    }

     */

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setNumberOfStarByUser(int numberOfStarByUser) {
        this.numberOfStarByUser = numberOfStarByUser;
    }

    public void setUserReview(String userReview) {
        this.userReview = userReview;
    }

    public void setPostingUserId(String postingUserId) {
        this.postingUserId = postingUserId;
    }

    public void setPostingDate(String postingDate) {
        this.postingDate = postingDate;
    }

    /*

    public String getItemTitle() {
        return itemTitle;
    }

    public String getItemPublisher() {
        return itemPublisher;
    }

    public int getCoverImage() {
        return coverImage;
    }

     */

    public int getNumberOfComments() {
        return numberOfComments;
    }

    public int getNumberOfLikes() {
        return numberOfLikes;
    }

    public int getNumberOfStarByUser() {
        return numberOfStarByUser;
    }

    public String getItemId() {
        return itemId;
    }

    public String getCaption() {
        return caption;
    }

    public String getPostingUserId() {
        return postingUserId;
    }

    public String getUserReview() {
        return userReview;
    }

    public String getPostingDate() {
        return postingDate;
    }

}

