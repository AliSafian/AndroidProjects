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

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    ArrayList<SingleRow> list;
    Context context;

    public ListAdapter(Context c,ArrayList<SingleRow> list)
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
        View row = inflater.inflate(R.layout.single_row, parent, false);

        ImageView image = row.findViewById(R.id.iv_user_pic);
        TextView name = row.findViewById(R.id.tv_name);
        TextView description = row.findViewById(R.id.tv_description);

        final SingleRow temp = list.get(position);

        name.setText(temp.name);
        description.setText(temp.description);
        image.setImageResource(temp.image);

        return row;
    }
}
