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

public class SubscriptionRequestsFragment extends Fragment {

    ListView lv_req_received;
    ListView lv_req_sent;
    ArrayList<SingleRowReceiveSubscription> list_received;
    ArrayList<SingleRowSendSubscription> list_sent;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subscription_requests , container, false);

        FillReceivedListView(view);
        FillSentListView(view);


        return view;
    }
    private void FillReceivedListView(View view)
    {
        lv_req_received = (ListView) view.findViewById(R.id.lv_req_received);
        Resources res = this.getResources();
        String[] names = res.getStringArray(R.array.names);
        int [] images ={R.drawable.fareed,R.drawable.usama,R.drawable.shahid,R.drawable.amina,R.drawable.saira,R.drawable.ali,R.drawable.ashar};
        list_received = new ArrayList<SingleRowReceiveSubscription>();
        for (int i =0; i<7 ; i++)
        {
            SingleRowReceiveSubscription row = new SingleRowReceiveSubscription(names[i] , images[i]);
            list_received.add(row);

        }

        ReceiveSubscriptionListAdapter adapter = new ReceiveSubscriptionListAdapter(getActivity(), list_received);
        lv_req_received.setAdapter(adapter);
        lv_req_received.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }
        });
    }
    private void FillSentListView(View view)
    {



        lv_req_sent = (ListView) view.findViewById(R.id.lv_req_sent);
        Resources res = this.getResources();
        String[] names = res.getStringArray(R.array.names);
        int [] images ={R.drawable.fareed,R.drawable.usama,R.drawable.shahid,R.drawable.amina,R.drawable.saira,R.drawable.ali,R.drawable.ashar};
        list_sent = new ArrayList<SingleRowSendSubscription>();
        for (int i =0; i<7 ; i++)
        {

            SingleRowSendSubscription row = new SingleRowSendSubscription(names[i], images[i]);
            list_sent.add(row);

        }

        SendSubscriptionListAdapter adapter = new SendSubscriptionListAdapter(getActivity(), list_sent);
        lv_req_sent.setAdapter(adapter);
        lv_req_sent.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                
            }
        });
    }
}
