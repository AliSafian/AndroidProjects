package com.facefive.meetbook;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Ashar on 1/20/2018.
 */

public class PublishersFragment extends Fragment {

    ListView lv_list;
    ArrayList<SingleRow> list;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_publishers , container, false);

        lv_list = (ListView) view.findViewById(R.id.lv_puclishers);
        Resources res = this.getResources();
        String[] names = res.getStringArray(R.array.names);
        String[] emails =res.getStringArray(R.array.emails);
        int [] images ={R.drawable.fareed,R.drawable.usama,R.drawable.shahid,R.drawable.amina,R.drawable.saira,R.drawable.ali,R.drawable.ashar};
        list = new ArrayList<SingleRow>();
        for (int i =0; i<7 ; i++)
        {

                SingleRow row = new SingleRow(names[i] , emails[i], images[i]);
                list.add(row);

        }

        ListAdapter adapter = new ListAdapter(getActivity(), list);
        lv_list.setAdapter(adapter);
        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(getActivity(), ProfileActivity.class);
                i.putExtra("name", list.get(position).name);
                i.putExtra("email" ,list.get(position).description);
                i.putExtra("img_id", list.get(position).image);
                startActivity(i);

            }
        });

        return view;
    }
}
