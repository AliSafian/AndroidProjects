package com.facefive.meetbook;

/**
 * Created by Shahid on 1/11/2018.
 */


import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.app.DialogFragment;
import android.app.Dialog;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    Calendar datetime;
    String toEventDate;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //Use the current time as the default values for the time picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        //Create and return a new instance of TimePickerDialog
        return new TimePickerDialog(getActivity(),this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }


    //onTimeSet() callback method
    public void onTimeSet(TimePicker view, int hourOfDay, int minute){



        datetime = Calendar.getInstance();
        Calendar c = Calendar.getInstance();

        datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        datetime.set(Calendar.MINUTE, minute);
        int hours=datetime.get(Calendar.HOUR_OF_DAY);
        int minutes=datetime.get(Calendar.MINUTE);
        String aMpM = " AM";

        if(hours>12)
        {
            aMpM=" PM";
            hours-=12;
        }
        String time=String.valueOf(hours)+":"+String.valueOf(minutes)+aMpM;



//        String aMpM = "AM";
//        if(hours >11)
//        {
//            aMpM = "PM";
//        }
        EditText timeViewer = (EditText) getActivity().findViewById(R.id.start_time);
//        if (datetime.getTimeInMillis() <= c.getTimeInMillis()) {
//            //it's after current
//            //  _refToFromEvent.showToEventToast();
//            timeViewer.setText("Event cannot have time past current time");
//            return;
//        }
        timeViewer.setText(time  );
        //timeViewer.setTextColor();
        timeViewer.setHint("");


    }

}