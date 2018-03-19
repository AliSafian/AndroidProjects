package com.facefive.meetbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class show_all_meetings extends AppCompatActivity {

    RelativeLayout request1;
    TextView subject;

    RelativeLayout request2;
    TextView subject2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_meetings);
        request1=(RelativeLayout)findViewById(R.id.all_meetings_request1);
        subject=(TextView)findViewById(R.id.showmeet_subject) ;

        request1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( show_all_meetings.this,response.class);
                intent.putExtra("subject",subject.getText().toString());
                startActivity(intent);

            }
        });
        request2=(RelativeLayout)findViewById(R.id.all_meetings_request2);
        subject2=(TextView)findViewById(R.id.showmeet_subject2) ;

        request2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( show_all_meetings.this,response.class);
                intent.putExtra("subject",subject2.getText().toString());
                startActivity(intent);

            }
        });




    }
}
