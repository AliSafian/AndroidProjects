package com.facefive.meetbook.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facefive.meetbook.R;
import com.facefive.meetbook.TimeTableExpandableListAdapter;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeTableViewActivity extends AppCompatActivity {

    private ExpandableListView listView;
    private TimeTableExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table_view);

        listView = findViewById(R.id.timatable_ex_lv);
        int userID = Integer.parseInt(getIntent().getStringExtra("userID"));
        getCompeleteTimeTable(userID);
        initData();
        listAdapter = new TimeTableExpandableListAdapter(this , listDataHeader, listHash);
        listView.setAdapter(listAdapter);
    }

    private void initData() {

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
    public  void getCompeleteTimeTable(final int userid){
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
}
