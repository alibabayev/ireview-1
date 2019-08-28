package com.sscompany.ireview.Elements;

public class UserAccountSettings
{
    private String username;
    private String display_name;
    private long followers;
    private long followings;
    private long reviews;
    private String profile_photo;

    public UserAccountSettings(String username, String display_name, long followers, long followings, long reviews, String profile_photo) {
        this.username = username;
        this.display_name = display_name;
        this.followers = followers;
        this.followings = followings;
        this.reviews = reviews;
        this.profile_photo = profile_photo;
    }

    public UserAccountSettings() {

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

    public long getFollowers() {
        return followers;
    }

    public void setFollowers(long followers) {
        this.followers = followers;
    }

    public long getFollowings() {
        return followings;
    }

    public void setFollowings(long followings) {
        this.followings = followings;
    }

    public long getReviews() {
        return reviews;
    }

    public void setReviews(long reviews) {
        this.reviews = reviews;
    }

    public String getProfile_photo() {
        return profile_photo;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }

    @Override
    public String toString() {
        return "UserAccountSettings{" +
                "username='" + username + '\'' +
                ", display_name='" + display_name + '\'' +
                ", followers=" + followers +
                ", followings=" + followings +
                ", reviews=" + reviews +
                ", profile_photo='" + profile_photo + '\'' +
                '}';
    }
}
