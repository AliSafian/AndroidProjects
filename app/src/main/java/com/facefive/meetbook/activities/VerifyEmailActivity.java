package com.facefive.meetbook.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facefive.meetbook.R;
import com.facefive.meetbook.app.AppConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class VerifyEmailActivity extends AppCompatActivity {

    private Button btn;
    private EditText email_et;
    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);


        btn = (Button)findViewById(R.id.btn_next);


        btn.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
               email_et =(EditText) findViewById(R.id.et_email1);
               email = email_et.getText().toString();

               if(!email.matches(AppConfig.EMAIL_PATTERN))
               {
                   email_et.setError("Invalid Email Format");
               }

                   isEmailExist(email, new VolleyCallback(){
                       @Override
                       public void onEmailExist(Boolean result){

                           if(result)
                           {
                               Intent i = new Intent(getApplicationContext(), VerifyCodeActivity.class);

                               i.putExtra("Flow", "FromForgetPassword");
                               i.putExtra("email", email);
                               startActivity(i);
                           }
                           else
                           {
                               email_et.setError("This User Is Not Registered");
                               email_et.requestFocus();

                           }

                       }
                   });
               }
       }

        );




    }



    public  void isEmailExist( final String email, final VolleyCallback callback ){

        RequestQueue requestQueue= Volley.newRequestQueue(this);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, AppConfig.URL_CHECK_EMAIL_EXIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jObj = new JSONObject(response);

                    Boolean found= jObj.getBoolean("found");

                    callback.onEmailExist(found);




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

                params.put("email", email);
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }
    public interface VolleyCallback{
        void onEmailExist(Boolean result);
    }
}
