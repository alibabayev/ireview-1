package com.sscompany.ireview.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.sscompany.ireview.R;
import com.parse.ParseException;
import com.parse.ParseUser;

public class GenderSetting extends AppCompatActivity
{
    Spinner spinner;
    Toast alert;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gender_setting);

        alert = Toast.makeText(GenderSetting.this, "Please choose your gender!", Toast.LENGTH_SHORT);

        spinner = findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(GenderSetting.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

    }

    public void save(View v) throws ParseException {
        String gender = spinner.getSelectedItem().toString();

        if(gender.equals("Your gender"))
        {
            if(alert != null)
            {
                alert.cancel();
            }
            alert = Toast.makeText(GenderSetting.this, "Please choose your gender!", Toast.LENGTH_SHORT);
            alert.show();
        }
        else
        {
            ParseUser.getCurrentUser().put("gender", gender);
            ParseUser.getCurrentUser().save();

            Intent intent = new Intent(GenderSetting.this, Settings.class);
            startActivity(intent);
            finish();
        }

    }

    public void cancel(View v)
    {
        Intent intent = new Intent(GenderSetting.this, Settings.class);
        startActivity(intent);
        finish();
    }
}
