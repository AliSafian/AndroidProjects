package com.facefive.meetbook.TimetableSession;

import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facefive.meetbook.app.AppConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hafiz on 3/27/2018.
 */

public  class  TimetableSession {
    public static int noOfSlots;
    public static int timetableID;
    public  static Time duration;
    public static Time startTime = new Time(0,0,0);
    public static Time  endTime= new Time(0,0,0);
    public static ArrayList<TimetableDay> Days = new ArrayList<>();

    public TimetableSession getTimetable(int UserID)
    {
        return  null;
    }
}
