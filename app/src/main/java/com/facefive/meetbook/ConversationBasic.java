package com.facefive.meetbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class ConversationBasic extends AppCompatActivity {

    ImageView backpress;
    ListView conv_list;
    ImageView sendmessage;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_basic);
        final ArrayList<String> message=new ArrayList<>();

        backpress=(ImageView) findViewById(R.id.conv_back_press);
        conv_list=(ListView) findViewById(R.id.list_view);
        sendmessage=(ImageView) findViewById(R.id.image_chatbox_send);
        editText=(EditText)findViewById(R.id.text_chatbox);


        message.add(editText.getText().toString());


        sendmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conv_list.setAdapter(new MessageAdapter(ConversationBasic.this,message,editText.getText().toString()));
            }
        });


        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

}
