package com.facefive.meetbook;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    Button next_btn ;
    ImageView dp_iv;
    EditText name_et;
    EditText email_et;
    EditText con_pass_et;
    EditText pass_et;
    Spinner uni;
   final String SU_NAME="name",SU_EMAIL="email",SU_PASS="password",SU_UNINAME="uniname";
    String su_name,su_email,su_pass,su_conpass,su_uniname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name_et= (EditText)findViewById(R.id.fNameET);
        email_et= (EditText)findViewById(R.id.email_signupET);
        pass_et= (EditText)findViewById(R.id.pass_signupET);
        con_pass_et= (EditText)findViewById(R.id.confPass_signupET);
        uni=(Spinner)findViewById(R.id.sp_uni);

        next_btn= (Button)findViewById(R.id.btn_su_next);
        //dp_iv= (ImageView)findViewById(R.id.iv_cdp_img);

        Spinner spinner = (Spinner) findViewById(R.id.sp_uni);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.uni_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 su_name = name_et.getText().toString().trim();
                 su_email = email_et.getText().toString().trim();
               su_pass = pass_et.getText().toString().trim();
                 su_conpass = con_pass_et.getText().toString().trim();
                 su_uniname=uni.getSelectedItem().toString().trim();

                boolean flag= true;
                int redColor =getResources().getColor(R.color.colorError);
                if(su_name.equals(""))
                {
                    name_et.setHint("Invalid Name");
                    name_et.setHintTextColor(redColor);
                    flag= false;
                }
                if(!su_email.toLowerCase().equals("test@mba"))
                {
                    email_et.setText("");
                    email_et.setHint("Invalid Email");
                    email_et.setHintTextColor(redColor);
                    flag= false;
                }
                if(su_pass.equals("") || su_conpass.length() < 4)
                {
                    pass_et.setText("");
                    pass_et.setHint("Invalid Pass");
                    pass_et.setHintTextColor(redColor);
                    flag= false;
                }
                if(!su_pass.equals(su_conpass))
                {
                    con_pass_et.setText("");
                    con_pass_et.setHint("Password Not Match");
                    con_pass_et.setHintTextColor(redColor);
                    flag= false;
                }

                if(flag) {
                    Intent i = new Intent(getApplicationContext(), VerifyCodeActivity.class);
                    //i.putExtra("flow", "onSignUp");
                   // i.putExtra("email_key" , email);
                    signUP();

                   // startActivity(i);

                }
//                Intent i = new Intent(getApplicationContext(), VerifyCodeActivity.class);
//                i.putExtra("flow", "onSignUp");
//                startActivity(i);
            }
        });

       // dp_iv.setOnClickListener(btnChoosePhotoPressed);


    }

    public void signUP(){

        RequestQueue requestQueue= Volley.newRequestQueue(this);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, AppConfig.URL_SIGNUP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(SignupActivity.this,response,Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(SignupActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignupActivity.this,error.toString(),Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params=new HashMap<String, String>();
                params.put(SU_NAME,su_name);
                params.put(SU_EMAIL,su_email);
                params.put(SU_PASS,su_pass);
                params.put(SU_UNINAME,su_uniname);

                return params;
            }
        };
        requestQueue.add(stringRequest);

    }




   /* public View.OnClickListener btnChoosePhotoPressed = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            final int ACTIVITY_SELECT_IMAGE = 1234;
            startActivityForResult(i, ACTIVITY_SELECT_IMAGE);
        }
    };

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case 1234:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();
                    Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
                }
        }

    };*/

}
