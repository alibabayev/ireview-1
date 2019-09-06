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
import com.sscompany.ireview.Elements.Music;
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

public class AddMusic extends AppCompatActivity implements View.OnKeyListener{

    private ImageView musicListeningImage;
    private Button buttonImportMusicImage;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private ImageView musicCoverPhoto;
    private Button buttonImportMusicCoverPhoto;
    static final int REQUEST_IMAGE_CAPTURE1 = 2;

    static final int REQUEST_GALLERY = 3;
    static final int REQUEST_GALLERY1 = 4;

    private int starNumber;
    private CheckBox postReview;
    private EditText musicReview;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_music);

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

        musicReview = findViewById(R.id.MusicReview);

        postReview = findViewById(R.id.post_review);

        musicListeningImage = (ImageView) findViewById(R.id.MusicListeningImage);
        buttonImportMusicImage = (Button) findViewById(R.id.MusicLoadImageButton);

        musicCoverPhoto = (ImageView) findViewById(R.id.MusicCoverPhoto);
        buttonImportMusicCoverPhoto = (Button) findViewById(R.id.MusicLoadCoverPhotoButton);

        buttonImportMusicImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] options = { "Take Photo", "Choose from Gallery" };

                AlertDialog.Builder builder = new AlertDialog.Builder(AddMusic.this);
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

        buttonImportMusicCoverPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] options = { "Take Photo", "Choose from Gallery"};

                AlertDialog.Builder builder = new AlertDialog.Builder(AddMusic.this);
                builder.setTitle("Import Music Cover Photo");

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
            musicListeningImage.setImageBitmap(imageBitmap);
            musicListeningImage.setBackground(null);
            musicListeningImage.setOnClickListener(listenerImage);
            musicListeningImage.setLongClickable(true);
            musicListeningImage.setOnLongClickListener(longClickListenerImage);
        }

        if (requestCode == REQUEST_IMAGE_CAPTURE1 && resultCode == RESULT_OK)
        {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            musicCoverPhoto.setImageBitmap(imageBitmap);
            musicCoverPhoto.setBackground(null);
            musicCoverPhoto.setOnClickListener(listenerCover);
            musicCoverPhoto.setLongClickable(true);
            musicCoverPhoto.setOnLongClickListener(longClickListenerCover);
        }

        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK)
        {
            Uri imageUri = data.getData();

            InputStream inputStream;

            try {
                inputStream = getContentResolver().openInputStream(imageUri);

                Bitmap image = BitmapFactory.decodeStream(inputStream);

                musicListeningImage.setImageBitmap(image);
                musicListeningImage.setBackground(null);
                musicListeningImage.setOnClickListener(listenerImage);
                musicListeningImage.setLongClickable(true);
                musicListeningImage.setOnLongClickListener(longClickListenerImage);

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

                musicCoverPhoto.setImageBitmap(image);
                musicCoverPhoto.setBackground(null);
                musicCoverPhoto.setOnClickListener(listenerCover);
                musicCoverPhoto.setLongClickable(true);
                musicCoverPhoto.setOnLongClickListener(longClickListenerCover);

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
                addMusic(view);
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
            AlertDialog.Builder ImageDialog = new AlertDialog.Builder(AddMusic.this);
            ImageView showImage = new ImageView(AddMusic.this);
            musicListeningImage.invalidate();
            BitmapDrawable dr = (BitmapDrawable)((ImageView)musicListeningImage).getDrawable();
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
            AlertDialog.Builder ImageDialog = new AlertDialog.Builder(AddMusic.this);
            ImageView showImage = new ImageView(AddMusic.this);
            musicCoverPhoto.invalidate();
            BitmapDrawable dr = (BitmapDrawable)((ImageView)musicCoverPhoto).getDrawable();
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
                    AddMusic.this);
            alert.setTitle(null);
            alert.setMessage("Are you sure to remove image?");
            alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    musicListeningImage.setImageBitmap(null);
                    musicListeningImage.setOnClickListener(null);
                    musicListeningImage.setBackgroundResource(R.drawable.l_music);
                    musicListeningImage.setOnLongClickListener(null);
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
                    AddMusic.this);
            alert.setTitle(null);
            alert.setMessage("Are you sure to delete cover photo?");
            alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    musicCoverPhoto.setImageBitmap(null);
                    musicCoverPhoto.setOnClickListener(null);
                    musicCoverPhoto.setBackgroundResource(R.drawable.music);
                    musicCoverPhoto.setOnLongClickListener(null);
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

    public void addMusic(View view) throws ParseException {
        /*

        //More Details

        EditText title = (EditText) findViewById(R.id.MusicTitle);
        EditText album = (EditText) findViewById(R.id.MusicAlbum);
        EditText artist = (EditText) findViewById(R.id.MusicArtist);
        EditText genre = (EditText) findViewById(R.id.MusicGenre);
        EditText duration = (EditText) findViewById(R.id.MusicDuration);
        EditText language = (EditText) findViewById(R.id.MusicLanguage);

        Music newMusic = new Music(title.getText().toString(), album.getText().toString(), artist.getText().toString(), genre.getText().toString(), duration.getText().toString(), language.getText().toString());

        ParseObject music = new ParseObject("Music");

        music.put("title", title.getText().toString());
        music.put("album", album.getText().toString());
        music.put("artist", artist.getText().toString());
        music.put("genre", genre.getText().toString());
        music.put("duration", duration.getText().toString());
        music.put("language", language.getText().toString());*/

        EditText title = (EditText) findViewById(R.id.MusicTitle);
        EditText artist = (EditText) findViewById(R.id.MusicArtist);
        EditText genre = (EditText) findViewById(R.id.MusicGenre);
        EditText language = (EditText) findViewById(R.id.MusicLanguage);

        if( TextUtils.isEmpty(title.getText())){
            title.setError( "Music title is required!" );
        }
        else if( TextUtils.isEmpty(artist.getText())){
            artist.setError( "Artist name is required!" );
        }
        else if( TextUtils.isEmpty(genre.getText())){
            genre.setError( "Genre is required!" );
        }
        else if( TextUtils.isEmpty(language.getText())){
            language.setError( "Language is required!" );
        }
        else if (postReview.isChecked() && starNumber == 0)
        {
            Toast.makeText(getApplicationContext(), "You need to rate the item by stars to post!", Toast.LENGTH_SHORT).show();
        }
        else {

            Music newMusic = new Music();

            final ParseObject music = new ParseObject("Music");

            music.put("title", title.getText().toString());
            music.put("artist", artist.getText().toString());
            music.put("genre", genre.getText().toString());
            music.put("language", language.getText().toString());

            if(musicListeningImage.getDrawable() != null)
            {
                System.out.println("Image not NULL");
                musicListeningImage.invalidate();
                BitmapDrawable dr = (BitmapDrawable)((ImageView)musicListeningImage).getDrawable();
                Bitmap bitmap = dr.getBitmap();

                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                int bitmapSize = bitmap.getRowBytes();

                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                byte[] byteArray = stream.toByteArray();

                ParseFile fileImage = new ParseFile("image.jpg", byteArray);

                music.put("image", fileImage);
            }

            if(musicCoverPhoto.getDrawable() != null)
            {
                System.out.println("Cover not NULL");
                musicCoverPhoto.invalidate();
                BitmapDrawable dr = (BitmapDrawable)((ImageView)musicCoverPhoto).getDrawable();
                Bitmap bitmap = dr.getBitmap();

                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                int bitmapSize = bitmap.getRowBytes();

                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                byte[] byteArray = stream.toByteArray();

                ParseFile fileCover = new ParseFile("cover.jpg", byteArray);

                music.put("cover", fileCover);
            }

            music.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException ex) {
                    if (ex == null)
                    {
                        Log.i("Parse Result", "Successful!");

                        String musicId = music.getObjectId();

                        ArrayList<String> musics = (ArrayList<String>) ParseUser.getCurrentUser().get("Musics");

                        musics.add(musicId);

                        final ParseUser currentUser = ParseUser.getCurrentUser();

                        currentUser.put("Musics", musics);
                        currentUser.saveInBackground();


                        final ParseObject item = new ParseObject("Items");
                        item.put("Title", music.get("title"));
                        item.put("Publisher", music.get("artist"));
                        item.put("Category", "Music");
                        item.put("Id", music.getObjectId());

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
                                    post.put("userReview", musicReview.getText().toString());
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
            music.save();

            String musicId = music.getObjectId();

            ArrayList<String> musics = (ArrayList<String>) ParseUser.getCurrentUser().get("Places");

            musics.add(musicId);

            System.out.println("musicId:  " + musicId + "    musics.size()   " + musics.size());

            ParseUser.getCurrentUser().put("Musics", musics);
            ParseUser.getCurrentUser().save();
            */

            Intent intent = new Intent(getApplicationContext(), Homepage.class);
            startActivity(intent);
        }
    }
}
