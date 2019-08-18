package com.sscompany.ireview.LoginRelatedPages;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sscompany.ireview.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

public class ForgotPassword extends AppCompatActivity
{
    EditText email;
    Button submit;

    //FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        email = (EditText) findViewById(R.id.emailText);
        submit = (Button) findViewById(R.id.sendCodeButton);

        //firebaseAuth = FirebaseAuth.getInstance();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.requestPasswordResetInBackground(email.getText().toString(),
                        new RequestPasswordResetCallback() {
                            public void done(ParseException e) {
                                if (e == null) {
                                    Toast.makeText(ForgotPassword.this, "Password is sent to your email.", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), LoginPage.class);
                                    startActivity(intent);
                                } else {
                                    //Toast.makeText(ForgotPassword.this, "Password cannot be sent to your email.", Toast.LENGTH_LONG).show();
                                    Toast.makeText(ForgotPassword.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });


            }
        });

    }

    /*public void send(View view)
    {
        Intent intent = new Intent(getApplicationContext(), VerificationCodePage.class);
        startActivity(intent);
    }*/
}
