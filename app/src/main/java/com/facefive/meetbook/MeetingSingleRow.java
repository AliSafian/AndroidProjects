package com.facefive.meetbook;

/**
 * Created by Ashar on 1/21/2018.
 */

public class MeetingSingleRow{
    public String name;
    public String description;
    public int image;
    public String purpose;
    public String date,starttime,endtime;
    public int senderID,receiverID;

    public MeetingSingleRow(String name , String description , int image)
    {
        this.name = name;
        this.description=description;
        this.image = image;
    }
    public MeetingSingleRow(String name , String description , int image, String purpose)
    {
        this.name = name;
        this.description=description;
        this.image = image;
        this.purpose=purpose;
    }
    public MeetingSingleRow(String name , String description , int image, String purpose,String date,String starttime,String endtime,int senderID)
    {
        this.name = name;
        this.description=description;
        this.image = image;
        this.purpose=purpose;
        this.date=date;
        this.starttime=starttime;
        this.endtime=endtime;
        this.senderID=senderID;
    }
    public String getName()
    {
        return this.name;
    }

    public String getDescription()
    {
        return this.description;
    }
    public int getImageInt()
    {
        return this.image;
    }
}
