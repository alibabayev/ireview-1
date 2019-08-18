package com.sscompany.ireview.LoginRelatedPages;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sscompany.ireview.R;
import com.sscompany.ireview.Settings.ChangePasswordPage;

public class VerificationCodePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verification_code_page);
    }

    public void verify(View view) {
        Intent intent = new Intent(getApplicationContext(), ChangePasswordPage.class);
        startActivity(intent);
    }
}
