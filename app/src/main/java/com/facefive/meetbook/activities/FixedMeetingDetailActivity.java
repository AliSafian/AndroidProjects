package com.facefive.meetbook.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.facefive.meetbook.R;
public class FixedMeetingDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixed_meeting_detail);

        TextView textView = findViewById(R.id.tv_fixed_meeting_detail);

        Intent intent= getIntent();
        String msg = "You have a meeting with "+intent.getStringExtra("Name")
                +" on "+intent.getStringExtra("Day")
                +" from "+intent.getStringExtra("StartTime")
                + " to " + intent.getStringExtra("EndTime");
        textView.setText(msg);


    }
}
