package com.facefive.meetbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
               int redColor =getResources().getColor(R.color.colorError);
               if(!email.toLowerCase().equals("test@meetbook.com"))
               {
                   email_et.setText("");
                   email_et.setHint("Invalid Email");
                   email_et.setHintTextColor(redColor);

               }
               else
               {
                   Intent i = new Intent(getApplicationContext() , VerifyCodeActivity.class);
                   i.putExtra("email_key", email );
                   i.putExtra("flow","onForgetPassword");
                   startActivity(i);
               }


           }
       }

        );

    }
}
