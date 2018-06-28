package com.facefive.meetbook.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.tv.TvInputService;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facefive.meetbook.R;
import com.facefive.meetbook.UserHandling.SessionManager;
import com.facefive.meetbook.app.AppConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ChangePictureActivity extends AppCompatActivity {


    private static final int MY_REQUEST_CAMERA   = 10;
    private static final int MY_REQUEST_WRITE_CAMERA   = 11;
    private static final int CAMERA   = 12;

    private static final int MY_REQUEST_READ_GALLERY   = 13;
    private static final int MY_REQUEST_WRITE_GALLERY   = 14;
    private static final int GALLERY   = 15;
    private static final int CROP  = 16;



    private Uri picUri;
    private ImageView iv_profile_photo;
    private  String mCurrentPhotoPath;
    private  String encodedString;
    private  String imageName;
    private boolean isPhotoChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_picture);

        Button btn_change_photo = findViewById(R.id.btn_change_picture);
        final Button btn_save_photo = findViewById(R.id.btn_save_picture);

        isPhotoChanged = false;

        iv_profile_photo = findViewById(R.id.iv_profile_picture);
        final SessionManager session = new SessionManager(getApplicationContext());

        mCurrentPhotoPath =  session.getPicturePath();

        if(mCurrentPhotoPath !=null)
        {
            picUri =  Uri.parse(new File(mCurrentPhotoPath).toString());
            iv_profile_photo.setImageURI(picUri);
        }
        else
        {
            iv_profile_photo.setImageResource(R.drawable.ic_dp_demo);
        }


        btn_save_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isPhotoChanged)
                {
                    new EncodeImage().execute();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Select a new photo before saving", Toast.LENGTH_SHORT).show();
                }
            }
        }
        );
        btn_change_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] options = {"Take Photo", "Choose From Gallery","Cancel"};


                AlertDialog.Builder builder = new AlertDialog.Builder(ChangePictureActivity.this);
                builder.setTitle("Select Option");

                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {

                       // boolean result=Utility.checkPermission(activity_Set_Image.this);
                        if (options[item].equals("Take Photo")) {
                            checkPermissionCW();

                        } else if (options[item].equals("Choose From Gallery")) {
                            checkPermissionRG();

                        } else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });

    }
    private void checkPermissionCW(){
        int permissionCheck = ContextCompat.checkSelfPermission(ChangePictureActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    ChangePictureActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_REQUEST_WRITE_CAMERA);
        } else {
            checkPermissionCA();
        }
    }
    private void checkPermissionCA(){
        int permissionCheck = ContextCompat.checkSelfPermission(ChangePictureActivity.this, Manifest.permission.CAMERA);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    ChangePictureActivity.this, new String[]{Manifest.permission.CAMERA}, MY_REQUEST_CAMERA);
        } else {
            catchPhoto();
        }
    }
    private void checkPermissionRG(){
        int permissionCheck = ContextCompat.checkSelfPermission(ChangePictureActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    ChangePictureActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_REQUEST_READ_GALLERY);
        } else {
            checkPermissionWG();
        }
    }
    private void checkPermissionWG(){
        int permissionCheck = ContextCompat.checkSelfPermission(ChangePictureActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    ChangePictureActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_REQUEST_WRITE_GALLERY);
        } else {
            getPhotos();
        }
    }

    private void catchPhoto() {

        File file = getFile();
        if(file!=null) {
            try {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    picUri = FileProvider.getUriForFile(this,
                            "com.facefive.meetbook.fileprovider",
                            file);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    startActivityForResult(intent, CAMERA);
                }
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(ChangePictureActivity.this, "please check your sdcard status", Toast.LENGTH_SHORT).show();
        }
    }

    private void getPhotos() {

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        photoPickerIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivityForResult(photoPickerIntent, GALLERY);

    }

    public File getFile(){

        File fileDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files");

        if (!fileDir.exists()){
            if (!fileDir.mkdirs()){
                return null;
            }
        }

        imageName = getPictureName();
        File mediaFile = new File(fileDir.getPath() + File.separator +imageName);
        mCurrentPhotoPath = mediaFile.getAbsolutePath();
        return mediaFile;
    }

    private String getPictureName() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp=sdf.format(new Date());
        SessionManager sm = new SessionManager(getApplicationContext());
        return "MeetBook"+sm.getUserID()+timestamp+".jpg";
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != RESULT_OK){
            Log.e("msg", "photo not get");
            return;
        }

        SessionManager session = new SessionManager(getApplicationContext());

        switch (requestCode) {

            case CAMERA:
                iv_profile_photo.setImageURI(picUri);
                isPhotoChanged = true;
                break;

            case GALLERY:
               if (data != null) {
                    picUri = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), picUri);
                        mCurrentPhotoPath = saveImage(bitmap);
                        picUri =  Uri.parse(new File(mCurrentPhotoPath).toString());
                        iv_profile_photo.setImageURI(picUri);
                        isPhotoChanged = true;

                    } catch (IOException e) {
                        Toast.makeText(ChangePictureActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case CROP:
                iv_profile_photo.setImageURI(picUri);
                break;

        }

    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        try {
            File f = getFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            fo.close();
            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }
    public void onRequestPermissionsResult (int requestCode, String[] permissions,  int[] grantResults)
    {

        switch (requestCode) {
            case MY_REQUEST_CAMERA:
                catchPhoto();
                break;
            case MY_REQUEST_WRITE_CAMERA:
                checkPermissionCA();
                break;
            case MY_REQUEST_READ_GALLERY:
                checkPermissionWG();
                break;
            case MY_REQUEST_WRITE_GALLERY:
                getPhotos();
                break;
        }
    }

    private class EncodeImage extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {

            Bitmap bm = BitmapFactory.decodeFile(mCurrentPhotoPath);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG,30, stream);

            byte [] array = stream.toByteArray();
            encodedString = Base64.encodeToString(array,0);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            makeRequest();
        }
    }

    private void makeRequest() {

        RequestQueue requestQueue= Volley.newRequestQueue(this);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, AppConfig.URL_SAVE_IMAGE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("Error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        SessionManager session = new SessionManager(getApplicationContext());
                        session.setPicturePath(mCurrentPhotoPath);
                        Toast.makeText(getApplicationContext(),"Picture Updated Succesfully", Toast.LENGTH_SHORT).show();
                        finish();

                        // Launch home activity
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("ErrorMsg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();

                        finish();

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    finish();
                   /* Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);*/
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


                SessionManager sm = new SessionManager(getApplicationContext());


                params.put("user_id",sm.getUserID()+"");
                params.put("image_name",imageName);
                params.put("image_eccoded_string",encodedString);

                return params;
            }
        };
        requestQueue.add(stringRequest);

    }
}
