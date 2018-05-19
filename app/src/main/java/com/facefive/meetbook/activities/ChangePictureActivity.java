package com.facefive.meetbook.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.facefive.meetbook.R;
import com.facefive.meetbook.UserHandling.SessionManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_picture);

        Button btn_change_photo = findViewById(R.id.btn_change_picture);
        Button btn_crop_photo = findViewById(R.id.btn_crop_picture);

        iv_profile_photo = findViewById(R.id.iv_profile_picture);


        SessionManager session = new SessionManager(getApplicationContext());

        mCurrentPhotoPath =  session.getPicturePath();
        if(mCurrentPhotoPath != null)
        {
            picUri =  Uri.parse(new File(mCurrentPhotoPath).toString());
            iv_profile_photo.setImageURI(picUri);
        }


        btn_crop_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ImageCropFunction();
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

        File mediaFile = new File(fileDir.getPath() + File.separator + getPictureName());
        mCurrentPhotoPath = mediaFile.getAbsolutePath();
        return mediaFile;
    }


    private String getPictureName()
    {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp=sdf.format(new Date());
        return "MeetBook"+timestamp+".jpg";
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

                session.setPicturePath(mCurrentPhotoPath);

                break;

            case GALLERY:
               if (data != null) {
                    picUri = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), picUri);
                        mCurrentPhotoPath = saveImage(bitmap);
                        picUri =  Uri.parse(new File(mCurrentPhotoPath).toString());
                        iv_profile_photo.setImageURI(picUri);
                        session.setPicturePath(mCurrentPhotoPath);

                    } catch (IOException e) {
                        Toast.makeText(ChangePictureActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case CROP:
                iv_profile_photo.setImageURI(picUri);
        }

    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = iv_profile_photo.getWidth();
        int targetH = iv_profile_photo.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        iv_profile_photo.setImageBitmap(bitmap);
    }
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
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

        public void ImageCropFunction() {

        // Image Crop Code
       try {
            Intent CropIntent = new Intent("com.android.camera.action.CROP");

            CropIntent.setDataAndType(picUri, "image/*");

            CropIntent.putExtra("crop", "true");
            CropIntent.putExtra("outputX", iv_profile_photo.getWidth());
            CropIntent.putExtra("outputY",  iv_profile_photo.getHeight());
            CropIntent.putExtra("aspectX", 5);
            CropIntent.putExtra("aspectY", 5);
            CropIntent.putExtra("scaleUpIfNeeded", true);
            CropIntent.putExtra("return-data", true);

            startActivityForResult(CropIntent, CROP);

        } catch (ActivityNotFoundException e) {
           String errorMessage = "Whoops - your device doesn't support the crop action!";
           Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
           toast.show();
        }
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
}
