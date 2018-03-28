package com.facefive.meetbook.TimetableSession;

import java.sql.Time;

public class SlotSingleRow {

    //..................................dataMembers
    private int sID;
    private Time startTime;
    private Time endTime;
    private String header;
    private String slotType;
    private String slotDay;
    private int tID;


    //.................................constructor

    public SlotSingleRow( int sID, Time startTime, Time endTime, String header, String slotType, String slotDay, int tID) {

        this.sID = sID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.header = header;
        this.slotType = slotType;
        this.slotDay = slotDay;
        this.tID = tID;
    }

    //...................................getter
    public int getsID() {
        return sID;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public String getHeader() {
        return header;
    }

    public String getSlotType() {
        return slotType;
    }

    public String getSlotDay() {
        return slotDay;
    }

    public int gettID() {
        return tID;
    }



    //.................................setter


    public void setsID(int sID) {
        this.sID = sID;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setSlotType(String slotType) {
        this.slotType = slotType;
    }

    public void setSlotDay(String slotDay) {
        this.slotDay = slotDay;
    }

    public void settID(int tID) {
        this.tID = tID;
    }
}
