package com.sscompany.ireview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Date;

public class NewsFeedAdapter extends BaseAdapter
{
    private Context context;
    private ArrayList<NewsFeedItem> newsFeedItems;
    private ParseQuery parseItems;

    static class NewsFeedViewHolder
    {
        ImageView like;
        ImageView bookmark;
        ImageView postingUserProfilePicture;
        TextView userName;
        TextView date;
        TextView titleAndPublisher;
        ImageView cover;
        TextView review;
        TextView likeCount;
        ImageView stars;
    }

    public NewsFeedAdapter(Context context, ArrayList<NewsFeedItem> newsFeedItems) {
        this.context = context;
        this.newsFeedItems = newsFeedItems;
    }

    @Override
    public int getCount() {
        return newsFeedItems.size();
    }

    @Override
    public Object getItem(int position) {
        return newsFeedItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final NewsFeedViewHolder viewHolder;

        if(convertView == null)
        {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.news_feed_item, null);
            //layoutResourceId, parent, false

            viewHolder = new NewsFeedViewHolder();

            viewHolder.like = convertView.findViewById(R.id.like);
            viewHolder.bookmark = convertView.findViewById(R.id.bookmark);
            viewHolder.stars = convertView.findViewById(R.id.star);

            viewHolder.postingUserProfilePicture = convertView.findViewById(R.id.user_profile_picture);
            viewHolder.userName = convertView.findViewById(R.id.user_name);
            viewHolder.date = convertView.findViewById(R.id.date);

            viewHolder.titleAndPublisher = convertView.findViewById(R.id.title_and_publisher);
            viewHolder.cover = convertView.findViewById(R.id.cover);
            viewHolder.review = convertView.findViewById(R.id.review);

            viewHolder.likeCount = convertView.findViewById(R.id.like_count);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (NewsFeedViewHolder) convertView.getTag();
        }

        //Coding Components of ConvertView

        final NewsFeedItem newsFeedItem = newsFeedItems.get(position);

        ParseQuery<ParseObject> posts = ParseQuery.getQuery("Post");

        String postId = newsFeedItem.getPostId();
        ParseObject currentPost = null;

