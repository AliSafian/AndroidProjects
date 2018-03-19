package com.facefive.meetbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class SetCounsellingHoursActivity extends AppCompatActivity {


    CheckBox cb_1;
    CheckBox cb_2;
    CheckBox cb_3;
    CheckBox cb_4;
    CheckBox cb_5;

    TextView tv_1;
    TextView tv_2;
    TextView tv_3;
    TextView tv_4;
    TextView tv_5;

    Button btn_edit1;
    Button btn_edit2;
    Button btn_edit3;
    Button btn_edit4;
    Button btn_edit5;

    Button btn_sub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_counselling_hours);

        int tv_no = getIntent().getIntExtra("tv_no",0);
        String str = getIntent().getStringExtra("string");



        if(tv_no == 1)
        {    tv_1.setText(str);
        }
        else if(tv_no == 2)
        {
            tv_2.setText(str);
        }
        else if(tv_no == 3)
        {
            tv_3.setText(str);
        }
        else if(tv_no == 4)
        {
            tv_4.setText(str);
        }
        else if(tv_no == 5)
        {
            tv_5.setText(str);
        }


        cb_1 = (CheckBox) findViewById(R.id.mon_cb_setcounsellinghours_xml);
        cb_2 = (CheckBox) findViewById(R.id.tue_cb_setcounsellinghours_xml);
        cb_3 = (CheckBox) findViewById(R.id.wed_cb_setcounsellinghours_xml);
        cb_4 = (CheckBox) findViewById(R.id.thu_cb_setcounsellinghours_xml);
        cb_5 = (CheckBox) findViewById(R.id.fri_cb_setcounsellinghours_xml);

        tv_1 = (TextView) findViewById(R.id.mon_tv_setcounsellinghour_xml);
        tv_2 = (TextView) findViewById(R.id.tue_tv_setcounsellinghour_xml);
        tv_3 = (TextView) findViewById(R.id.wed_tv_setcounsellinghour_xml);
        tv_4 = (TextView) findViewById(R.id.thu_tv_setcounsellinghour_xml);
        tv_5 = (TextView) findViewById(R.id.fri_tv_setcounsellinghour_xml);

        btn_edit1 = (Button) findViewById(R.id.editmon_btn_setcounsellinghour_xml);
        btn_edit2 = (Button) findViewById(R.id.edittue_btn_setcounsellinghour_xml);
        btn_edit3 = (Button) findViewById(R.id.editwed_btn_setcounsellinghour_xml);
        btn_edit4 = (Button) findViewById(R.id.editthu_btn_setcounsellinghour_xml);
        btn_edit5 = (Button) findViewById(R.id.editfri_btn_setcounsellinghour_xml);

        btn_sub = (Button) findViewById(R.id.submit_btn_setcounsellinghour_xml);





        btn_edit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),SetHoursActivity.class);
                int tv_no = 1;
                i.putExtra("tv_no",tv_no);
                startActivity(i);
            }
        });
        btn_edit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),SetHoursActivity.class);
                int tv_no = 2;
                i.putExtra("tv_no",tv_no);
                startActivity(i);
            }
        });
        btn_edit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),SetHoursActivity.class);
                int tv_no = 3;
                i.putExtra("tv_no",tv_no);
                startActivity(i);
            }
        });
        btn_edit4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),SetHoursActivity.class);
                int tv_no = 4;
                i.putExtra("tv_no",tv_no);
                startActivity(i);
            }
        });
        btn_edit5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),SetHoursActivity.class);
                int tv_no = 5;
                i.putExtra("tv_no",tv_no);
                startActivity(i);
            }
        });



    }
}
