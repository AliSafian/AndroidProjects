package com.facefive.meetbook;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UpdateMessage extends AppCompatActivity {

    ListView lv_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_message);


        lv_list = (ListView) findViewById(R.id.lv_updatemessages);

        getUpdateMessages(18);
        ArrayList<String> list = new ArrayList<String>();
        for (int i =0; i<4 ; i++)
        {

            list.add("My message");

        }

        UpdatemessageListAdapter adapter = new UpdatemessageListAdapter(getApplicationContext(), list);
        lv_list.setAdapter(adapter);
        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent i = new Intent(getApplicationContext() , request.class );
                startActivity(i);
            }
        });

    }

    public  void getUpdateMessages( final int UserID){



        RequestQueue requestQueue= Volley.newRequestQueue(this);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, AppConfig.URL_GETUPDATEMESSAGES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if(! jsonObject.getBoolean("error"))
                    {
                        JSONArray array=jsonObject.getJSONArray("messages");
                        //Toast.makeText(getApplicationContext()," successfull"+ array.get(2),Toast.LENGTH_SHORT).show();

                        ArrayList<String> list = new ArrayList<String>();
                        for (int i =1; i<array.length() ; i++)
                        {

                            list.add(""+array.get(i));

                        }

                        UpdatemessageListAdapter adapter = new UpdatemessageListAdapter(getApplicationContext(), list);
                        lv_list.setAdapter(adapter);
                        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        });

                        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                                Intent i = new Intent(getApplicationContext() , request.class );
                                startActivity(i);
                            }
                        });

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

                // Toast.makeText(getApplicationContext(),jarray.toString(),Toast.LENGTH_SHORT).show();
                params.put("UserID",""+UserID);
                return  params;
            }
        };
        requestQueue.add(stringRequest);

    }
}
