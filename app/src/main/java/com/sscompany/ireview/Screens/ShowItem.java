package com.sscompany.ireview.Screens;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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
import com.sscompany.ireview.Models.InterfaceItem;
import com.sscompany.ireview.Models.UserAccountSettings;
import com.sscompany.ireview.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShowItem extends AppCompatActivity
{

    private CircleImageView profile_picture;
    private TextView username;
    private TextView date;
    private ImageView three_dots;
    private TextView item_name;
    private TextView item_type;
    private TextView item_owner;
    private TextView item_detail;
    private ImageView cover_photo;
    private RatingBar rating_bar;
    private TextView review;
    private ImageView post_image;
    private Intent predecessorIntent;

    private Context mContext;

    private String current_user_id;

    private DatabaseReference myRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_item);

        //Initializing mContext
        mContext = ShowItem.this;

        //Initializing current_user_id
        current_user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //Getting intent
        predecessorIntent = getIntent();

        //Initializing myRef
        myRef = FirebaseDatabase.getInstance().getReference();

        init_widgets();

        get_intent_extras_and_set_widgets();

        //set_widgets();
    }

    private void get_intent_extras_and_set_widgets()
    {
        final String profile_pictureS = predecessorIntent.getStringExtra("profile_picture");
        final String usernameS = predecessorIntent.getStringExtra("username");
        final String dateS = predecessorIntent.getStringExtra("date");
        final String item_nameS = predecessorIntent.getStringExtra("item_name");
        final String item_typeS = predecessorIntent.getStringExtra("item_type");
        final String item_ownerS = predecessorIntent.getStringExtra("item_owner");
        final String item_detailS = predecessorIntent.getStringExtra("item_detail");
        final String cover_photoS = predecessorIntent.getStringExtra("cover_photo");
        final float ratingS = predecessorIntent.getFloatExtra("rating", 0);
        final String reviewS = predecessorIntent.getStringExtra("review");
        final String post_imageS = predecessorIntent.getStringExtra("post_image");
        final String post_id = predecessorIntent.getStringExtra("post_id");
        final String category = predecessorIntent.getStringExtra("category");
        final String user_id = predecessorIntent.getStringExtra("user_id");

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.image_placeholder);

        Glide.with(mContext).applyDefaultRequestOptions(requestOptions).load(cover_photoS)
                .into(cover_photo);

        Glide.with(mContext).applyDefaultRequestOptions(requestOptions).load(profile_pictureS)
                .into(profile_picture);

        Glide.with(mContext).applyDefaultRequestOptions(requestOptions).load(post_imageS)
                .into(post_image);

        username.setText(usernameS);
        date.setText(dateS);
        item_name.setText(item_nameS);
        item_type.setText(item_typeS);
        item_owner.setText(item_ownerS);
        item_detail.setText(item_detailS);
        rating_bar.setRating(ratingS);
        review.setText(reviewS);

        if(!user_id.equals(current_user_id))
        {
            three_dots.setVisibility(View.GONE);
        }
        else {
            three_dots.setVisibility(View.VISIBLE);
            three_dots.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //creating a popup menu
                    PopupMenu popup = new PopupMenu(mContext, three_dots);
                    //inflating menu from xml resource
                    popup.inflate(R.menu.menu_for_own_post);
                    //adding click listener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.edit:
                                    //Edit clicked

                                    AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                                    alert.setTitle(null);
                                    alert.setMessage("Are you sure to edit the post?");
                                    alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // do whatever
                                            Intent intent = new Intent(mContext, AddItem.class);

                                            intent.putExtra("action", "edit");
                                            intent.putExtra("category", category);
                                            intent.putExtra("item_name", item_nameS);
                                            intent.putExtra("item_type", item_typeS);
                                            intent.putExtra("item_owner", item_ownerS);
                                            intent.putExtra("item_detail", item_detailS);
                                            intent.putExtra("cover_photo", cover_photoS);
                                            intent.putExtra("post_image", post_imageS);
                                            intent.putExtra("review", reviewS);
                                            intent.putExtra("date", dateS);
                                            intent.putExtra("rating", ratingS);
                                            intent.putExtra("post_id", post_id);
                                            intent.putExtra("profile_picture", profile_pictureS);
                                            intent.putExtra("username", usernameS);

                                            mContext.startActivity(intent);
                                        }
                                    });
                                    alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });

                                    alert.show();


                                    break;
                                case R.id.delete:
                                    //Delete Item is clicked

                                    alert = new AlertDialog.Builder(mContext);
                                    alert.setTitle(null);
                                    alert.setMessage("Are you sure to edit the post?");
                                    alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            FirebaseDatabase.getInstance().getReference().child("user_items")
                                                    .child(current_user_id)
                                                    .child(post_id)
                                                    .removeValue();
                                        }
                                    });
                                    alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
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

    }

    private void init_widgets()
    {
        profile_picture = findViewById(R.id.profile_picture);
        username = findViewById(R.id.username);
        date = findViewById(R.id.date);
        three_dots = findViewById(R.id.three_dots);
        item_name = findViewById(R.id.item_name);
        item_type = findViewById(R.id.item_type);
        item_owner = findViewById(R.id.item_owner);
        item_detail = findViewById(R.id.item_detail);
        cover_photo = findViewById(R.id.cover_photo);
        rating_bar = findViewById(R.id.rating_bar);
        review = findViewById(R.id.review);
        post_image = findViewById(R.id.post_image);
    }
}
