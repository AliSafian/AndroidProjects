package com.facefive.meetbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.ProgressDialog;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {

    TextView forgotPass_link;
    Button login_btn;
    EditText email_et;
    EditText pass_et;
    String EMAIL="email";
    String PASSWORD="password";

    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //this.setTitle("Login");

        email_et= (EditText)findViewById(R.id.et_email);
        pass_et= (EditText)findViewById(R.id.passET);
        login_btn =(Button) findViewById(R.id.btn_login);

        login_btn.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {

           checkLogin();

           }});

    }
    public void checkLogin(){

        RequestQueue requestQueue=Volley.newRequestQueue(this);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, AppConfig.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(LoginActivity.this,response,Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this,error.toString(),Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params=new HashMap<String, String>();
                params.put(EMAIL,email_et.getText().toString().trim());
                params.put(PASSWORD,pass_et.getText().toString().trim());

                return params;
            }
        };
        requestQueue.add(stringRequest);

    }
}
