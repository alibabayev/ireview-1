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

public class AddWebsite extends AppCompatActivity implements View.OnKeyListener{

    private ImageView websiteUsingImage;
    private Button buttonImportWebsiteImage;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private ImageView websiteLogo;
    private Button buttonImportWebsiteLogo;
    static final int REQUEST_IMAGE_CAPTURE1 = 2;

    static final int REQUEST_GALLERY = 3;
    static final int REQUEST_GALLERY1 = 4;

    private int starNumber;
    private CheckBox postReview;
    private EditText websiteReview;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_website);

        starNumber = 0;

        /**
         * Star Initializations (filled / unfilled)
         */
        final Drawable filledStarDrawable = getResources().getDrawable(R.drawable.filled_star_icon);
        final Drawable unfilledStarDrawable = getResources().getDrawable(R.drawable.star_icon);

        /**
         * Widget initializations
         */
        final ImageView firstStar = findViewById(R.id.first_star);
        final ImageView secondStar = findViewById(R.id.second_star);
        final ImageView thirdStar = findViewById(R.id.third_star);
        final ImageView fourthStar = findViewById(R.id.fourth_star);
        final ImageView fifthStar = findViewById(R.id.fifth_star);

        websiteReview = findViewById(R.id.WebsiteReview);
        postReview = findViewById(R.id.post_review);

        websiteUsingImage = (ImageView) findViewById(R.id.WebsiteUsingImage);
        buttonImportWebsiteImage = (Button) findViewById(R.id.WebsiteLoadImageButton);

        websiteLogo = (ImageView) findViewById(R.id.WebsiteLogo);
        buttonImportWebsiteLogo = (Button) findViewById(R.id.WebsiteLoadLogoButton);

        /**
         * Star rating onClickListener's
         */
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

        /**
         * Import Website Image Button onClickListener
         */
        buttonImportWebsiteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] options = { "Take Photo", "Choose from Gallery" };

                AlertDialog.Builder builder = new AlertDialog.Builder(AddWebsite.this);
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

        /**
         * Import Website Image Button onClickListener
         */
        buttonImportWebsiteLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] options = { "Take Photo", "Choose from Gallery"};

                AlertDialog.Builder builder = new AlertDialog.Builder(AddWebsite.this);
                builder.setTitle("Import Website Cover Photo");

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
        });
    }

    /**
     * onActivityResult for getting image from gallery or taking photo
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
        {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            websiteUsingImage.setImageBitmap(imageBitmap);
            websiteUsingImage.setBackground(null);
            websiteUsingImage.setOnClickListener(listenerImage);
            websiteUsingImage.setLongClickable(true);
            websiteUsingImage.setOnLongClickListener(longClickListenerImage);
        }

        if (requestCode == REQUEST_IMAGE_CAPTURE1 && resultCode == RESULT_OK)
        {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            websiteLogo.setImageBitmap(imageBitmap);
            websiteLogo.setBackground(null);
            websiteLogo.setOnClickListener(listenerCover);
            websiteLogo.setLongClickable(true);
            websiteLogo.setOnLongClickListener(longClickListenerCover);
        }

        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK)
        {
            Uri imageUri = data.getData();

            InputStream inputStream;

            try {
                inputStream = getContentResolver().openInputStream(imageUri);

                Bitmap image = BitmapFactory.decodeStream(inputStream);

                websiteUsingImage.setImageBitmap(image);
                websiteUsingImage.setBackground(null);
                websiteUsingImage.setOnClickListener(listenerImage);
                websiteUsingImage.setLongClickable(true);
                websiteUsingImage.setOnLongClickListener(longClickListenerImage);

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

                websiteLogo.setImageBitmap(image);
                websiteLogo.setBackground(null);
                websiteLogo.setOnClickListener(listenerCover);
                websiteLogo.setLongClickable(true);
                websiteLogo.setOnLongClickListener(longClickListenerCover);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Enter is pressed
     *
     * @param view
     * @param i
     * @param keyEvent
     * @return
     */
    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if(i == keyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
            try {
                addWebsite(view);
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
            AlertDialog.Builder ImageDialog = new AlertDialog.Builder(AddWebsite.this);
            ImageView showImage = new ImageView(AddWebsite.this);
            websiteUsingImage.invalidate();
            BitmapDrawable dr = (BitmapDrawable)((ImageView)websiteUsingImage).getDrawable();
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
            AlertDialog.Builder ImageDialog = new AlertDialog.Builder(AddWebsite.this);
            ImageView showImage = new ImageView(AddWebsite.this);
            websiteLogo.invalidate();
            BitmapDrawable dr = (BitmapDrawable)((ImageView)websiteLogo).getDrawable();
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
                    AddWebsite.this);
            alert.setTitle(null);
            alert.setMessage("Are you sure to remove image?");
            alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    websiteUsingImage.setImageBitmap(null);
                    websiteUsingImage.setOnClickListener(null);
                    websiteUsingImage.setBackgroundResource(R.drawable.u_website);
                    websiteUsingImage.setOnLongClickListener(null);
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
                    AddWebsite.this);
            alert.setTitle(null);
            alert.setMessage("Are you sure to delete website logo?");
            alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    websiteLogo.setImageBitmap(null);
                    websiteLogo.setOnClickListener(null);
                    websiteLogo.setBackgroundResource(R.drawable.website);
                    websiteLogo.setOnLongClickListener(null);
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

    public void addWebsite(View view) throws ParseException {
        EditText name = (EditText) findViewById(R.id.WebsiteName);
        EditText use = (EditText) findViewById(R.id.WebsiteUse);

        if( TextUtils.isEmpty(name.getText())){
            name.setError( "Website name is required!" );
        }
        else if( TextUtils.isEmpty(use.getText())){
            use.setError( "Use is required!" );
        }
        else if (postReview.isChecked() && starNumber == 0)
        {
            Toast.makeText(getApplicationContext(), "You need to rate the item by stars to post!", Toast.LENGTH_SHORT).show();
        }
        else {

            Website newWebsite = new Website();

            final ParseObject website = new ParseObject("Website");

            website.put("name", name.getText().toString());
            website.put("use", use.getText().toString());

            if(websiteUsingImage.getDrawable() != null)
            {
                System.out.println("Image not NULL");
                websiteUsingImage.invalidate();
                BitmapDrawable dr = (BitmapDrawable)((ImageView)websiteUsingImage).getDrawable();
                Bitmap bitmap = dr.getBitmap();

                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                int bitmapSize = bitmap.getRowBytes();

                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);

                byte[] byteArray = stream.toByteArray();

                ParseFile fileImage = new ParseFile("image.jpg", byteArray);

                website.put("image", fileImage);
            }

            if(websiteLogo.getDrawable() != null)
            {
                System.out.println("Cover not NULL");
                websiteLogo.invalidate();
                BitmapDrawable dr = (BitmapDrawable)((ImageView)websiteLogo).getDrawable();
                Bitmap bitmap = dr.getBitmap();

                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                int bitmapSize = bitmap.getRowBytes();

                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);

                byte[] byteArray = stream.toByteArray();

                ParseFile fileCover = new ParseFile("cover.jpg", byteArray);

                website.put("cover", fileCover);
            }

            website.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException ex) {
                    if (ex == null)
                    {
                        Log.i("Parse Result", "Successful!");

                        String websiteId = website.getObjectId();

                        ArrayList<String> websites = (ArrayList<String>) ParseUser.getCurrentUser().get("Websites");

                        websites.add(websiteId);

                        final ParseUser currentUser = ParseUser.getCurrentUser();

                        currentUser.put("Websites", websites);
                        currentUser.saveInBackground();


                        final ParseObject item = new ParseObject("Items");
                        item.put("Title", website.get("name"));
                        item.put("Category", "Website");
                        item.put("Id", website.getObjectId());

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
                                    post.put("userReview", websiteReview.getText().toString());
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

            website.save();

            String websiteId = website.getObjectId();

            ArrayList<String> websites = (ArrayList<String>) ParseUser.getCurrentUser().get("Websites");

            websites.add(websiteId);

            System.out.println("websiteId:  " + websiteId + "    websites.size()   " + websites.size());

            ParseUser.getCurrentUser().put("Websites", website);
            ParseUser.getCurrentUser().save();

            */

            Intent intent = new Intent(getApplicationContext(), Homepage.class);
            startActivity(intent);
        }
    }
}
