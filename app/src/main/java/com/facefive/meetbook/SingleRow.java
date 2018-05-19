package com.facefive.meetbook;

/**
 * Created by Ashar on 1/21/2018.
 */

public class SingleRow{
    String name;
    String description;
    int image;
    String purpose;
    String date,starttime,endtime;
    int senderID,receiverID;

    public SingleRow(String name , String description , int image)
    {
        this.name = name;
        this.description=description;
        this.image = image;
    }
    public SingleRow(String name , String description , int image, String purpose)
    {
        this.name = name;
        this.description=description;
        this.image = image;
        this.purpose=purpose;
    }
    public SingleRow(String name , String description , int image, String purpose,String date,String starttime,String endtime,int senderID)
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
}
