package com.facefive.meetbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SendSubscriptionListAdapter extends BaseAdapter {

    ArrayList<SingleRowSendSubscription> list;
    Context context;

    public SendSubscriptionListAdapter(Context c, ArrayList<SingleRowSendSubscription> list)
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
        View row = inflater.inflate(R.layout.single_row_send_sub, parent, false);

        ImageView image = row.findViewById(R.id.iv_user_pic);
        TextView name = row.findViewById(R.id.tv_name);

        final SingleRowSendSubscription temp = list.get(position);

        name.setText(temp.name);
        image.setImageResource(temp.image);
        return row;

    }
}
