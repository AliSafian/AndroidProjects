package com.facefive.meetbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Selection;
import android.view.KeyEvent;
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


//        code1_et.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                Toast.makeText(getApplicationContext(), code1_et.getText(), Toast.LENGTH_SHORT).show();
//                if(keyCode<= 96 && keyCode <= 105) {
//                    Selection.setSelection(code2_et.getText(),code1_et.getSelectionStart());
//                    code2_et.requestFocus();
//            }
//                return false;
//            }
//        });

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = code1_et.getText().toString()+code2_et.getText().toString()+code3_et.getText().toString()+code4_et.getText().toString();

                if( !code.equals("1234"))
                {
                    Toast.makeText(getApplicationContext(), "Invalid Code" , Toast.LENGTH_SHORT).show();
                }
                else {
                    String flow=getIntent().getStringExtra("flow");
                    if(flow.equals("onSignUp"))
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
}
