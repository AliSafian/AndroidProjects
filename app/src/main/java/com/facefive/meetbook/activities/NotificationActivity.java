package com.facefive.meetbook.activities;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.facefive.meetbook.fragments.FragmentLight;
import com.facefive.meetbook.fragments.FragmentPopUp;
import com.facefive.meetbook.fragments.FragmentVibrate;
import com.facefive.meetbook.R;
import com.facefive.meetbook.my_dialog;


public class NotificationActivity extends AppCompatActivity {

    private RelativeLayout rl_3;
    private RelativeLayout rl_4;
    private RelativeLayout rl_5;
    private RelativeLayout rl_6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rl_4=findViewById(R.id.rl_4);
        rl_3=findViewById(R.id.rl_3);
        rl_5=findViewById(R.id.rl_5);
        rl_6=findViewById(R.id.rl_6);

        rl_3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                    FragmentManager manager = getFragmentManager();
                    my_dialog myDialog = new my_dialog();
                    myDialog.show(manager, "my fragment");

            }
        });
        rl_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager1 = getFragmentManager();
                FragmentVibrate fmvib=new FragmentVibrate();
                fmvib.show(manager1,"my frag");


            }
        });
        rl_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager2=getFragmentManager();
                FragmentPopUp fm_pop=new FragmentPopUp();
                fm_pop.show(manager2,"my fragment 2");
            }
        });
        rl_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager4=getFragmentManager();
                FragmentLight fm_light=new FragmentLight();
                fm_light.show(manager4,"hello");
            }
        });

    }

}
