package com.facefive.meetbook;

import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.facefive.meetbook.fragments.EndTimePickerFragment;
import com.facefive.meetbook.fragments.TimePickerFragment;
import com.github.badoualy.datepicker.MonthView;

import java.util.Calendar;

public class request extends AppCompatActivity {

    EditText timePicker;
    EditText timePicker2;
    EditText subject;
    com.github.badoualy.datepicker.DatePickerTimeline datePickerTimeline;
    Button req_sub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        datePickerTimeline=findViewById(R.id.meet_req_date);
        datePickerTimeline.setFirstVisibleDate(2018, Calendar.JULY, 19);
        datePickerTimeline.setLastVisibleDate(2019, Calendar.JULY, 19);

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
       int month = c.get(Calendar.MONTH);
       int day = c.get(Calendar.DAY_OF_MONTH);



        datePickerTimeline.setFirstVisibleDate(year,month,day);
        datePickerTimeline.setDateLabelAdapter(new MonthView.DateLabelAdapter() {
            @Override
            public CharSequence getLabel(Calendar calendar, int index) {
                return Integer.toString(calendar.get(Calendar.MONTH) + 1) + "/" + (calendar.get(Calendar.YEAR) % 2000);
            }
        });


        timePicker=findViewById(R.id.start_time);
        timePicker2=findViewById(R.id.end_time);
        subject=findViewById(R.id.meet_req_sub);

        req_sub=findViewById(R.id.meetreq_sub_btn);

        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(),"TimePicker");

            }
        });
        timePicker2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new EndTimePickerFragment();
                newFragment.show(getFragmentManager(),"TimePicker");

            }
        });

        req_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               if(timePicker.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Please select Starting Time",Toast.LENGTH_SHORT).show();
                }

                else if(timePicker2.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Please select Ending Time",Toast.LENGTH_SHORT).show();
                }

                else if(!timePicker.getText().toString().equals("") && !timePicker.getText().toString().equals(""))
                {
                    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa"); // here set the pattern as you date in string was containing like date/month/year
                    try {
                        Date time1 = sdf.parse(timePicker.getText().toString());
                        Date time2 = sdf.parse(timePicker2.getText().toString());
                        if (time1.after(time2))
                        {
                            Toast.makeText(getApplicationContext(),"Please select a valid time",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                           String year= Integer.toString(datePickerTimeline.getSelectedYear());
                            String month= Integer.toString(datePickerTimeline.getSelectedMonth()+1);
                            String day= Integer.toString(datePickerTimeline.getSelectedDay());
                            new FancyAlertDialog.Builder(request.this)
                                    .setTitle("Request Is Ready to be sent")
                                    .setDate(year+"/"+month+"/"+day)
                                    .setStartTime(timePicker.getText().toString())
                                    .setEndTime(timePicker2.getText().toString())
                                    .setNegativeBtnText("Cancel")
                                    .setPositiveBtnText("Ok")
                                    .setAnimation(Animation.POP)
                                    .isCancellable(true)
                                    .setIcon(R.drawable.ic_star_border_black_24dp,Icon.Visible)
                                    .OnPositiveClicked(new FancyAlertDialogListener() {
                                        @Override
                                        public void OnClick() {
                                            Toast.makeText(request.this,"Request has been sent",Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .OnNegativeClicked(new FancyAlertDialogListener() {
                                        @Override
                                        public void OnClick() {
                                            Toast.makeText(request.this,"Cancel",Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .build();
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }


            }
        });

    }
}
