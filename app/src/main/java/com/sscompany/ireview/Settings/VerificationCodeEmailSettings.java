package com.sscompany.ireview.Settings;

import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.parse.ParseException;
import com.sscompany.ireview.GMailSender;
import com.sscompany.ireview.R;

public class VerificationCodeEmailSettings extends AppCompatActivity
{
    private static final String TAG = "VerificationCodeEmail";

    private Context mContext;

    private String emailToChange;
    private String message;

    private String verificationCodeSent;

    private Button sendAgainButton;
    private EditText verificationCode;

    private final String senderEmail = "ireview.secure@gmail.com";
    private final String senderPassword = "IReview2019";
    private final String emailSubject = "New Email Address Verification Code";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_change_verification_code);

        //Initializing mContext
        mContext = VerificationCodeEmailSettings.this;

        //Getting the email entered
        emailToChange = getIntent().getStringExtra("email");

        //Initializing widgets
        sendAgainButton = findViewById(R.id.send_verification_code_again_button);
        verificationCode = findViewById(R.id.verification_code_edit_text);

        //Getting textView widget and changing the text in order to let user see the email entered
        TextView messageTextView = findViewById(R.id.message_text_view);
        message = "Please, enter verification code sent to " + emailToChange + " to change your email address.";
        messageTextView.setText(message);

        //Setting onKeyListener to verification_code_edit_text
        verificationCode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if(keyCode == event.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    verify(v);
                }
                return false;
            }
        });

        //Sending verification code
        sendVerificationCode(emailToChange);


    }

    /**
     * Verify button is clicked
     */
    public void verify(View v)
    {
        String verificationCodeEntered = verificationCode.getText().toString();

        //Checking if verificationCodeEntered is same as verificationCodeSent
        if(verificationCodeEntered.equals(""))
        {
            Toast.makeText(mContext, "Verification code field cannot be empty.", Toast.LENGTH_SHORT).show();
        }
        else if(verificationCodeEntered.equals(verificationCodeSent))
        {
            //Change current user's email to entered email
            FirebaseAuth.getInstance().getCurrentUser().updateEmail(emailToChange)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            Toast.makeText(mContext, "Your email is changed succesfully.", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(mContext, Settings.class);
                            startActivity(intent);
                            finish();
                        }
                    });


        }
        else
        {
            verificationCode.getText().clear();
            Toast.makeText(mContext, "Please enter verification code sent to " + emailToChange + " correctly.", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Send verification code again button is clicked
     */
    public void sendVerificationCodeAgain(View v)
    {
        sendVerificationCode(emailToChange);
        Toast.makeText(mContext, "Verification code is sent to " + emailToChange + ".", Toast.LENGTH_LONG).show();

        sendAgainButton.setVisibility(View.GONE);

        new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished)
            {

            }

            public void onFinish()
            {
                sendAgainButton.setVisibility(View.VISIBLE);
            }

        }.start();
    }

    /**
     * Cancel button is clicked
     */
    public void cancel(View v)
    {
        Intent intent = new Intent(mContext, EmailSettings.class);
        startActivity(intent);
        finish();
    }

    private void sendVerificationCode(String emailToChange)
    {
        //Getting Verification Code
        verificationCodeSent = getVerificationCode();

        //Initializing the message
        String messageText = "The verification code to change your email is " + verificationCodeSent + "." ;

        //Sending message
        sendMessage(messageText);
    }

    private void sendMessage(final String messageToSend) {

        //final ProgressDialog dialog = new ProgressDialog(ActivityMain.this);
        //dialog.setTitle("Sending Email");
        //dialog.setMessage("Please wait");
        //dialog.show();

        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender(senderEmail, senderPassword);
                    sender.sendMail(emailSubject,
                            messageToSend,
                            senderEmail,
                            emailToChange);
                    //dialog.dismiss();
                } catch (Exception e) {
                    Log.e("mylog", "Error: " + e.getMessage());
                }
            }
        });
        sender.start();
    }

    /**
     * Method that generates 5 digit number and returns it
     *
     * @return verification code
     */
    private String getVerificationCode()
    {
        String new_code = "";

        int randomInt;

        //Not to make first integer 0
        randomInt = (int)(Math.random() * 9) + 1;
        new_code = new_code + Integer.toString(randomInt);

        for(int i = 0; i < 4; i++)
        {
            randomInt = (int)(Math.random() * 10);
            new_code = new_code + Integer.toString(randomInt);
        }

        return new_code;
    }
}
