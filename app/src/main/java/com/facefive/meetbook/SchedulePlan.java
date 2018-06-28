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
import com.facefive.meetbook.TimetableSession.TimetableDay;
import com.facefive.meetbook.TimetableSession.TimetableSession;
import com.facefive.meetbook.app.AppConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_plan);


        listView = findViewById(R.id.timatable_ex_lv);
        editBtn = findViewById(R.id.editschedule_btn_scheduleplan_xml);



        initData();
        listAdapter = new TimeTableExpandableListAdapter(this , listDataHeader, listHash);
        listView.setAdapter(listAdapter);


        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SchedulePlanSetting.class);
                startActivity(intent);
            }
        });
    }


    private void initData() {

        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();



        int daysCount = TimetableSession.Days.size();

        for(TimetableDay object: TimetableSession.Days){
            Toast.makeText(getApplicationContext(),object.getDay(),Toast.LENGTH_SHORT).show();
        }

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

}
