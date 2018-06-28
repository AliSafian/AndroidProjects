package com.facefive.meetbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facefive.meetbook.UserHandling.SessionManager;
import com.facefive.meetbook.activities.HomeActivity;
import com.facefive.meetbook.app.AppConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class response extends AppCompatActivity {

    Button startconv,accept,reject;
    TextView subject,name,tv_date,tv_starttime,tv_endtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);
        startconv=(Button)findViewById(R.id.resp_startconv);
        accept=(Button)findViewById(R.id.resp_btn_accept);
        subject=(TextView)findViewById(R.id.meet_subject_in_resp_activity);
        name=(TextView)findViewById(R.id.tv_resp_name);
        tv_date=(TextView)findViewById(R.id.tv_resp_date);
        tv_starttime=(TextView)findViewById(R.id.tv_resp_starttime);
        tv_endtime=(TextView)findViewById(R.id.tv_resp_endtime);
        Intent intent=getIntent();
        String value=intent.getStringExtra("subject");
        String namevalue=intent.getStringExtra("name");
        final String date=intent.getStringExtra("starttime").substring(0,10);;
        final String starttime=intent.getStringExtra("starttime").substring(11,19);
        String endtime=intent.getStringExtra("endtime").substring(11,19);
        final int MeetID=intent.getIntExtra("MeetID",0);
        subject.setText(value);
        name.setText(namevalue);
        tv_date.setText(date);
        tv_starttime.setText(starttime);
        tv_endtime.setText(endtime);

        reject=(Button)findViewById(R.id.resp_btn_reject);
        final Calendar c = Calendar.getInstance();
        int myear = c.get(Calendar.YEAR);
        int mmonth = c.get(Calendar.MONTH)+1;
        int mday = c.get(Calendar.DAY_OF_MONTH);
        int mhour=c.get(Calendar.HOUR_OF_DAY);
        int mminut=c.get(Calendar.MINUTE);
       final String responTime=myear+"-"+mmonth+"-"+mday+" "+mhour+":"+mminut+":"+"00";
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager manager=new SessionManager(getApplicationContext());
                // Toast.makeText(getApplicationContext(),""+MeetID,Toast.LENGTH_SHORT).show();

                ChangeMeetingStatus(MeetID,responTime,1);
               // Toast.makeText(getApplicationContext(),date+" "+starttime+":"+"00",Toast.LENGTH_SHORT).show();
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeMeetingStatus(MeetID,responTime,2);
                //Toast.makeText(getApplicationContext(),"Request has been Rejected",Toast.LENGTH_SHORT).show();
            }
        });
        startconv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( response.this,ConversationBasic.class);
                startActivity(intent);
            }
        });
    }
    public  void ChangeMeetingStatus(final int myMeetID, final String respTime,final int Status)
    {
        RequestQueue requestQueue= Volley.newRequestQueue(response.this);

        final StringRequest stringRequest=new StringRequest(Request.Method.POST, AppConfig.URL_CHANGEMEETINGSTATUS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    //Toast.makeText(getContext()," successfull"+ jsonObject,Toast.LENGTH_SHORT).show();

                    if(! jsonObject.getBoolean("Error"))
                    {
                        //JSONArray object=jsonObject.getJSONArray("result");
                        Toast.makeText(response.this,"Added To Fixed Meetings",Toast.LENGTH_SHORT).show();


                    }
                    else
                    {

                        Toast.makeText(response.this,jsonObject.getString("error_msg"),Toast.LENGTH_SHORT).show();
                    }

                    finishAffinity();
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(response.this,error.toString(),Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {



                Map<String, String> params = new HashMap<String, String>();

                params.put("MeetID",""+myMeetID);
                params.put("Status",Status+"");
                params.put("respTime",respTime);
                return  params;
            }
        };
        requestQueue.add(stringRequest);

    }
}
