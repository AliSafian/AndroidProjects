package com.facefive.meetbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facefive.meetbook.TimetableSession.SlotSingleRow;
import com.facefive.meetbook.TimetableSession.TimetableSession;

import java.util.ArrayList;

public class SlotListAdapter extends BaseAdapter {

    ArrayList<SlotSingleRow> list;
    Context context;

    public SlotListAdapter(Context c,ArrayList<SlotSingleRow> list)
    {
        this.context = c;
        this.list=list;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(list.size() == 0)
            return null;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.slotsinglerow, parent, false);


        TextView duration = row.findViewById(R.id.duration_tv_slotsinglerow_xml);
        RadioButton lecture_rb=(RadioButton)row.findViewById(R.id.lh_rb_slotsinglerow_xml);
        RadioButton meeting_rb=(RadioButton)row.findViewById(R.id.mh_rb_slotsinglerow_xml);
        RadioButton free_rb=(RadioButton)row.findViewById(R.id.fh_rb_slotsinglerow_xml);


        if(list.get(position).getSlotType().equals("Lecture"))
        {
            lecture_rb.setChecked(true);
        }
        else if(list.get(position).getSlotType().equals("Meeting"))
        {
            meeting_rb.setChecked(true);
        }
        else
        {
            free_rb.setChecked(true);
        }

        lecture_rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    list.get(position).setSlotType("Lecture");
                }
            }
        });
        meeting_rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    list.get(position).setSlotType("Meeting");
                    Toast.makeText(context, list.get(position).getSlotType(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        free_rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    list.get(position).setSlotType("Free");
                }
            }
        });



        duration.setText("Timing: "+list.get(position).getStartTime().toString()+" - "+list.get(position).getEndTime().toString());
        return row;
    }
}

