package com.facefive.meetbook.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.facefive.meetbook.ListAdapter;
import com.facefive.meetbook.R;
import com.facefive.meetbook.SingleRow;

import java.util.ArrayList;

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
            lv_list = (ListView) findViewById(R.id.lv_users);
            Resources res = this.getResources();
            String[] names = res.getStringArray(R.array.names);
            String[] emails =res.getStringArray(R.array.emails);
            int [] images ={R.drawable.fareed,R.drawable.usama,R.drawable.shahid,R.drawable.amina,R.drawable.saira,R.drawable.ali,R.drawable.ashar};
            list = new ArrayList<SingleRow>();
            for (int i =0; i<7 ; i++)
            {
                if(names[i].toLowerCase().contains(query.toLowerCase()) ||emails[i].toLowerCase().contains(query.toLowerCase()))
                {
                    SingleRow row = new SingleRow(names[i] , emails[i], images[i]);
                    list.add(row);
                }

            }
            ListAdapter adapter = new ListAdapter(this, list);
            lv_list.setAdapter(adapter);
            lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                    i.putExtra("name", list.get(position).name);
                    i.putExtra("email" ,list.get(position).description);
                    i.putExtra("img_id", list.get(position).image);
                    startActivity(i);

                }
            });

        }
    }
}
