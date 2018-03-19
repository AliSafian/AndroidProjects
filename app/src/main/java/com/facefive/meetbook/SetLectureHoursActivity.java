package com.facefive.meetbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class SetLectureHoursActivity extends AppCompatActivity {


    RadioButton rb_mon;
    RadioButton rb_tue;
    RadioButton rb_wed;
    RadioButton rb_thu;
    RadioButton rb_fri;

    TextView tv_11;
    TextView tv_12;
    TextView tv_13;
    TextView tv_14;
    TextView tv_21;
    TextView tv_22;
    TextView tv_23;
    TextView tv_24;
    TextView tv_31;
    TextView tv_32;
    TextView tv_33;
    TextView tv_34;
    TextView tv_41;
    TextView tv_42;
    TextView tv_43;
    TextView tv_44;
    TextView tv_51;
    TextView tv_52;
    TextView tv_53;
    TextView tv_54;


    Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_lecture_hours);




        rb_mon = (RadioButton) findViewById(R.id.rb_mon);
        rb_tue = (RadioButton) findViewById(R.id.rb_tue);
        rb_wed = (RadioButton) findViewById(R.id.rb_wed);
        rb_thu = (RadioButton) findViewById(R.id.rb_thu);
        rb_fri = (RadioButton) findViewById(R.id.rb_fri);


        tv_11 = (TextView) findViewById(R.id.st_11);
        tv_12 = (TextView) findViewById(R.id.st_12);
        tv_13 = (TextView) findViewById(R.id.st_13);
        tv_14 = (TextView) findViewById(R.id.st_14);
        tv_21 = (TextView) findViewById(R.id.st_21);
        tv_22 = (TextView) findViewById(R.id.st_22);
        tv_23 = (TextView) findViewById(R.id.st_23);
        tv_24 = (TextView) findViewById(R.id.st_24);
        tv_31 = (TextView) findViewById(R.id.st_31);
        tv_32 = (TextView) findViewById(R.id.st_32);
        tv_33 = (TextView) findViewById(R.id.st_33);
        tv_34 = (TextView) findViewById(R.id.st_34);
        tv_41 = (TextView) findViewById(R.id.st_41);
        tv_42 = (TextView) findViewById(R.id.st_42);
        tv_43 = (TextView) findViewById(R.id.st_43);
        tv_44 = (TextView) findViewById(R.id.st_44);
        tv_51 = (TextView) findViewById(R.id.st_51);
        tv_52 = (TextView) findViewById(R.id.st_52);
        tv_53 = (TextView) findViewById(R.id.st_53);
        tv_54 = (TextView) findViewById(R.id.st_54);



        tv_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),SetSlotActivity.class);
                i.putExtra("day",rb_mon.getText().toString());
                startActivity(i);
            }
        });
        tv_12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),SetSlotActivity.class);
                i.putExtra("day",rb_mon.getText().toString());
                startActivity(i);
            }
        });
        tv_13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),SetSlotActivity.class);
                i.putExtra("day",rb_mon.getText().toString());
                startActivity(i);
            }
        });
        tv_14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),SetSlotActivity.class);
                i.putExtra("day",rb_mon.getText().toString());
                startActivity(i);
            }
        });
        tv_21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),SetSlotActivity.class);
                i.putExtra("day",rb_tue.getText().toString());
                startActivity(i);
            }
        });
        tv_22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),SetSlotActivity.class);
                i.putExtra("day",rb_tue.getText().toString());
                startActivity(i);
            }
        });
        tv_23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),SetSlotActivity.class);
                i.putExtra("day",rb_tue.getText().toString());
                startActivity(i);
            }
        });
        tv_24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),SetSlotActivity.class);
                i.putExtra("day",rb_tue.getText().toString());
                startActivity(i);
            }
        });
        tv_31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),SetSlotActivity.class);
                i.putExtra("day",rb_wed.getText().toString());
                startActivity(i);
            }
        });
        tv_32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),SetSlotActivity.class);
                i.putExtra("day",rb_wed.getText().toString());
                startActivity(i);
            }
        });
        tv_33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),SetSlotActivity.class);
                i.putExtra("day",rb_wed.getText().toString());
                startActivity(i);
            }
        });
        tv_34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),SetSlotActivity.class);
                i.putExtra("day",rb_wed.getText().toString());
                startActivity(i);
            }
        });
        tv_41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),SetSlotActivity.class);
                i.putExtra("day",rb_thu.getText().toString());
                startActivity(i);
            }
        });
        tv_42.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),SetSlotActivity.class);
                i.putExtra("day",rb_thu.getText().toString());
                startActivity(i);
            }
        });
        tv_43.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),SetSlotActivity.class);
                i.putExtra("day",rb_thu.getText().toString());
                startActivity(i);
            }
        });
        tv_44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),SetSlotActivity.class);
                i.putExtra("day",rb_thu.getText().toString());
                startActivity(i);
            }
        });
        tv_51.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),SetSlotActivity.class);
                i.putExtra("day",rb_fri.getText().toString());
                startActivity(i);
            }
        });
        tv_52.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),SetSlotActivity.class);
                i.putExtra("day",rb_fri.getText().toString());
                startActivity(i);
            }
        });
        tv_53.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),SetSlotActivity.class);
                i.putExtra("day",rb_fri.getText().toString());
                startActivity(i);
            }
        });
        tv_54.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),SetSlotActivity.class);
                i.putExtra("day",rb_fri.getText().toString());
                startActivity(i);
            }
        });
    }
}
