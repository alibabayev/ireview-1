package com.sscompany.ireview.LoginRelatedPages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sscompany.ireview.Elements.FirebaseMethods;
import com.sscompany.ireview.Homepage;
import com.sscompany.ireview.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupPage extends AppCompatActivity implements View.OnKeyListener, View.OnClickListener
{
    private static final String TAG = "Sign Up Activity";

    private EditText passwordAgainText;
    private EditText passwordText;
    private EditText emailText;
    private EditText usernameText;
    private EditText display_nameText;
    private TextView loginText;

    private String display_name;
    private String email;
    private String username;
    private String password;
    private String passwordAgain;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    private FirebaseMethods firebaseMethods;

    private Context mContext;

    private boolean exists;

    private Pattern pattern;
    private Matcher matcher;
    private static final String USERNAME_PATTERN = "^(?=.{6,15}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);

        ConstraintLayout backgroundLayout = (ConstraintLayout) findViewById(R.id.backgroundRelativeLayoutSignUp) ;
        backgroundLayout.setOnClickListener(this);
        ImageView logoImageView = (ImageView) findViewById(R.id.menuLogo);
        logoImageView.setOnClickListener(this);

        //Username Pattern
        pattern = Pattern.compile(USERNAME_PATTERN);

        //Initializing mAuth
        mAuth = FirebaseAuth.getInstance();

        //Initializing mContext
        mContext = SignupPage.this;

        //Initializing firebaseMethods
        firebaseMethods = new FirebaseMethods(mContext);

        //Initializing EditTexts
        display_nameText = (EditText) findViewById(R.id.nameText);
        usernameText = (EditText) findViewById(R.id.usernameSignUpText);
        emailText = (EditText) findViewById(R.id.emailSignUp);
        passwordText = (EditText) findViewById(R.id.passwordSignUp);
        passwordAgainText = (EditText) findViewById(R.id.passwordSignUpAgain);
        loginText = findViewById(R.id.login_text);

        //Setting OnKeyListener to passwordAgainText
        passwordAgainText.setOnKeyListener(this);

        //Setting OnClickListener to loginText
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupPage.this, LoginPage.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * Enter is Clicked
     *
     * @param view
     * @param i
     * @param keyEvent
     * @return
     */
    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {

        if(i == keyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
            signUp(view);
        }
        return false;
    }

    /**
     * SignUp Button is Clicked
     *
     * @param view
     */
    public void signUp(View view)
    {
        //Initializing FirebaseDatabase and DatabaseReference
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        //Getting EditText texts
        display_name = display_nameText.getText().toString();
        email = emailText.getText().toString();
        username = usernameText.getText().toString();
        password = passwordText.getText().toString();
        passwordAgain = passwordAgainText.getText().toString();

        username.toLowerCase();

        if(display_name.equals("") || username.equals("") || email.equals("") || password.equals("")) {
            Toast.makeText(this, "A display name, username, email and password are required.", Toast.LENGTH_SHORT).show();
        }
        else if(!validate(username))
        {
            Toast.makeText(this, "Entered username is not valid. You can check the username guidelines by pressing info button.", Toast.LENGTH_LONG).show();
        }
        else if(!(password.equals(passwordAgain))){
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
        }
        else
        {
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //1st check: Make sure the username is not already in use
                    if(firebaseMethods.checkIfUsernameExists(username, dataSnapshot)){
                        Log.d(TAG, "onDataChange: username already exists.");
                        Toast.makeText(mContext, "Username already exists. Please choose another username.", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        firebaseMethods.registerNewEmail(email, password, username, display_name);

                        if (mAuth.getCurrentUser() != null) {
                            mAuth.signOut();
                        }

                        Toast.makeText(mContext, "Signup successful. Sending verification email.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignupPage.this, LoginPage.class);
                        startActivity(intent);
                        finish();

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.backgroundRelativeLayoutLogIn || view.getId() == R.id.menuLogo){
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromInputMethod(getCurrentFocus().getWindowToken(),0);
        }
    }

    /**
     * Method to validate username (Facebook Username Guidelines)
     *
     * @param username
     */
    public boolean validate(final String username)
    {
        matcher = pattern.matcher(username);
        return matcher.matches();
    }

    /**
     * Method to show AlertDialog when Info Button is Clicked
     *
     * @param view
     */
    public void info(View view)
    {
        new AlertDialog.Builder(SignupPage.this)
                .setTitle("Username Guidelines")
                .setMessage("* Usernames should consist of 6-15 characters.\n" +
                        "* Usernames can only contain alphanumeric characters (a-z, 0–9), periods (\".\") and symbol underscores (\"_\").\n" +
                        "* There should not be double periods (\"..\"), and double underscore (\"__\") in the middle of username, and there should not be periods (\".\") and underscores (\"_\") in the beginning and the end of usernames.\n" +
                        "* Capitalization can't be used to differentiate usernames.")
                .setNegativeButton("OK", null)
                .show();
    }

    public void goToHomepage(){
        Intent intent = new Intent(getApplicationContext(), Homepage.class);
        startActivity(intent);
    }

    // ------------------------- Firebase -------------------------------

    /**
     * A method needed for Firebase
     * It changes the UI according to user (If user is null, go to LoginPage)
     */
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null)
        {
            Intent intent = new Intent(getApplicationContext(), Homepage.class);
            startActivity(intent);
            finish();
        }
    }

}
