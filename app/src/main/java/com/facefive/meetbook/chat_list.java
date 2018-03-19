package com.facefive.meetbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class chat_list extends AppCompatActivity {

    RelativeLayout chat1;

    RelativeLayout chat2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_row_chat_item);

        chat1=(RelativeLayout)findViewById(R.id.all_meetings_request1);

        chat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( chat_list.this,ConversationBasic.class);
                startActivity(intent);

            }
        });
    }
}
