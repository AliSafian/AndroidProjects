package com.facefive.meetbook;

import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.KeyEvent.KEYCODE_BACK;
import static android.view.KeyEvent.KEYCODE_ENTER;

public class SettingsActivity extends AppCompatActivity implements Communicator{

    private TextView name_tv;
    private ImageView image;
    RelativeLayout notification;
    RelativeLayout privacy;
    RelativeLayout change_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


            image=(ImageView)findViewById(R.id.circleImageView);

            name_tv= (TextView)findViewById(R.id.name);
            notification = (RelativeLayout)findViewById(R.id.layout_notification) ;
            privacy = (RelativeLayout)findViewById(R.id.layout_privacy) ;
            change_pass = (RelativeLayout)findViewById(R.id.layout_change_pass) ;



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

            change_pass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(),ChangePasswordActivity.class);
                    startActivity(intent);
                }
            });
            name_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentManager fm = getFragmentManager();
                        ChangeNameDialogFragment frag = new ChangeNameDialogFragment();
                        frag.show(fm , "frag_change_name");
                    }
        });

        image.setOnClickListener(btnChoosePhotoPressed);

    }
    public View.OnClickListener btnChoosePhotoPressed = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            final int ACTIVITY_SELECT_IMAGE = 1234;
            startActivityForResult(i, ACTIVITY_SELECT_IMAGE);
        }
    };
    public void onPrivacyActivity(){


    }
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
                    Toast.makeText(getApplicationContext(), "ImageChanged", Toast.LENGTH_SHORT).show();
                }
        }

    };

    @Override
    public void onDialogName(String name) {

        name_tv.setText(name);
    }

}
