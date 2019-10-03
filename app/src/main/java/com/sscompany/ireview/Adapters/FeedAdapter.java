package com.sscompany.ireview.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sscompany.ireview.AddItem;
import com.sscompany.ireview.FeedItem;
import com.sscompany.ireview.Models.Post;
import com.sscompany.ireview.R;
import com.sscompany.ireview.ReadMoreTextView;

import de.hdodenhof.circleimageview.CircleImageView;

import java.util.ArrayList;


public class FeedAdapter extends ArrayAdapter<FeedItem> {
    private static final String TAG = "FeedAdapter";

    private ArrayList<FeedItem> feedItems;
    Context mContext;

    private String current_user_id;

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    private static class ViewHolder {
        CircleImageView profile_picture;
        ImageView cover_photo;
        TextView username;
        TextView date;
        TextView name;
        TextView type;
        TextView owner;
        TextView detail;
        RatingBar rating_bar;
        ReadMoreTextView review;
        ImageView like;
        TextView likes;
        ImageView postImage;
        ImageView three_dots;
    }

    public FeedAdapter(ArrayList<FeedItem> feedItems, Context mContext) {
        super(mContext, R.layout.feed_item, feedItems);
        this.mContext = mContext;
        this.feedItems = feedItems;

        current_user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        final FeedItem feedItem = getItem(position);

        final ViewHolder viewHolder;

        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.feed_item, parent, false);

            viewHolder.profile_picture = convertView.findViewById(R.id.profile_picture);
            viewHolder.cover_photo = convertView.findViewById(R.id.cover_photo);
            viewHolder.username = convertView.findViewById(R.id.username);
            viewHolder.date = convertView.findViewById(R.id.date);
            viewHolder.name = convertView.findViewById(R.id.firstRow);
            viewHolder.type = convertView.findViewById(R.id.secondRow);
            viewHolder.owner = convertView.findViewById(R.id.thirdRow);
            viewHolder.detail = convertView.findViewById(R.id.fourthRow);
            viewHolder.rating_bar = convertView.findViewById(R.id.rating_bar);
            viewHolder.review = convertView.findViewById(R.id.review);

            viewHolder.like = convertView.findViewById(R.id.like);
            viewHolder.likes = convertView.findViewById(R.id.likes);
            viewHolder.postImage = convertView.findViewById(R.id.post_image);
            viewHolder.three_dots = convertView.findViewById(R.id.three_dots);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
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

        //Setting name, type, owner, detail
        viewHolder.name.setText(getItem(position).getItem_name());
        viewHolder.type.setText(getItem(position).getItem_type());
        viewHolder.owner.setText(getItem(position).getItem_owner());

        if(!getItem(position).getItem_detail().equals(""))
        {
            viewHolder.detail.setVisibility(View.VISIBLE);
            viewHolder.detail.setText(getItem(position).getItem_detail());
        }
        else
        {
            viewHolder.detail.setVisibility(View.GONE);
        }


        //Setting rating_bar
        viewHolder.rating_bar.setRating(getItem(position).getRating());

        //Setting review
        if(getItem(position).getReview().equals(""))
        {
            viewHolder.review.setVisibility(View.GONE);
        }
        else
        {
            viewHolder.review.setVisibility(View.VISIBLE);
            viewHolder.review.setText(getItem(position).getReview());
        }


        //Setting like button and like_count
        databaseReference.child("posts")
                .child(getItem(position).getPost_id())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Post post = dataSnapshot.getValue(Post.class);

