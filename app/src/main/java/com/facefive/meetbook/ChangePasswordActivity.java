package com.facefive.meetbook;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Context;
import android.content.Intent;

import android.widget.EditText;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class ChangePasswordActivity extends AppCompatActivity  {




    private EditText OldPassword;
    private EditText NewPassword;
    private EditText ConfirmPassword;

    private Button btnSubmit;
    CharSequence text = "Password changed successfully!";
    CharSequence text1 = "Incorrect Password!";
    CharSequence text2 = "Please fill the fields!";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        addListenerOnButton();
      }
    public void addListenerOnButton() {


        OldPassword = (EditText) findViewById(R.id.et_old_pass);
        NewPassword = (EditText) findViewById(R.id.et_new_pass);
        ConfirmPassword = (EditText) findViewById(R.id.et_con_pass);

         btnSubmit = (Button) findViewById(R.id.btn_cp_submit);
        btnSubmit.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

                if(OldPassword.getText().toString().length() == 0 || NewPassword.getText().toString().length() == 0 || ConfirmPassword.getText().toString().length() == 0) {

                    Toast.makeText(ChangePasswordActivity.this, text2,
                            Toast.LENGTH_SHORT).show();
                }
                else {

                    String strPass1 = NewPassword.getText().toString();
                    String strPass2 = ConfirmPassword.getText().toString();
                    if (strPass1.equals(strPass2)) {
                        Toast.makeText(ChangePasswordActivity.this, text,
                                Toast.LENGTH_SHORT).show();

                        finish();

                        OldPassword.setText("");
                        NewPassword.setText("");
                        ConfirmPassword.setText("");


                    } else {
                        Toast.makeText(ChangePasswordActivity.this, text1,
                                Toast.LENGTH_SHORT).show();
                        OldPassword.setText("");
                        NewPassword.setText("");
                        ConfirmPassword.setText("");




                    }

                                        Intent intent = new Intent( getApplicationContext(),HomeActivity.class);
//                    startActivity(intent);
                }



            }

        });

    }

}







