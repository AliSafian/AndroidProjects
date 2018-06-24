package com.facefive.meetbook;

/**
 * Created by Ashar on 1/21/2018.
 */

public class MeetingSingleRow{
    public int MeetID;
    public String name;
    public String description;
    public int image;
    public String purpose;
    public String starttime,endtime;
    public int senderID,receiverID;
    public String reqTime;




    public MeetingSingleRow(String name , String description , int image,int meetID, String purpose,String starttime,String endtime,int SenderID,int ReceiverID,String reqTime)
    {
        this.name = name;
        this.description=description;
        this.image = image;
        this.purpose=purpose;
        this.starttime=starttime;
        this.endtime=endtime;
        this.MeetID=meetID;
        this.senderID=SenderID;
        this.receiverID=ReceiverID;
        this.reqTime=reqTime;
    }
}
