package com.facefive.meetbook.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.facefive.meetbook.R;

public class NoInternetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();  // optional depending on your needs
        finish();
        System.exit(0);
    }
}

