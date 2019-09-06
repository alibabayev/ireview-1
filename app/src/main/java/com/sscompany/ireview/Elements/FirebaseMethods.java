package com.sscompany.ireview.Elements;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sscompany.ireview.AddItem;
import com.sscompany.ireview.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

/**
 * Created by User on 6/26/2017.
 */

public class FirebaseMethods {

    private static final String TAG = "FirebaseMethods";

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private StorageReference mStorageReference;
    private double mPhotoUploadProgress;
    private String userID;

    private Context mContext;

    private String itemId;

    public FirebaseMethods(Context context) {
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        mStorageReference = FirebaseStorage.getInstance().getReference();
        mContext = context;

        mPhotoUploadProgress = 0;

        if(mAuth.getCurrentUser() != null){
            userID = mAuth.getCurrentUser().getUid();
        }

        itemId = "";
    }

    public boolean checkIfUsernameExists(String username, DataSnapshot datasnapshot){
        Log.d(TAG, "checkIfUsernameExists: checking if " + username + " already exists.");

        User user = new User();

        for (DataSnapshot ds: datasnapshot.child(mContext.getString(R.string.dbname_users)).getChildren()){
            Log.d(TAG, "checkIfUsernameExists: datasnapshot: " + ds);

            user.setUsername(ds.getValue(User.class).getUsername());
            Log.d(TAG, "checkIfUsernameExists: username: " + user.getUsername());

            if(user.getUsername().equals(username)){
                Log.d(TAG, "checkIfUsernameExists: FOUND A MATCH: " + user.getUsername());
                return true;
            }
        }
        return false;
    }

    /**
     * Register a new email and password to Firebase Authentication
     * @param email
     * @param password
     * @param username
     */
    public void registerNewEmail(final String email, String password, final String username, final String display_name)
    {
        System.out.println("YesssSSSSSOKOKOKOKO");
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                        System.out.println("INsideIn");
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(mContext, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();

                            System.out.println("NonONONONoNONO");

                        }
                        else if(task.isSuccessful()){
                            //send verificaton email
                            System.out.println("NoonONNonONokjJISISSSIIojokokokok");
                            sendVerificationEmail();
                            System.out.println("Verification email is sent.");
                            userID = mAuth.getCurrentUser().getUid();
                            addNewUser(email, username, display_name, "");
                        }

                    }
                });
    }

    public void sendVerificationEmail(){

        System.out.println("Inside the sendVerificationEmail method.");
        FirebaseUser user = mAuth.getCurrentUser();

        if(user != null){
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {

                            }
                            else {
                                Toast.makeText(mContext, "Couldn't send verification email.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    /**
     * Add information to the users nodes
     * Add information to the user_account_settings node
     * @param email
     * @param username
     * @param display_name
     * @param profile_photo
     */
    public void addNewUser(String email, String username, String display_name, String profile_photo){

        User user = new User(
                userID,
                email,
                username,
                display_name,
                1
        );

        myRef.child(mContext.getString(R.string.dbname_users))
                .child(userID)
                .setValue(user);


        UserAccountSettings settings = new UserAccountSettings(
                username,
                display_name,
                0,
                0,
                0,
                profile_photo
        );

        myRef.child(mContext.getString(R.string.dbname_user_account_settings))
                .child(userID)
                .setValue(settings);

    }

    public void addNewPost(String itemId, String caption, float rating)
    {
        String newPostKey = myRef.child("posts").push().getKey();

        Post newPost = new Post();

        newPost.setCaption(caption);
        newPost.setItem_id(itemId);
        newPost.setLike_count(0);
        newPost.setRating(rating);
        newPost.setUser_id(userID);
        newPost.setData_created(getTimestamp());

        myRef.child("user_posts").child(userID).child(newPostKey).setValue(newPost);

        myRef.child("posts").child(newPostKey).setValue(newPost);
    }

    public String getTimestamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.CANADA);
        sdf.setTimeZone(TimeZone.getTimeZone("Canada/Pacific"));
        return sdf.format(new Date());
    }

    /**
     * Getting number of photos already added to Firebase Storage
     * to the folder named 'folder'
     * in order to give the unique path (filename)
     *
     * @param dataSnapshot
     * @param folder
     * @return
     */
    public int getImageCount(DataSnapshot dataSnapshot, String folder){
        int count = 0;
        for(DataSnapshot ds: dataSnapshot
                .child(folder)
                .getChildren()){
            count++;
        }
        return count;
    }

    public String getItemId()
    {
        return itemId;
    }


}
