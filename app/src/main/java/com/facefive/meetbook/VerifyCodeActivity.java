package com.facefive.meetbook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);


        name =getIntent().getStringExtra("name");
        email =getIntent().getStringExtra("email");
        pass=getIntent().getStringExtra("pass");
        uni_name=getIntent().getStringExtra("name");

        email_tv =(TextView) findViewById(R.id.tv_emailCode);
        resend_tv = (TextView)findViewById(R.id.tv_link_resend);
        code1_et = (EditText) findViewById(R.id.et_code1);
        code2_et = (EditText)findViewById(R.id.et_code2);
        code3_et =(EditText) findViewById(R.id.et_code3);
        code4_et =(EditText) findViewById(R.id.et_code4);
        submit_btn = (Button) findViewById(R.id.btn_submit);


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
                    String flow=getIntent().getStringExtra("Flow");
                    if(flow.equals("FromSignUp"))
                    {
                        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(i);
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
                    String body = "Dear "+name+ ", Thank You For Registration. \nYou Registration code is "+pin;
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

}
