package com.facefive.meetbook.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
import com.facefive.meetbook.app.AppConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchResultActivity extends AppCompatActivity {

    ListView lv_list;
    ArrayList<SingleRow> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        handleIntent(getIntent());




    }
    @Override
    protected void onNewIntent(Intent intent) {

        handleIntent(intent);
    }
    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            GetSearchedUsers(query);


        }
    }

    public  void GetSearchedUsers(final String query)
    {
        RequestQueue requestQueue= Volley.newRequestQueue(SearchResultActivity.this);

        final StringRequest stringRequest=new StringRequest(Request.Method.POST, AppConfig.URL_GETSEARCHEDUSERS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);
                   // Toast.makeText(getApplicationContext()," successfull"+ jsonObject,Toast.LENGTH_SHORT).show();

                    if(! jsonObject.getBoolean("Error"))
                    {
                        final JSONArray arrays=jsonObject.getJSONArray("User");
                       // Toast.makeText(getApplicationContext()," successfull"+ object.get(1),Toast.LENGTH_SHORT).show();

                        //JSONObject object=jsonObject.getJSONObject("User");

                          lv_list = (ListView) findViewById(R.id.lv_users);
                           // int [] images ={R.drawable.fareed,R.drawable.usama,R.drawable.shahid,R.drawable.amina,R.drawable.saira,R.drawable.ali,R.drawable.ashar};
                            list = new ArrayList<SingleRow>();
                            for (int i =0; i<arrays.length() ; i++)
                            {
                                SingleRow row = new SingleRow(arrays.getJSONArray(i).get(1).toString() , arrays.getJSONArray(i).get(2).toString() , R.drawable.ic_dp_demo);
                                list.add(row);

                            }
                            ListAdapter adapter = new ListAdapter(getApplication(), list);
                            lv_list.setAdapter(adapter);
                            lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                                    try {
                                        i.putExtra("UserID",arrays.getJSONArray(position).get(0).toString());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    i.putExtra("name", list.get(position).name);
                                    i.putExtra("email" ,list.get(position).description);
                                    i.putExtra("img_id", list.get(position).image);
                                    startActivity(i);

                                }
                            });

                    }
                    else
                    {

                        Toast.makeText(getApplicationContext(),jsonObject.getString("error_msg"),Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {



                Map<String, String> params = new HashMap<String, String>();

                params.put("query",""+query);
                return  params;
            }
        };
        requestQueue.add(stringRequest);

    }
}
