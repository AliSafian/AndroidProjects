package com.facefive.meetbook;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    Button next_btn ;
    EditText name_et;
    EditText email_et;
    EditText con_pass_et;
    EditText pass_et;
    Spinner uni;
    boolean flag;
   final String SU_NAME="name",SU_EMAIL="email",SU_PASS="password",SU_UNINAME="uniname";
    String name,email,pass,con_pass,uni_name;
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
        Spinner spinner = (Spinner) findViewById(R.id.sp_uni);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.uni_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);




        spinner.setAdapter(adapter);
        isValidForm();
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 name = name_et.getText().toString().trim();
                 email = email_et.getText().toString().trim();
                 pass = pass_et.getText().toString().trim();
                 con_pass = con_pass_et.getText().toString().trim();
                 uni_name= uni.getSelectedItem().toString().trim();
                Pattern digit = Pattern.compile("[0-9]");
                Pattern aplha = Pattern.compile("[A-Za-z]");
                Matcher hasDigit = digit.matcher(pass);
                Matcher hasAlpha = aplha.matcher(pass);

                String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
               // String passwordPattern = "^(?=.*\\d).{6,15}$";
                String passwordPattern = "^[A-Za-z0-9-\\+]{6,16}$";

                flag = true;
                if(name.equals("")) {
                    name_et.setError("Empty name is not allowed");
                    name_et.requestFocus();
                    flag= false;
                }
                else if(!email.matches(emailPattern)) {
                    email_et.setError("Email is not valid");
                    email_et.requestFocus();
                    flag= false;
                }
                else if(!pass.matches(passwordPattern)) {
                    pass_et.setError("Pass is not valid");
                    pass_et.requestFocus();
                    flag= false;
                }
//                else if(pass.isEmpty() || pass.length() < 8) {
//                    pass_et.setError("Enter atleast 8 alphanumeric characters");
//                    pass_et.setText("");
//                    pass_et.requestFocus();
//                    flag= false;
//
//                }
//                else if(hasDigit.find()==false)
//                {
//                    pass_et.setError("It must contain atleast 1 digit");
//                    pass_et.setText("");
//                    pass_et.requestFocus();
//                    flag= false;
//                }
//                else if(hasAlpha.find()==false)
//                {
//                    pass_et.setError("It must contain alphabets");
//                    pass_et.setText("");
//                    pass_et.requestFocus();
//                    flag= false;
//                }
                else if (!con_pass.equals(pass)) {
                    con_pass_et.setError("Password does not match");
                    con_pass_et.setText("");
                    con_pass_et.requestFocus();
                    flag =false;
                }

                if (flag){
                    Intent i = new Intent(getApplicationContext(), VerifyCodeActivity.class);
                    i.putExtra("Flow", "FromSignUp");
                    i.putExtra("name", name);
                    i.putExtra("email", email);
                    i.putExtra("pass", pass);
                    i.putExtra("uni_name", uni_name);
                    startActivity(i);
                    finish();

                }
            }
        });
    }



    public boolean isValidForm()
    {
        flag = true;
        name_et.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if(name_et.getText().toString().trim().equals("")) {
                        name_et.setError("Empty name is not allowed");
                        flag= false;
                    }
                }
            }
        });

        email_et.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {

                String email = email_et.getText().toString();
                String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

                if(!email.matches(emailPattern)) {
                    email_et.setError("Email is not valid");
                    email_et.requestFocus();
                    flag= false;
                }
            }

            @Override
            public void afterTextChanged(Editable arg0) {}

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { }


        });

        pass_et.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String pass = pass_et.getText().toString();

                    String passwordPattern = "^[_A-Za-z0-9-\\+]{6,16}$";
                    if(!pass.matches(passwordPattern)) {
                        pass_et.setError("Pass is not valid");
                        flag= false;
                    }
                    /*Pattern digit = Pattern.compile("[0-9]");
                    Pattern aplha = Pattern.compile("[A-Za-z]");
                    Matcher hasDigit = digit.matcher(pass);
                    Matcher hasAlpha = aplha.matcher(pass);


                    if (pass.isEmpty() || pass.length() < 8) {
                        pass_et.setError("Enter atleast 8 alphanumeric characters");
                        flag= false;

                    } else if(hasDigit.find()==false)
                    {
                        pass_et.setError("It must contain atleast 1 digit");
                        flag= false;
                    }
                    else if(hasAlpha.find()==false)
                    {
                        pass_et.setError("It must contain alphabets");
                        flag= false;
                    }*/
                }
            }
        });
        con_pass_et.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String con_pass = con_pass_et.getText().toString();
                    String pass = pass_et.getText().toString();
                    if (!con_pass.equals(pass)) {
                        con_pass_et.setError("Password does not match");
                        flag =false;

                    }
                }
            }
        });

        return flag?true:false;
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
                params.put(SU_NAME,name);
                params.put(SU_EMAIL,email);
                params.put(SU_PASS,pass);
                params.put(SU_UNINAME,uni_name);

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
