package com.facefive.meetbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.ProgressDialog;
import android.widget.Toast;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facefive.meetbook.UserHandling.SQLiteHandler;
import com.facefive.meetbook.UserHandling.UserSessionManager;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {

    TextView forgotPass_link,signupLink;

    private Button login_btn;
    private EditText email_et;
    private EditText pass_et;
    private String EMAIL="email";
    private String PASSWORD="password";
    private UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email_et= (EditText)findViewById(R.id.et_email);
        pass_et= (EditText)findViewById(R.id.passET);
        login_btn =(Button) findViewById(R.id.btn_login);
        signupLink=(TextView)findViewById(R.id.signUpLink);

        forgotPass_link=(TextView)findViewById(R.id.forgotPassLink);
        session = new UserSessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }




        login_btn.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
                String email = email_et.getText().toString().trim();
                String pass = pass_et.getText().toString().trim();
                if(email.equals(""))
                {
                    email_et.setError("Please Provide Email");
                    email_et.requestFocus();
                }
                else if(pass.equals(""))
                {
                    pass_et.setError("Please Provide Password");
                    pass_et.requestFocus();
                }
                else
                {
                    checkLogin(email , pass);
                }


           }});
        signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });
        forgotPass_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(LoginActivity.this,VerifyEmailActivity.class);
                startActivity(i);
            }
        });

    }

    private void checkLogin(final String email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";



        RequestQueue requestQueue=Volley.newRequestQueue(this);
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("Error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        session.setLogin(true);

                        JSONObject user = jObj.getJSONObject("User");
                        int userID = user.getInt("UserID");
                        String name = user.getString("Name");
                        String email = user.getString("Email");
                        String picName = user.getString("PictureName");
                        String uniName = user.getString("UniName");


                        // Inserting row in session
                        session.setUser(userID, name, email, picName,uniName);
                        // Launch main activity
                        Intent intent = new Intent(LoginActivity.this,
                                HomeActivity.class);
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
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };
        requestQueue.add(strReq);
    }

}
