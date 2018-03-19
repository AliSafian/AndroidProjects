package com.facefive.meetbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    private TextView name_tv;
    private TextView email_tv;
    private ImageView image;
    private Button btn_req;
    private  Button btn_sub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent i= getIntent();

       String name = i.getStringExtra("name");
        String email = i.getStringExtra("email");
        int img_id = i.getIntExtra("img_id", R.drawable.ic_dp_demo);

        name_tv= findViewById(R.id.name);
        email_tv= findViewById(R.id.email);
        image= findViewById(R.id.iv_dp);
        btn_req= findViewById(R.id.meet_req);
        btn_sub= findViewById(R.id.subs);

        name_tv.setText(name);
        email_tv.setText(email);
        image.setImageResource(img_id);

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
