package com.sscompany.ireview.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sscompany.ireview.R;

public class ConfirmPasswordForEmail extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_password_for_email);
    }

    public void confirmPassword(View view)
    {
        EditText password = findViewById(R.id.editText);
        final String pass = password.getText().toString();

        if(pass.equals(""))
        {
            Toast.makeText(ConfirmPasswordForEmail.this, "Password field cannot be empty!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            AuthCredential credential = EmailAuthProvider
                    .getCredential(user.getEmail(), pass);

            user.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(ConfirmPasswordForEmail.this, "Verified!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ConfirmPasswordForEmail.this, EmailSettings.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(ConfirmPasswordForEmail.this, "Please, enter the your password correctly.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }
}
