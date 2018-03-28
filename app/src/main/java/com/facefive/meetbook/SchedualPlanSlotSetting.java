package com.facefive.meetbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.facefive.meetbook.TimetableSession.TimetableSession;

public class SchedualPlanSlotSetting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedual_plan_slot_setting);



//        Toast.makeText(getApplicationContext(), TimetableSession.noOfSlots+"",Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), TimetableSession.Days.size()+"",Toast.LENGTH_SHORT).show();
//
//        Toast.makeText(getApplicationContext(), TimetableSession.Days.get(1).getSlotList().get(0).getEndTime().toString()+"",Toast.LENGTH_SHORT).show();
//        Toast.makeText(getApplicationContext(), TimetableSession.Days.get(1).getSlotList().size()+"",Toast.LENGTH_SHORT).show();
//        Toast.makeText(getApplicationContext(), TimetableSession.startTime.toString(),Toast.LENGTH_SHORT).show();
//        Toast.makeText(getApplicationContext(), TimetableSession.endTime.toString(),Toast.LENGTH_SHORT).show();

 int i =0;
        while(i <TimetableSession.Days.size())
        {
            Toast.makeText(getApplicationContext(),TimetableSession.Days.get(i).getDay(),Toast.LENGTH_SHORT).show();
            i++;
        }



    }
}