                        if (post.getLikes() != null) {
                            viewHolder.likes.setText(post.getLikes().size() + "");

                            boolean liked = false;

                            for (int i = 0; i < post.getLikes().size(); i++) {
                                if (post.getLikes().get(i).equals(current_user_id)) {
                                    liked = true;
                                }
                            }

                            if (liked) {
                                viewHolder.like.setImageResource(R.drawable.liked);
                            } else {
                                viewHolder.like.setImageResource(R.drawable.not_liked);
                            }
                        } else {
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
            public void onClick(View v) {
                databaseReference.child("posts")
                        .child(getItem(position).getPost_id())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Post post = dataSnapshot.getValue(Post.class);

                                if (post.getLikes() != null) {
                                    boolean liked = false;
                                    int index = 0;
                                    for (int i = 0; i < post.getLikes().size(); i++) {
                                        if (post.getLikes().get(i).equals(current_user_id)) {
                                            index = i;
                                            liked = true;
                                        }
                                    }

                                    if (liked) {
                                        viewHolder.like.setImageResource(R.drawable.not_liked);

                                        ArrayList<String> newLikes;
                                        newLikes = post.getLikes();
                                        newLikes.remove(index);

                                        post.setLikes(newLikes);
                                        post.setLike_count(post.getLike_count() - 1);

                                        viewHolder.likes.setText(post.getLike_count() + "");
                                    } else {
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
                                } else {
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

        //Checking if postImage is available
        if(getItem(position).getPost_image().equals("none"))
        {
            viewHolder.postImage.setVisibility(View.GONE);
        }
        else
        {
            viewHolder.postImage.setVisibility(View.VISIBLE);

            //Setting post image
            requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.image_placeholder);

            Glide.with(mContext).applyDefaultRequestOptions(requestOptions).load(getItem(position).getPost_image())
                    .into(viewHolder.postImage);
        }

        //Adding menu if poster is not current user
        if(!getItem(position).getUser_id().equals(current_user_id))
        {
            viewHolder.three_dots.setVisibility(View.GONE);
        }
        else
        {
            viewHolder.three_dots.setVisibility(View.VISIBLE);
            viewHolder.three_dots.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //creating a popup menu
                    PopupMenu popup = new PopupMenu(mContext, viewHolder.three_dots);
                    //inflating menu from xml resource
                    popup.inflate(R.menu.menu_for_own_post);
                    //adding click listener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.edit:
                                    //Edit clicked

                                    Intent intent = new Intent(mContext, AddItem.class);

                                    intent.putExtra("action", "edit");
                                    intent.putExtra("category", getItem(position).getCategory());
                                    intent.putExtra("item_name", getItem(position).getItem_name());
                                    intent.putExtra("item_type", getItem(position).getItem_type());
                                    intent.putExtra("item_owner", getItem(position).getItem_owner());
                                    intent.putExtra("item_detail", getItem(position).getItem_detail());
                                    intent.putExtra("cover_photo", getItem(position).getCover_photo());
                                    intent.putExtra("post_image", getItem(position).getPost_image());
                                    intent.putExtra("review", getItem(position).getReview());
                                    intent.putExtra("date", getItem(position).getDate());
                                    intent.putExtra("rating", getItem(position).getRating());
                                    intent.putExtra("post_id", getItem(position).getPost_id());
                                    intent.putExtra("profile_picture", getItem(position).getProfile_picture());
                                    intent.putExtra("username", getItem(position).getUsername());

                                    mContext.startActivity(intent);

                                    break;
                                case R.id.delete:
                                    //Delete Item is clicked

                                    AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                                    alert.setTitle(null);
                                    alert.setMessage("Are you sure to edit the post?");
                                    alert.setPositiveButton("YES", new DialogInterface.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which)
                                        {
                                            FirebaseDatabase.getInstance().getReference().child("user_posts")
                                                .child(current_user_id)
                                                .child(getItem(position).getPost_id())
                                                .removeValue();

                                            FirebaseDatabase.getInstance().getReference().child("posts")
                                                    .child(getItem(position).getPost_id())
                                                    .removeValue();
                                        }
                                    });
                                    alert.setNegativeButton("NO", new DialogInterface.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });

                                    alert.show();


                                    break;
                            }
                            return false;
                        }
                    });
                    //displaying the popup
                    popup.show();

                }
            });

        }
        return convertView;
    }
}