package com.facefive.meetbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facefive.meetbook.UserHandling.UserSessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VerifyCodeActivity extends AppCompatActivity {

    private EditText code1_et;
    private EditText code2_et;
    private EditText code3_et;
    private EditText code4_et;
    private String email;
    private String name;
    private String pass;
    private String uni_name;
    private TextView email_tv;
    private TextView resend_tv;
    private Button submit_btn;
    private int tries=0;
    private static int pin;
    private UserSessionManager session;

    final String KEY_NAME="name",KEY_EMAIL="email",KEY_PASS="password",KEY_PIC_NAME = "picname",KEY_UNI_NAME="uniname";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);
        String flow =getIntent().getStringExtra("Flow");

        if(flow.equals("FromSignUp"))
        {
            pass=getIntent().getStringExtra("pass");
            uni_name=getIntent().getStringExtra("uni_name");
            name =getIntent().getStringExtra("name");
        }

        email =getIntent().getStringExtra("email");


        email_tv = findViewById(R.id.tv_emailCode);
        resend_tv = findViewById(R.id.tv_link_resend);
        code1_et =  findViewById(R.id.et_code1);
        code2_et = findViewById(R.id.et_code2);
        code3_et = findViewById(R.id.et_code3);
        code4_et = findViewById(R.id.et_code4);
        submit_btn =  findViewById(R.id.btn_submit);

         session = new UserSessionManager(getApplicationContext());


        email_tv.setText(email);
        sendEmail();
        onSingleCharacter();

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String code = code1_et.getText().toString()+code2_et.getText().toString()+code3_et.getText().toString()+code4_et.getText().toString();

                if( !code.equals(pin+""))
                {
                    Toast.makeText(getApplicationContext(), "Invalid Code" , Toast.LENGTH_SHORT).show();
                }
                else {
                    String flow =getIntent().getStringExtra("Flow");
                    if(flow.equals("FromSignUp"))
                    {
                        signUP();
                    }
                    else if(flow.equals("FromForgetPassword")){
                        Intent i = new Intent(getApplicationContext(), NewPasswordActivity.class);
                        startActivity(i);
                    }
                }
            }
        });

        resend_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tries <3)
                {
                    sendEmail();
                    tries++;
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Tries No More Than 3, Please Sign up Again", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent i = new Intent(getApplicationContext(), SignupActivity.class);
                    startActivity(i);
                }
            }
        });



    }

    private int generatePIN()
    {

        return (int)(Math.random()*9000)+1000;
    }
    private void sendEmail()
    {
        new Thread(new Runnable() {

            public void run() {

                try {

                    final String senderEmail ="we.meetbook@gmail.com";
                    final String password ="mba@gmail";
                    pin = generatePIN();
                    MailSender sender = new MailSender( senderEmail ,password);
                    // sender.addAttachment(Environment.getExternalStorageDirectory().getPath()+"/image.jpg");
                    String body = "Dear Sir/Madam, Thank You For Registration. \nYou Registration code is "+pin;
                    String subject ="MeetBook: Verification Code ";
                    sender.sendMail(subject ,body , senderEmail,email );

                } catch (Exception e) {

                    Toast.makeText(getApplicationContext(),"Facing Some Problem While Sending Mail",Toast.LENGTH_LONG).show();
                }

            }

        }).start();
    }
    public void onSingleCharacter()
    {

       code1_et.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                String c1 = code1_et.getText().toString();
                if(c1.length()==1  ) {
                    code2_et.requestFocus();
                    code2_et.setFocusable(true);
                }

            }

            @Override
            public void afterTextChanged(Editable arg0) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { }


        });
        code2_et.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {

                String c2 = code2_et.getText().toString();
                if(c2.length()==1) {
                    code3_et.requestFocus();
                    code3_et.setFocusable(true);
                }
            }

            @Override
            public void afterTextChanged(Editable arg0) {}

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { }


        });
        code3_et.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {

                String c3 = code3_et.getText().toString();
                if(c3.length()==1 ) {
                    code4_et.requestFocus();
                    code4_et.setFocusable(true);
                }
            }

            @Override
            public void afterTextChanged(Editable arg0) {}

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { }


        });
    }

        public void signUP(){

        RequestQueue requestQueue= Volley.newRequestQueue(this);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, AppConfig.URL_SIGNUP, new Response.Listener<String>() {
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

                        Toast.makeText(getApplicationContext(),
                                "YES", Toast.LENGTH_LONG).show();

                        JSONObject user = jObj.getJSONObject("User");
                        int userID = user.getInt("UserID");
                        String name = user.getString("Name");
                        String email = user.getString("Email");
                        String picName = user.getString("PictureName");
                        String uniName = user.getString("UniName");



                        // Inserting row in session
                        session.setUser(userID, name, email, picName,uniName);
                        // Launch main activity
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
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
                }            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params=new HashMap<String, String>();
                params.put(KEY_NAME,name);
                params.put(KEY_EMAIL,email);
                params.put(KEY_PASS,pass);
                params.put(KEY_PIC_NAME ,"");
                params.put(KEY_UNI_NAME,uni_name);

                return params;
            }
        };
        requestQueue.add(stringRequest);

    }

}
