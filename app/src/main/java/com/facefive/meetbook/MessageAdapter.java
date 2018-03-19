package com.facefive.meetbook; /**
 * Created by Shahid on 1/20/2018.
 */
import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MessageAdapter extends BaseAdapter {
    Context context;
    private LayoutInflater inflater;
    private ArrayList<String> messagelist;
    String msg;
    public MessageAdapter(Context context, ArrayList<String>messagelist,String msg)
    {
        this.context = context;
        this.messagelist=messagelist;
        this.msg=msg;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return messagelist.size();
    }

    @Override
    public Object getItem(int i) {
        return messagelist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolderClientNewsFeed holder;
        if (convertView == null) {

            holder = new ViewHolderClientNewsFeed();
            convertView = inflater.inflate(R.layout.conv_model, parent, false);
            holder.recmsg_pic = (ImageView) convertView.findViewById(R.id.image_message_profile);
            holder.rec_msg=(TextView) convertView.findViewById(R.id.text_rec_message_body);
            holder.rec_time=(TextView) convertView.findViewById(R.id.text_rec_message_time);
            holder.send_msg=(TextView) convertView.findViewById(R.id.text_send_message_body);
            holder.send_time=(TextView) convertView.findViewById(R.id.conv_send_message_time);
            holder.send_msg.setText(msg);
            holder.rec_msg.setText(msg);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolderClientNewsFeed) convertView.getTag();
        }
        return convertView;
    }
    static class ViewHolderClientNewsFeed
    {
        ImageView recmsg_pic;
        TextView rec_msg;
        TextView rec_time;
        TextView send_msg;
        TextView send_time;

    }
}
