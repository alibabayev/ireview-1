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

public class AddMovie extends AppCompatActivity implements View.OnKeyListener{

    private ImageView movieWatchingImage;
    private Button buttonImportMovieImage;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private ImageView movieCoverPhoto;
    private Button buttonImportMovieCoverPhoto;
    static final int REQUEST_IMAGE_CAPTURE1 = 2;

    static final int REQUEST_GALLERY = 3;
    static final int REQUEST_GALLERY1 = 4;

    private int starNumber;
    private CheckBox postReview;
    private EditText movieReview;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_movie);

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

        movieReview = findViewById(R.id.MovieReview);

        postReview = findViewById(R.id.post_review);

        movieWatchingImage = (ImageView) findViewById(R.id.MovieWatchingImage);
        buttonImportMovieImage = (Button) findViewById(R.id.MovieLoadImageButton);

        movieCoverPhoto = (ImageView) findViewById(R.id.MovieCoverPhoto);
        buttonImportMovieCoverPhoto = (Button) findViewById(R.id.MovieLoadCoverPhotoButton);

        buttonImportMovieImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] options = { "Take Photo", "Choose from Gallery" };

                AlertDialog.Builder builder = new AlertDialog.Builder(AddMovie.this);
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

        buttonImportMovieCoverPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] options = { "Take Photo", "Choose from Gallery"};

                AlertDialog.Builder builder = new AlertDialog.Builder(AddMovie.this);
                builder.setTitle("Import Movie Cover Photo");

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
            movieWatchingImage.setImageBitmap(imageBitmap);
            movieWatchingImage.setBackground(null);
            movieWatchingImage.setOnClickListener(listenerImage);
            movieWatchingImage.setLongClickable(true);
            movieWatchingImage.setOnLongClickListener(longClickListenerImage);
        }

        if (requestCode == REQUEST_IMAGE_CAPTURE1 && resultCode == RESULT_OK)
        {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            movieCoverPhoto.setImageBitmap(imageBitmap);
            movieCoverPhoto.setBackground(null);
            movieCoverPhoto.setOnClickListener(listenerCover);
            movieCoverPhoto.setLongClickable(true);
            movieCoverPhoto.setOnLongClickListener(longClickListenerCover);
        }

        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK)
        {
            Uri imageUri = data.getData();

            InputStream inputStream;

            try {
                inputStream = getContentResolver().openInputStream(imageUri);

                Bitmap image = BitmapFactory.decodeStream(inputStream);

                movieWatchingImage.setImageBitmap(image);
                movieWatchingImage.setBackground(null);
                movieWatchingImage.setOnClickListener(listenerImage);
                movieWatchingImage.setLongClickable(true);
                movieWatchingImage.setOnLongClickListener(longClickListenerImage);

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

                movieCoverPhoto.setImageBitmap(image);
                movieCoverPhoto.setBackground(null);
                movieCoverPhoto.setOnClickListener(listenerCover);
                movieCoverPhoto.setLongClickable(true);
                movieCoverPhoto.setOnLongClickListener(longClickListenerCover);

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
                addMovie(view);
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
            AlertDialog.Builder ImageDialog = new AlertDialog.Builder(AddMovie.this);
            ImageView showImage = new ImageView(AddMovie.this);
            movieWatchingImage.invalidate();
            BitmapDrawable dr = (BitmapDrawable)((ImageView)movieWatchingImage).getDrawable();
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
            AlertDialog.Builder ImageDialog = new AlertDialog.Builder(AddMovie.this);
            ImageView showImage = new ImageView(AddMovie.this);
            movieCoverPhoto.invalidate();
            BitmapDrawable dr = (BitmapDrawable)((ImageView)movieCoverPhoto).getDrawable();
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
                    AddMovie.this);
            alert.setTitle(null);
            alert.setMessage("Are you sure to remove image?");
            alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    movieWatchingImage.setImageBitmap(null);
                    movieWatchingImage.setOnClickListener(null);
                    movieWatchingImage.setBackgroundResource(R.drawable.w_movie);
                    movieWatchingImage.setOnLongClickListener(null);
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
                    AddMovie.this);
            alert.setTitle(null);
            alert.setMessage("Are you sure to delete cover photo?");
            alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    movieCoverPhoto.setImageBitmap(null);
                    movieCoverPhoto.setOnClickListener(null);
                    movieCoverPhoto.setBackgroundResource(R.drawable.movie);
                    movieCoverPhoto.setOnLongClickListener(null);
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

    public void addMovie(View view) throws ParseException
    {

        EditText title = (EditText) findViewById(R.id.MovieTitle);
        EditText director = (EditText) findViewById(R.id.MovieDirector);
        EditText leadActors = (EditText) findViewById(R.id.MovieLeadActors);
        EditText genre = (EditText) findViewById(R.id.MovieGenre);

        if( TextUtils.isEmpty(title.getText())){
            title.setError( "Movie title is required!" );
        }
        else if( TextUtils.isEmpty(genre.getText())){
            genre.setError( "Genre is required!" );
        }
        else if (postReview.isChecked() && starNumber == 0)
        {
            Toast.makeText(getApplicationContext(), "You need to rate the item by stars to post!", Toast.LENGTH_SHORT).show();
        }
        else {

            Movie newMovie = new Movie();

            final ParseObject movie = new ParseObject("Movie");

            movie.put("title", title.getText().toString());
            movie.put("genre", genre.getText().toString());

            if (director != null) {
                movie.put("director", director.getText().toString());
            }
            else
                movie.put("director", null);

            if (leadActors != null) {
                movie.put("leadActors", leadActors.getText().toString());
            }
            else
                movie.put("leadActors", null);

            if(movieWatchingImage.getDrawable() != null)
            {
                System.out.println("Image not NULL");
                movieWatchingImage.invalidate();
                BitmapDrawable dr = (BitmapDrawable)((ImageView)movieWatchingImage).getDrawable();
                Bitmap bitmap = dr.getBitmap();

                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                int bitmapSize = bitmap.getRowBytes();

                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);

                byte[] byteArray = stream.toByteArray();

                ParseFile fileImage = new ParseFile("image.jpg", byteArray);

                movie.put("image", fileImage);
            }

            if(movieCoverPhoto.getDrawable() != null)
            {
                System.out.println("Cover not NULL");
                movieCoverPhoto.invalidate();
                BitmapDrawable dr = (BitmapDrawable)((ImageView)movieCoverPhoto).getDrawable();
                Bitmap bitmap = dr.getBitmap();

                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                int bitmapSize = bitmap.getRowBytes();

                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                byte[] byteArray = stream.toByteArray();

                ParseFile fileCover = new ParseFile("cover.jpg", byteArray);

                movie.put("cover", fileCover);
            }

            movie.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException ex) {
                    if (ex == null)
                    {
                        Log.i("Parse Result", "Successful!");

                        String movieId = movie.getObjectId();

                        ArrayList<String> movies = (ArrayList<String>) ParseUser.getCurrentUser().get("Movies");

                        movies.add(movieId);

                        final ParseUser currentUser = ParseUser.getCurrentUser();

                        currentUser.put("Movies", movies);
                        currentUser.saveInBackground();


                        final ParseObject item = new ParseObject("Items");
                        item.put("Title", movie.get("title"));
                        if(movie.get("director") != null)
                        item.put("Publisher", movie.get("director"));
                        item.put("Category", "Movie");
                        item.put("Id", movie.getObjectId());

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
                                    post.put("userReview", movieReview.getText().toString());
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

            /*movie.save();

            String movieId = movie.getObjectId();

            ArrayList<String> movies = (ArrayList<String>) ParseUser.getCurrentUser().get("Movies");

            movies.add(movieId);

            System.out.println("movieId:  " + movieId + "    movies.size()   " + movies.size());

            ParseUser.getCurrentUser().put("Movies", movies);
            ParseUser.getCurrentUser().save();
            */

            Intent intent = new Intent(getApplicationContext(), Homepage.class);
            startActivity(intent);
        }
    }
}
