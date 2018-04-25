package com.facefive.meetbook.TimetableSession;

import java.sql.Array;
import java.sql.Time;
import java.util.ArrayList;

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
}
