package com.facefive.meetbook;

import android.content.Intent;
import android.media.MediaCas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facefive.meetbook.UserHandling.UserSessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NewPasswordActivity extends AppCompatActivity {

    private EditText pass_et;
    private EditText con_pass_et;
    private Button save_btn;
    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        email = getIntent().getStringExtra("email");
        pass_et=findViewById(R.id.et_np_new_pass);
        con_pass_et= findViewById(R.id.et_np_con_pass);

        save_btn = findViewById(R.id.btn_np_save);
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
                    changePassword(email , pass);
                }

            }
        });

    }
    public void changePassword(final String email,final  String pass){

        RequestQueue requestQueue= Volley.newRequestQueue(this);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, AppConfig.URL_CHANGE_PASSWORD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("Error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        UserSessionManager session = new UserSessionManager(getApplicationContext());
                        session.setLogin(true);

                        JSONObject user = jObj.getJSONObject("User");
                        int userID = user.getInt("UserID");
                        String name = user.getString("Name");
                        String email = user.getString("Email");
                        String picName = user.getString("PictureName");
                        String uniName = user.getString("UniName");



                        // Inserting row in session
                        session.setUser(userID, name, email, picName,uniName);

                        Toast.makeText(getApplicationContext(),"Password Changed Succesfully", Toast.LENGTH_SHORT).show();
                        // Launch home activity
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("ErrorMsg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    finish();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params=new HashMap<String, String>();
                params.put("email",email);
                params.put("password",pass);

                return params;
            }
        };
        requestQueue.add(stringRequest);

    }
}
