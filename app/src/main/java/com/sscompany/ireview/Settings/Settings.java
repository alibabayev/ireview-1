package com.sscompany.ireview.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sscompany.ireview.Homepage;
import com.sscompany.ireview.LoginRelatedPages.DeleteAccount;
import com.sscompany.ireview.R;
import com.parse.ParseUser;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        String[] settingTypes = new String[]
                {
                        "Name Setting",
                        "Username Setting",
                        "Email Setting",
                        "Delete Account",
                        "Password Setting",
                        "Change Profile Picture",
                        "Change Gender"
                };

        final ListView listView = (ListView) findViewById(R.id.list);

        settingTypes[0] += "\n( " + ParseUser.getCurrentUser().get("Name") + " ) ";
        settingTypes[1] += "\n( " + ParseUser.getCurrentUser().getUsername() + " ) ";
        settingTypes[2] += "\n( " + ParseUser.getCurrentUser().getEmail() + " ) ";

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, settingTypes);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if(position == 0)
                {
                    Intent intent = new Intent(getApplicationContext(), NameSetting.class);
                    startActivity(intent);
                }
                else if(position == 1)
                {
                    Intent intent = new Intent(getApplicationContext(), UsernameSetting.class);
                    startActivity(intent);
                }
                else if(position == 2)
                {
                    Intent intent = new Intent(getApplicationContext(), ConfirmPasswordForEmail.class);
                    startActivity(intent);
                }
                else if(position == 3)
                {
                    Intent intent = new Intent(getApplicationContext(), DeleteAccount.class);
                    startActivity(intent);
                }
                else if(position == 4)
                {
                    Intent intent = new Intent(getApplicationContext(), PasswordSetting.class);
                    startActivity(intent);
                }
                else if(position == 5)
                {
                    Intent intent = new Intent(getApplicationContext(), ProfilePictureSetting.class);
                    startActivity(intent);
                }
                else if(position == 6)
                {
                    Intent intent = new Intent(getApplicationContext(), GenderSetting.class);
                    startActivity(intent);
                }


            }
        });

    }

    public void cancel(View view)
    {
        Intent intent = new Intent(getApplicationContext(), Homepage.class);
        startActivity(intent);
    }
}
