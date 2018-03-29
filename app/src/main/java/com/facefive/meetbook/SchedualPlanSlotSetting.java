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
    private Button btn_pre;
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
        btn_pre = (Button) findViewById(R.id.pre_btn_scheduleplanslotsetting_xml);

        btn_pre.setVisibility(View.INVISIBLE);

        tv_day = (TextView) findViewById(R.id.day_tv_scheduleplanslotsetting_xml);


        tempDayCout = 0;

        tv_day.setText(TimetableSession.Days.get(tempDayCout).getDay());
        SlotListAdapter customAdapter = new SlotListAdapter(getApplicationContext(), TimetableSession.Days.get(tempDayCout).getSlotList());
        list.setAdapter(customAdapter);
        tempDayCout++;


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tempDayCout<TimetableSession.Days.size()) {


                    if(tempDayCout>0)
                    {
                        btn_pre.setVisibility(View.VISIBLE);

                    }
                    if(tempDayCout==0)
                    {
                        tempDayCout++;
                    }
                    tv_day.setText(TimetableSession.Days.get(tempDayCout).getDay());
                    SlotListAdapter customAdapter = new SlotListAdapter(getApplicationContext(), TimetableSession.Days.get(tempDayCout).getSlotList());
                    list.setAdapter(customAdapter);
                    tempDayCout++;
                    if(tempDayCout == TimetableSession.Days.size())
                    {
                        btn.setText("Save");

                    }
                    if(tempDayCout<0)
                    {
                        tempDayCout=0;
                    }
                    if(tempDayCout==TimetableSession.Days.size())
                    {
                        tempDayCout=tempDayCout-1;
                    }


                }
                else
                {
                   Toast.makeText(getApplicationContext(),"Data saved!",Toast.LENGTH_SHORT);
                }

            }
        });

        btn_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tempDayCout >= 0) {
                    tempDayCout--;
                    tv_day.setText(TimetableSession.Days.get(tempDayCout).getDay());
                    SlotListAdapter customAdapter = new SlotListAdapter(getApplicationContext(), TimetableSession.Days.get(tempDayCout).getSlotList());
                    list.setAdapter(customAdapter);
                    btn.setText("Next");
                    if(tempDayCout==0)
                        btn_pre.setVisibility(View.INVISIBLE);
                    if(tempDayCout<0)
                    {
                        tempDayCout=0;
                    }
                    if(tempDayCout==TimetableSession.Days.size())
                    {
                        tempDayCout=tempDayCout-1;
                    }
                }



            }
        });











    }
}
