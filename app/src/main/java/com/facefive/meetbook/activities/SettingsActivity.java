package com.facefive.meetbook.activities;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facefive.meetbook.R;
import com.facefive.meetbook.UserHandling.SessionManager;
import com.facefive.meetbook.app.AppConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class SettingsActivity extends AppCompatActivity  {

    private TextView name_tv;
    private TextView email_tv;
    private ImageView image;
    private RelativeLayout change_name;
    private RelativeLayout change_picture;
    private RelativeLayout change_pass;
    private RelativeLayout logout;
    private SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


            image=(ImageView)findViewById(R.id.circleImageView);





            name_tv= findViewById(R.id.name);
            email_tv= findViewById(R.id.email);
            session=new SessionManager(getApplicationContext());
            name_tv.setText(session.getName());
            email_tv.setText(session.getEmail());

            String path =session.getPicturePath();
            if(path!= null)
            {
                image.setImageURI(Uri.parse(new File(path).toString()));
            }
            else
            {
                image.setImageResource(R.drawable.ic_dp_demo);
            }

            change_name = (RelativeLayout)findViewById(R.id.layout_change_name) ;
            change_picture = (RelativeLayout)findViewById(R.id.layout_change_picture) ;
            change_pass = (RelativeLayout)findViewById(R.id.layout_change_pass) ;
            logout = (RelativeLayout)findViewById(R.id.layout_logout) ;





            change_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent=new Intent(getApplicationContext(),ChangeNameActivity.class);
                    startActivity(intent);
                }
            });

            change_picture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(),ChangePictureActivity.class);
                    startActivity(intent);
                }
            });

            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    finish();
                    Intent i = new Intent(getApplicationContext(),LoginActivity.class );
                    SessionManager session = new SessionManager(getApplicationContext());
                    sendRegistrationToServer(session.getUserID(),"");
                    session.setTokenRefreshed(true);
                    session.setLogin(false);
                    startActivity(i);
                }
            });

            change_pass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent=new Intent(getApplicationContext(),ChangePasswordActivity.class);
                    startActivity(intent);
                }
            });
    }

    private void sendRegistrationToServer(final int userId , final String token) {



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


    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case 1234:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();


                    Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
                    //image.setImageURI(selectedImage);
                    Toast.makeText(getApplicationContext(), "ImageChanged", Toast.LENGTH_SHORT).show();
                }
        }

    };
    @Override
    public void onResume(){
        super.onResume();

        session=new SessionManager(getApplicationContext());
        name_tv.setText(session.getName());
        String path =session.getPicturePath();
        if(path!=null)
        {
            image.setImageURI(Uri.parse(new File(path).toString()));
        }

    }

}
