package com.facefive.meetbook.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.facefive.meetbook.R;


public class SendUpdateActivity extends AppCompatActivity {


    private Button btn;
    private EditText update_et;
    private String msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_update);

        btn = (Button) findViewById(R.id.btn_publish);
        update_et = (EditText) findViewById(R.id.et_update_msg);

        btn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                msg = update_et.getText().toString();
                if(!msg.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Update Succesfully Sent",Toast.LENGTH_SHORT).show();
                    update_et.setText("");
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Cannnot send an empty message",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
