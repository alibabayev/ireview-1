package com.sscompany.ireview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.session.PlaybackState;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.sscompany.ireview.Elements.Post;
import com.sscompany.ireview.Elements.User;
import com.sscompany.ireview.Elements.UserAccountSettings;

import de.hdodenhof.circleimageview.CircleImageView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FeedAdapter extends ArrayAdapter<FeedItem>
{
    private static final String TAG = "FeedAdapter";

    private ArrayList<FeedItem> feedItems;
    Context mContext;

    private String current_user_id;

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    private static class ViewHolder
    {
        CircleImageView profile_picture;
        ImageView cover_photo;
        TextView username;
        TextView date;
        TextView description;
        RatingBar rating_bar;
        TextView review;
        ImageView like;
        TextView likes;
    }

    public FeedAdapter(ArrayList<FeedItem> feedItems, Context mContext)
    {
        super(mContext, R.layout.feed_item, feedItems);
        this.mContext = mContext;
        this.feedItems = feedItems;

        current_user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public View getView (final int position, View convertView, ViewGroup parent)
    {
        final FeedItem feedItem = getItem(position);

        final ViewHolder viewHolder;

        final View result;

        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.feed_item, parent, false);

            viewHolder.profile_picture = (CircleImageView) convertView.findViewById(R.id.profile_picture);
            viewHolder.cover_photo = (ImageView) convertView.findViewById(R.id.cover_photo);
            viewHolder.username = (TextView) convertView.findViewById(R.id.username);
            viewHolder.date = (TextView) convertView.findViewById(R.id.date);
            viewHolder.description = (TextView) convertView.findViewById(R.id.description);
            viewHolder.rating_bar = (RatingBar) convertView.findViewById(R.id.rating_bar);
            viewHolder.review = (TextView) convertView.findViewById(R.id.review);
            viewHolder.like = (ImageView) convertView.findViewById(R.id.like);
            viewHolder.likes = (TextView) convertView.findViewById(R.id.likes);

            result = convertView;

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        //Setting cover_photo
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.image_placeholder);

        Glide.with(mContext).applyDefaultRequestOptions(requestOptions).load(getItem(position).getCover_photo())
                .into(viewHolder.cover_photo);

        //Setting date
        viewHolder.date.setText(getItem(position).getDate());

        //Setting description
        if(getItem(position).getItem_owner().equals("") || getItem(position).getItem_owner().equals("none"))
            viewHolder.description.setText(getItem(position).getItem_name());
        else
            viewHolder.description.setText(getItem(position).getItem_name() + " by " + getItem(position).getItem_owner());

        //Setting rating_bar
        viewHolder.rating_bar.setRating(getItem(position).getRating());

        //Setting review
        viewHolder.review.setText(getItem(position).getReview());

        //Setting like button and like_count
        databaseReference.child("posts")
                .child(getItem(position).getPost_id())
                .addListenerForSingleValueEvent(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        Post post = dataSnapshot.getValue(Post.class);

                        if(post.getLikes() != null)
                        {
                            viewHolder.likes.setText(post.getLikes().size() + "");

                            boolean liked = false;

                            for(int i = 0; i < post.getLikes().size(); i++)
                            {
                                if(post.getLikes().get(i).equals(current_user_id))
                                {
                                    liked = true;
                                }
                            }

                            if(liked)
                            {
                                viewHolder.like.setImageResource(R.drawable.liked);
                            }
                            else
                            {
                                viewHolder.like.setImageResource(R.drawable.not_liked);
                            }
                        }
                        else
                        {
                            viewHolder.likes.setText("0");
                            viewHolder.like.setImageResource(R.drawable.not_liked);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


        //Setting onClickListener to like button
        viewHolder.like.setTag(viewHolder);
        viewHolder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                databaseReference.child("posts")
                        .child(getItem(position).getPost_id())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Post post = dataSnapshot.getValue(Post.class);

                                if(post.getLikes() != null)
                                {
                                    boolean liked = false;
                                    int index = 0;
                                    for(int i = 0; i < post.getLikes().size(); i++)
                                    {
                                        if(post.getLikes().get(i).equals(current_user_id))
                                        {
                                            index = i;
                                            liked = true;
                                        }
                                    }

                                    if(liked)
                                    {
                                        viewHolder.like.setImageResource(R.drawable.not_liked);

                                        ArrayList<String> newLikes;
                                        newLikes = post.getLikes();
                                        newLikes.remove(index);

                                        post.setLikes(newLikes);
                                        post.setLike_count(post.getLike_count() - 1);

                                        viewHolder.likes.setText(post.getLike_count() + "");
                                    }
                                    else
                                    {
                                        viewHolder.like.setImageResource(R.drawable.liked);

                                        ArrayList<String> newLikes;
                                        newLikes = post.getLikes();
                                        newLikes.add(current_user_id);

                                        post.setLikes(newLikes);
                                        post.setLike_count(post.getLike_count() + 1);

                                        viewHolder.likes.setText(post.getLike_count() + "");
                                    }

                                    databaseReference.child("posts").child(post.getPost_id()).child("likes").setValue(post.getLikes());
                                    databaseReference.child("user_posts").child(post.getUser_id()).child(post.getPost_id()).child("likes").setValue(post.getLikes());

                                    databaseReference.child("posts").child(post.getPost_id()).child("like_count").setValue(post.getLike_count());
                                    databaseReference.child("user_posts").child(post.getUser_id()).child(post.getPost_id()).child("like_count").setValue(post.getLike_count());
                                }
                                else
                                {
                                    viewHolder.like.setImageResource(R.drawable.liked);

                                    ArrayList<String> newLikes;
                                    newLikes = new ArrayList<>();
                                    newLikes.add(current_user_id);

                                    post.setLikes(newLikes);
                                    post.setLike_count(post.getLike_count() + 1);

                                    viewHolder.likes.setText(post.getLike_count() + "");

                                    databaseReference.child("posts").child(post.getPost_id()).child("likes").setValue(post.getLikes());
                                    databaseReference.child("user_posts").child(post.getUser_id()).child(post.getPost_id()).child("likes").setValue(post.getLikes());

                                    databaseReference.child("posts").child(post.getPost_id()).child("like_count").setValue(post.getLike_count());
                                    databaseReference.child("user_posts").child(post.getUser_id()).child(post.getPost_id()).child("like_count").setValue(post.getLike_count());
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
            }
        });

        //Setting username
        viewHolder.username.setText(getItem(position).getUsername());

        //Setting profile_picture
        requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.image_placeholder);

        Glide.with(mContext).applyDefaultRequestOptions(requestOptions).load(getItem(position).getProfile_picture())
                .into(viewHolder.profile_picture);

        return convertView;
    }

}


