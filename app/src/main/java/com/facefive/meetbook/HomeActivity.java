package com.facefive.meetbook;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facefive.meetbook.UserHandling.UserSessionManager;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView name_tv ;
    private TextView email_tv;




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


        LinearLayout ll_more = findViewById(R.id.ll_more_home_activity);
        LinearLayout ll_messages = findViewById(R.id.ll_messages_home_activity);
        LinearLayout ll_timetable = findViewById(R.id.ll_timetable_home_activity);
        LinearLayout ll_meetings = findViewById(R.id.ll_meeting_home_activity);



        inItFixedMeetingRecyclerView();



        ll_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FeaturesActivity.class);
                startActivity(intent);
            }
        });
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
                Intent intent = new Intent(getApplicationContext(), MeetingsActivity.class);
                startActivity(intent);
            }
        });








    }

    private void inItFixedMeetingRecyclerView()
    {
        Resources res = this.getResources();
        String[] names = res.getStringArray(R.array.names);
        String[] descrip =res.getStringArray(R.array.descriptions);
        int [] images ={R.drawable.fareed,R.drawable.usama,R.drawable.shahid,R.drawable.amina,R.drawable.saira,R.drawable.ali,R.drawable.ashar};

        ArrayList<Integer> mImageIDs = new ArrayList<>();
        ArrayList<String> mNames = new ArrayList<>();
        ArrayList<String> mDescriptions= new ArrayList<>();
        for(int i =0; i< names.length ; i++)
        {
            mNames.add(names[i]);
            mImageIDs.add(images[i]);
            mDescriptions.add(descrip[i]);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL ,false);
        RecyclerView recyclerView = findViewById(R.id.rv_fixed_meetings);
        recyclerView.setLayoutManager(layoutManager);
        FixedMeetingRecyclerViewAdapter adapter = new FixedMeetingRecyclerViewAdapter(getApplicationContext(), mImageIDs, mNames, mDescriptions);
        recyclerView.setAdapter(adapter);


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
        else if (id == R.id.action_privacy) {
            Intent i = new Intent(getApplicationContext(), PrivacyActivity.class);
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

        if (id == R.id.nav_print) {

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
