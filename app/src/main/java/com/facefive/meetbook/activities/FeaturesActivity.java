package com.facefive.meetbook.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

import com.facefive.meetbook.R;
import com.facefive.meetbook.SchedulePlan;
import com.facefive.meetbook.UpdateMessage;

public class FeaturesActivity extends AppCompatActivity {

    private CardView cv_timetable;
    private CardView cv_meeting;
    private CardView cv_send_update;
    private CardView cv_connection;
    private CardView cv_messages;
    private CardView cv_notification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features);

        cv_timetable = findViewById(R.id.cv_timetable);
        cv_meeting = findViewById(R.id.cv_meeting);
        cv_connection = findViewById(R.id.cv_connection);
        cv_send_update = findViewById(R.id.cv_send_update);
        cv_messages = findViewById(R.id.cv_messages);
        cv_notification = findViewById(R.id.cv_notification);


        cv_timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),SchedulePlan.class);
                startActivity(i);
            }
        });

        cv_meeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),MeetingsActivity.class);
                startActivity(i);
            }
        });
        cv_connection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),SubscriptionsActivity.class);
                startActivity(i);
            }
        });
        cv_send_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),UpdateMessage.class);
                startActivity(i);
            }
        });

        cv_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Under Development", Toast.LENGTH_SHORT).show();
            }
        });
        cv_messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Under Development", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
