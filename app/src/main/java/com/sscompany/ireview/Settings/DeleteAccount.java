package com.sscompany.ireview.Settings;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.sscompany.ireview.LoginRelatedPages.LoginPage;
import com.sscompany.ireview.R;

public class DeleteAccount extends AppCompatActivity
{
    private static final String TAG = "DeleteAccount";

    private Context mContext;

    private FirebaseUser user;
    private String user_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_password_for_email);

        //Initializing mContext
        mContext = DeleteAccount.this;

        //Initializing current user
        user = FirebaseAuth.getInstance().getCurrentUser();

        //Initializing current user id
        user_id = user.getUid();
    }

    public void confirmPassword(View view)
    {
        EditText password = findViewById(R.id.editText);
        final String pass = password.getText().toString();

        AuthCredential credential = EmailAuthProvider
                .getCredential(user.getEmail(), pass);

        FirebaseAuth.getInstance().getCurrentUser().reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(DeleteAccount.this, "Verified!", Toast.LENGTH_SHORT).show();

                            AlertDialog.Builder builder = new AlertDialog.Builder(DeleteAccount.this)
                                    .setTitle("Deleting Account")
                                    .setMessage("Delete Account?")
                                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which)
                                        {
                                            dialog.cancel();

                                            //Removing all the posts of user from user_posts class in Firebase Database
                                            //The posts will not be removed from all posts class for now
                                            FirebaseDatabase.getInstance().getReference()
                                                    .child("user_posts")
                                                    .child(user_id)
                                                    .removeValue();

                                            //Removing user's data from users class in Firebase Database
                                            FirebaseDatabase.getInstance().getReference()
                                                    .child("users")
                                                    .child(user_id)
                                                    .removeValue();

                                            //Removing user's data from user_account_settings class in Firebase Database
                                            FirebaseDatabase.getInstance().getReference()
                                                    .child("user_account_settings")
                                                    .child(user_id)
                                                    .removeValue();

                                            //Removing user's profile picture from Firebase Storage
                                            //We cannot delete entire folder (Functionality is not available yet)
                                            FirebaseStorage.getInstance().getReference()
                                                    .child("users")
                                                    .child(user_id)
                                                    .child("profile_picture")
                                                    .delete();

                                            //Deleting data from Firebase Authentication and signing out the user
                                            FirebaseAuth.getInstance().getCurrentUser().delete();
                                            FirebaseAuth.getInstance().signOut();

                                            Toast.makeText(mContext, "Account Deleted!", Toast.LENGTH_SHORT);

                                            Intent intent = new Intent(DeleteAccount.this, LoginPage.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);

                                        }
                                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                            Intent intent = new Intent(DeleteAccount.this, Settings.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        }
                                    });
                            AlertDialog ok = builder.create();
                            ok.show();
                        }
                        else
                            Toast.makeText(DeleteAccount.this, "Please, enter the your password correctly.", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void cancel(View v)
    {
        Intent intent = new Intent(DeleteAccount.this, LoginPage.class);
        startActivity(intent);
        finish();
    }
}
