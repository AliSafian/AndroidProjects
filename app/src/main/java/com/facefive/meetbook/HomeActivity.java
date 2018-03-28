package com.facefive.meetbook;

import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    RelativeLayout rl1;
    RelativeLayout rl2;


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


        //rl1 = (RelativeLayout) findViewById(R.id.layout1);

        //rl2 = (RelativeLayout) findViewById(R.id.layout2);



//        rl1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getApplicationContext(), ConversationBasic.class);
//                startActivity(i);
//            }
//        });
//
//        rl2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getApplicationContext(), ConversationBasic.class);
//                startActivity(i);
//            }
//        });

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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

       if (id == R.id.nav_meetings) {
            Intent i = new Intent(getApplicationContext(),MeetingsActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_subscriptions) {
            Intent i = new Intent(getApplicationContext(),SubscriptionsActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_send_updates) {
            Intent i = new Intent(getApplicationContext(),SendUpdateActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_timetable) {
            Intent i = new Intent(getApplicationContext(),SchedulePlan.class);
            startActivity(i);

        } else if (id == R.id.nav_print) {

                Intent i = new Intent(getApplicationContext(),PrintSettingActivity.class);
                startActivity(i);

        } else if (id == R.id.nav_conn_to_rpi) {
            Toast.makeText(getApplicationContext(), "Successfully Connected To Rpi", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_setting) {
                Intent i = new Intent(getApplicationContext(),SettingsActivity.class );
                startActivity(i);
        } else if (id == R.id.nav_logout) {
            finish();
            Intent i = new Intent(getApplicationContext(),LoginActivity.class );
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
