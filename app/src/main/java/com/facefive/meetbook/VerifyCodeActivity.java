package com.facefive.meetbook;

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

    EditText code1_et;
    EditText code2_et;
    EditText code3_et;
    EditText code4_et;
    String code1;
    String code2;
    String code3;
    String code4;
    TextView email_tv;
    Button submit_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);

        Intent i = getIntent();
        String email = i.getStringExtra("email_key");
        email_tv = findViewById(R.id.tv_emailCode);
        email_tv.setText(email);
        code1_et = (EditText) findViewById(R.id.et_code1);
        code2_et = (EditText)findViewById(R.id.et_code2);
        code3_et =(EditText) findViewById(R.id.et_code3);
        code4_et =(EditText) findViewById(R.id.et_code4);
        submit_btn = (Button) findViewById(R.id.btn_submit);



        cnFocusChange();



        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String code = code1_et.getText().toString()+code2_et.getText().toString()+code3_et.getText().toString()+code4_et.getText().toString();

                if( !code.equals("1234"))
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
                    else if(flow.equals("onForgetPassword")){
                        Intent i = new Intent(getApplicationContext(), NewPasswordActivity.class);
                        startActivity(i);
                    }
                }
            }
        });




    }
    public void cnFocusChange()
    {

       code1_et.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                String c1 = code1_et.getText().toString();
                if(c1.length()==1  ) {
                    code1 =c1;
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
                   code2=c2;
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
                   code3=c3;
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
