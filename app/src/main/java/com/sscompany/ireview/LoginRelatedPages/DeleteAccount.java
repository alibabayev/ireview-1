package com.sscompany.ireview.LoginRelatedPages;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sscompany.ireview.R;
import com.sscompany.ireview.Settings.Settings;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class DeleteAccount extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_password);
    }

    public void confirmPassword(View view)
    {
        EditText password = findViewById(R.id.editText);
        final String pass = password.getText().toString();

        ParseUser.logInInBackground(ParseUser.getCurrentUser().getUsername(), pass, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null)
                {
                    Toast.makeText(DeleteAccount.this, "Verified!", Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder builder = new AlertDialog.Builder(DeleteAccount.this)
                            .setTitle("Deleting Account")
                            .setMessage("Delete Account?")
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    try {
                                        ParseUser.getCurrentUser().delete();
                                    } catch (ParseException e1) {
                                        e1.printStackTrace();
                                    }
                                    ParseUser.logOut();
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
                else {
                    Toast.makeText(DeleteAccount.this, "Please, enter the your password correctly.", Toast.LENGTH_LONG).show();
                }
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
