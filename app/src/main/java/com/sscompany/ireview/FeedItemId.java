package com.sscompany.ireview;

import android.support.annotation.NonNull;

import com.google.firebase.database.Exclude;


public class FeedItemId {

    @Exclude
    public String FeedItemId;

    public <T extends FeedItemId> T withId(@NonNull final String id) {
        this.FeedItemId = id;
        return (T) this;
    }

}
