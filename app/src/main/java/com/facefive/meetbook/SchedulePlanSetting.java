package com.facefive.meetbook;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.facefive.meetbook.TimetableSession.TimetableSession;

import java.sql.Time;
import java.util.Calendar;

public class SchedulePlanSetting extends AppCompatActivity {








    private NumberPicker numberPickerp;

    private EditText startTime_et;
    private EditText endTime_et;

    private CheckBox monday;
    private CheckBox tuesday;
    private CheckBox wednesday;
    private CheckBox thursday;
    private CheckBox friday;
    private CheckBox saturday;
    private CheckBox sunday;

    private Button nextBtn;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_plan_setting);




        //...................................Time Picker

        startTime_et = (EditText) findViewById(R.id.timefrom_et_scheduleplan_xml);
        endTime_et = (EditText) findViewById(R.id.timeto_et_scheduleplan_xml);


        startTime_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(SchedulePlanSetting.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        startTime_et.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                        TimetableSession.startTime.setHours((Integer) selectedHour);
                        TimetableSession.startTime.setMinutes((Integer) selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        endTime_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(SchedulePlanSetting.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        endTime_et.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                        TimetableSession.endTime.setHours((Integer) selectedHour);
                        TimetableSession.endTime.setMinutes((Integer) selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });





        //.........................slot number picker..............................

        final NumberPicker numberPicker = (NumberPicker) findViewById(R.id.sspd_np_scheduleplansetting_xml);

        //Populate NumberPicker values from minimum and maximum value range
        //Set the minimum value of NumberPicker
        numberPicker.setMinValue(2);
        //Specify the maximum value/number of NumberPicker
        numberPicker.setMaxValue(10);

        //Gets whether the selector wheel wraps when reaching the min/max value.
         numberPicker.setWrapSelectorWheel(true);



        //............................ getting data for next activity


        monday = (CheckBox) findViewById(R.id.monday_cb_scheduleplansetting_xml);
        tuesday = (CheckBox) findViewById(R.id.tuesday_cb_scheduleplansetting_xml);
        wednesday = (CheckBox) findViewById(R.id.wednesday_cb_scheduleplansetting_xml);
        thursday = (CheckBox) findViewById(R.id.thursday_cb_scheduleplansetting_xml);
        friday= (CheckBox) findViewById(R.id.friday_cb_scheduleplansetting_xml);
        saturday = (CheckBox) findViewById(R.id.saturday_cb_scheduleplansetting_xml);
        sunday = (CheckBox) findViewById(R.id.monday_cb_scheduleplansetting_xml);


        //........................................go to next........................


        nextBtn = (Button) findViewById(R.id.next_btn_scheduleplansetting_xml);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected number from picker
                TimetableSession.noOfSlots = newVal;
            }
        });


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimetableSession.Days.clear();

                if(monday.isChecked())
                    TimetableSession.Days.add("Monday");
                if(tuesday.isChecked())
                    TimetableSession.Days.add("Tuesday");
                if(wednesday.isChecked())
                    TimetableSession.Days.add("Wednesday");
                if(thursday.isChecked())
                    TimetableSession.Days.add("Thursday");
                if(friday.isChecked())
                    TimetableSession.Days.add("Friday");
                if(saturday.isChecked())
                    TimetableSession.Days.add("Saturday");
                if(sunday.isChecked())
                    TimetableSession.Days.add("Sunday");

                Intent intent = new Intent(getApplicationContext(), SchedualPlanSlotSetting.class);
                startActivity(intent);
            }
        });


    }
}
