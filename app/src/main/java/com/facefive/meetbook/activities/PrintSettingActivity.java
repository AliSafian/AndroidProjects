package com.facefive.meetbook.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facefive.meetbook.R;

import java.util.ArrayList;
import java.util.List;

public class PrintSettingActivity extends AppCompatActivity {


    Button btn_minus;
    Button btn_plus;
    ImageView btn_print;

    EditText et_noOfPages;

    Spinner sp_paperSize;
    Spinner sp_orientation;

    TextView tv_file;
    TextView tv_type;
    TextView tv_pages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_setting);

        btn_minus = (Button) findViewById(R.id.btn_minus);
        btn_plus = (Button) findViewById(R.id.btn_plus);
        btn_print = (ImageView) findViewById(R.id.btn_print);

        et_noOfPages = (EditText) findViewById(R.id.et_noOfPages);


        sp_paperSize = (Spinner) findViewById(R.id.sp_paperSize);
        sp_orientation = (Spinner) findViewById(R.id.sp_orientation);


        tv_file = (TextView) findViewById(R.id.tv_file);
        tv_type = (TextView) findViewById(R.id.tv_type);
        tv_pages = (TextView) findViewById(R.id.tv_pages);


        tv_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("*/*");
                startActivityForResult(i, 7);
            }
        });
////
//
//        Intent i = getIntent();
//        String path = i.getStringExtra("fileUrl");
//        Toast.makeText(PrintSettingActivity.this, path , Toast.LENGTH_LONG).show();
//

//
//        String type = null;
//        String extension = MimeTypeMap.getFileExtensionFromUrl(path);
//        if (extension != null) {
//            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
//        }
//







        List<String> paperSizeList = new ArrayList<String>();
        paperSizeList.add("Letter");
        paperSizeList.add("Legal");
        paperSizeList.add("A4");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, paperSizeList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_paperSize.setAdapter(dataAdapter);


        List<String> orientationList = new ArrayList<String>();
        orientationList.add("Portrait");
        orientationList.add("Landscape");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, orientationList);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_orientation.setAdapter(dataAdapter1);




        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int noOfPages = Integer.parseInt(et_noOfPages.getText().toString());
                noOfPages--;
                if(noOfPages < 1)
                    noOfPages = 1;
                et_noOfPages.setText(Integer.toString(noOfPages));
            }
        });
        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int noOfPages = Integer.parseInt(et_noOfPages.getText().toString());
                noOfPages++;
                et_noOfPages.setText(Integer.toString(noOfPages));

            }
        });

        btn_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(PrintSettingActivity.this, "Document is sent to printer successfully." , Toast.LENGTH_LONG).show();
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        switch(requestCode){

            case 7:

                if(resultCode==RESULT_OK){

                    String PathHolder = data.getData().getPath();
                    tv_file.setText(PathHolder);
                }
                break;

        }
    }
}
