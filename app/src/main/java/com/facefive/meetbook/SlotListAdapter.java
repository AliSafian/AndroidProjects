package com.facefive.meetbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.facefive.meetbook.TimetableSession.SlotSingleRow;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        if(list.size() == 0)
            return null;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.slotsinglerow, parent, false);

        TextView duration = row.findViewById(R.id.duration_tv_slotsinglerow_xml);
        RadioGroup radioGroup=(RadioGroup)row.findViewById(R.id.slottype_rg_slotsinglerow_xml);
        RadioButton lecture_rb=(RadioButton)row.findViewById(R.id.lh_rb_slotsinglerow_xml);
        RadioButton meeting_rb=(RadioButton)row.findViewById(R.id.mh_rb_slotsinglerow_xml);
        RadioButton free_rb=(RadioButton)row.findViewById(R.id.fh_rb_slotsinglerow_xml);




        final SlotSingleRow temp = list.get(position);

        String type = temp.getSlotType();
        if(type.equals("Lecture"))
        {
            lecture_rb.setSelected(true);
        }
        else if(type.equals("Meeting"))
        {
            meeting_rb.setSelected(true);
        }
        else
        {
            free_rb.setSelected(true);
        }

//       int selectedId=radioGroup.getCheckedRadioButtonId();
//       RadioButton radioButton=(RadioButton)row.findViewById(selectedId);
        duration.setText(temp.getStartTime().toString()+" - "+temp.getEndTime().toString());

//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                row.get(checkedId);
//            }
//        });


        return row;
    }
}

