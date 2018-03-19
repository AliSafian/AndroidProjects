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
                int redColor =getResources().getColor(R.color.colorError);

                if(pass.equals("") || pass.length() < 4)
                {
                    pass_et.setText("");
                    pass_et.setHint("Invalid Pass");
                    pass_et.setHintTextColor(redColor);
                    flag= false;
                }
                if(!pass.equals(con_pass))
                {
                    con_pass_et.setText("");
                    con_pass_et.setHint("Password Not Match");
                    con_pass_et.setHintTextColor(redColor);
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
