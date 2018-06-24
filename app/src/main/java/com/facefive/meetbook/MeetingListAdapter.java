package com.facefive.meetbook;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facefive.meetbook.utils.DateUtils;

import java.util.ArrayList;

public class MeetingListAdapter extends BaseAdapter {

    ArrayList<MeetingSingleRow> list;
    Context context;

    public MeetingListAdapter(Context c,ArrayList<MeetingSingleRow> list)
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
        View row = inflater.inflate(R.layout.single_row_receive_meeting, parent, false);

        ImageView image = row.findViewById(R.id.iv_user_pic);
        TextView name = row.findViewById(R.id.rec_meet_tv_name);
        TextView description = row.findViewById(R.id.recv_meet_purpose);
        TextView time = row.findViewById(R.id.tv_recv_meet_time);
        //ImageView moreVert = row.findViewById(R.id.recv_meet_more_vert);

        final MeetingSingleRow temp = list.get(position);

        name.setText(temp.name);
        description.setText(temp.purpose);
        image.setImageResource(temp.image);

        String showtime= DateUtils.getDateTimeString(temp.reqTime);

        time.setText(showtime);
  /*      moreVert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"yo clicked "+position,Toast.LENGTH_SHORT).show();
            }
        });*/

        return row;
    }
}
