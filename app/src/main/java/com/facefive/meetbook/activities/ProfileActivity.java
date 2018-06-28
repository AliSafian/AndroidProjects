package com.facefive.meetbook.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facefive.meetbook.R;
import com.facefive.meetbook.TimetableSession.SlotSingleRow;
import com.facefive.meetbook.TimetableSession.TimetableDay;
import com.facefive.meetbook.TimetableSession.TimetableSession;
import com.facefive.meetbook.UserHandling.SessionManager;
import com.facefive.meetbook.app.AppConfig;
import com.facefive.meetbook.request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private TextView name_tv;
    private ImageView image;
    private ImageView meetReq;
    private ImageView follow;
    private TextView btn_req;
    private  TextView btn_sub;
    private  TextView followtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent i= getIntent();

       final String otherUserID = i.getStringExtra("UserID");

        CheckFollow(Integer.parseInt(otherUserID));
        String name = i.getStringExtra("name");
        int img_id = i.getIntExtra("img_id", R.drawable.ic_dp_demo);
        meetReq=findViewById(R.id.meetingreq_iv_profileactivity_aml);
        follow=findViewById(R.id.followreq_iv_profileactivity_aml);
        followtext=findViewById(R.id.followreq_tv_profileactivity_xml);
        name_tv= findViewById(R.id.name_tv_profileactivity_xml);

        name_tv= findViewById(R.id.name_tv_profileactivity_xml);
        image= findViewById(R.id.dp_iv_profileactivity_xml);
        btn_req= findViewById(R.id.meetingreq_tv_profileactivity_xml);
        btn_sub= findViewById(R.id.followreq_tv_profileactivity_xml);
        TextView tv_timetable_view= findViewById(R.id.tv_timetable_view_profile_xml);

        tv_timetable_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TimeTableViewActivity.class);
                intent.putExtra("userID", otherUserID);
                startActivity(intent);
            }
        });

        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFollow(Integer.parseInt(otherUserID));
            }
        });
        name_tv.setText(name);
        image.setImageResource(img_id);

        meetReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),request.class);
                intent.putExtra("ReceiverID",otherUserID);
                intent.putExtra("profile",true);
                startActivity(intent);
            }
        });

        btn_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), request.class);
                startActivity(i);
            }
        });




    }
    public  void CheckFollow(final int userid){
        RequestQueue requestQueue= Volley.newRequestQueue(this);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, AppConfig.URL_CHANGEFOLLOW, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);


                    if(! jsonObject.getBoolean("Error"))
                    {

                        if(jsonObject.getString("Status").equals("0"))
                        {
                            followtext.setTextColor(Color.RED);
                            followtext.setText("Followed");
                            follow.setImageResource(R.drawable.ic_following_acc);
                        }


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
                SessionManager manager=new SessionManager(getApplicationContext());
                params.put("FollowingID",""+userid);
                params.put("FollowerID",""+manager.getUserID());
                params.put("Status",""+1);
                // Toast.makeText(getApplicationContext(),jarray.toString(),Toast.LENGTH_SHORT).show();
                return  params;
            }
        };
        requestQueue.add(stringRequest);

    }
    public  void setFollow(final int userid){
        RequestQueue requestQueue= Volley.newRequestQueue(this);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, AppConfig.URL_CHANGEFOLLOW, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);


                    if(! jsonObject.getBoolean("Error"))
                    {

                        if(jsonObject.getString("Status").equals("0"))
                        {
                            followtext.setTextColor(Color.RED);
                            followtext.setText("Followed");
                            follow.setImageResource(R.drawable.ic_following_acc);
                        }
                        if(jsonObject.getString("Status").equals("1"))
                        {
                            followtext.setTextColor(Color.BLACK);
                            followtext.setText("Follow");
                            follow.setImageResource(R.drawable.ic_follow_req);
                        }

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
                SessionManager manager=new SessionManager(getApplicationContext());
                params.put("FollowingID",""+userid);
                params.put("FollowerID",""+manager.getUserID());
                params.put("Status",""+0);
                // Toast.makeText(getApplicationContext(),jarray.toString(),Toast.LENGTH_SHORT).show();
                return  params;
            }
        };
        requestQueue.add(stringRequest);

    }
    public void onBackPressed(){
        super.onBackPressed();
    }

}
