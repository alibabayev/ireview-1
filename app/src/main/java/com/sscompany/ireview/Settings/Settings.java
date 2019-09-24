package com.sscompany.ireview.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sscompany.ireview.Models.User;
import com.sscompany.ireview.Screens.Homepage;
import com.sscompany.ireview.R;

public class Settings extends AppCompatActivity
{
    private static final String TAG = "Settings";

    private String display_name;
    private String username;
    private String gender;

    private ListView listView;
    private String[] settingTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        //Initializing current user_id
        final String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //Initializing listView
        listView = (ListView) findViewById(R.id.list);

        //Getting user's display name, username, gender
        FirebaseDatabase.getInstance().getReference().child("users").child(user_id)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        //Getting user, display_name, and username
                        User user = dataSnapshot.getValue(User.class);
                        display_name = user.getDisplay_name();
                        username = user.getUsername();
                        gender = user.getGender();

                        //Calling method to initialize listView
                        init_list();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    /**
     * Initializes settingTypes
     * Initializes adapter for listView
     * Sets onClickListeners for listView components
     */
    private void init_list() {
        settingTypes = new String[]
                {
                        "Name Setting",
                        "Username Setting",
                        "Email Setting",
                        "Delete Account",
                        "Password Setting",
                        "Change Profile Picture",
                        "Change Gender"
                };

        //Adding current display name preview on Name Setting button
        settingTypes[0] += "\n( " + display_name + " ) ";

        //Adding current username preview on Username Setting button
        settingTypes[1] += "\n( " + username + " ) ";

        //Adding current email preview on Email Setting button
        settingTypes[2] += "\n( " + FirebaseAuth.getInstance().getCurrentUser().getEmail() + " ) ";

        //Adding current gender preview on Change Gender button
        if (gender.equals(""))
        {
            settingTypes[6] += "\n( Not yet specified ) ";
        }
        else
            settingTypes[6] += "\n( " + gender + " ) ";

        //Creating an adapter and adding it to the listView
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, settingTypes);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent;

                if(position == 0)
                {
                    intent = new Intent(getApplicationContext(), NameSetting.class);
                }
                else if(position == 1)
                {
                    intent = new Intent(getApplicationContext(), UsernameSetting.class);
                }
                else if(position == 2)
                {
                    intent = new Intent(getApplicationContext(), ConfirmPasswordForEmail.class);
                }
                else if(position == 3)
                {
                    intent = new Intent(getApplicationContext(), DeleteAccount.class);
                }
                else if(position == 4)
                {
                    intent = new Intent(getApplicationContext(), PasswordSetting.class);
                }
                else if(position == 5)
                {
                    intent = new Intent(getApplicationContext(), ProfilePictureSetting.class);
                }
                else if(position == 6)
                {
                    intent = new Intent(getApplicationContext(), GenderSetting.class);
                }
                else {
                    intent = new Intent(getApplicationContext(), Settings.class);
                }

                startActivity(intent);
            }
        });
    }

    public void cancel(View view)
    {
        Intent intent = new Intent(getApplicationContext(), Homepage.class);
        startActivity(intent);
        finish();
    }
}