/*
public class FeedAdapter extends ArrayAdapter<FeedItem>
{

    public interface OnLoadMoreItemsListener
    {
        void onLoadMoreItems();
    }

    OnLoadMoreItemsListener mOnLoadMoreItemsListener;

    private static final String TAG = "FeedAdapter";

    private LayoutInflater mInflater;
    private int mLayoutResource;
    private Context mContext;
    private DatabaseReference mReference;
    private String currentUsername = "";
    private List<FeedItem> feed_list;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    public FeedAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<FeedItem> objects)
    {
        super(context, resource, objects);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayoutResource = resource;
        this.mContext = context;
        mReference = FirebaseDatabase.getInstance().getReference();
    }

    static class ViewHolder
    {
        CircleImageView profile_picture;
        TextView username, time, review, description, likes;
        ImageView cover_photo;
        ImageView like, unlike;

        UserAccountSettings userAccountSettings = new UserAccountSettings();
        User user = new User();

        GestureDetector detector;
        FeedItem feedItem;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final ViewHolder holder;

        if(convertView == null){
            convertView = mInflater.inflate(mLayoutResource, parent, false);
            holder = new ViewHolder();

            holder.username = (TextView) convertView.findViewById(R.id.username);
            holder.cover_photo = convertView.findViewById(R.id.cover_photo);
            holder.unlike = (ImageView) convertView.findViewById(R.id.unlike_button);
            holder.like = (ImageView) convertView.findViewById(R.id.like_button);
            holder.likes = (TextView) convertView.findViewById(R.id.like_count);
            holder.review = (TextView) convertView.findViewById(R.id.review);
            holder.time = (TextView) convertView.findViewById(R.id.date);
            holder.profile_picture = (CircleImageView) convertView.findViewById(R.id.profile_picture);
            holder.description = convertView.findViewById(R.id.description);

            holder.feedItem = getItem(position);
            holder.detector = new GestureDetector(mContext, new GestureListener(holder));
            holder.users = new StringBuilder();

            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        //get the current users username (need for checking likes string)
        getCurrentUsername();

        //get likes string
        getLikesString(holder);

        //set the caption
        holder.caption.setText(getItem(position).getCaption());

        //set the comment
        List<Comment> comments = getItem(position).getComments();
        holder.comments.setText("View all " + comments.size() + " comments");
        holder.comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: loading comment thread for " + getItem(position).getPhoto_id());
                ((HomeActivity)mContext).onCommentThreadSelected(getItem(position),
                        mContext.getString(R.string.home_activity));

                //going to need to do something else?
                ((HomeActivity)mContext).hideLayout();

            }
        });

        //set the time it was posted
        String timestampDifference = getTimestampDifference(getItem(position));
        if(!timestampDifference.equals("0")){
            holder.timeDetla.setText(timestampDifference + " DAYS AGO");
        }else{
            holder.timeDetla.setText("TODAY");
        }

        //set the profile image
        final ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(getItem(position).getImage_path(), holder.image);


        //get the profile image and username
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child(mContext.getString(R.string.dbname_user_account_settings))
                .orderByChild(mContext.getString(R.string.field_user_id))
                .equalTo(getItem(position).getUser_id());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){

                    // currentUsername = singleSnapshot.getValue(UserAccountSettings.class).getUsername();

                    Log.d(TAG, "onDataChange: found user: "
                            + singleSnapshot.getValue(UserAccountSettings.class).getUsername());

                    holder.username.setText(singleSnapshot.getValue(UserAccountSettings.class).getUsername());
                    holder.username.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d(TAG, "onClick: navigating to profile of: " +
                                    holder.user.getUsername());

                            Intent intent = new Intent(mContext, ProfileActivity.class);
                            intent.putExtra(mContext.getString(R.string.calling_activity),
                                    mContext.getString(R.string.home_activity));
                            intent.putExtra(mContext.getString(R.string.intent_user), holder.user);
                            mContext.startActivity(intent);
                        }
                    });

                    imageLoader.displayImage(singleSnapshot.getValue(UserAccountSettings.class).getProfile_photo(),
                            holder.mprofileImage);
                    holder.mprofileImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d(TAG, "onClick: navigating to profile of: " +
                                    holder.user.getUsername());

                            Intent intent = new Intent(mContext, ProfileActivity.class);
                            intent.putExtra(mContext.getString(R.string.calling_activity),
                                    mContext.getString(R.string.home_activity));
                            intent.putExtra(mContext.getString(R.string.intent_user), holder.user);
                            mContext.startActivity(intent);
                        }
                    });



                    holder.settings = singleSnapshot.getValue(UserAccountSettings.class);
                    holder.comment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((HomeActivity)mContext).onCommentThreadSelected(getItem(position),
                                    mContext.getString(R.string.home_activity));

                            //another thing?
                            ((HomeActivity)mContext).hideLayout();
                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item, parent, false);
        context = parent.getContext();
        //firebaseFirestore = FirebaseFirestore.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.setIsRecyclable(false);

        final String feedItemId = feed_list.get(position).FeedItemId;
        final String currentUserId = firebaseAuth.getCurrentUser().getUid();

        String review = feed_list.get(position).getReview();
        holder.setReview(review);

        String image_url = feed_list.get(position).getCover_photo();
        holder.setCoverPhoto(image_url);

        final String user_id = feed_list.get(position).getUser_id();


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.child("users").child(user_id).getValue(User.class);
                holder.setUsername(user.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "Cannot get user");
            }
        });


        String dateString = feed_list.get(position).getDate();
        holder.setTime(dateString);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                Post post = dataSnapshot.child("posts").child(feedItemId).getValue(Post.class);
                int count = post.getLike_count();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "Cannot get post");
            }
        });
    }
*/
/*
    @Override
    public int getItemCount() {
        return feed_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View mView;

        private TextView review_view;
        private ImageView cover_photo_view;
        private TextView date_view;

        private TextView username_view;
        private CircleImageView profile_picture_view;

        private ImageView like_button;
        private TextView like_count_view;

        public ViewHolder(View itemView)
        {
            super(itemView);
            mView = itemView;

            like_button = mView.findViewById(R.id.like_button);
        }

        public void setReview(String review)
        {
            review_view = mView.findViewById(R.id.review);
            review_view.setText(review);
        }

        public void setCoverPhoto(String downloadUri)
        {
            cover_photo_view = mView.findViewById(R.id.cover_photo);

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.image_placeholder);

            Glide.with(context).applyDefaultRequestOptions(requestOptions).load(downloadUri)
            .into(cover_photo_view);

        }

        public void setTime(String date)
        {
            date_view = mView.findViewById(R.id.date);
            date_view.setText(date);
        }

        public void setUsername(String name)
        {
            username_view = mView.findViewById(R.id.username);
            username_view.setText(name);
        }

        public void updateLikesCount(int count)
        {
            like_count_view = mView.findViewById(R.id.like_count);
            like_count_view.setText(count + " Likes");
        }

    }

}
*/