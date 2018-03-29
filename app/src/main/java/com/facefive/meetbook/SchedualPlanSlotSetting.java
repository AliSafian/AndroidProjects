package com.facefive.meetbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.facefive.meetbook.TimetableSession.TimetableSession;

public class SchedualPlanSlotSetting extends AppCompatActivity {

    private ListView list;
    private Button btn;
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


//        for(int i = 0; i< TimetableSession.Days.get(0).getSlotList().size(); i++)
//        {
//            Toast.makeText(getApplicationContext(), TimetableSession.Days.get(0).getSlotList().get(i).getSlotType().toString()+i,Toast.LENGTH_SHORT).show();
//        }



        SlotListAdapter customAdapter = new SlotListAdapter(getApplicationContext(), TimetableSession.Days.get(0).getSlotList());
        list.setAdapter(customAdapter);


       /* btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "";
                // get the value of selected answers from custom adapter
                for (int i = 0; i < SlotListAdapter.size(); i++) {
                    message = message + "\n" + (i + 1) + " " + CustomAdapter.selectedAnswers.get(i);
                }
                // display the message on screen with the help of Toast.
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

            }
        });*/












    }
}
