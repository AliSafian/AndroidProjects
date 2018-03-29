package com.facefive.meetbook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class SignupActivity extends AppCompatActivity {

    private Button next_btn ;
    private EditText name_et;
    private EditText email_et;
    private EditText con_pass_et;
    private EditText pass_et;
    private Spinner uni;
    private boolean flag;
    private ProgressDialog pDialog;
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


        getUniversitiesList(new VolleyCallback() {
            @Override
            public void onEmailExist(Boolean result) {

            }

            @Override
            public void onGetUniversities(String[] list) {
                Spinner spinner = (Spinner) findViewById(R.id.sp_uni);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, list);
                spinner.setAdapter(adapter);
            }
        });

        isValidForm();
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 name = name_et.getText().toString().trim();
                 email = email_et.getText().toString().trim();
                 pass = pass_et.getText().toString().trim();
                 con_pass = con_pass_et.getText().toString().trim();
                 uni_name= uni.getSelectedItem().toString().trim();

                flag = true;
                if(name.equals("")) {
                    name_et.setError("Empty name is not allowed");
                    name_et.requestFocus();
                    flag= false;
                    return;
                }
                else if(!email.matches(AppConfig.EMAIL_PATTERN)) {
                    email_et.setError("Email is not valid");
                    email_et.requestFocus();
                    flag= false;
                    return;
                }
                else if(!pass.matches(AppConfig.PASSWORD_PATTERN)) {
                    pass_et.setError("Password requires \nAtleast 1 uppercase and 1 lower case\nLength from 6 to 15");
                    pass_et.requestFocus();
                    flag= false;
                    return;
                }
                else if (!con_pass.equals(pass)) {
                    con_pass_et.setError("Password does not match");
                    con_pass_et.setText("");
                    con_pass_et.requestFocus();
                    flag =false;
                    return;
                }


                isEmailExist(email, new VolleyCallback(){
                    @Override
                    public void onEmailExist(Boolean result){

                        if(!result)
                        {
                            Intent i = new Intent(getApplicationContext(), VerifyCodeActivity.class);

                            i.putExtra("Flow", "FromSignUp");
                            i.putExtra("name", name);
                            i.putExtra("email", email);
                            i.putExtra("pass", pass);
                            i.putExtra("uni_name", uni_name);
                            startActivity(i);
                            finish();
                        }
                        else
                        {
                            email_et.setError("Already Taken");
                            email_et.requestFocus();

                        }

                    }
                    @Override
                    public void onGetUniversities(String [] list){

                    }
                });


            }
        });
    }



    public boolean isValidForm()
    {
        name_et.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if(name_et.getText().toString().trim().equals("")) {
                        name_et.setError("Empty name is not allowed");
                    }
                }
            }
        });

        email_et.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {

                String email = email_et.getText().toString();


                if(!email.matches(AppConfig.EMAIL_PATTERN)) {
                    email_et.setError("Email is not valid");
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

                    if(!pass.matches(AppConfig.PASSWORD_PATTERN)) {
                        pass_et.setError("Password requires \nAtleast 1 uppercase and 1 lower case \nAtleast 1 digit \nLength from 6 to 15");
                    }
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
                    }
                }
            }
        });

        return flag?true:false;
    }
    public  void getUniversitiesList(final VolleyCallback callback ){

        RequestQueue requestQueue= Volley.newRequestQueue(this);

        StringRequest stringRequest=new StringRequest(Request.Method.GET, AppConfig.URL_GET_UNIVERSITIES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jObj = new JSONObject(response);

                    int size= jObj.getInt("count");


                    String []uniList = new String[size];

                    for(int i =0 ; i < size; i++)
                    {
                        uniList[i] =jObj.getString(i+"");
                    }

                    callback.onGetUniversities(uniList);

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
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

                params.put("email", email);
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }
    public  void isEmailExist(final String email, final VolleyCallback callback ){

        RequestQueue requestQueue= Volley.newRequestQueue(this);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, AppConfig.URL_CHECK_EMAIL_EXIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jObj = new JSONObject(response);

                    Boolean found= jObj.getBoolean("found");

                        callback.onEmailExist(found);




                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
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

                params.put("email", email);
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

   public interface VolleyCallback{
       void onEmailExist(Boolean result);
       void onGetUniversities(String [] list);



   }

}

