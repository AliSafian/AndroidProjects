package com.facefive.meetbook;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facefive.meetbook.TimetableSession.TimetableSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class NewUpdateMessage extends AppCompatActivity {

    EditText datetext,timetext;
    Button saveandupdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_update_message);
        datetext = findViewById(R.id.update_message_seldate);
        timetext = findViewById(R.id.update_message_seltime);
        saveandupdate = findViewById(R.id.btn_updatemsgpublish);
        timetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(NewUpdateMessage.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {


                        timetext.setText(String.format("%02d:%02d", selectedHour, selectedMinute));



                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.show();

            }
        });



        datetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(NewUpdateMessage.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                datetext.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        // Get Current Date
        saveandupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        saveNewUpdateMessage(18);
    }

    public  void saveNewUpdateMessage( final int UserID){


Toast.makeText(getApplicationContext(),datetext.getText().toString()+" "+timetext.getText().toString(),Toast.LENGTH_SHORT).show();
        RequestQueue requestQueue= Volley.newRequestQueue(this);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, AppConfig.URL_SAVEUPDATEMESSAGE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if(! jsonObject.getBoolean("error"))
                    {
                        JSONArray array=jsonObject.getJSONArray("messages");
                        //Toast.makeText(getApplicationContext()," successfull"+ array.get(2),Toast.LENGTH_SHORT).show();


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
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
               String endtime=datetext.getText().toString()+" "+timetext.getText().toString()+":00";
               String starttime=mYear + "-" + (mMonth + 1) + "-" + mDay+ " "+c.get(Calendar.HOUR)+c.get(Calendar.MINUTE)+":00";

                JSONObject object=new JSONObject();
                try {
                    object.put("UserID",UserID);
                    object.put("endtime",endtime);
                    object.put("starttime",starttime);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Map<String, String> params = new HashMap<String, String>();
                // Toast.makeText(getApplicationContext(),jarray.toString(),Toast.LENGTH_SHORT).show();
                params.put("params",""+object.toString());
                return  params;
            }
        };
        requestQueue.add(stringRequest);

    }



}

