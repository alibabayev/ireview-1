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
import com.parse.ParseException;
import com.parse.ParseUser;

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
                if(keyCode == event.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    try {
                        changeName(v);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });
    }

    public void changeName(View view) throws ParseException {
        EditText name = findViewById(R.id.editText);
        String nameOfUser = name.getText().toString();

        if(nameOfUser.length() > 30)
        {
            Toast.makeText(getApplicationContext(), "Name should consist of at most 30 characters.", Toast.LENGTH_LONG);
        }
        else {
            ParseUser user = ParseUser.getCurrentUser();
            user.put("Name", nameOfUser);
            user.save();

            Intent intent = new Intent(getApplicationContext(), Settings.class);
            startActivity(intent);
        }
    }

    public void cancel(View view)
    {
        Intent intent = new Intent(getApplicationContext(), Settings.class);
        startActivity(intent);
    }
}
