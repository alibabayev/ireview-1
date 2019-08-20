package com.sscompany.ireview.LoginRelatedPages;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.sscompany.ireview.Homepage;
import com.sscompany.ireview.R;
import com.google.firebase.auth.FirebaseAuth;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupPageActivity extends AppCompatActivity implements View.OnKeyListener, View.OnClickListener
{
    private static final String TAG = "Sign Up Activity";

    private EditText passwordAgainText;
    private EditText passwordText;
    private EditText emailText;
    private EditText usernameText;
    private EditText nameText;
    private TextView loginText;

    private String email;
    private String username;
    private String password;
    private String passwordAgain;

    private FirebaseAuth mAuth;

    private Pattern pattern;
    private Matcher matcher;
    private static final String USERNAME_PATTERN = "^(?=.{6,15}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);

        RelativeLayout backgroundLayout = (RelativeLayout) findViewById(R.id.backgroundRelativeLayoutSignUp) ;
        backgroundLayout.setOnClickListener(this);
        ImageView logoImageView = (ImageView) findViewById(R.id.menuLogo);
        logoImageView.setOnClickListener(this);

        //Username Pattern
        pattern = Pattern.compile(USERNAME_PATTERN);

        //Initialization of EditTexts
        nameText = (EditText) findViewById(R.id.nameText);
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
                Intent intent = new Intent(SignupPageActivity.this, LoginPage.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {

        if(i == keyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
            signUp(view);
        }
        return false;
    }

    public void signUp(View view)
    {
        email = emailText.getText().toString();
        username = usernameText.getText().toString();
        password = passwordText.getText().toString();
        passwordAgain = passwordAgainText.getText().toString();

        if(username.equals("") || email.equals("") || password.equals("")) {
            Toast.makeText(this, "A username, email and password are required.", Toast.LENGTH_SHORT).show();
        }
        else if(!(password.equals(passwordAgain))){
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
        }
        else
        {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignupPageActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            if (mAuth.getCurrentUser() != null) {
                Toast.makeText(this, "Logged In As  " + mAuth.getCurrentUser().getDisplayName(), Toast.LENGTH_SHORT).show();
                goToHomepage();
                finish();
            }

            /*
            final ParseUser user = new ParseUser();
            user.setUsername(username.getText().toString().toLowerCase());
            user.setEmail(email.getText().toString());
            user.setPassword(password.getText().toString());
            user.put("Name", name.getText().toString());
            user.put("Books", new ArrayList<String>());
            user.put("Movies", new ArrayList<String>());
            user.put("Musics", new ArrayList<String>());
            user.put("TVShows", new ArrayList<String>());
            user.put("Places", new ArrayList<String>());
            user.put("Websites", new ArrayList<String>());
            user.put("VideoGames", new ArrayList<String>());
            user.put("gender", "");

            Drawable drawable = getResources().getDrawable(R.drawable.profile_picture_icon);

            BitmapDrawable dr = (BitmapDrawable)drawable;
            Bitmap bitmap = dr.getBitmap();

            System.out.println("Yes2");

            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            System.out.println("Yes3");

            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);

            byte[] byteArray = stream.toByteArray();

            System.out.println("Yes4");

            final ParseFile fileImage = new ParseFile("profile.jpg", byteArray);

            System.out.println("Yes5");

            fileImage.saveInBackground(new SaveCallback() {
                public void done(ParseException e) {
                    // If successful add file to user and signUpInBackground
                    if(e == null)
                    {
                        user.put("profilePicture", fileImage);

                        System.out.println("Yes6");

                        user.signUpInBackground(new SignUpCallback() {
                            @Override
                            public void done(ParseException e) {
                                if(e == null) {
                                    System.out.println("Yes7");
                                    ParseUser.logOut();
                                    Toast.makeText(getApplicationContext(), "Account Created Successfully! Please Verify Your Email Before Login.", Toast.LENGTH_LONG);
                                    Intent intent = new Intent(SignupPageActivity.this, LoginPage.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                                else{
                                    System.out.println("Yes8 - " + e.getMessage());
                                    ParseUser.logOut();
                                    Toast.makeText(getApplicationContext(), "Error! Account Creation Failed! Account Could Not Be Created.", Toast.LENGTH_LONG);
                                    Intent intent = new Intent(SignupPageActivity.this, LoginPage.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                }
            });
            */
        }
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.backgroundRelativeLayoutLogIn || view.getId() == R.id.menuLogo){
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromInputMethod(getCurrentFocus().getWindowToken(),0);
        }
    }

    public boolean validate(final String username)
    {
        matcher = pattern.matcher(username);
        return matcher.matches();
    }

    public void info(View view)
    {
        new AlertDialog.Builder(SignupPageActivity.this)
                .setTitle("Username Guidelines")
                .setMessage("* Usernames should consist of 6-15 characters.\n* Usernames can only contain alphanumeric characters (a-z, 0–9), periods (\".\") and symbol underscores (\"_\").\n* There should not be double periods (\"..\"), and double underscore (\"__\") in the middle of username, and there should not be periods (\".\") and underscores (\"_\") in the beginning and the end of usernames.\n* Capitalization can't be used to differentiate usernames.")
                .setNegativeButton("OK", null)
                .show();
    }

    public void goToHomepage(){
        Intent intent = new Intent(getApplicationContext(), Homepage.class);
        startActivity(intent);
    }

    // ------------------------- Firebase -------------------------------

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null)
        {
            Intent intent = new Intent(getApplicationContext(), LoginPage.class);
            startActivity(intent);
            finish();
        }
    }

}
