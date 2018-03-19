package com.facefive.meetbook;

/**
 * Created by Ashar on 1/22/2018.
 */

public class SingleRowChatItem {

    String name;
    String message;
    String time;
    int image;
    int status;
    public SingleRowChatItem(String name, String message, String time, int image,int status)
    {
        this.name = name;
        this.message=message;
        this.time = time;
        this.image = image;
        this.status = status;
    }

}
