package com.sscompany.ireview.LoginRelatedPages;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sscompany.ireview.Homepage;
import com.sscompany.ireview.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginPage extends AppCompatActivity implements View.OnKeyListener, View.OnClickListener {

    EditText password;
    EditText email;

    private Pattern pattern;
    private Matcher matcher;
    private static final String USERNAME_PATTERN = "^(?=.{6,15}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pattern = Pattern.compile(USERNAME_PATTERN);

        if(!isConnected(LoginPage.this)) buildDialog(LoginPage.this).show();
        else {
            Toast.makeText(LoginPage.this, "Welcome", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.login_page);

            RelativeLayout backgroundLayout = (RelativeLayout) findViewById(R.id.backgroundRelativeLayoutLogIn);
            backgroundLayout.setOnClickListener(this);
            ImageView logoImageView = (ImageView) findViewById(R.id.menuLogoLogIn);
            logoImageView.setOnClickListener(this);

            password = findViewById(R.id.passwordLoginText);

            password.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event)
                {
                    if(keyCode == event.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                        logIn(v);
                    }
                    return false;
                }
            });

            if (ParseUser.getCurrentUser() != null) {
                Toast.makeText(this, "Logged In As  " + ParseUser.getCurrentUser().getUsername(), Toast.LENGTH_SHORT).show();
                goToHomepage();
            }
        }
    }

    public void logIn(View view)
    {
        email = (EditText) findViewById(R.id.emailLoginText);
        password = (EditText) findViewById(R.id.passwordLoginText);

        String emailOrUsername = email.getText().toString();
        emailOrUsername.toLowerCase();

        if(email.getText().toString().matches("") || password.getText().toString().matches("")) {
            Toast.makeText(this, "An Email/Username and Password Are Required.", Toast.LENGTH_SHORT).show();
        }
        else if(validate(emailOrUsername))
        {
            ParseUser.logInInBackground(emailOrUsername, password.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e)
                {
                    if(user != null) {

                        if(user.getBoolean("emailVerified"))
                        {
                            Toast.makeText(LoginPage.this, "Login Successful. Welcome, " + user.getUsername() + "!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LoginPage.this, Homepage.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                        else
                        {
                            ParseUser.logOut();
                            Toast.makeText(LoginPage.this, "Login Failed. Please Verify Your Email First.", Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        ParseUser.logOut();
                        Toast.makeText(LoginPage.this, "Login Failed. Please Enter Your Login Information Correctly.", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
        else {
            ParseUser.logInInBackground(email.getText().toString(), password.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e)
                {
                    if(user != null) {

                        if(user.getBoolean("emailVerified"))
                        {
                            Toast.makeText(LoginPage.this, "Login Successful. Welcome, " + user.getUsername() + "!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LoginPage.this, Homepage.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                        else
                        {
                            ParseUser.logOut();
                            Toast.makeText(LoginPage.this, "Login Failed. Please Verify Your Email First.", Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        ParseUser.logOut();
                        Toast.makeText(LoginPage.this, "Login Failed. Please Enter Your Login Information Correctly.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent)
    {
        if(i == keyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
            logIn(view);
        }
        return false;
    }

    public void goToHomepage(){
        Intent intent = new Intent(getApplicationContext(), Homepage.class);
        startActivity(intent);
    }

    public void forgotPassword(View view)
    {
        Intent intent = new Intent(getApplicationContext(), ForgotPassword.class);
        startActivity(intent);
    }

    public void signUp(View view)
    {
        Intent intent = new Intent(getApplicationContext(), SignupPageActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId() == R.id.backgroundRelativeLayoutLogIn || view.getId() == R.id.menuLogo){
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromInputMethod(getCurrentFocus().getWindowToken(),0);
        }
    }


    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
        else
            return false;
        }
        else
            return false;
    }

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this. Press Ok to Exit");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        return builder;
    }

    public boolean validate(final String username)
    {
        matcher = pattern.matcher(username);
        return matcher.matches();
    }

    private void alertDisplayer(String title,String message, final boolean error){
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginPage.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        if(!error) {
                            Intent intent = new Intent(LoginPage.this, Homepage.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }
}