        try {
            currentPost = posts.get(postId);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        /*
        Like Button - Not Completed

        Add liked News Feed Item to currentUser's Liked Array
        Remove not liked News Feed Item from currentUser's Liked Array
         */

        if(newsFeedItem.isLiked())
        {
            Drawable likedIcon = context.getResources().getDrawable(R.drawable.liked);
            viewHolder.like.setImageDrawable(likedIcon);
        }
        else if(!newsFeedItem.isLiked())
        {
            Drawable notLikedIcon = context.getResources().getDrawable(R.drawable.not_liked);
            viewHolder.like.setImageDrawable(notLikedIcon);
        }

        viewHolder.like.setClickable(true);

        final ParseObject finalCurrentPost = currentPost;
        viewHolder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                newsFeedItem.setLiked(!newsFeedItem.isLiked());

                if(newsFeedItem.isLiked())
                {
                    Drawable likedIcon = context.getResources().getDrawable(R.drawable.liked);
                    viewHolder.like.setImageDrawable(likedIcon);
                    int likeNumber = (int) finalCurrentPost.get("likeCount");
                    finalCurrentPost.put("likeCount", likeNumber + 1);
                    finalCurrentPost.saveInBackground();
                }
                else if(!newsFeedItem.isLiked())
                {
                    Drawable likedIcon = context.getResources().getDrawable(R.drawable.not_liked);
                    viewHolder.like.setImageDrawable(likedIcon);
                    int likeNumber = (int) finalCurrentPost.get("likeCount");
                    finalCurrentPost.put("likeCount", likeNumber - 1);
                    finalCurrentPost.saveInBackground();
                }

            }
        });



        /*
        Bookmark Button - Not Completed

        Add bookmarked News Feed Item to currentUser's Bookmarked Array
        Remove not bookmarked News Feed Item from currentUser's Bookmarked Array
         */

        if(newsFeedItem.isBookmarked())
        {
            Drawable bookmarkedIcon = context.getResources().getDrawable(R.drawable.bookmarked);
            viewHolder.bookmark.setImageDrawable(bookmarkedIcon);
        }
        else if(!newsFeedItem.isBookmarked())
        {
            Drawable notBookmarkedIcon = context.getResources().getDrawable(R.drawable.not_bookmarked);
            viewHolder.bookmark.setImageDrawable(notBookmarkedIcon);
        }

        viewHolder.bookmark.setClickable(true);
        viewHolder.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                newsFeedItem.setBookmarked(!newsFeedItem.isBookmarked());

                if(newsFeedItem.isBookmarked())
                {
                    Drawable bookmarkedIcon = context.getResources().getDrawable(R.drawable.bookmarked);
                    viewHolder.bookmark.setImageDrawable(bookmarkedIcon);
                }
                else if(!newsFeedItem.isBookmarked())
                {
                    Drawable notBookmarkedIcon = context.getResources().getDrawable(R.drawable.not_bookmarked);
                    viewHolder.bookmark.setImageDrawable(notBookmarkedIcon);
                }
            }
        });

        /*
        Posting User's Profile Picture
         */

        viewHolder.postingUserProfilePicture.setBackgroundResource(0);

        ParseUser user = new ParseUser();
        ParseQuery <ParseUser> users = ParseUser.getQuery();

        try {
            user = users.get(currentPost.get("postingUserId").toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ParseFile imageFile = (ParseFile) user.get("profilePicture");

        if(imageFile != null)
        {
            imageFile.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    viewHolder.postingUserProfilePicture.setImageBitmap(bitmap);
                }
            });
        }

        else
        {
            Drawable imageDrawable = convertView.getResources().getDrawable(R.drawable.profile_picture_icon);
            viewHolder.cover.setImageDrawable(imageDrawable);
        }


        /*
        Posting User's Name
         */

        viewHolder.userName.setText((String)user.get("Name"));


        /*
        Item's title and publisher
         */

        String itemId = currentPost.get("itemId").toString();
        String elementId;
        String elementCategory;
        ParseObject item = null;

        parseItems = ParseQuery.getQuery("Items");
        try {
            item = parseItems.get(itemId);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(item.get("Title") != null && item.get("Publisher") != null)
        {
            viewHolder.titleAndPublisher.setText(item.get("Title").toString() + " by " + item.get("Publisher").toString());
        }

        else if(item.get("Title") != null)
        {
            viewHolder.titleAndPublisher.setText(item.get("Title").toString());
        }
        else
        {
            viewHolder.titleAndPublisher.setText("");
        }

        /*
        Given Stars by user
         */

        viewHolder.stars.setBackgroundResource(0);

        int numberOfStarsGiven = (int) currentPost.get("stars");

        if(numberOfStarsGiven == 1)
        {
            Drawable drawable = convertView.getResources().getDrawable(R.drawable.one_star);
            viewHolder.stars.setImageDrawable(drawable);
        }

        else if(numberOfStarsGiven == 2)
        {
            Drawable drawable = convertView.getResources().getDrawable(R.drawable.two_star);
            viewHolder.stars.setImageDrawable(drawable);
        }

        else if(numberOfStarsGiven == 3)
        {
            Drawable drawable = convertView.getResources().getDrawable(R.drawable.three_star);
            viewHolder.stars.setImageDrawable(drawable);
        }

        else if(numberOfStarsGiven == 4)
        {
            Drawable drawable = convertView.getResources().getDrawable(R.drawable.four_star);
            viewHolder.stars.setImageDrawable(drawable);
        }

        else if(numberOfStarsGiven == 5)
        {
            Drawable drawable = convertView.getResources().getDrawable(R.drawable.five_star);
            viewHolder.stars.setImageDrawable(drawable);
        }


        /*
        Item's Cover - Not Completed

        Change the structure of Database
         */

        viewHolder.cover.setBackgroundResource(0);

        elementId = item.get("Id").toString();
        elementCategory = item.get("Category").toString();

        ParseQuery parseElements = ParseQuery.getQuery(elementCategory);

        System.out.println(elementId + " element id " + elementCategory);
        ParseObject element = null;

        try {
            element = parseElements.get(elementId);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println(" lscsd dssdlds dsld sd ss sd   " + element);
        ParseObject elementCopy = null;
        elementCopy = element;

        imageFile = (ParseFile) element.get("cover");

        if(imageFile != null)
        {
            imageFile.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    viewHolder.cover.setImageBitmap(bitmap);
                }
            });
        }
        else
        {
            Drawable imageDrawable = convertView.getResources().getDrawable(R.drawable.operator_down_cover);
            viewHolder.cover.setImageDrawable(imageDrawable);
        }

        /*
        Written Review by user
         */

        viewHolder.review.setText(currentPost.get("userReview").toString());

        /*
        Like Count
         */

        viewHolder.likeCount.setText(currentPost.get("likeCount") + "");

        /*
        Date
         */

        viewHolder.date.setText(currentPost.get("postingTime").toString());

        return convertView;
    }
}
