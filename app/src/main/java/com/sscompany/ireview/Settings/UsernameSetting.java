package com.sscompany.ireview.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sscompany.ireview.R;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsernameSetting extends AppCompatActivity
{
    ArrayList<String> usernames = new ArrayList<>();

    private Pattern pattern;
    private Matcher matcher;
    private static final String USERNAME_PATTERN = "^(?=.{6,15}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.username_setting);

        EditText username = findViewById(R.id.editText);

        pattern = Pattern.compile(USERNAME_PATTERN);

        username.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if(keyCode == event.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    try {
                        changeUsername(v);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });
    }

    public void changeUsername(View view) throws ParseException {
        ParseUser user = ParseUser.getCurrentUser();
        EditText username = findViewById(R.id.editText);
        String usernameOfUser = username.getText().toString();

        usernameOfUser.toLowerCase();

        usernames = new ArrayList<>();

        if(!validate(usernameOfUser))
        {
            Toast.makeText(this, "Entered username is not valid. You read the username guidelines by pressing info button.", Toast.LENGTH_LONG).show();
        }
        else if(usernameExists(usernameOfUser))
        {
            Toast.makeText(UsernameSetting.this, "Username is already used by another user. Please enter another username.",Toast.LENGTH_LONG).show();
        }
        else {
            user.setUsername(usernameOfUser);
            user.save();

            Intent intent = new Intent(getApplicationContext(), Settings.class);
            startActivity(intent);
        }

    }

    private boolean usernameExists(final String username) throws ParseException
    {
        final ParseQuery<ParseUser> usernameQuery = ParseUser.getQuery();
        usernameQuery.whereEqualTo("username", username);

        if(usernameQuery.count() == 0)
            return false;

        else
            return true;

    }

    public void cancel(View view)
    {
        Intent intent = new Intent(getApplicationContext(), Settings.class);
        startActivity(intent);
    }

    public boolean validate(final String username)
    {
        matcher = pattern.matcher(username);
        return matcher.matches();
    }

    public void info(View view)
    {
        new AlertDialog.Builder(UsernameSetting.this)
                .setTitle("Username Guidelines")
                .setMessage("* Usernames should consist of 6-15 characters.\n* Usernames can only contain alphanumeric characters (a-z, 0–9), periods (\".\") and symbol underscores (\"_\").\n* There should not be double periods (\"..\"), and double underscore (\"__\") in the middle of username, and there should not be periods (\".\") and underscores (\"_\") in the beginning and the end of usernames.\n* Capitalization can't be used to differentiate usernames.")
                .setNegativeButton("OK", null)
                .show();
    }
}
