package com.facefive.meetbook.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import android.widget.EditText;
import android.widget.Toast;
import android.view.View.OnClickListener;

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

import java.util.HashMap;
import java.util.Map;


public class ChangePasswordActivity extends AppCompatActivity  {




    private EditText old_pass_et;
    private EditText new_pass_et;
    private EditText con_pass_et;

    private Button btnSubmit;
    CharSequence text = "Password changed successfully!";
    CharSequence text1 = "Incorrect Password!";
    CharSequence text2 = "Please fill the fields!";

    private String old_pass;
    private String new_pass;
    private String con_pass;
    private String email;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        addListenerOnButton();
      }
    public void addListenerOnButton() {


        old_pass_et = (EditText) findViewById(R.id.et_old_pass);
        new_pass_et = (EditText) findViewById(R.id.et_new_pass);
        con_pass_et = (EditText) findViewById(R.id.et_con_pass);



         btnSubmit = (Button) findViewById(R.id.btn_cp_submit);
        btnSubmit.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

                old_pass = old_pass_et.getText().toString();
                new_pass = new_pass_et.getText().toString();
                con_pass = con_pass_et.getText().toString();
                boolean flag= true;
                if(!new_pass.matches(AppConfig.PASSWORD_PATTERN))
                {

                    new_pass_et.setError("Password requires \nAtleast 1 uppercase and 1 lower case\nLength from 6 to 15");
                    new_pass_et.requestFocus();
                    flag= false;
                }
                if(!new_pass.equals(con_pass))
                {
                    con_pass_et.setError("Password does not match");
                    con_pass_et.requestFocus();
                    flag= false;
                }
                if(flag) {
                    SessionManager session = new SessionManager(getApplicationContext());

                    email = session.getEmail();

                    checkOldPassword(email, old_pass, new VolleyCallback() {
                        @Override
                        public void onCheckOldPassword(Boolean result) {
                            if(result)
                            {

                                changePassword(email, new_pass);

                            }
                            else
                            {
                                old_pass_et.setError("Wrong Password");
                                old_pass_et.requestFocus();
                            }
                        }
                    });
                }








            }

        });


    }
    public  void checkOldPassword( final String email,final String pass, final VolleyCallback callback ){

        RequestQueue requestQueue= Volley.newRequestQueue(this);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, AppConfig.URL_CHECK_OLD_PASSWORD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jObj = new JSONObject(response);

                    Boolean found= jObj.getBoolean("found");

                    callback.onCheckOldPassword(found);

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
                params.put("password", pass);
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }
    private void changePassword(final String email,final  String pass){

        RequestQueue requestQueue= Volley.newRequestQueue(this);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, AppConfig.URL_CHANGE_PASSWORD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("Error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        SessionManager session = new SessionManager(getApplicationContext());
                        session.setLogin(false);

                        Toast.makeText(getApplicationContext(),"Please Login Again", Toast.LENGTH_SHORT).show();
                        // Launch home activity
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("ErrorMsg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    finish();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
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
                params.put("email",email);
                params.put("password",pass);

                return params;
            }
        };
        requestQueue.add(stringRequest);

    }
    private interface VolleyCallback{
        void onCheckOldPassword(Boolean result);
    }

}







