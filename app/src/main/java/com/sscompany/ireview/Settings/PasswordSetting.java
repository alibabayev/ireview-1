package com.sscompany.ireview.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sscompany.ireview.R;
import com.google.firebase.auth.FirebaseAuth;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class PasswordSetting extends AppCompatActivity
{
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_setting);

        EditText newPasswordAgain = findViewById(R.id.editText3);
        newPasswordAgain.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if(keyCode == event.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    changePassword(v);
                }
                return false;
            }
        });
    }

    public void changePassword(View view0)
    {
        EditText currentPassword = findViewById(R.id.editText);
        EditText newPassword = findViewById(R.id.editText2);
        EditText newPasswordAgain = findViewById(R.id.editText3);

        String currentPass = currentPassword.getText().toString();
        final String newPass = newPassword.getText().toString();
        final String newPassAgain = newPasswordAgain.getText().toString();


        ParseUser.logInInBackground(ParseUser.getCurrentUser().getUsername(), currentPass, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null)
                {
                    if(!newPass.equals(newPassAgain))
                    {
                        Toast.makeText(PasswordSetting.this, "New passwords don't match", Toast.LENGTH_LONG).show();
                    }
                    else {

                        if(newPass.length() >= 6)
                        {
                            user.setPassword(newPass);
                            try {
                                user.save();
                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }
                            Intent intent = new Intent(getApplicationContext(), Settings.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(PasswordSetting.this, "Password should consist of at least 6 characters.", Toast.LENGTH_LONG).show();
                        }
                    }

                }
                else {
                    Toast.makeText(PasswordSetting.this, "Please, enter the current password correctly.", Toast.LENGTH_LONG).show();
                }
            }
        });

        /*
        firebaseAuth.signInWithEmailAndPassword(user.getEmail(), currentPass).addOnCompleteListener(new OnCompleteListener<AuthResult>()
        {

            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if (task.isSuccessful())
                {
                    if(!newPass.equals(newPassAgain))
                    {
                        Toast.makeText(PasswordSetting.this, "New passwords don't match", Toast.LENGTH_LONG).show();
                    }
                    else {

                        if(newPass.length() >= 6)
                        {
                            user.updatePassword(newPass);
                            Intent intent = new Intent(getApplicationContext(), Settings.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(PasswordSetting.this, "Password should consist of at least 6 characters.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                else
                {
                    Toast.makeText(PasswordSetting.this, "Please, enter the current password correctly", Toast.LENGTH_LONG).show();
                }

            }
        });
        */
    }

    public void cancel(View view)
    {
        Intent intent = new Intent(getApplicationContext(), Settings.class);
        startActivity(intent);
    }
}
