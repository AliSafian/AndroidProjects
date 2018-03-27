package com.facefive.meetbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewPasswordActivity extends AppCompatActivity {

    EditText pass_et;
    EditText con_pass_et;
    Button save_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        pass_et= (EditText)findViewById(R.id.et_np_new_pass);
        con_pass_et= (EditText)findViewById(R.id.et_np_con_pass);

        save_btn = (Button)findViewById(R.id.btn_np_save);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = pass_et.getText().toString();
                String con_pass = con_pass_et.getText().toString();
                boolean flag= true;
                if(!pass.matches(AppConfig.PASSWORD_PATTERN))
                {

                    pass_et.setError("Password requires \nAtleast 1 uppercase and 1 lower case\nLength from 6 to 15");
                    pass_et.requestFocus();
                    flag= false;
                }
                if(!pass.equals(con_pass))
                {
                    con_pass_et.setError("Password does not match");
                    con_pass_et.requestFocus();
                    flag= false;
                }
                if(flag) {
                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(i);
                }

            }
        });

    }
}
