package com.sscompany.ireview.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
import com.sscompany.ireview.FeedItem;
import com.sscompany.ireview.Models.Post;
import com.sscompany.ireview.R;

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
        TextView description;
        RatingBar rating_bar;
        TextView review;
        ImageView like;
        TextView likes;
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

            viewHolder.profile_picture = (CircleImageView) convertView.findViewById(R.id.profile_picture);
            viewHolder.cover_photo = (ImageView) convertView.findViewById(R.id.cover_photo);
            viewHolder.username = (TextView) convertView.findViewById(R.id.username);
            viewHolder.date = (TextView) convertView.findViewById(R.id.date);
            viewHolder.description = (TextView) convertView.findViewById(R.id.description);
            viewHolder.rating_bar = (RatingBar) convertView.findViewById(R.id.rating_bar);
            viewHolder.review = (TextView) convertView.findViewById(R.id.review);
            //makeTextViewResizable(viewHolder.review, 3, "Show More", true);
            viewHolder.like = (ImageView) convertView.findViewById(R.id.like);
            viewHolder.likes = (TextView) convertView.findViewById(R.id.likes);

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

        //Setting description
        if (getItem(position).getItem_owner().equals("") || getItem(position).getItem_owner().equals("none"))
            viewHolder.description.setText(getItem(position).getItem_name());
        else
            viewHolder.description.setText(getItem(position).getItem_name() + " by " + getItem(position).getItem_owner());

        //Setting rating_bar
        viewHolder.rating_bar.setRating(getItem(position).getRating());

        //Setting review
        viewHolder.review.setText(getItem(position).getReview());
        //makeTextViewResizable(viewHolder.review, 3, "Show More", true);

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

        return convertView;
    }

    public static void makeTextViewResizable(final TextView review, final int maxLine, final String expandText, final boolean viewMore) {

        if (review.getTag() == null) {
            review.setTag(review.getText());
        }
        ViewTreeObserver vto = review.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                String text;
                int lineEndIndex;
                ViewTreeObserver obs = review.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    lineEndIndex = review.getLayout().getLineEnd(0);
                    text = review.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                } else if (maxLine > 0 && review.getLineCount() >= maxLine) {
                    lineEndIndex = review.getLayout().getLineEnd(maxLine - 1);
                    text = review.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                } else {
                    lineEndIndex = review.getLayout().getLineEnd(review.getLayout().getLineCount() - 1);
                    text = review.getText().subSequence(0, lineEndIndex) + " " + expandText;
                }
                review.setText(text);
                review.setMovementMethod(LinkMovementMethod.getInstance());
                review.setText(
                        addClickablePartTextViewResizable(Html.fromHtml(review.getText().toString()), review, lineEndIndex, expandText,
                                viewMore), TextView.BufferType.SPANNABLE);
            }
        });

    }

    private static SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView review,
                                                                            final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {
            ssb.setSpan(new ClickableSpan() {

                @Override
                public void onClick(View widget) {
                    review.setLayoutParams(review.getLayoutParams());
                    review.setText(review.getTag().toString(), TextView.BufferType.SPANNABLE);
                    review.invalidate();
                    if (viewMore) {
                        makeTextViewResizable(review, -1, "Show Less", false);
                    } else {
                        makeTextViewResizable(review, 3, "Show More", true);
                    }

                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;

    }
}