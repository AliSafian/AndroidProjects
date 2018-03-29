package com.facefive.meetbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class VerifyEmailActivity extends AppCompatActivity {

    Button btn;
    EditText email_et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);


        btn = (Button)findViewById(R.id.btn_next);


        btn.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
               email_et =(EditText) findViewById(R.id.et_email1);
               String email = email_et.getText().toString();

               if(!email.matches(AppConfig.EMAIL_PATTERN))
               {
                   email_et.setError("Invalid Email Format");
               }
               else
               {
                   Intent i = new Intent(getApplicationContext() , VerifyCodeActivity.class);
                   i.putExtra("email", email );
                   i.putExtra("Flow","FromForgetPassword");
                   startActivity(i);
               }


           }
       }

        );

    }
}
