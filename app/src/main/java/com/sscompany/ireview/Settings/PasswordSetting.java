package com.sscompany.ireview.Settings;

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
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.sscompany.ireview.R;
import com.google.firebase.auth.FirebaseAuth;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.function.ToDoubleBiFunction;

public class PasswordSetting extends AppCompatActivity
{
    private static final String TAG = "PasswordSetting";

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

    public void changePassword(View view0) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();

        EditText currentPassword = findViewById(R.id.editText);
        EditText newPassword = findViewById(R.id.editText2);
        EditText newPasswordAgain = findViewById(R.id.editText3);

        String currentPass = currentPassword.getText().toString();
        final String newPass = newPassword.getText().toString();
        final String newPassAgain = newPasswordAgain.getText().toString();

        if (currentPass.equals("") || newPass.equals("") || newPassAgain.equals(""))
        {
            Toast.makeText(PasswordSetting.this, "Password fields cannot be empty!", Toast.LENGTH_LONG).show();
        }
        else {

            AuthCredential credential = EmailAuthProvider
                    .getCredential(user.getEmail(), currentPass);


            user.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
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

        }
    }

    public void cancel(View view)
    {
        Intent intent = new Intent(getApplicationContext(), Settings.class);
        startActivity(intent);
    }
}
