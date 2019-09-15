package com.sscompany.ireview.Settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.sscompany.ireview.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class EmailSettings extends AppCompatActivity
{
    private static final String TAG = "EmailSettings";

    private Context mContext;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_setting);

        mContext = EmailSettings.this;

        EditText email = findViewById(R.id.editText);

        email.setOnKeyListener(new View.OnKeyListener() {
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
        finish();
    }

    public void changeEmail(View view) throws ParseException
    {
        EditText email = findViewById(R.id.editText);
        final String emailToChange = email.getText().toString();

        if(emailToChange.equals(""))
        {
            Toast.makeText(EmailSettings.this, "Email field cannot be empty!", Toast.LENGTH_LONG).show();
        }
        else
        {
            firebaseAuth.fetchSignInMethodsForEmail(emailToChange)
                    .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                        @Override

                        public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                            if (task.isSuccessful()) {
                                SignInMethodQueryResult result = task.getResult();
                                List<String> signInMethods = result.getSignInMethods();
                                if (signInMethods.contains(EmailAuthProvider.EMAIL_PASSWORD_SIGN_IN_METHOD))
                                {
                                    Toast.makeText(EmailSettings.this, "Email is already in use. Please enter another email address.", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Intent intent = new Intent(mContext, VerificationCodeEmailSettings.class);
                                    intent.putExtra("email", emailToChange);
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                Log.e(TAG, "An Unexpected Error Occurred. Please, enter your email again.", task.getException());
                            }
                        }
                    });
        }
    }
}
