package com.sscompany.ireview.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.sscompany.ireview.R;

public class NameSetting extends AppCompatActivity
{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name_setting);

        EditText name = findViewById(R.id.editText);

        name.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if(keyCode == event.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    changeName(v);
                }
                return false;
            }
        });
    }

    public void changeName(View view)
    {
        EditText name = findViewById(R.id.editText);
        String nameOfUser = name.getText().toString();

        if(nameOfUser.length() > 30)
        {
            Toast.makeText(getApplicationContext(), "Name should consist of at most 30 characters.", Toast.LENGTH_LONG);
        }
        else {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String user_id = user.getUid();

            //Setting display_name under users class
            FirebaseDatabase.getInstance().getReference().child("users").child(user_id).child("display_name").setValue(nameOfUser);

            //Setting display_name under user_account_settings class
            FirebaseDatabase.getInstance().getReference().child("user_account_settings").child(user_id).child("display_name").setValue(nameOfUser);

            Intent intent = new Intent(getApplicationContext(), Settings.class);
            startActivity(intent);
            finish();
        }
    }

    public void cancel(View view)
    {
        Intent intent = new Intent(getApplicationContext(), Settings.class);
        startActivity(intent);
        finish();
    }
}
