package com.facefive.meetbook;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Switch;
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
import com.facefive.meetbook.UserHandling.SessionManager;
import com.facefive.meetbook.app.AppConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yuku.ambilwarna.AmbilWarnaDialog;

public class SchedulePlan extends AppCompatActivity {

    private ExpandableListView listView;
    private TimeTableExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHash;
    private Button editBtn ;

    private boolean flag ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_plan);


        listView = findViewById(R.id.timatable_ex_lv);
        editBtn = findViewById(R.id.editschedule_btn_scheduleplan_xml);
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        getCompeleteTimeTable(sessionManager.getUserID(), new VolleyCallback() {
            @Override
            public void onSuccess() {
                initData();
                listAdapter = new TimeTableExpandableListAdapter(getApplicationContext() , listDataHeader, listHash);
                listView.setAdapter(listAdapter);
            }
        });




        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SchedulePlanSetting.class);
                startActivity(intent);

            }
        });
    }

     @Override
     protected void onResume()
     {
         super.onResume();
         SessionManager sessionManager = new SessionManager(getApplicationContext());
         getCompeleteTimeTable(sessionManager.getUserID(), new VolleyCallback() {
             @Override
             public void onSuccess() {
                 initData();
                 listAdapter = new TimeTableExpandableListAdapter(getApplicationContext() , listDataHeader, listHash);
                 listView.setAdapter(listAdapter);
             }
         });
     }
    private void initData() {

        if(TimetableSession.Days == null)
            return;
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();
        int daysCount = TimetableSession.Days.size();

        if(daysCount> 0)
        {

            for (int i =0; i<daysCount ; i++ )
            {
                listDataHeader.add(TimetableSession.Days.get(i).getDay());
                List<String> temp = new ArrayList<>();
                for (int j =0; j<  TimetableSession.Days.get(i).getSlotList().size() ; j++)
                {
                    temp.add(TimetableSession.Days.get(i).getSlotList().get(j).getSlotType()+"\t\t\t"
                            +TimetableSession.Days.get(i).getSlotList().get(j).getStartTime() +"  -  "
                            +TimetableSession.Days.get(i).getSlotList().get(j).getEndTime());
                }

                listHash.put(TimetableSession.Days.get(i).getDay(), temp);
            }

        }

    }
    public  void getCompeleteTimeTable(final int userid, final VolleyCallback callback){
        RequestQueue requestQueue= Volley.newRequestQueue(this);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, AppConfig.URL_GETCOMPLETETIMETABLE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);


                    if(! jsonObject.getBoolean("error"))
                    {
                        JSONArray bigArray=jsonObject.getJSONArray("item");
                       // Toast.makeText(getApplicationContext(), jsonObject.getJSONArray("item").toString(),Toast.LENGTH_SHORT).show();
                        TimetableSession.noOfSlots=Integer.parseInt(bigArray.get(0).toString());

                        TimetableSession.startTime.setHours(Integer.parseInt(bigArray.getString(1).substring(0,2)));
                        TimetableSession.startTime.setMinutes(Integer.parseInt(bigArray.getString(1).substring(3,5)));

                        TimetableSession.endTime.setHours(Integer.parseInt(bigArray.getString(2).substring(0,2)));
                        TimetableSession.endTime.setMinutes(Integer.parseInt(bigArray.getString(2).substring(3,5)));
                        TimetableSession.Days=new ArrayList<TimetableDay>();

                        for (int i=4;i<bigArray.length();i++)
                        {
                            String day=null;
                            ArrayList<SlotSingleRow> singleRowsList=new ArrayList<SlotSingleRow>();
                            for (int j=0;j<TimetableSession.noOfSlots;j++)

                            {

                                JSONArray array=bigArray.getJSONArray(i);
                                 day=array.getString(5);
                                Time starttime = new Time(Integer.parseInt(array.getString(1).substring(0, 2)), Integer.parseInt(array.getString(1).substring(3, 5)), 0);
                                Time endtime = new Time(Integer.parseInt(array.getString(2).substring(0, 2)), Integer.parseInt(array.getString(2).substring(3, 5)), 0);

                                SlotSingleRow singleRow = new SlotSingleRow(array.getInt(0), starttime, endtime, array.getString(3), array.getString(4), array.getString(5), array.getInt(6));
                                singleRowsList.add(singleRow);

                                i++;
                            }
                            TimetableDay timetableDay = new TimetableDay(day, singleRowsList);
                            TimetableSession.Days.add(timetableDay);
                            i--;
                        }

                    }
                    else
                    {

                        Toast.makeText(getApplicationContext(),jsonObject.getString("error_msg"),Toast.LENGTH_SHORT).show();
                    }



                    callback.onSuccess();
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
                return  params;
            }
        };
        requestQueue.add(stringRequest);

    }
    private interface VolleyCallback{
        void onSuccess();
    }
}
