package com.facefive.meetbook.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facefive.meetbook.R;
import com.facefive.meetbook.request;

public class ProfileActivity extends AppCompatActivity {

    private TextView name_tv;
    private ImageView image;
    private ImageView meetReq;
    private TextView btn_req;
    private  TextView btn_sub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent i= getIntent();

       final String receiverID = i.getStringExtra("UserID");

        String name = i.getStringExtra("name");
        int img_id = i.getIntExtra("img_id", R.drawable.ic_dp_demo);
        meetReq=findViewById(R.id.meetingreq_iv_profileactivity_aml);
       name_tv= findViewById(R.id.name_tv_profileactivity_xml);

        name_tv= findViewById(R.id.name_tv_profileactivity_xml);
        image= findViewById(R.id.dp_iv_profileactivity_xml);
        btn_req= findViewById(R.id.meetingreq_tv_profileactivity_xml);
        btn_sub= findViewById(R.id.followreq_tv_profileactivity_xml);

        name_tv.setText(name);
        image.setImageResource(img_id);

        meetReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),request.class);
                intent.putExtra("ReceiverID",receiverID);
                intent.putExtra("profile",true);
                startActivity(intent);
            }
        });

        btn_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), request.class);
                startActivity(i);
            }
        });

        btn_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn_sub.getText().toString().equals("subscribe"))
                {
                    btn_sub.setText("subscribed");
                }
                else
                {
                    btn_sub.setText("subscribe");
                }
            }
        });


    }
    public void onBackPressed(){
        super.onBackPressed();
    }

}
