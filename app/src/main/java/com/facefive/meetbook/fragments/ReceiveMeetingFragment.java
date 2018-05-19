package com.facefive.meetbook.fragments;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.facefive.meetbook.ListAdapter;
import com.facefive.meetbook.R;
import com.facefive.meetbook.SingleRow;
import com.facefive.meetbook.response;

import java.util.ArrayList;

/**
 * Created by Ashar on 1/20/2018.
 */

public class ReceiveMeetingFragment extends Fragment {
    ListView lv_list;
    ArrayList<SingleRow> list;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_receive_meeting , container, false);

        lv_list = (ListView) view.findViewById(R.id.lv_meeting_received);
        Resources res = this.getResources();
        String[] names = res.getStringArray(R.array.names);

        int [] images ={R.drawable.fareed,R.drawable.usama,R.drawable.shahid,R.drawable.amina,R.drawable.saira,R.drawable.ali,R.drawable.ashar};
        list = new ArrayList<SingleRow>();
        for (int i =0; i<7 ; i++)
        {

            SingleRow row = new SingleRow(names[i] , "For Project Evaluation We Want To Meet You", images[i]);
            list.add(row);

        }

        ListAdapter adapter = new ListAdapter(getActivity(), list);
        lv_list.setAdapter(adapter);
        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent i = new Intent(getActivity() , response.class );
                i.putExtra("subject" , "For Project Evaluation We Want To Meet You");
                startActivity(i);
            }
        });

        return view;
    }
}
