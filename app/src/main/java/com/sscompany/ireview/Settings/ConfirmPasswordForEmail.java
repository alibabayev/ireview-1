package com.sscompany.ireview.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sscompany.ireview.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class ConfirmPasswordForEmail extends AppCompatActivity
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
                    Toast.makeText(ConfirmPasswordForEmail.this, "Verified!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ConfirmPasswordForEmail.this, EmailSetting.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(ConfirmPasswordForEmail.this, "Please, enter the your password correctly.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
