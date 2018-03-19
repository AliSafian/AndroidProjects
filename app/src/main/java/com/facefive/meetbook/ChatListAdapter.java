package com.facefive.meetbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatListAdapter extends BaseAdapter {

    ArrayList<SingleRowChatItem> list;
    Context context;

    public ChatListAdapter(Context c,ArrayList<SingleRowChatItem> list)
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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.single_row_chat_item, parent, false);

        ImageView image = row.findViewById(R.id.iv_user_photo);
        ImageView status = row.findViewById(R.id.online_indicator);
        TextView name = row.findViewById(R.id.tv_name);
        TextView message = row.findViewById(R.id.tv_last_chat);
        TextView time = row.findViewById(R.id.tv_time);


        final SingleRowChatItem temp = list.get(position);

        name.setText(temp.name);
        message.setText(temp.message);
        time.setText(temp.time);
        status.setImageResource(temp.image);
        image.setImageResource(temp.image);

        return row;
    }
}
