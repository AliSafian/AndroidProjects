package com.facefive.meetbook;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.media.MediaCas;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facefive.meetbook.TimetableSession.SlotSingleRow;
import com.facefive.meetbook.TimetableSession.TimetableDay;
import com.facefive.meetbook.TimetableSession.TimetableSession;
import com.facefive.meetbook.UserHandling.UserSessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SchedulePlanSetting extends AppCompatActivity {








    private NumberPicker numberPicker;

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


    private Time tempStart;
    private Time tempEnd;
    private int numOfSlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_plan_setting);


        UserSessionManager userSessionManager=new UserSessionManager(getApplicationContext());

        getTimeTable(userSessionManager.getUserID());





        //...................................Time Picker
        tempStart = new Time(0,0,0);
        tempEnd = new Time(0,0,0);

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

                        tempStart.setHours(selectedHour);
                        tempStart.setMinutes(selectedMinute);

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

                        tempEnd.setHours(selectedHour);
                        tempEnd.setMinutes(selectedMinute);

                       if(calMinutes(tempStart) < calMinutes(tempEnd))
                        {

                            endTime_et.setError(null);
                            endTime_et.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                            TimetableSession.endTime.setHours((Integer) selectedHour);
                            TimetableSession.endTime.setMinutes((Integer) selectedMinute);

                        }
                        else{
                            endTime_et.setError("End time should be greater than start time.");
                        }
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });





        //.........................slot number picker..............................

        numberPicker = (NumberPicker) findViewById(R.id.sspd_np_scheduleplansetting_xml);

        //Populate NumberPicker values from minimum and maximum value range
        //Set the minimum value of NumberPicker
        numberPicker.setMinValue(1);
        //Specify the maximum value/number of NumberPicker
        numberPicker.setMaxValue(10);

        //Gets whether the selector wheel wraps when reaching the min/max value.
         numberPicker.setWrapSelectorWheel(true);

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected number from picker
                TimetableSession.noOfSlots = newVal;
                numOfSlot = newVal;
            }
        });

        //............................ getting data for next activity


        monday = (CheckBox) findViewById(R.id.monday_cb_scheduleplansetting_xml);
        tuesday = (CheckBox) findViewById(R.id.tuesday_cb_scheduleplansetting_xml);
        wednesday = (CheckBox) findViewById(R.id.wednesday_cb_scheduleplansetting_xml);
        thursday = (CheckBox) findViewById(R.id.thursday_cb_scheduleplansetting_xml);
        friday= (CheckBox) findViewById(R.id.friday_cb_scheduleplansetting_xml);
        saturday = (CheckBox) findViewById(R.id.saturday_cb_scheduleplansetting_xml);
        sunday = (CheckBox) findViewById(R.id.sunday_cb_scheduleplansetting_xml);


        //........................................go to next........................


        nextBtn = (Button) findViewById(R.id.next_btn_scheduleplansetting_xml);



        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if(TimetableSession.Days.size()!=0)
                if(numOfSlot <= 0)
                {
                    Toast.makeText(getApplicationContext(),"Please! select number of slots per day.",Toast.LENGTH_SHORT).show();
                }
                else if(startTime_et.getText().toString().contains("") && endTime_et.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Please! select start time and end time both.",Toast.LENGTH_SHORT).show();

                }
                else if((calMinutes(TimetableSession.endTime)-calMinutes(TimetableSession.startTime))/numOfSlot < 30)
                {
                    endTime_et.setError("Please! select the valid end working time");
                    Toast.makeText(getApplicationContext(),"Slot duration should be greater than or equal to 30 minutes.",Toast.LENGTH_SHORT).show();
                }
                else if((monday.isChecked() == false)&&(tuesday.isChecked() == false)&&(wednesday.isChecked() == false)&&(thursday.isChecked() == false)&&(friday.isChecked() == false)&&(saturday.isChecked() == false)&&(sunday.isChecked() == false))
                {
                    Toast.makeText(getApplicationContext(),"Please! select working day(s).",Toast.LENGTH_SHORT).show();
                }
                else {
                    TimetableSession.Days.clear();

                    int dur=(calMinutes(TimetableSession.endTime)-calMinutes(TimetableSession.startTime))/numOfSlot;

                    TimetableSession.duration = new Time(dur/60, dur%60,0);


                    if(monday.isChecked()) {
                        TimetableDay td = new TimetableDay("Monday",TimetableSession.noOfSlots,tempStart,tempEnd);
                        TimetableSession.Days.add(td);
                    }
                    if(tuesday.isChecked()) {
                        TimetableDay td = new TimetableDay("Tuesday",TimetableSession.noOfSlots,tempStart,tempEnd);
                        TimetableSession.Days.add(td);
                    }
                    if(wednesday.isChecked()) {
                        TimetableDay td = new TimetableDay("Wednesday",TimetableSession.noOfSlots,tempStart,tempEnd);
                        TimetableSession.Days.add(td);
                    }
                    if(thursday.isChecked()) {
                        TimetableDay td = new TimetableDay("Thursday",TimetableSession.noOfSlots,tempStart,tempEnd);
                        TimetableSession.Days.add(td);
                    }
                    if(friday.isChecked()) {
                        TimetableDay td = new TimetableDay("Friday",TimetableSession.noOfSlots,tempStart,tempEnd);
                        TimetableSession.Days.add(td);
                    }
                    if(saturday.isChecked()){
                        TimetableDay td = new TimetableDay("Saturday",TimetableSession.noOfSlots,tempStart,tempEnd);
                        TimetableSession.Days.add(td);
                    }
                    if(sunday.isChecked()) {
                        TimetableDay td = new TimetableDay("Sunday",TimetableSession.noOfSlots,tempStart,tempEnd);
                        TimetableSession.Days.add(td);
                    }


                    Intent intent = new Intent(getApplicationContext(), SchedualPlanSlotSetting.class);
                    startActivity(intent);

                }


            }
        });


    }


    public  void getTimeTable(final int userid){



        RequestQueue requestQueue= Volley.newRequestQueue(this);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, AppConfig.URL_GETTIMETABLE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if(! jsonObject.getBoolean("error"))
                    {
                        startTime_et.setText(jsonObject.getJSONObject("item").get("StartTime").toString());
                        endTime_et.setText(jsonObject.getJSONObject("item").get("EndTime").toString());
                        numberPicker.setValue(jsonObject.getJSONObject("item").getInt("SlotsPerDay"));

                        Toast.makeText(getApplicationContext()," successfull"+jsonObject.getJSONObject("item").get("TID"),Toast.LENGTH_SHORT).show();

                    }
                    else
                    {

                        Toast.makeText(getApplicationContext(),jsonObject.getString("error_msg"),Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {



                Map<String, String> params = new HashMap<String, String>();
                    params.put("userID",""+userid);
                // Toast.makeText(getApplicationContext(),jarray.toString(),Toast.LENGTH_SHORT).show();
                return  params;
            }
        };
        requestQueue.add(stringRequest);

    }



    private  Time getTime(int m)
    {
        Time t = new Time(0,0,0);
        t.setHours(m/60);
        t.setMinutes(m%60);
        return t;
    }
    private int calMinutes(Time t)
    {
        int m=0;
        m = t.getHours()*60+t.getMinutes();
        return m;
    }
}
