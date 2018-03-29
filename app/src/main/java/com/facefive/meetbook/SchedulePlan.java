package com.facefive.meetbook;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import yuku.ambilwarna.AmbilWarnaDialog;

public class SchedulePlan extends AppCompatActivity {

    int currentColor;

    LinearLayout colorLayout;

    EditText timeFrom;
    EditText timeTo;



    int dateId;

    private DatePicker datePicker;
    private Calendar calendar;
    private EditText dateView1;
    private EditText dateView2;
    private int year, month, day;



    private Switch s;
    private EditText dateTo;
    private EditText dateFrom;
    private EditText time1;
    private EditText time2;
    Button editBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_plan);


        dateTo = (EditText) findViewById(R.id.dateto_et_scheduleplan_xml);
        dateFrom = (EditText) findViewById(R.id.datefrom_et_scheduleplan_xml);
        time1 = (EditText) findViewById(R.id.timeto_et_scheduleplan_xml);
        time2 = (EditText) findViewById(R.id.timefrom_et_scheduleplan_xml);

        dateTo.setEnabled(false);
        dateFrom.setEnabled(false);
        time1.setEnabled(false);
        time2.setEnabled(false);
        //................................Date picker

        dateView1 = (EditText) findViewById(R.id.datefrom_et_scheduleplan_xml);
        dateView2 = (EditText) findViewById(R.id.dateto_et_scheduleplan_xml);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
//        showDate(year, month+1, day);

        dateView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateId = v.getId();
                setDate(v);
            }
        });

        dateView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dateId=v.getId();
                setDate(v);
            }
        });

        editBtn = (Button) findViewById(R.id.editschedule_btn_scheduleplan_xml);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SchedulePlanSetting.class);
                startActivity(intent);
            }
        });

        timeFrom = (EditText) findViewById(R.id.timefrom_et_scheduleplan_xml);
        timeTo = (EditText) findViewById(R.id.timeto_et_scheduleplan_xml);

        timeFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(SchedulePlan.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timeFrom.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        timeTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(SchedulePlan.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timeTo.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        currentColor = ContextCompat.getColor(this, R.color.colorAccent);

        Button mhBtn = (Button) findViewById(R.id.meetinghourspicker_btn_scheduleplan_xml);
        Button lhBtn = (Button) findViewById(R.id.lecturehourspicker_btn_scheduleplan_xml);
        Button nahBtn = (Button) findViewById(R.id.notavailablehourspicker_btn_scheduleplan_xml);

        mhBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorLayout = (LinearLayout) findViewById(R.id.meetinghourscolorl_layout_scheduleplan_xml);
                openDialog(false);
            }
        });



        lhBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorLayout = (LinearLayout) findViewById(R.id.lecturehourscolorl_layout_scheduleplan_xml);
                openDialog(false);
            }
        });



        nahBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorLayout = (LinearLayout) findViewById(R.id.notavailablehourscolorl_layout_scheduleplan_xml);
                openDialog(false);
            }
        });



        //.........................Switch


        s = (Switch)findViewById(R.id.inavailability_swh_scheduleplan_xml);

        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if(s.isChecked())
                {
                    dateTo.setEnabled(true);
                    dateFrom.setEnabled(true);
                    time1.setEnabled(true);
                    time2.setEnabled(true);
                }
                else {
                    dateTo.setEnabled(false);
                    dateFrom.setEnabled(false);
                    time1.setEnabled(false);
                    time2.setEnabled(false);
                }

            }
        });




    }

    private void openDialog(boolean supportsAlpha) {
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(this, currentColor, supportsAlpha, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                currentColor = color;
                colorLayout.setBackgroundColor(color);
            }

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                Toast.makeText(getApplicationContext(), "Action canceled!", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }





    @SuppressWarnings("deprecation")
    public void setDate(View view) {

        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }

    private void showDate(int year, int month, int day) {

        EditText et = (EditText) findViewById(dateId);
        et.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }



    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };


}
