package com.sscompany.ireview.AddElementScreens;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.sscompany.ireview.*;
import com.sscompany.ireview.Elements.*;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddTVShow extends AppCompatActivity implements View.OnKeyListener{

    private ImageView tvshowWatchingImage;
    private Button buttonImportTVShowImage;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private ImageView tvShowCoverPhoto;
    private Button buttonImportTVShowCoverPhoto;
    static final int REQUEST_IMAGE_CAPTURE1 = 2;

    static final int REQUEST_GALLERY = 3;
    static final int REQUEST_GALLERY1 = 4;

    private int starNumber;
    private CheckBox postReview;
    private EditText tvShowReview;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_tv_show);

        starNumber = 0;

        final ImageView firstStar = findViewById(R.id.first_star);
        final ImageView secondStar = findViewById(R.id.second_star);
        final ImageView thirdStar = findViewById(R.id.third_star);
        final ImageView fourthStar = findViewById(R.id.fourth_star);
        final ImageView fifthStar = findViewById(R.id.fifth_star);

        final Drawable filledStarDrawable = getResources().getDrawable(R.drawable.filled_star_icon);
        final Drawable unfilledStarDrawable = getResources().getDrawable(R.drawable.star_icon);

        firstStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstStar.setBackgroundResource(0);
                firstStar.setImageDrawable(filledStarDrawable);

                secondStar.setBackgroundResource(0);
                secondStar.setImageDrawable(unfilledStarDrawable);

                thirdStar.setBackgroundResource(0);
                thirdStar.setImageDrawable(unfilledStarDrawable);

                fourthStar.setBackgroundResource(0);
                fourthStar.setImageDrawable(unfilledStarDrawable);

                fifthStar.setBackgroundResource(0);
                fifthStar.setImageDrawable(unfilledStarDrawable);
                starNumber = 1;
            }
        });

        secondStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstStar.setBackgroundResource(0);
                firstStar.setImageDrawable(filledStarDrawable);

                secondStar.setBackgroundResource(0);
                secondStar.setImageDrawable(filledStarDrawable);

                thirdStar.setBackgroundResource(0);
                thirdStar.setImageDrawable(unfilledStarDrawable);

                fourthStar.setBackgroundResource(0);
                fourthStar.setImageDrawable(unfilledStarDrawable);

                fifthStar.setBackgroundResource(0);
                fifthStar.setImageDrawable(unfilledStarDrawable);

                starNumber = 2;
            }
        });

        thirdStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstStar.setBackgroundResource(0);
                firstStar.setImageDrawable(filledStarDrawable);

                secondStar.setBackgroundResource(0);
                secondStar.setImageDrawable(filledStarDrawable);

                thirdStar.setBackgroundResource(0);
                thirdStar.setImageDrawable(filledStarDrawable);

                fourthStar.setBackgroundResource(0);
                fourthStar.setImageDrawable(unfilledStarDrawable);

                fifthStar.setBackgroundResource(0);
                fifthStar.setImageDrawable(unfilledStarDrawable);

                starNumber = 3;
            }
        });

        fourthStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstStar.setBackgroundResource(0);
                firstStar.setImageDrawable(filledStarDrawable);

                secondStar.setBackgroundResource(0);
                secondStar.setImageDrawable(filledStarDrawable);

                thirdStar.setBackgroundResource(0);
                thirdStar.setImageDrawable(filledStarDrawable);

                fourthStar.setBackgroundResource(0);
                fourthStar.setImageDrawable(filledStarDrawable);

                fifthStar.setBackgroundResource(0);
                fifthStar.setImageDrawable(unfilledStarDrawable);

                starNumber = 4;
            }
        });

        fifthStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstStar.setBackgroundResource(0);
                firstStar.setImageDrawable(filledStarDrawable);

                secondStar.setBackgroundResource(0);
                secondStar.setImageDrawable(filledStarDrawable);

                thirdStar.setBackgroundResource(0);
                thirdStar.setImageDrawable(filledStarDrawable);

                fourthStar.setBackgroundResource(0);
                fourthStar.setImageDrawable(filledStarDrawable);

                fifthStar.setBackgroundResource(0);
                fifthStar.setImageDrawable(filledStarDrawable);

                starNumber = 5;
            }
        });

        tvShowReview = findViewById(R.id.TVShowReview);

        postReview = findViewById(R.id.post_review);

        tvshowWatchingImage = (ImageView) findViewById(R.id.TVShowWatchingImage);
        buttonImportTVShowImage = (Button) findViewById(R.id.TVShowLoadImageButton);

        tvShowCoverPhoto = (ImageView) findViewById(R.id.TVShowCoverPhoto);
        buttonImportTVShowCoverPhoto = (Button) findViewById(R.id.TVShowLoadCoverPhotoButton);

        buttonImportTVShowImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] options = { "Take Photo", "Choose from Gallery" };

                AlertDialog.Builder builder = new AlertDialog.Builder(AddTVShow.this);
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

                /*
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
                */
            }
        });

        buttonImportTVShowCoverPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] options = { "Take Photo", "Choose from Gallery"};

                AlertDialog.Builder builder = new AlertDialog.Builder(AddTVShow.this);
                builder.setTitle("Import TV Show Cover Photo");

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

                /*
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE1);
                }
                */
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
        {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            tvshowWatchingImage.setImageBitmap(imageBitmap);
            tvshowWatchingImage.setBackground(null);
            tvshowWatchingImage.setOnClickListener(listenerImage);
            tvshowWatchingImage.setLongClickable(true);
            tvshowWatchingImage.setOnLongClickListener(longClickListenerImage);
        }

        if (requestCode == REQUEST_IMAGE_CAPTURE1 && resultCode == RESULT_OK)
        {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            tvShowCoverPhoto.setImageBitmap(imageBitmap);
            tvShowCoverPhoto.setBackground(null);
            tvShowCoverPhoto.setOnClickListener(listenerCover);
            tvShowCoverPhoto.setLongClickable(true);
            tvShowCoverPhoto.setOnLongClickListener(longClickListenerCover);
        }

        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK)
        {
            Uri imageUri = data.getData();

            InputStream inputStream;

            try {
                inputStream = getContentResolver().openInputStream(imageUri);

                Bitmap image = BitmapFactory.decodeStream(inputStream);

                tvshowWatchingImage.setImageBitmap(image);
                tvshowWatchingImage.setBackground(null);
                tvshowWatchingImage.setOnClickListener(listenerImage);
                tvshowWatchingImage.setLongClickable(true);
                tvshowWatchingImage.setOnLongClickListener(longClickListenerImage);

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

                tvShowCoverPhoto.setImageBitmap(image);
                tvShowCoverPhoto.setBackground(null);
                tvShowCoverPhoto.setOnClickListener(listenerCover);
                tvShowCoverPhoto.setLongClickable(true);
                tvShowCoverPhoto.setOnLongClickListener(longClickListenerCover);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if(i == keyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
            try {
                addTVShow(view);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private View.OnClickListener listenerImage = new View.OnClickListener()
    {

        @Override
        public void onClick(View v)
        {
            Bitmap bitmap;
            AlertDialog.Builder ImageDialog = new AlertDialog.Builder(AddTVShow.this);
            ImageView showImage = new ImageView(AddTVShow.this);
            tvshowWatchingImage.invalidate();
            BitmapDrawable dr = (BitmapDrawable)((ImageView)tvshowWatchingImage).getDrawable();
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

    private View.OnClickListener listenerCover = new View.OnClickListener() {

        public void onClick(View v)
        {
            Bitmap bitmap;
            AlertDialog.Builder ImageDialog = new AlertDialog.Builder(AddTVShow.this);
            ImageView showImage = new ImageView(AddTVShow.this);
            tvShowCoverPhoto.invalidate();
            BitmapDrawable dr = (BitmapDrawable)((ImageView)tvShowCoverPhoto).getDrawable();
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

    private View.OnLongClickListener longClickListenerImage = new View.OnLongClickListener() {

        @Override
        public boolean onLongClick(View v)
        {
            AlertDialog.Builder alert = new AlertDialog.Builder(
                    AddTVShow.this);
            alert.setTitle(null);
            alert.setMessage("Are you sure to remove image?");
            alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    tvshowWatchingImage.setImageBitmap(null);
                    tvshowWatchingImage.setOnClickListener(null);
                    tvshowWatchingImage.setBackgroundResource(R.drawable.w_tvshow);
                    tvshowWatchingImage.setOnLongClickListener(null);
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

    private View.OnLongClickListener longClickListenerCover = new View.OnLongClickListener() {

        @Override
        public boolean onLongClick(View v)
        {
            AlertDialog.Builder alert = new AlertDialog.Builder(
                    AddTVShow.this);
            alert.setTitle(null);
            alert.setMessage("Are you sure to delete cover photo?");
            alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    tvShowCoverPhoto.setImageBitmap(null);
                    tvShowCoverPhoto.setOnClickListener(null);
                    tvShowCoverPhoto.setBackgroundResource(R.drawable.tvshow);
                    tvShowCoverPhoto.setOnLongClickListener(null);
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

    public void addTVShow(View view) throws ParseException
    {
        /*

        //More Details

        EditText title = (EditText) findViewById(R.id.TVShowTitle);
        EditText director = (EditText) findViewById(R.id.TVShowDirector);
        EditText host = (EditText) findViewById(R.id.TVShowHost);
        EditText year = (EditText) findViewById(R.id.TVShowYear);
        EditText country = (EditText) findViewById(R.id.TVShowCountry);
        EditText genre = (EditText) findViewById(R.id.TVShowGenre);
        EditText episodeDuration = (EditText) findViewById(R.id.TVShowEpisodeDuration);
        EditText seasons = (EditText) findViewById(R.id.TVShowSeasons);
        EditText favoriteEpisode = (EditText) findViewById(R.id.TVShowFavoriteEpisode);

        TVShow newTVShow = new TVShow(title.getText().toString(), director.getText().toString(), host.getText().toString(), Integer.parseInt(year.getText().toString()), country.getText().toString(), genre.getText().toString(), episodeDuration.getText().toString(), Integer.parseInt(seasons.getText().toString()), favoriteEpisode.getText().toString());

        ParseObject tvshow = new ParseObject("TVShow");

        tvshow.put("title", title.getText().toString());
        tvshow.put("director", director.getText().toString());
        tvshow.put("host", host.getText().toString());
        tvshow.put("year", Integer.parseInt(year.getText().toString()));
        tvshow.put("country", country.getText().toString());
        tvshow.put("genre", genre.getText().toString());
        tvshow.put("episodeDuration", episodeDuration.getText().toString());
        tvshow.put("seasons", Integer.parseInt(seasons.getText().toString()));
        tvshow.put("favoriteEpisode", favoriteEpisode.getText().toString());*/

        EditText title = (EditText) findViewById(R.id.TVShowTitle);
        EditText host = (EditText) findViewById(R.id.TVShowHost);
        EditText genre = (EditText) findViewById(R.id.TVShowGenre);
        EditText favoriteEpisode = (EditText) findViewById(R.id.TVShowFavoriteEpisode);

        if( TextUtils.isEmpty(title.getText())){
            title.setError( "TV Show title is required!" );
        }
        else if( TextUtils.isEmpty(genre.getText())){
            genre.setError( "Genre is required!" );
        }
        else if (postReview.isChecked() && starNumber == 0)
        {
            Toast.makeText(getApplicationContext(), "You need to rate the item by stars to post!", Toast.LENGTH_SHORT).show();
        }
        else {

            TVShow newTVShow = new TVShow(title.getText().toString(), host.getText().toString(), genre.getText().toString(), favoriteEpisode.getText().toString());

            final ParseObject tvshow = new ParseObject("TVShow");

            tvshow.put("title", title.getText().toString());
            tvshow.put("genre", genre.getText().toString());

            if(host != null) {
                tvshow.put("host", host.getText().toString());
            }
            else
                tvshow.put("host", null);


            if(favoriteEpisode != null) {
                tvshow.put("favoriteEpisode", favoriteEpisode.getText().toString());
            }
            else
                tvshow.put("favoriteEpisode", null);

            if(tvshowWatchingImage.getDrawable() != null)
            {
                System.out.println("Image not NULL");
                tvshowWatchingImage.invalidate();
                BitmapDrawable dr = (BitmapDrawable)((ImageView)tvshowWatchingImage).getDrawable();
                Bitmap bitmap = dr.getBitmap();

                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                int bitmapSize = bitmap.getRowBytes();

                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);

                byte[] byteArray = stream.toByteArray();

                ParseFile fileImage = new ParseFile("image.jpg", byteArray);

                tvshow.put("image", fileImage);
            }

            if(tvShowCoverPhoto.getDrawable() != null)
            {
                System.out.println("Cover not NULL");
                tvShowCoverPhoto.invalidate();
                BitmapDrawable dr = (BitmapDrawable)((ImageView)tvShowCoverPhoto).getDrawable();
                Bitmap bitmap = dr.getBitmap();

                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                int bitmapSize = bitmap.getRowBytes();

                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);

                byte[] byteArray = stream.toByteArray();

                ParseFile fileCover = new ParseFile("cover.jpg", byteArray);

                tvshow.put("cover", fileCover);
            }

            tvshow.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException ex) {
                    if (ex == null)
                    {
                        Log.i("Parse Result", "Successful!");

                        String tvshowId = tvshow.getObjectId();

                        ArrayList<String> tvshows = (ArrayList<String>) ParseUser.getCurrentUser().get("TVShows");

                        tvshows.add(tvshowId);

                        final ParseUser currentUser = ParseUser.getCurrentUser();

                        currentUser.put("TVShows", tvshows);
                        currentUser.saveInBackground();


                        final ParseObject item = new ParseObject("Items");
                        item.put("Title", tvshow.get("title"));
                        if(tvshow.get("host") != null)
                            item.put("Publisher", tvshow.get("host"));
                        item.put("Category", "TVShow");
                        item.put("Id", tvshow.getObjectId());

                        item.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if(postReview.isChecked())
                                {
                                    String time = DateFormat.getDateTimeInstance().format(new Date());
                                    ParseObject post = new ParseObject("Post");
                                    ArrayList<String> peopleLikedIds = new ArrayList<>();
                                    post.put("itemId", item.getObjectId());
                                    post.put("likeCount", 0);
                                    post.put("peopleLiked", peopleLikedIds);
                                    post.put("postingUserId", currentUser.getObjectId());
                                    post.put("postingTime", time);
                                    post.put("userReview", tvShowReview.getText().toString());
                                    post.put("stars", starNumber);

                                    post.saveInBackground();
                                }
                            }
                        });

                    } else {
                        Log.i("Parse Result", "Failed" + ex.toString());
                    }
                }
            });

            /*

            tvshow.save();

            String tvshowId = tvshow.getObjectId();

            ArrayList<String> tvshows = (ArrayList<String>) ParseUser.getCurrentUser().get("TVShows");

            tvshows.add(tvshowId);

            ParseUser.getCurrentUser().put("TVShows", tvshows);
            ParseUser.getCurrentUser().save();

            */

            Intent intent = new Intent(getApplicationContext(), Homepage.class);
            startActivity(intent);
        }
    }
}
