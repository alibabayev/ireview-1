package com.sscompany.ireview;

public class NewsFeedItem
{
    private String postId;
    private boolean liked;
    private boolean bookmarked;

    public NewsFeedItem(String post)
    {
        this.postId = post;
        liked = false;
        bookmarked = false;
    }

    public NewsFeedItem(String post, boolean liked, boolean bookmarked) {
        this.postId = post;
        this.liked = liked;
        this.bookmarked = bookmarked;
    }

    public void setPostId(String post) {
        this.postId = post;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    public String getPostId() {
        return postId;
    }

    public boolean isLiked() {
        return liked;
    }

    public boolean isBookmarked() {
        return bookmarked;
    }
}
