package com.facefive.meetbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;

public class TimetableTypeSelectionActivity extends AppCompatActivity {

    Button btn_goo;
    RadioButton rb_ch;
    RadioButton rb_lh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable_type_selection);

        //AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);



        btn_goo = (Button) findViewById(R.id.ok_btn_timetabletypeselection_xml);
        rb_ch = (RadioButton) findViewById(R.id.rb_ch);
        rb_lh = (RadioButton) findViewById(R.id.rb_lh);


        btn_goo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(rb_ch.isChecked())
                {
                    Intent i = new Intent(TimetableTypeSelectionActivity.this,SetCounsellingHoursActivity.class);

                    int tv_no = 0;
                    i.putExtra("string","");
                    i.putExtra("tv_no",0);
                    startActivity(i);

                }
                else if (rb_lh.isChecked())
                {
                    Intent i = new Intent(TimetableTypeSelectionActivity.this,SetLectureHoursActivity.class);
                    startActivity(i);
                }
            }
        });



    }
}
