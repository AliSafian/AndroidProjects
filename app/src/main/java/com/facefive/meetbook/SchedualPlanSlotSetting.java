package com.facefive.meetbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facefive.meetbook.TimetableSession.TimetableSession;

public class SchedualPlanSlotSetting extends AppCompatActivity {

    private ListView list;
    private Button btn;
    private TextView tv_day;



    private int tempDayCout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedual_plan_slot_setting);




//        Toast.makeText(getApplicationContext(), TimetableSession.noOfSlots+"",Toast.LENGTH_SHORT).show();
            //Toast.makeText(getApplicationContext(), TimetableSession.Days.size()+"",Toast.LENGTH_SHORT).show();
//
//        Toast.makeText(getApplicationContext(), TimetableSession.Days.get(1).getSlotList().get(0).getEndTime().toString()+"",Toast.LENGTH_SHORT).show();
//        Toast.makeText(getApplicationContext(), TimetableSession.Days.get(1).getSlotList().size()+"",Toast.LENGTH_SHORT).show();
//        Toast.makeText(getApplicationContext(), TimetableSession.startTime.toString(),Toast.LENGTH_SHORT).show();
//        Toast.makeText(getApplicationContext(), TimetableSession.endTime.toString(),Toast.LENGTH_SHORT).show();



        //..............................start working



        list = (ListView) findViewById(R.id.slot_lv_scheduleplanslotsetting_xml);
        btn = (Button) findViewById(R.id.next_btn_scheduleplanslotsetting_xml);

        tv_day = (TextView) findViewById(R.id.day_tv_scheduleplanslotsetting_xml);


        tv_day.setText(TimetableSession.Days.get(0).getDay());
        SlotListAdapter customAdapter = new SlotListAdapter(getApplicationContext(), TimetableSession.Days.get(0).getSlotList());
        list.setAdapter(customAdapter);


        tempDayCout = 1;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tempDayCout<TimetableSession.Days.size()) {

                    tv_day.setText(TimetableSession.Days.get(tempDayCout).getDay());
                    SlotListAdapter customAdapter = new SlotListAdapter(getApplicationContext(), TimetableSession.Days.get(tempDayCout++).getSlotList());
                    list.setAdapter(customAdapter);

                }
                else if(tempDayCout == TimetableSession.Days.size())
                {
                    btn.setText("Save");
                }

            }
        });











    }
}
