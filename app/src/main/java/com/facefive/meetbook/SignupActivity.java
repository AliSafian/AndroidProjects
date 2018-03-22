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

public class SignupActivity extends AppCompatActivity {

    Button next_btn ;
    ImageView dp_iv;
    EditText name_et;
    EditText email_et;
    EditText con_pass_et;
    EditText pass_et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name_et= (EditText)findViewById(R.id.fNameET);
        email_et= (EditText)findViewById(R.id.email_signupET);
        pass_et= (EditText)findViewById(R.id.pass_signupET);
        con_pass_et= (EditText)findViewById(R.id.confPass_signupET);


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
                String name = name_et.getText().toString();
                String email = email_et.getText().toString();
                String pass = pass_et.getText().toString();
                String con_pass = con_pass_et.getText().toString();
                boolean flag= true;
                int redColor =getResources().getColor(R.color.colorError);
                if(name.equals(""))
                {
                    name_et.setHint("Invalid Name");
                    name_et.setHintTextColor(redColor);
                    flag= false;
                }
                if(!email.toLowerCase().equals("test@meetbook.com"))
                {
                    email_et.setText("");
                    email_et.setHint("Invalid Email");
                    email_et.setHintTextColor(redColor);
                    flag= false;
                }
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
                    Intent i = new Intent(getApplicationContext(), VerifyCodeActivity.class);
                    i.putExtra("flow", "onSignUp");
                    i.putExtra("email_key" , email);
                    startActivity(i);

                }
//                Intent i = new Intent(getApplicationContext(), VerifyCodeActivity.class);
//                i.putExtra("flow", "onSignUp");
//                startActivity(i);
            }
        });

       // dp_iv.setOnClickListener(btnChoosePhotoPressed);


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
