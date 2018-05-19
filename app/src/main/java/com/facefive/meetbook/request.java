package com.facefive.meetbook;

import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.media.Ringtone;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facefive.meetbook.app.AppConfig;
import com.github.badoualy.datepicker.DatePickerTimeline;

import com.facefive.meetbook.fragments.EndTimePickerFragment;
import com.facefive.meetbook.fragments.TimePickerFragment;
import com.github.badoualy.datepicker.MonthView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class request extends AppCompatActivity {

    EditText timePicker1;
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


        timePicker1=findViewById(R.id.start_time);
        timePicker2=findViewById(R.id.end_time);
        subject=findViewById(R.id.meet_req_sub);

        req_sub=findViewById(R.id.meetreq_sub_btn);

        timePicker1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(request.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timePicker1.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        timePicker2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(request.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timePicker2.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        req_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               if(timePicker1.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Please select Starting Time",Toast.LENGTH_SHORT).show();
                }

                else if(timePicker2.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Please select Ending Time",Toast.LENGTH_SHORT).show();
                }

                else
                {
                    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa"); // here set the pattern as you date in string was containing like date/month/year
                    try {
                        Date time1 = sdf.parse(timePicker1.getText().toString());
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
                                    .setStartTime(timePicker1.getText().toString())
                                    .setEndTime(timePicker2.getText().toString())
                                    .setNegativeBtnText("Cancel")
                                    .setPositiveBtnText("Ok")
                                    .setAnimation(Animation.POP)
                                    .isCancellable(true)
                                    .setIcon(R.drawable.ic_star_border_black_24dp,Icon.Visible)
                                    .OnPositiveClicked(new FancyAlertDialogListener() {
                                        @Override
                                        public void OnClick() {

                                           // SendMeetingRequest(18,18,0);
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

    public  void SendMeetingRequest(final int ReceiverID,final int SenderID,final int Status)
    {
        RequestQueue requestQueue= Volley.newRequestQueue(request.this);

        final StringRequest stringRequest=new StringRequest(Request.Method.POST, AppConfig.URL_CHANGEMEETINGSTATUS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    //Toast.makeText(getContext()," successfull"+ jsonObject,Toast.LENGTH_SHORT).show();

                    if(! jsonObject.getBoolean("Error"))
                    {
                        JSONArray object=jsonObject.getJSONArray("result");
                        Toast.makeText(request.this,object.toString(),Toast.LENGTH_SHORT).show();


                    }
                    else
                    {

                        Toast.makeText(request.this,jsonObject.getString("error_msg"),Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(request.this,error.toString(),Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {



                Map<String, String> params = new HashMap<String, String>();

                params.put("ReceiverID",""+ReceiverID);
                params.put("SenderID",""+SenderID);
                params.put("Status",Status+"");
                return  params;
            }
        };
        requestQueue.add(stringRequest);

    }

}
