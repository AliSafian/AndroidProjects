package com.facefive.meetbook.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuInflater;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facefive.meetbook.MeetingSingleRow;
import com.facefive.meetbook.SingleRow;
import com.facefive.meetbook.UserHandling.SessionManager;
import com.facefive.meetbook.FixedMeetingRecyclerViewAdapter;
import com.facefive.meetbook.R;
import com.facefive.meetbook.SchedulePlan;
import com.facefive.meetbook.app.AppConfig;
import com.facefive.meetbook.utils.NotificationUtils;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView name_tv ;
    private TextView email_tv;

    private static final String TAG = HomeActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private TextView txtRegId, txtMessage;
    private ArrayList<MeetingSingleRow> list;



    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SessionManager sManager = new SessionManager(getApplicationContext());

        if(sManager.isTokenRefreshed())
        {
            int UserID = sManager.getUserID();
            String FCMToken =sManager.getFCMToken();
            sendRegistrationToServer(UserID, FCMToken);
            sManager.setTokenRefreshed(false);
        }


       // LinearLayout ll_more = findViewById(R.id.ll_more_home_activity);
        LinearLayout ll_messages = findViewById(R.id.ll_messages_home_activity);
        LinearLayout ll_timetable = findViewById(R.id.ll_timetable_home_activity);
        LinearLayout ll_meetings = findViewById(R.id.ll_meeting_home_activity);
        LinearLayout ll_send_update = findViewById(R.id.ll_send_update_home_activity);
        LinearLayout ll_notificatione = findViewById(R.id.ll_notification_home_activity);
        LinearLayout ll_connections = findViewById(R.id.ll_connections_home_activity);



        inItFixedMeetingRecyclerView();



       /* ll_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FeaturesActivity.class);
                startActivity(intent);
            }
        });*/
        ll_messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Under Development", Toast.LENGTH_SHORT).show();
            }
        });
        ll_timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SchedulePlan.class);
                startActivity(intent);
            }
        });
        ll_meetings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // getAllSentMeetings(18);
                Intent intent = new Intent(getApplicationContext(), MeetingsActivity.class);
                startActivity(intent);
            }
        });



        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(AppConfig.CHANNEL_ID, AppConfig.CHANNEL_NAME, importance);
            mChannel.setDescription(AppConfig.CHANNEL_DESCRIPTION);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager.createNotificationChannel(mChannel);
        }


        txtRegId = (TextView) findViewById(R.id.txt_reg_id);
        txtMessage = (TextView) findViewById(R.id.txt_push_message);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(AppConfig.REGISTRATION_COMPLETE)) {
                    // fcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(AppConfig.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(AppConfig.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                    txtMessage.setText(message);
                }
            }
        };

        displayFirebaseRegId();


    }

    private void sendRegistrationToServer(final int userId , final String token) {
        // sending gcm token to server
        Log.e(TAG, "sendRegistrationToServer: " + token);


        RequestQueue requestQueue= Volley.newRequestQueue(this);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, AppConfig.URL_UPDATE_FCM_TOKEN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("Error");


                    // Check for error node in json
                    if (!error) {
                        String successMsg = jObj.getString("SuccessMsg");
                        Toast.makeText(getApplicationContext(),
                                successMsg, Toast.LENGTH_SHORT).show();

                    } else {
                        String errorMsg = jObj.getString("ErrorMsg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
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

                Map<String, String> params=new HashMap<String, String>();
                params.put("UserID",userId+"");
                params.put("FCMToken",token);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void displayFirebaseRegId() {

        SessionManager sessionManager = new SessionManager(getApplicationContext());

        String regId = sessionManager.getFCMToken();

        Log.e(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId))
            txtRegId.setText("Firebase Reg Id: " + regId);
        else
            txtRegId.setText("Firebase Reg Id is not received yet!");
    }

    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(AppConfig.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(AppConfig.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }






    private void inItFixedMeetingRecyclerView()
    {
     /*   Resources res = this.getResources();
        String[] names = res.getStringArray(R.array.names);

        int [] images ={R.drawable.fareed,R.drawable.usama,R.drawable.shahid,R.drawable.amina,R.drawable.saira,R.drawable.ali,R.drawable.ashar};
        list = new ArrayList<SingleRow>();
        for (int i =0; i<7 ; i++)
        {

            SingleRow row = new SingleRow(names[i] , "You sent meeting at 16:00", images[i]);
            list.add(row);

        }*/


      getAllFixedMeetings(18);


    }


    public  void getAllFixedMeetings(final int UserID)
    {
        RequestQueue requestQueue= Volley.newRequestQueue(HomeActivity.this);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, AppConfig.URL_GETALLSENTMEETINGS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    //Toast.makeText(getContext()," successfull"+ jsonObject,Toast.LENGTH_SHORT).show();

                    if(! jsonObject.getBoolean("Error"))
                    {
                        JSONArray object=jsonObject.getJSONArray("result");
                        //JSONArray array=jsonObject.getJSONArray("Array");

                        Calendar calendar=Calendar.getInstance();
                        calendar.get(Calendar.DAY_OF_WEEK);
                        int [] images ={R.drawable.fareed,R.drawable.usama,R.drawable.shahid,R.drawable.amina,R.drawable.saira,R.drawable.ali,R.drawable.ashar};

                        list = new ArrayList<MeetingSingleRow>();
                        for (int i =1; i< object.length(); i++)
                        {

                            JSONArray array=object.getJSONArray(i);

                            String Reqtime=array.get(2).toString();
                            int day=Integer.parseInt( Reqtime.substring(8,10));
                            int sysday= calendar.get(Calendar.DAY_OF_MONTH);
                            String showday=null;
                            if(day - sysday==0)
                            {
                                showday="Today";
                            }
                            else if(day - sysday ==1)
                            {
                                showday="Tomorrow";
                            }
                            else if(day - sysday <7)
                            {
                                showday=array.get(3).toString();
                            }
                            else
                            {
                                showday=Reqtime.substring(0,10);
                            }
                            String purpose=array.get(4).toString();
                            String starttime=array.get(5).toString();
                            String endtime=array.get(6).toString();
                            int meetID=Integer.parseInt(array.get(7).toString());
                            int SenderID=Integer.parseInt(array.get(8).toString());
                            int ReceiverID=Integer.parseInt(array.get(9).toString());
                            MeetingSingleRow row = new MeetingSingleRow(array.get(0).toString() , starttime.substring(11,16)+" to "+endtime.substring(11,16), images[i],meetID,purpose,starttime,endtime,SenderID,ReceiverID,Reqtime);
                            list.add(row);

                        }

                        LinearLayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL ,false);
                        RecyclerView recyclerView = findViewById(R.id.rv_fixed_meetings);
                        recyclerView.setLayoutManager(layoutManager);
                        FixedMeetingRecyclerViewAdapter adapter = new FixedMeetingRecyclerViewAdapter(getApplicationContext(),list);
                        recyclerView.setAdapter(adapter);

                    }
                    else
                    {

                        Toast.makeText(HomeActivity.this,jsonObject.getString("error_msg"),Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeActivity.this,error.toString(),Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {



                Map<String, String> params = new HashMap<String, String>();

                params.put("UserID",""+UserID);
                params.put("Status",""+3);
                return  params;
            }
        };
        requestQueue.add(stringRequest);

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(i);

            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_features) {

            Intent i = new Intent(getApplicationContext(), FeaturesActivity.class);
            startActivity(i);

        }else if (id == R.id.nav_print) {

                Intent i = new Intent(getApplicationContext(),PrintSettingActivity.class);
                startActivity(i);

        } else if (id == R.id.nav_conn_to_rpi) {
            Toast.makeText(getApplicationContext(), "Under Development", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_setting) {
                Intent i = new Intent(getApplicationContext(),SettingsActivity.class );
                startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        switch(requestCode){

            case 1:

                if(resultCode==RESULT_OK){

                    String PathHolder = data.getData().getPath();
                    Intent i = new Intent(getApplicationContext(),PrintSettingActivity.class);
                    i.putExtra("fileUrl",PathHolder);
                    startActivity(i);
                }
                break;

        }
    }
}
