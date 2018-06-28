package com.facefive.meetbook.TimetableSession;


import java.sql.Time;
import java.util.ArrayList;

public class TimetableDay {

    //..........................................private data members
    private String day;
//    private int numOfSlots;
//    private Time startTime;
//    private Time endTime;
    private ArrayList<SlotSingleRow> slotList;

    //........................................... constructor
    public TimetableDay(String day,ArrayList<SlotSingleRow> list)
    {
        this.day=day;
        this.slotList=list;
    }

    public TimetableDay(String day, int numOfSlots, Time startTime, Time endTime) {
        this.day = day;
//        this.numOfSlots = numOfSlots;
//        this.startTime = startTime;
//        this.endTime = endTime;
        slotList = new ArrayList<>();

        //get startTime and endTime in minutes
        int mStart = calMinutes(startTime);
        int mEnd = calMinutes(endTime);

        //get slot duration
        int duration = (mEnd-mStart)/numOfSlots;

        int tempStart = mStart;
        int tempEnd = mEnd;
        Time temp;
        for (int i = 0; i < numOfSlots; i++)
        {
            SlotSingleRow s = new SlotSingleRow(-1,getTime(tempStart),getTime(tempStart+duration-1),"","Free",day,-1);
            tempStart = tempStart+duration;
            slotList.add(s);
        }

    }
    private  Time getTime(int m)
    {
        Time t = new Time(0,0,0);
        t.setHours(m/60);
        t.setMinutes(m%60);
        return t;
    }
    private int calMinutes(Time t)
    {
        int m=0;
        m = t.getHours()*60+t.getMinutes();
        return m;
    }


    //.................................................getter


    public String getDay() {
        return day;
    }

//    public int getNumOfSlots() {
//        return numOfSlots;
//    }
//
//    public Time getStartTime() {
//        return startTime;
//    }
//
//    public Time getEndTime() {
//        return endTime;
//    }

    public ArrayList<SlotSingleRow> getSlotList() {
        return slotList;
    }

    //.............................................setter


    public void setDay(String day) {
        this.day = day;
    }

//    public void setNumOfSlots(int numOfSlots) {
//        this.numOfSlots = numOfSlots;
//    }
//
//    public void setStartTime(Time startTime) {
//        this.startTime = startTime;
//    }
//
//    public void setEndTime(Time endTime) {
//        this.endTime = endTime;
//    }

    public void setSlotList(ArrayList<SlotSingleRow> slotList) {
        this.slotList = slotList;
    }
}

