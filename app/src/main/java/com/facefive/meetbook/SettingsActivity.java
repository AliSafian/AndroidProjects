package com.facefive.meetbook;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facefive.meetbook.UserHandling.UserSessionManager;

import java.io.File;


public class SettingsActivity extends AppCompatActivity implements Communicator{

    private TextView name_tv;
    private TextView email_tv;
    private ImageView image;
    private RelativeLayout notification;
    private RelativeLayout privacy;
    private RelativeLayout change_pass;
    private RelativeLayout logout;
    private UserSessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


            image=(ImageView)findViewById(R.id.circleImageView);





            name_tv= findViewById(R.id.name);
            email_tv= findViewById(R.id.email);
           session=new UserSessionManager(getApplicationContext());
            name_tv.setText(session.getName());
            email_tv.setText(session.getEmail());

            String path =session.getPicturePath();
            if(path!=null)
            {
                image.setImageURI(Uri.parse(new File(path).toString()));
            }
            else
            {
                image.setImageResource(R.drawable.dp_demo);
            }

            notification = (RelativeLayout)findViewById(R.id.layout_notification) ;
            privacy = (RelativeLayout)findViewById(R.id.layout_privacy) ;
            change_pass = (RelativeLayout)findViewById(R.id.layout_change_pass) ;
            logout = (RelativeLayout)findViewById(R.id.layout_logout) ;





            notification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent=new Intent(getApplicationContext(),NotificationActivity.class);
                    startActivity(intent);
                }
            });

            privacy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(),PrivacyActivity.class);
                    startActivity(intent);
                }
            });

            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    finish();
                    Intent i = new Intent(getApplicationContext(),LoginActivity.class );
                    UserSessionManager session = new UserSessionManager(getApplicationContext());
                    session.setLogin(false);
                    startActivity(i);
                }
            });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),NotificationActivity.class);
                startActivity(intent);
            }
        });
            name_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getApplicationContext(),ChangeNameActivity.class);
                        startActivity(intent);
                        /*FragmentManager fm = getFragmentManager();
                        ChangeNameDialogFragment frag = new ChangeNameDialogFragment();
                        frag.show(fm , "frag_change_name");*/
                    }
        });

        image.setOnClickListener(btnChoosePhotoPressed);

    }
    public View.OnClickListener btnChoosePhotoPressed = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          /*  Intent i = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            final int ACTIVITY_SELECT_IMAGE = 1234;
            startActivityForResult(i, ACTIVITY_SELECT_IMAGE);*/

          Intent intent = new Intent(getApplicationContext(), ChangePictureActivity.class);
          startActivity(intent);
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
                    //image.setImageURI(selectedImage);
                    Toast.makeText(getApplicationContext(), "ImageChanged", Toast.LENGTH_SHORT).show();
                }
        }

    };
    @Override
    public void onResume(){
        super.onResume();

        session=new UserSessionManager(getApplicationContext());
        name_tv.setText(session.getName());
        String path =session.getPicturePath();
        if(path!=null)
        {
            image.setImageURI(Uri.parse(new File(path).toString()));
        }

    }

    @Override
    public void onDialogName(String name) {

        name_tv.setText(name);
    }

}
