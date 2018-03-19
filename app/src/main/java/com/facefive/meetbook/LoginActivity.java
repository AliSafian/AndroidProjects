package com.facefive.meetbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class LoginActivity extends AppCompatActivity {

    TextView signup_link;
    TextView forgotPass_link;
    Button login_btn;
    EditText email_et;
    EditText pass_et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //this.setTitle("Login");

        email_et= (EditText)findViewById(R.id.et_email);
        pass_et= (EditText)findViewById(R.id.passET);


        signup_link = findViewById(R.id.signUpLink);
        forgotPass_link = findViewById(R.id.forgotPassLink);
        login_btn = findViewById(R.id.btn_login);

        forgotPass_link.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              Intent i = new Intent(getApplicationContext() , VerifyEmailActivity.class);
              startActivity(i);
          }
        }

        );
        login_btn.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {

               String email = email_et.getText().toString();
               String pass = pass_et.getText().toString();
               boolean flag= true;
               int redColor =getResources().getColor(R.color.colorError);

               if(!email.toLowerCase().equals("test@meetbook.com"))
               {
                   email_et.setText("");
                   email_et.setHint("Incorrect Email");
                   email_et.setHintTextColor(redColor);
                   flag= false;
               }
               if(!pass.equals("1234") )
               {
                   pass_et.setText("");
                   pass_et.setHint("Incorrect Pass");
                   pass_et.setHintTextColor(redColor);
                   flag= false;
               }

               if(flag) {
                   Intent i = new Intent(getApplicationContext() , HomeActivity.class);
                   startActivity(i);
               }

           }
        }

        );
        signup_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent i = new Intent(getApplicationContext() , SignupActivity.class);
               startActivity(i);
           }
       }

        );

    }

}
