package com.facefive.meetbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class response extends AppCompatActivity {

    Button startconv,accept,reject;
    TextView subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);
        startconv=(Button)findViewById(R.id.resp_startconv);
        accept=(Button)findViewById(R.id.resp_btn_accept);
        subject=(TextView)findViewById(R.id.meet_subject_in_resp_activity);
        Intent intent=getIntent();
        String value=intent.getStringExtra("subject");
        subject.setText(value);

        reject=(Button)findViewById(R.id.resp_btn_reject);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Request has been accepted",Toast.LENGTH_SHORT).show();
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Request has been Rejected",Toast.LENGTH_SHORT).show();
            }
        });
        startconv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( response.this,ConversationBasic.class);
                startActivity(intent);
            }
        });
    }
}
