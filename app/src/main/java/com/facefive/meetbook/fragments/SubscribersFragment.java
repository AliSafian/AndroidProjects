package com.facefive.meetbook.fragments;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facefive.meetbook.ListAdapter;
import com.facefive.meetbook.R;
import com.facefive.meetbook.SingleRow;
import com.facefive.meetbook.UserHandling.SessionManager;
import com.facefive.meetbook.activities.ProfileActivity;
import com.facefive.meetbook.app.AppConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ashar on 1/20/2018.
 */

public class SubscribersFragment extends Fragment {
    ListView lv_list;
    ArrayList<SingleRow> list;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subscribers , container, false);
        SessionManager manager=new SessionManager(getContext());
        lv_list = (ListView) view.findViewById(R.id.lv_suscribers);
        GetAllFollowers(manager.getUserID());

        return view;
    }
    public  void GetAllFollowers(final int userid){
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());

        StringRequest stringRequest=new StringRequest(Request.Method.POST, AppConfig.URL_GETFOLLOWERSANDFOLLOWINGS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);


                    if(! jsonObject.getBoolean("Error"))
                    {
                        JSONArray bigarray=jsonObject.getJSONArray("result");
                           // int [] images ={R.drawable.fareed,R.drawable.usama,R.drawable.shahid,R.drawable.amina,R.drawable.saira,R.drawable.ali,R.drawable.ashar};
                            list = new ArrayList<SingleRow>();

                            for (int i =0; i<bigarray.length() ; i++)
                            {
                                JSONArray array=bigarray.getJSONArray(i);
                                SingleRow row = new SingleRow(array.getString(1) , array.getString(2), R.drawable.ic_dp_demo);
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



                    }
                    else
                    {

                        Toast.makeText(getContext(),jsonObject.getString("error_msg"),Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_SHORT).show();


            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {



                Map<String, String> params = new HashMap<String, String>();
                SessionManager manager=new SessionManager(getContext());
                params.put("UserID",""+userid);
                params.put("Status",""+2);
                // Toast.makeText(getApplicationContext(),jarray.toString(),Toast.LENGTH_SHORT).show();
                return  params;
            }
        };
        requestQueue.add(stringRequest);

    }
}
