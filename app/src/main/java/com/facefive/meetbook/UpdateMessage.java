package com.facefive.meetbook;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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
import com.facefive.meetbook.activities.NewUpdateMessage;
import com.facefive.meetbook.app.AppConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UpdateMessage extends AppCompatActivity {

    ListView lv_listUpdatemessage;
    ArrayList<Integer> mylist;
    android.support.design.widget.FloatingActionButton plus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_message);


        lv_listUpdatemessage = (ListView) findViewById(R.id.lv_updatemessages);
        plus=findViewById(R.id.update_message_plusbtn);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),NewUpdateMessage.class);
                startActivity(intent);
            }
        });
        SessionManager manager=new SessionManager(getApplicationContext());
        getUpdateMessages(manager.getUserID());



      lv_listUpdatemessage.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,final int position, long id) {
                TextView v = (TextView) view.findViewById(R.id.update_message);
                new AlertDialog.Builder(UpdateMessage.this)
                        .setTitle(v.getText().toString())
                        .setMessage("Do you really want to delete?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                               deleteUpdateMessage(mylist.get(position));

                                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();


                // Toast.makeText(getApplicationContext(), "selected Item Name is " + v.getText()+position, Toast.LENGTH_SHORT).show();
               //

                return false;
            }
        });



    }

    public  void deleteUpdateMessage(final int messageID){



        RequestQueue requestQueue= Volley.newRequestQueue(this);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, AppConfig.URL_DELUPDATEMESSAGE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if(! jsonObject.getBoolean("error"))
                    {
                         Toast.makeText(getApplicationContext()," successfull"+jsonObject,Toast.LENGTH_SHORT).show();
                         SessionManager manager=new SessionManager(getApplicationContext());
                         getUpdateMessages(manager.getUserID());


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
                params.put("messageID",""+messageID);
                return  params;
            }
        };
        requestQueue.add(stringRequest);

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
                       // Toast.makeText(getApplicationContext()," successfull"+ array,Toast.LENGTH_SHORT).show();

                        ArrayList<String> list = new ArrayList<String>();
                        mylist=new ArrayList<>();
                        for (int i =2; i<array.length() ; i=i+2)
                        {

                            mylist.add(array.getInt(i-1));
                           // Toast.makeText(getApplicationContext(),""+mylist.get(0),Toast.LENGTH_SHORT).show();

                            list.add(""+array.get(i));

                        }

                        UpdatemessageListAdapter adapter = new UpdatemessageListAdapter(getApplicationContext(), list);
                        lv_listUpdatemessage.setAdapter(adapter);

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
