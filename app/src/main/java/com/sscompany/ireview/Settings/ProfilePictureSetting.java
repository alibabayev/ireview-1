package com.sscompany.ireview.Settings;

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
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sscompany.ireview.Models.ImageManagerClass;
import com.sscompany.ireview.Models.UserAccountSettings;
import com.sscompany.ireview.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ProfilePictureSetting extends AppCompatActivity
{
    private static final String TAG = "ProfilePictureSetting";

    private ImageView profile;
    private Button changeProfile;

    private String user_id;
    private String profile_picture_url;

    private Context mContext;

    private static AlertDialog alertDialog;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_GALLERY = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_picture_setting);

        mContext = ProfilePictureSetting.this;

        //Initializing widgets
        profile = findViewById(R.id.profile_picture);
        changeProfile = findViewById(R.id.change_profile_button);

        //Getting user_id
        user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //Getting current profile picture url from user_account_settings class
        FirebaseDatabase.getInstance().getReference()
                .child("user_account_settings")
                .child(user_id)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        UserAccountSettings userAccountSettings = dataSnapshot.getValue(UserAccountSettings.class);

                        profile_picture_url = userAccountSettings.getProfile_photo();

                        /*
                        RequestOptions requestOptions = new RequestOptions();
                        requestOptions.placeholder(R.drawable.image_placeholder);

                        Glide.with(mContext).applyDefaultRequestOptions(requestOptions).load(profile_picture_url)
                                .into(profile);
                        */

                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        try {
                            URL url = new URL(profile_picture_url);
                            Drawable d = new BitmapDrawable(getResources(), BitmapFactory.decodeStream((InputStream)url.getContent()));
                            profile.setImageDrawable(d);
                        } catch (IOException e) {
                            Log.e(TAG, e.getMessage());
                        }

                        profile.setOnClickListener(listenerImage);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


        alertDialog = null;

        changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                final CharSequence[] options = { "Take Photo", "Choose from Gallery" };

                AlertDialog.Builder builder = new AlertDialog.Builder(ProfilePictureSetting.this);
                builder.setTitle("Change Profile Picture");

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
        });
    }

    public void removeProfilePicture(View v)
    {
        if(alertDialog != null)
            alertDialog.dismiss();

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(ProfilePictureSetting.this, R.style.myDialog));

        builder.setTitle("Remove Profile Picture")
                .setMessage("Are you sure to remove your profile picture?")
                .setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("general_images/standard_profile_picture.png");

                        storageReference.getDownloadUrl().addOnSuccessListener(
                                new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri)
                                    {
                                        Uri downloadUrl = uri;
                                        String general_profile_picture_url = downloadUrl.toString();

                                        /*
                                        RequestOptions requestOptions = new RequestOptions();
                                        requestOptions.placeholder(R.drawable.image_placeholder);

                                        Glide.with(mContext).applyDefaultRequestOptions(requestOptions).load(general_profile_picture_url)
                                                .into(profile);
                                        */

                                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                        StrictMode.setThreadPolicy(policy);
                                        try {
                                            URL url = new URL(general_profile_picture_url);
                                            Drawable d = new BitmapDrawable(getResources(), BitmapFactory.decodeStream((InputStream)url.getContent()));
                                            profile.setImageDrawable(d);
                                        } catch (IOException e) {
                                            Log.e(TAG, e.getMessage());
                                        }

                                    }
                                });
                        //Drawable drawable = getResources().getDrawable(R.drawable.profile_picture_icon);
                        //profile.setImageDrawable(drawable);
                    }
                })
                .setIcon(R.drawable.alert_icon)
                .setCancelable(false);

        alertDialog = builder.create();
        alertDialog.show();

    }

    public void save(View v)
    {
        profile.invalidate();

        BitmapDrawable dr = (BitmapDrawable)((ImageView)profile).getDrawable();
        final Bitmap bitmap = dr.getBitmap();

        //Converting bitmap to bytes
        final byte[] bytes = ImageManagerClass.getBytesFromBitmap(bitmap, 100);

        //Creating new imageView and setting its drawable to general_profile_picture
        final ImageView imageView = new ImageView(mContext);

        FirebaseStorage.getInstance().getReference().child("general_images/standard_profile_picture.png")
                .getDownloadUrl().addOnSuccessListener(
                new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri)
                    {
                        Uri downloadUrl = uri;
                        String general_profile_picture_url = downloadUrl.toString();

                        /*
                        RequestOptions requestOptions = new RequestOptions();
                        requestOptions.placeholder(R.drawable.image_placeholder);

                        Glide.with(mContext).applyDefaultRequestOptions(requestOptions).load(general_profile_picture_url)
                                .into(imageView);
                        */

                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        try {
                            URL url = new URL(general_profile_picture_url);
                            Drawable d = new BitmapDrawable(getResources(), BitmapFactory.decodeStream((InputStream)url.getContent()));
                            imageView.setImageDrawable(d);
                        } catch (IOException e) {
                            Log.e(TAG, e.getMessage());
                        }

                        //Getting bitmap of created imageView
                        BitmapDrawable imageDrawable = (BitmapDrawable)((ImageView)imageView).getDrawable();
                        Bitmap imageBitmap = imageDrawable.getBitmap();

                        //If bitmap is equal to image bitmap, no need to upload photo
                        if(imageBitmap.sameAs(bitmap))
                        {
                            //Adding general_profile_picture_url to profile_photo under user_account_settings class
                            FirebaseDatabase.getInstance().getReference()
                                    .child("user_account_settings")
                                    .child(user_id)
                                    .child("profile_photo")
                                    .setValue(general_profile_picture_url);

                            //Deleting uploaded profile picture
                            FirebaseStorage.getInstance().getReference()
                                    .child("users")
                                    .child(user_id)
                                    .child("profile_picture")
                                    .delete();

                            Intent intent = new Intent(getApplicationContext(), Settings.class);
                            startActivity(intent);
                            finish();
                        }
                        //Else, we need to upload photo to the FirebaseStorage and setting url to user's profile_photo
                        else
                        {
                            //Initializing storageReference according to category (folder)
                            final StorageReference newStorageReference = FirebaseStorage.getInstance().getReference()
                                    .child("users")
                                    .child(user_id)
                                    .child("profile_picture");

                            //Creating an uploadTask in order to manage upload process of cover photo
                            UploadTask uploadTask = null;
                            uploadTask = newStorageReference.putBytes(bytes);

                            //Creating a processDialog and initializing it
                            final ProgressDialog progressDialog = new ProgressDialog(mContext);

                            //Setting processDialog
                            progressDialog.setTitle("Changing Profile Picture...\nPlease Wait...");
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
                                    Log.d(TAG, "onFailure: Changing profile picture failed.");
                                    progressDialog.dismiss();
                                    Toast.makeText(mContext, "Changing profile picture failed.", Toast.LENGTH_SHORT).show();
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

                                    //Getting download link of profile picture and adding it to user's profile_photo
                                    newStorageReference.getDownloadUrl().addOnSuccessListener(
                                            new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri)
                                                {
                                                    Uri downloadUrl = uri;
                                                    String profile_picture_url = downloadUrl.toString();

                                                    FirebaseDatabase.getInstance().getReference()
                                                            .child("user_account_settings")
                                                            .child(user_id)
                                                            .child("profile_photo")
                                                            .setValue(profile_picture_url);

                                                    Intent intent = new Intent(mContext, Settings.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            });

                                    Toast.makeText(mContext, "Profile Photo Changed!", Toast.LENGTH_SHORT).show();


                                }

                            });
                        }

                    }
                });
    }

    public void cancel(View v)
    {
        Intent intent = new Intent(getApplicationContext(), Settings.class);
        startActivity(intent);
        finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
        {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            profile.setImageBitmap(imageBitmap);
            profile.setBackground(null);
            profile.setOnClickListener(listenerImage);
        }

        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK)
        {
            Uri imageUri = data.getData();

            InputStream inputStream;

            try {
                inputStream = getContentResolver().openInputStream(imageUri);

                Bitmap image = BitmapFactory.decodeStream(inputStream);

                profile.setImageBitmap(image);
                profile.setBackground(null);
                profile.setOnClickListener(listenerImage);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
            }
        }
    }

    private View.OnClickListener listenerImage = new View.OnClickListener()
    {

        @Override
        public void onClick(View v)
        {
            Bitmap bitmap;
            AlertDialog.Builder ImageDialog = new AlertDialog.Builder(ProfilePictureSetting.this);
            ImageView showImage = new ImageView(ProfilePictureSetting.this);
            profile.invalidate();
            BitmapDrawable dr = (BitmapDrawable)((ImageView)profile).getDrawable();
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
}
