package com.sscompany.ireview;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sscompany.ireview.Models.Book;
import com.sscompany.ireview.Models.FirebaseMethods;
import com.sscompany.ireview.Models.Game;
import com.sscompany.ireview.Models.ImageManagerClass;
import com.sscompany.ireview.Models.InterfaceItem;
import com.sscompany.ireview.Models.Item;
import com.sscompany.ireview.Models.Movie;
import com.sscompany.ireview.Models.Music;
import com.sscompany.ireview.Models.Place;
import com.sscompany.ireview.Models.Post;
import com.sscompany.ireview.Models.TVShow;
import com.sscompany.ireview.Models.UserAccountSettings;
import com.sscompany.ireview.Models.Website;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class AddItem extends AppCompatActivity
{
    private static final String TAG = "AddItem";

    private Context mContext;

    private LinearLayout rightLinearLayout;
    private CheckBox postReviewCheckBox;
    private EditText review;
    private RatingBar ratingBar;
    private Button submit;

    private EditText firstEditText;
    private EditText secondEditText;
    private EditText thirdEditText;
    private EditText fourthEditText;

    private int widthOfEditText;

    private Intent predecessorIntent;
    private String categoryExtra;
    private String actionExtra;

    private FirebaseMethods firebaseMethods;

    private ImageView imageImageView;
    private ImageView cover_photoImageView;

    final int REQUEST_IMAGE_CAPTURE = 1;
    final int REQUEST_IMAGE_CAPTURE1 = 2;
    final int REQUEST_GALLERY = 3;
    final int REQUEST_GALLERY1 = 4;

    private boolean finished;

    private StorageReference mStorageReference;
    private DatabaseReference myRef;
    private double mPhotoUploadProgress;

    private int imageCount;

    private String itemId;

    private String firebaseUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);


        //Initializing mContext
        mContext = AddItem.this;

        //Initializing firebaseMethods
        firebaseMethods = new FirebaseMethods(mContext);

        //Initializing firebaseStorage
        mStorageReference = FirebaseStorage.getInstance().getReference();

        //Initializing DatabaseReference myRef
        myRef = FirebaseDatabase.getInstance().getReference();

        //Initializing mPhotoUploadProgress
        mPhotoUploadProgress = 0;

        //Initializing predecessorIntent
        predecessorIntent = getIntent();

        //Initializing extras
        categoryExtra = predecessorIntent.getStringExtra("category");
        actionExtra = predecessorIntent.getStringExtra("action");

        //Initializing Widgets
        rightLinearLayout = findViewById(R.id.right_linear_layout);
        postReviewCheckBox = findViewById(R.id.post_review);
        review = findViewById(R.id.review);
        ratingBar = findViewById(R.id.rating_bar);
        submit = findViewById(R.id.submit);
        imageImageView = findViewById(R.id.image);
        cover_photoImageView = findViewById(R.id.cover_photo);

        //Setting review and stars widgets gone
        review.setVisibility(View.GONE);
        ratingBar.setVisibility(View.GONE);

        //Creating EditTexts of rightLinearLayout, setting sizes, and adding them to rightLinearLayout
        createEditTexts();

        //Organizing EditTexts according to intent extras
        initEditTexts();

        //gettingImageCount in order to upload photo to FirebaseStorage
        initImageCount();
    }

    /**
     * Import Cover Photo
     *
     * @param v
     */
    public void importCoverPhoto(View v)
    {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery" };

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Import Book Cover Photo");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE1);
                    }

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , REQUEST_GALLERY1);
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    /**
     * Import Image
     *
     * @param v
     */
    public void importImage(View v){

        final CharSequence[] options = { "Take Photo", "Choose from Gallery" };

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Import Your Image");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , REQUEST_GALLERY);
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * OnActivityResult method of getting photo from storage or taking photo
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
        {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            Drawable dr = new BitmapDrawable(getResources(), imageBitmap);
            cover_photoImageView.setImageDrawable(dr);

            //imageImageView.setImageBitmap(imageBitmap);
            imageImageView.setBackground(null);
            imageImageView.setOnClickListener(onClickListener);
            imageImageView.setLongClickable(true);
            imageImageView.setOnLongClickListener(onLongClickListener);
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE1 && resultCode == RESULT_OK)
        {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            Drawable dr = new BitmapDrawable(getResources(), imageBitmap);
            cover_photoImageView.setImageDrawable(dr);
            //cover_photoImageView.setImageBitmap(imageBitmap);
            cover_photoImageView.setBackground(null);
            cover_photoImageView.setOnClickListener(onClickListener);
            cover_photoImageView.setLongClickable(true);
            cover_photoImageView.setOnLongClickListener(onLongClickListener);
        }
        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK)
        {
            Uri imageUri = data.getData();

            InputStream inputStream;

            try {
                inputStream = getContentResolver().openInputStream(imageUri);

                Bitmap image = BitmapFactory.decodeStream(inputStream);

                Drawable dr = new BitmapDrawable(getResources(), image);
                cover_photoImageView.setImageDrawable(dr);

                //imageImageView.setImageBitmap(image);
                imageImageView.setBackground(null);
                imageImageView.setOnClickListener(onClickListener);
                imageImageView.setLongClickable(true);
                imageImageView.setOnLongClickListener(onLongClickListener);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == REQUEST_GALLERY1 && resultCode == RESULT_OK)
        {
            Uri imageUri = data.getData();

            InputStream inputStream;

            try {
                inputStream = getContentResolver().openInputStream(imageUri);

                Bitmap image = BitmapFactory.decodeStream(inputStream);

                Drawable dr = new BitmapDrawable(getResources(), image);
                cover_photoImageView.setImageDrawable(dr);

                //cover_photoImageView.setImageBitmap(image);
                cover_photoImageView.setBackground(null);
                cover_photoImageView.setOnClickListener(onClickListener);
                cover_photoImageView.setLongClickable(true);
                cover_photoImageView.setOnLongClickListener(onLongClickListener);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * When postReview checkBox is checked, review and stars will be visible,
     * and " and Post" string will be added to submit button text
     * other than this case, review and stars will be gone,
     * and " and Post" string will be removed from submit button text
     *
     * @param v
     */
    public void checkBoxClicked(View v)
    {
        if(postReviewCheckBox.isChecked())
        {
            review.setVisibility(View.VISIBLE);
            ratingBar.setVisibility(View.VISIBLE);

            submit.setText(submit.getText().toString() + " and Post");
        }
        else
        {
            review.setVisibility(View.GONE);
            ratingBar.setVisibility(View.GONE);

            submit.setText(submit.getText().toString().replaceAll(" and Post", ""));
        }
    }

    /**
     * Submit Button is clicked
     *
     * @param v
     */
    public void submitButton(View v)
    {
        submit.setActivated(false);

        System.out.println("Submit button is clicked");

        System.out.println("actionExtra : " + actionExtra + "  categoryExtra : " + categoryExtra);
        if(actionExtra.equals("add"))
        {
            /*
             * For Book
             */
            if(categoryExtra.equals("book")) {
                String name = firstEditText.getText().toString();
                String author = secondEditText.getText().toString();
                String genre = thirdEditText.getText().toString();

                if (name.equals("") || author.equals("") || genre.equals("") || cover_photoImageView.getDrawable() == null)
                {
                    Toast.makeText(mContext, "Book name, author, genre and cover photo are required", Toast.LENGTH_SHORT).show();
                }
                else if(postReviewCheckBox.isChecked() && ratingBar.getRating() == 0)
                {
                    Toast.makeText(mContext, "In order to post, you should rate the item!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    final Book newItem = new Book();
                    newItem.setName(name);
                    newItem.setOwner(author);
                    newItem.setGenre(genre);
                    newItem.setCover_photo("none");

                    cover_photoImageView.invalidate();
                    BitmapDrawable drawable = (BitmapDrawable)cover_photoImageView.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();

                    addNewPhotoToFirebaseStorage(bitmap, categoryExtra, imageCount, newItem);
                }
            }

            /*
             * For Movie
             */
            if(categoryExtra.equals("movie")) {
                String title = firstEditText.getText().toString();
                String director = secondEditText.getText().toString();
                String genre = thirdEditText.getText().toString();
                String lead_actors = fourthEditText.getText().toString();

                if (title.equals("") || director.equals("") || genre.equals("") || cover_photoImageView.getDrawable() == null)
                {
                    Toast.makeText(mContext, "Movie title, director, genre and cover photo are required", Toast.LENGTH_SHORT).show();
                }
                else if(postReviewCheckBox.isChecked() && ratingBar.getRating() == 0)
                {
                    Toast.makeText(mContext, "In order to post, you should rate the item!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Movie newItem = new Movie();
                    newItem.setName(title);
                    newItem.setOwner(director);
                    newItem.setGenre(genre);
                    newItem.setLead_actors(lead_actors);
                    newItem.setCover_photo("none");

                    cover_photoImageView.invalidate();
                    BitmapDrawable drawable = (BitmapDrawable)cover_photoImageView.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    addNewPhotoToFirebaseStorage(bitmap, categoryExtra, imageCount, newItem);

                }
            }

            /*
             * For Music
             */
            if(categoryExtra.equals("music")) {
                String name = firstEditText.getText().toString();
                String singer = secondEditText.getText().toString();
                String genre = thirdEditText.getText().toString();
                String language = fourthEditText.getText().toString();

                if (name.equals("") || singer.equals("") || genre.equals("") || cover_photoImageView.getDrawable() == null)
                {
                    Toast.makeText(mContext, "Music name, singer, genre and cover photo are required", Toast.LENGTH_SHORT).show();
                }
                else if(postReviewCheckBox.isChecked() && ratingBar.getRating() == 0)
                {
                    Toast.makeText(mContext, "In order to post, you should rate the item!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Music newItem = new Music();
                    newItem.setName(name);
                    newItem.setOwner(singer);
                    newItem.setGenre(genre);
                    newItem.setLanguage(language);
                    newItem.setCover_photo("none");

                    cover_photoImageView.invalidate();
                    BitmapDrawable drawable = (BitmapDrawable)cover_photoImageView.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();

                    addNewPhotoToFirebaseStorage(bitmap, categoryExtra, imageCount, newItem);

                }
            }

            /*
             * For Place
             */
            if(categoryExtra.equals("place")) {
                String name = firstEditText.getText().toString();
                String place_type = secondEditText.getText().toString();
                String address = thirdEditText.getText().toString();

                if (name.equals("") || place_type.equals("") || address.equals("") || cover_photoImageView.getDrawable() == null)
                {
                    Toast.makeText(mContext, "Place name, place type, address and cover photo are required", Toast.LENGTH_SHORT).show();
                }
                else if(postReviewCheckBox.isChecked() && ratingBar.getRating() == 0)
                {
                    Toast.makeText(mContext, "In order to post, you should rate the item!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Place newItem = new Place();
                    newItem.setName(name);
                    newItem.setOwner("none");
                    newItem.setPlace_type(place_type);
                    newItem.setAddress(address);
                    newItem.setCover_photo("none");

                    cover_photoImageView.invalidate();
                    BitmapDrawable drawable = (BitmapDrawable)cover_photoImageView.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();

                    addNewPhotoToFirebaseStorage(bitmap, categoryExtra, imageCount, newItem);

                    //itemId = firebaseMethods.addNewItem(newItem, categoryExtra);

                }
            }

            /*
             * For TV Show
             */
            if(categoryExtra.equals("tv_show")) {
                String name = firstEditText.getText().toString();
                String host = secondEditText.getText().toString();
                String genre = thirdEditText.getText().toString();

                if (name.equals("") || host.equals("") || genre.equals("") || cover_photoImageView.getDrawable() == null)
                {
                    Toast.makeText(mContext, "TV show name, host, genre and cover photo are required", Toast.LENGTH_SHORT).show();
                }
                else if(postReviewCheckBox.isChecked() && ratingBar.getRating() == 0)
                {
                    Toast.makeText(mContext, "In order to post, you should rate the item!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    TVShow newItem = new TVShow();
                    newItem.setName(name);
                    newItem.setOwner(host);
                    newItem.setGenre(genre);
                    newItem.setCover_photo("none");

                    cover_photoImageView.invalidate();
                    BitmapDrawable drawable = (BitmapDrawable)cover_photoImageView.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();

                    addNewPhotoToFirebaseStorage(bitmap, categoryExtra, imageCount, newItem);

                }
            }

            /*
             * For Website
             */
            if(categoryExtra.equals("website")) {
                String web_address = firstEditText.getText().toString();
                String use = secondEditText.getText().toString();

                if (web_address.equals("") || use.equals("") || cover_photoImageView.getDrawable() == null)
                {
                    Toast.makeText(mContext, "Web address, use and cover photo are required", Toast.LENGTH_SHORT).show();
                }
                else if(postReviewCheckBox.isChecked() && ratingBar.getRating() == 0)
                {
                    Toast.makeText(mContext, "In order to post, you should rate the item!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Website newItem = new Website();
                    newItem.setName(web_address);
                    newItem.setOwner("none");
                    newItem.setUse(use);
                    newItem.setCover_photo("none");

                    cover_photoImageView.invalidate();
                    BitmapDrawable drawable = (BitmapDrawable)cover_photoImageView.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();

                    addNewPhotoToFirebaseStorage(bitmap, categoryExtra, imageCount, newItem);

                }
            }

            /*
             * For Game
             */
            if(categoryExtra.equals("game")) {
                String name = firstEditText.getText().toString();
                String developer = secondEditText.getText().toString();
                String game_type = thirdEditText.getText().toString();

                if (name.equals("") || developer.equals("") || game_type.equals("") || cover_photoImageView.getDrawable() == null)
                {
                    Toast.makeText(mContext, "Name of the game, developer, game type and cover photo are required", Toast.LENGTH_SHORT).show();
                }
                else if(postReviewCheckBox.isChecked() && ratingBar.getRating() == 0)
                {
                    Toast.makeText(mContext, "In order to post, you should rate the item!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Game newItem = new Game();
                    newItem.setName(name);
                    newItem.setOwner(developer);
                    newItem.setGame_type(game_type);
                    newItem.setCover_photo("none");

                    cover_photoImageView.invalidate();
                    BitmapDrawable drawable = (BitmapDrawable)cover_photoImageView.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();

                    addNewPhotoToFirebaseStorage(bitmap, categoryExtra, imageCount, newItem);

                }
            }
        }
        submit.setActivated(true);
    }

    public void addNewPhotoToFirebaseStorage(Bitmap bitmap, final String category, final int imageCount, final InterfaceItem item)
    {
        //Initializing itemId
        itemId = "";

        //Initializing storageReference according to category (folder)
        final StorageReference storageReference = mStorageReference
                .child(category + "s/photo" + (imageCount + 1));

        //Converting bitmap to bytes
        byte[] bytes = ImageManagerClass.getBytesFromBitmap(bitmap, 100);

        //Creating an uploadTask in order to manage upload process of cover photo
        UploadTask uploadTask = null;
        uploadTask = storageReference.putBytes(bytes);

        //Creating a processDialog and initializing it
        final ProgressDialog progressDialog = new ProgressDialog(mContext);

        //Just to set title accordingly
        if(postReviewCheckBox.isChecked())
        {
            progressDialog.setTitle("Posting Review...\nPlease Wait...");
        }
        else
            progressDialog.setTitle("Adding Item...\nPlease Wait...");

        progressDialog.setCancelable(false);
        progressDialog.show();

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                progressDialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: Photo upload failed.");
                progressDialog.dismiss();
                Toast.makeText(mContext, "Item addition failed.", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot)
            {
                double progress = 100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount();

                progressDialog.setMessage("Progress " + (int)progress + "%");
            }
        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task)
            {
                progressDialog.dismiss();

                //Getting download link of cover photo and adding item / posting review
                storageReference.getDownloadUrl().addOnSuccessListener(
                        new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri)
                            {
                                Uri downloadUrl = uri;
                                firebaseUri = downloadUrl.toString();
                                item.setCover_photo(firebaseUri);

                                //Getting itemId
                                itemId = addNewItem(item, category);

                                //If user wants to post addNewPost method is called
                                if(postReviewCheckBox.isChecked())
                                {
                                    addNewPost(itemId, item, review.getText().toString(), ratingBar.getRating());
                                }

                                //After adding item and posting review, Add Element activity starts
                                Intent intent = new Intent(mContext, AddElement.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                //Just to toast correct message
                if(postReviewCheckBox.isChecked())
                {
                    Toast.makeText(mContext, "Posting Review Complete!", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(mContext, "Item Addition Complete!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    /**
     * This method is called before posting review process, after cover photo upload process being done
     * Adding new item to FirebaseDatabase
     *
     * @param newElement
     * @param category
     * @return
     */
    public String addNewItem(InterfaceItem newElement, String category)
    {
        //Getting id for element (from one category) which will be located under category items classes
        String newElementKey = myRef.child(category + "s").push().getKey();

        //Getting id for item (from any category) which will be located under items class
        String newItemKey = myRef.child("items").push().getKey();

        //Creating new Item (Not InterfaceItem)
        Item newItem = new Item();

        newItem.setItem_id(newElementKey);
        newItem.setCategory(category);
        newItem.setName(newElement.getName());
        newItem.setOwner(newElement.getOwner());
        newItem.setCover_photo(newElement.getCover_photo());

        myRef.child(category + "s")
                .child(newElementKey)
                .setValue(newElement);

        myRef.child("items")
                .child(newItemKey)
                .setValue(newItem);

        return newItemKey;
    }

    /**
     *
     * This method is called after cover photo upload process, after getting added items id
     * It adds new post to database class posts and user_posts
     *
     * @param itemId
     * @param item
     * @param review
     * @param rating
     */
    public void addNewPost(String itemId, InterfaceItem item, String review, float rating)
    {
        String newPostKey = myRef.child("posts").push().getKey();
        final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        ArrayList<String> newLikesArray = new ArrayList<>();

        //Creating and setting new post
        Post newPost = new Post();

        newPost.setReview(review);
        newPost.setItem_id(itemId);
        newPost.setItem_name(item.getName());
        newPost.setItem_owner(item.getOwner());
        newPost.setItem_cover_photo(item.getCover_photo());
        newPost.setLike_count(0);
        newPost.setLikes(newLikesArray);
        newPost.setRating(rating);
        newPost.setUser_id(userID);
        newPost.setData_created(firebaseMethods.getTimestamp());
        newPost.setPost_id(newPostKey);

        //Adding post to user_posts
        myRef.child("user_posts").child(userID).child(newPostKey).setValue(newPost);

        //Adding post to posts
        myRef.child("posts").child(newPostKey).setValue(newPost);

        //Getting number of reviews of current user in order to increase it
        FirebaseDatabase.getInstance().getReference()
                .child("user_account_settings")
                .child(userID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        UserAccountSettings userAccountSettings = dataSnapshot.getValue(UserAccountSettings.class);

                        //Increasing number of reviews for current user
                        myRef.child("user_account_settings").child(userID).child("reviews").setValue(userAccountSettings.getReviews() + 1);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        Log.d(TAG, "Review Posted");
    }

    /**
     * Initializes imageCount inside the folder in Firebase Storage
     * in order to upload a new photo
     *
     */
    private void initImageCount()
    {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                imageCount = firebaseMethods.getImageCount(dataSnapshot, categoryExtra + "s");
                Log.d(TAG, "onDataChange: image count: " + imageCount);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * Creates EditTexts for rightLinearLayout and adds them to rightLinearLayout
     */
    private void createEditTexts()
    {
        //Getting Screen Size
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        //Getting width for EditTexts
        widthOfEditText = displayMetrics.widthPixels / 2;

        //Creating and Adding EditTexts to rightLinearLayout
        firstEditText = new EditText(mContext);
        firstEditText.setHint("First");
        firstEditText.setLayoutParams(new ConstraintLayout.LayoutParams(widthOfEditText, ViewGroup.LayoutParams.WRAP_CONTENT));

        secondEditText = new EditText(mContext);
        secondEditText.setHint("Second");
        secondEditText.setLayoutParams(new ConstraintLayout.LayoutParams(widthOfEditText, ViewGroup.LayoutParams.WRAP_CONTENT));

        thirdEditText = new EditText(mContext);
        thirdEditText.setHint("Third");
        thirdEditText.setLayoutParams(new ConstraintLayout.LayoutParams(widthOfEditText, ViewGroup.LayoutParams.WRAP_CONTENT));

        fourthEditText = new EditText(mContext);
        fourthEditText.setHint("Fourth");
        fourthEditText.setLayoutParams(new ConstraintLayout.LayoutParams(widthOfEditText, ViewGroup.LayoutParams.WRAP_CONTENT));

        rightLinearLayout.addView(firstEditText);
        rightLinearLayout.addView(secondEditText);
        rightLinearLayout.addView(thirdEditText);
        rightLinearLayout.addView(fourthEditText);
    }

    /**
     * Initializes EditTexts (seta the sizes and hints)
     *
     */
    private void initEditTexts()
    {
        //Changing Submit Button Text
        if(actionExtra.equals("add"))
        {
            submit.setText("Add");
        }
        else if(actionExtra.equals("edit"))
        {
            submit.setText("Save");
        }

        //Organizing EditTexts according to categories
        if(categoryExtra.equals("book"))
        {
            firstEditText.setVisibility(View.VISIBLE);
            firstEditText.setHint("Name");

            secondEditText.setVisibility(View.VISIBLE);
            secondEditText.setHint("Author");

            thirdEditText.setVisibility(View.VISIBLE);
            thirdEditText.setHint("Genre");

            fourthEditText.setVisibility(View.GONE);
        }
        else if(categoryExtra.equals("movie"))
        {
            firstEditText.setVisibility(View.VISIBLE);
            firstEditText.setHint("Title");

            secondEditText.setVisibility(View.VISIBLE);
            secondEditText.setHint("Director");

            thirdEditText.setVisibility(View.VISIBLE);
            thirdEditText.setHint("Genre");

            fourthEditText.setVisibility(View.VISIBLE);
            fourthEditText.setHint("Lead Actors");
        }
        else if(categoryExtra.equals("music"))
        {
            firstEditText.setVisibility(View.VISIBLE);
            firstEditText.setHint("Name");

            secondEditText.setVisibility(View.VISIBLE);
            secondEditText.setHint("Singer");

            thirdEditText.setVisibility(View.VISIBLE);
            thirdEditText.setHint("Genre");

            fourthEditText.setVisibility(View.VISIBLE);
            fourthEditText.setHint("Language");
        }
        else if(categoryExtra.equals("place"))
        {
            firstEditText.setVisibility(View.VISIBLE);
            firstEditText.setHint("Name");

            secondEditText.setVisibility(View.VISIBLE);
            secondEditText.setHint("Place Type");

            thirdEditText.setVisibility(View.VISIBLE);
            thirdEditText.setHint("Address");

            fourthEditText.setVisibility(View.GONE);
        }
        else if(categoryExtra.equals("tv_show"))
        {
            firstEditText.setVisibility(View.VISIBLE);
            firstEditText.setHint("Name");

            secondEditText.setVisibility(View.VISIBLE);
            secondEditText.setHint("Host");

            thirdEditText.setVisibility(View.VISIBLE);
            thirdEditText.setHint("Genre");

            fourthEditText.setVisibility(View.GONE);
        }
        else if(categoryExtra.equals("website"))
        {
            firstEditText.setVisibility(View.VISIBLE);
            firstEditText.setHint("Web Address");

            secondEditText.setVisibility(View.VISIBLE);
            secondEditText.setHint("Use");

            thirdEditText.setVisibility(View.GONE);

            fourthEditText.setVisibility(View.GONE);
        }
        else if(categoryExtra.equals("game"))
        {
            firstEditText.setVisibility(View.VISIBLE);
            firstEditText.setHint("Name");

            secondEditText.setVisibility(View.VISIBLE);
            secondEditText.setHint("Developer");

            thirdEditText.setVisibility(View.VISIBLE);
            thirdEditText.setHint("Game Type");

            fourthEditText.setVisibility(View.GONE);
        }

        if(actionExtra.equals("edit"))
            setEditTextsForEdit();
    }

    private void setEditTextsForEdit()
    {
        String first_row = predecessorIntent.getStringExtra("first_row");
        String second_row = predecessorIntent.getStringExtra("second_row");
        String third_row = predecessorIntent.getStringExtra("third_row");
        String fourth_row = predecessorIntent.getStringExtra("fourth_row");

        String cover_photo = predecessorIntent.getStringExtra("cover_photo");

        firstEditText.setText(first_row);
        secondEditText.setText(second_row);
        thirdEditText.setText(third_row);
        fourthEditText.setText(fourth_row);


        //Setting profile_picture
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.image_placeholder);

        Glide.with(mContext).applyDefaultRequestOptions(requestOptions).load(cover_photo)
                .into(cover_photoImageView);

        //Arranging cover_photoImageView
        cover_photoImageView.setBackground(null);
        cover_photoImageView.setOnClickListener(onClickListener);
        cover_photoImageView.setLongClickable(true);
        cover_photoImageView.setOnLongClickListener(onLongClickListener);
    }

    /**
     * onClickListener for imageImageView and cover_photoImageView
     *
     * Previewing image selected
     *
     */
    private View.OnClickListener onClickListener = new View.OnClickListener() {

        public void onClick(View v)
        {
            Bitmap bitmap;
            AlertDialog.Builder ImageDialog = new AlertDialog.Builder(mContext);
            ImageView showImage = new ImageView(mContext);
            v.invalidate();
            BitmapDrawable dr = (BitmapDrawable)((ImageView)v).getDrawable();
            bitmap = dr.getBitmap();

            //*********************************************************************************************************
            //MAX Width  MAX Height
            //*********************************************************************************************************

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int maxHeight = displayMetrics.heightPixels - 150;
            int maxWidth = displayMetrics.widthPixels - 150;

            int inWidth = bitmap.getWidth();
            int inHeight = bitmap.getHeight();
            int outWidth = inWidth;
            int outHeight = inHeight;
            if(inHeight > maxHeight){
                outHeight = maxHeight;
                outWidth = (int)((double)(maxHeight * inWidth) / (double)inHeight);
            }
            else if(inWidth > maxWidth){
                outWidth = maxWidth;
                outHeight = (int)((double)(maxWidth * inHeight) / (double)inWidth);
            }

            bitmap = Bitmap.createScaledBitmap(bitmap, outWidth, outHeight, false);

            inWidth = bitmap.getWidth();
            inHeight = bitmap.getHeight();
            outWidth = inWidth;
            outHeight = inHeight;

            if(inHeight > maxHeight){
                outHeight = maxHeight;
                outWidth = (int)((double)(maxHeight * inWidth) / (double)inHeight);
            }
            else if(inWidth > maxWidth){
                outWidth = maxWidth;
                outHeight = (int)((double)(maxWidth * inHeight) / (double)inWidth);
            }

            bitmap = Bitmap.createScaledBitmap(bitmap, outWidth, outHeight, false);

            //*********************************************************************************************************
            //MIN Width  MIN Height
            //*********************************************************************************************************

            DisplayMetrics displayMetrics1 = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics1);
            int minHeight = displayMetrics.heightPixels - 200;
            int minWidth = displayMetrics.widthPixels - 200;

            inWidth = bitmap.getWidth();
            inHeight = bitmap.getHeight();
            outWidth = inWidth;
            outHeight = inHeight;
            if(inHeight < minHeight){
                outHeight = minHeight;
                outWidth = (int)((double)(minHeight * inWidth) / (double)inHeight);
            }
            else if(inWidth < minWidth){
                outWidth = minWidth;
                outHeight = (int)((double)(minWidth * inHeight) / (double)inWidth);
            }

            if(outWidth <= maxWidth && outHeight <= maxHeight) {
                bitmap = Bitmap.createScaledBitmap(bitmap, outWidth, outHeight, false);
            }

            inWidth = bitmap.getWidth();
            inHeight = bitmap.getHeight();
            outWidth = inWidth;
            outHeight = inHeight;

            if(inHeight < minHeight){
                outHeight = minHeight;
                outWidth = (int)((double)(minHeight * inWidth) / (double)inHeight);
            }
            else if(inWidth < minWidth){
                outWidth = minWidth;
                outHeight = (int)((double)(minWidth * inHeight) / (double)inWidth);
            }

            if(outWidth <= maxWidth && outHeight <= maxHeight) {
                bitmap = Bitmap.createScaledBitmap(bitmap, outWidth, outHeight, false);
            }

            showImage.setImageBitmap(bitmap);
            ImageDialog.setView(showImage);
            ImageDialog.setCancelable(true);
            AlertDialog alertDialog = ImageDialog.create();
            alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            alertDialog.show();

            alertDialog.getWindow().setLayout(bitmap.getWidth(), bitmap.getHeight());

        }
    };

    /**
     * onLongClickListener for imageImageView and cover_photoImageView
     *
     * Removing selected image
     *
     */
    private View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {

        @Override
        public boolean onLongClick(final View v)
        {
            AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
            alert.setTitle(null);
            alert.setMessage("Are you sure to remove image?");
            alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                final ImageView imageView = (ImageView) v;
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    imageView.setImageDrawable(null);
                    imageView.setOnClickListener(null);
                    imageView.setBackgroundResource(R.drawable.image_background);
                    imageView.setOnLongClickListener(null);
                    dialog.dismiss();

                }
            });
            alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            });

            alert.show();

            return true;
        }

    };
}
