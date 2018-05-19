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
        String date=intent.getStringExtra("date");
        String starttime=intent.getStringExtra("starttime");
        String endtime=intent.getStringExtra("endtime");
        final int senderID=intent.getIntExtra("senderID",0);
        subject.setText(value);
        name.setText(namevalue);
        tv_date.setText(date);
        tv_starttime.setText(starttime);
        tv_endtime.setText(endtime);

        reject=(Button)findViewById(R.id.resp_btn_reject);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Request has been accepted"+senderID,Toast.LENGTH_SHORT).show();
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Request has been Rejected",Toast.LENGTH_SHORT).show();
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
    public  void ChangeMeetingStatus(final int ReceiverID,final int SenderID,final int Status)
    {



        RequestQueue requestQueue= Volley.newRequestQueue(response.this);

        final StringRequest stringRequest=new StringRequest(Request.Method.POST, AppConfig.URL_GETALLSENTMEETINGS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    //Toast.makeText(getContext()," successfull"+ jsonObject,Toast.LENGTH_SHORT).show();

                    if(! jsonObject.getBoolean("Error"))
                    {
                        JSONArray object=jsonObject.getJSONArray("result");
                        Toast.makeText(response.this,"Successfull",Toast.LENGTH_SHORT).show();


                    }
                    else
                    {

                        Toast.makeText(response.this,jsonObject.getString("error_msg"),Toast.LENGTH_SHORT).show();
                    }
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

                params.put("ReceiverID",""+ReceiverID);
                params.put("SenderID",""+SenderID);
                params.put("Status",""+1);
                return  params;
            }
        };
        requestQueue.add(stringRequest);

    }
}
