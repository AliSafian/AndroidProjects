package com.facefive.meetbook.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.facefive.meetbook.ListAdapter;
import com.facefive.meetbook.MeetingListAdapter;
import com.facefive.meetbook.MeetingSingleRow;
import com.facefive.meetbook.R;
import com.facefive.meetbook.SingleRow;
import com.facefive.meetbook.TimetableSession.SlotSingleRow;
import com.facefive.meetbook.TimetableSession.TimetableDay;
import com.facefive.meetbook.TimetableSession.TimetableSession;
import com.facefive.meetbook.UpdateMessage;
import com.facefive.meetbook.UserHandling.SessionManager;
import com.facefive.meetbook.app.AppConfig;
import com.facefive.meetbook.request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ashar on 1/20/2018.
 */

public class SentMeetingFragment extends Fragment {
    ListView lv_list;
    ArrayList<MeetingSingleRow> list;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sent_meeting , container, false);

        lv_list = (ListView) view.findViewById(R.id.lv_meeting_sent);
        SessionManager manager=new SessionManager(getContext());

        getAllSentMeetings(manager.getUserID());


        Resources res = this.getResources();
        String[] names = res.getStringArray(R.array.names);

       // int [] images ={R.drawable.fareed,R.drawable.usama,R.drawable.shahid,R.drawable.amina,R.drawable.saira,R.drawable.ali,R.drawable.ashar};
   /*     list = new ArrayList<SingleRow>();
        for (int i =0; i<7 ; i++)
        {

            SingleRow row = new SingleRow(names[i] , "You sent meeting at 16:00", images[i]);
            list.add(row);

        }

        ListAdapter adapter = new ListAdapter(getActivity(), list);
        lv_list.setAdapter(adapter);*/



        lv_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,final int position, long id) {

                new AlertDialog.Builder(getContext())
                        .setTitle("Title")
                        .setMessage("Do you really want to delete?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {

                                DeleteSentMeeting(list.get(position).MeetID,2);
                               // Toast.makeText(getContext(), "You pressed long time", Toast.LENGTH_SHORT).show();
                            }}).setNegativeButton(android.R.string.cancel,null)
                        .setNeutralButton("Edit Meeting", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {

                                Intent intent=new Intent(getContext(),request.class);
                                intent.putExtra("flage",true);
                                intent.putExtra("purpose",list.get(position).purpose);
                                intent.putExtra("starttime",list.get(position).starttime);
                                intent.putExtra("endtime",list.get(position).endtime);
                                intent.putExtra("MeetID",list.get(position).MeetID);
                                intent.putExtra("senderID",list.get(position).senderID);
                                intent.putExtra("receiverID",list.get(position).receiverID);
                                startActivity(intent);
                            }}).show();


                // Toast.makeText(getApplicationContext(), "selected Item Name is " + v.getText()+position, Toast.LENGTH_SHORT).show();
                //

                return false;
            }
        });

        return view;
    }
    public  void DeleteSentMeeting(final int MeetID,final int Status)
    {
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());

        final StringRequest stringRequest=new StringRequest(Request.Method.POST, AppConfig.URL_CHANGEMEETINGSTATUS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    //Toast.makeText(getContext()," successfull"+ jsonObject,Toast.LENGTH_SHORT).show();

                    if(! jsonObject.getBoolean("Error"))
                    {
                        //JSONArray object=jsonObject.getJSONArray("result");
                        Toast.makeText(getContext(),jsonObject.toString(),Toast.LENGTH_SHORT).show();


                    }
                    else
                    {

                        Toast.makeText(getContext(),jsonObject.getString("error_msg"),Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {



                Map<String, String> params = new HashMap<String, String>();

                params.put("MeetID",""+MeetID);
                params.put("Status",Status+"");
                return  params;
            }
        };
        requestQueue.add(stringRequest);

    }
    public  void getAllSentMeetings(final int SenderID)
    {
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());

        StringRequest stringRequest=new StringRequest(Request.Method.POST, AppConfig.URL_GETALLSENTMEETINGS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    //Toast.makeText(getContext()," successfull"+ jsonObject,Toast.LENGTH_SHORT).show();

                    if(! jsonObject.getBoolean("Error"))
                    {
                        JSONArray object=jsonObject.getJSONArray("result");
                        //JSONArray array=jsonObject.getJSONArray("Array");

                        Calendar calendar=Calendar.getInstance();
                        calendar.get(Calendar.DAY_OF_WEEK);
                        int [] images ={R.drawable.fareed,R.drawable.usama,R.drawable.shahid,R.drawable.amina,R.drawable.saira,R.drawable.ali,R.drawable.ashar};

                        list = new ArrayList<MeetingSingleRow>();
                        for (int i =1; i< object.length(); i++)
                        {

                            JSONArray array=object.getJSONArray(i);

                            String Reqtime=array.get(2).toString();
                           int day=Integer.parseInt( Reqtime.substring(8,10));
                           int sysday= calendar.get(Calendar.DAY_OF_MONTH);
                           String showday=null;
                           if(day - sysday==0)
                           {
                               showday="Today";
                           }
                           else if(sysday - day ==1)
                           {
                                showday="Yesterday";
                           }
                           else if(sysday - day <7)
                           {
                               showday=array.get(3).toString();
                           }
                           else
                           {
                               showday=Reqtime.substring(0,10);
                           }
                           String purpose=array.get(4).toString();
                            String starttime=array.get(5).toString();
                            String endtime=array.get(6).toString();
                            int meetID=Integer.parseInt(array.get(7).toString());
                            int SenderID=Integer.parseInt(array.get(8).toString());
                            int ReceiverID=Integer.parseInt(array.get(9).toString());
                            MeetingSingleRow row = new MeetingSingleRow(array.get(0).toString() , "You sent meeting "+showday+" at "+Reqtime.substring(11,16), R.drawable.ic_dp_demo,meetID,purpose,starttime,endtime,SenderID,ReceiverID,Reqtime);
                            list.add(row);

                        }

                        MeetingListAdapter adapter = new MeetingListAdapter(getActivity(), list);
                        lv_list.setAdapter(adapter);

                    }
                    else
                    {

                        Toast.makeText(getContext(),jsonObject.getString("error_msg"),Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {



                Map<String, String> params = new HashMap<String, String>();

                params.put("UserID",""+SenderID);
                params.put("Status",""+1);
                return  params;
            }
        };
        requestQueue.add(stringRequest);

    }
}

