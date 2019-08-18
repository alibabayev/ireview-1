package com.sscompany.ireview.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sscompany.ireview.LoginRelatedPages.LoginPage;
import com.sscompany.ireview.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;

public class EmailSetting extends AppCompatActivity
{

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ArrayList<String> emails = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_setting);

        EditText password = findViewById(R.id.editText);

        password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if(keyCode == event.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    try {
                        changeEmail(v);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });

    }

    public void cancel(View view)
    {
        Intent intent = new Intent(getApplicationContext(), Settings.class);
        startActivity(intent);
    }

    public void changeEmail(View view) throws ParseException
    {
        EditText email = findViewById(R.id.editText);
        String emailToChange = email.getText().toString();

        if(emailExists(emailToChange))
        {
            Toast.makeText(EmailSetting.this, "Email is already used by another user. Please enter another email address.", Toast.LENGTH_LONG).show();
        }
        else {
            ParseUser.getCurrentUser().setEmail(emailToChange);
            System.out.println(ParseUser.getCurrentUser().getEmail());
            ParseUser.getCurrentUser().save();

            ParseUser.logOut();

            Intent intent = new Intent(getApplicationContext(), LoginPage.class);
            startActivity(intent);
        }
    }

    private boolean emailExists(final String email) throws ParseException {

        final ParseQuery<ParseUser> emailQuery = ParseUser.getQuery();
        emailQuery.whereEqualTo("email", email);

        if(emailQuery.count() == 0)
            return false;

        else
            return true;

    }
}
