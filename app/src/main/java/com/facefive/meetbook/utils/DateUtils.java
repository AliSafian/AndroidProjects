package com.facefive.meetbook.utils;

import java.time.Period;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    private final static String months[] = {"Jan", "Feb", "Mar","Apr", "May", "Jun", "Jul", "Aug","Sep", "Oct", "Nov", "Dec"};

    private final static String days[] = {"Sun", "Mon", "Tue","Wed", "Thu", "Fri", "Sat"};


    public static String getDateTimeString(String timeStamp){

        Calendar sysdate=Calendar.getInstance();
        Calendar givendate=Calendar.getInstance();
        int year=Integer.parseInt( timeStamp.substring(0,4));
        int month=Integer.parseInt( timeStamp.substring(5,7))-1;
        int day=Integer.parseInt( timeStamp.substring(8,10));
        int hours=Integer.parseInt( timeStamp.substring(11,13));
        int minuts=Integer.parseInt( timeStamp.substring(14,16));

        String time= timeStamp.substring(11,16);






     givendate.set(year,month,day,hours,minuts,0);

     if(sysdate.after(givendate))
     {
         long diff = (sysdate.getTimeInMillis() - givendate.getTimeInMillis());
         if(day==sysdate.get(Calendar.DAY_OF_MONTH))
         {
             if(diff<(60 * 1000))
             {
                 return (diff / (1000))+" seconds ago";
             }
             if(diff>(60 * 1000) && diff<(60 * 60 * 1000))
             {
                 return (diff / (60 * 1000))+" minuts ago";
             }
             if(diff>(60 * 1000) && diff<(60 * 60 * 1000))
             {
                 return (diff / (60 * 1000))+" minuts ago";
             }
             if((diff>(60 * 60 * 1000) && diff<(24 * 60 * 60 * 1000)))
             {
                 return (diff / (60 * 60 * 1000))+" hours ago";
             }
             else
                 return "Nothing";

         }

         if(sysdate.get(Calendar.DAY_OF_MONTH)-day==1)
         {
             return  "yesterday "+time;
         }
         if((sysdate.get(Calendar.DAY_OF_MONTH)-day>1) && (sysdate.get(Calendar.DAY_OF_MONTH)-day<7))
         {
             return  getNameOfDay(givendate)+ " "+time;
         }
         else
         {

             return  getNameOfMonth(month)+" "+day+", "+year;
         }
     }
     else
     {
         long diff = ( givendate.getTimeInMillis()-sysdate.getTimeInMillis());
         if(day==sysdate.get(Calendar.DAY_OF_MONTH))
         {
             if(diff<(60 * 1000))
             {
                 return (diff / (1000))+" seconds remaining";
             }
             if(diff>(60 * 1000) && diff<(60 * 60 * 1000))
             {
                 return (diff / (60 * 1000))+" minuts remaining";
             }
             if(diff>(60 * 1000) && diff<(60 * 60 * 1000))
             {
                 return (diff / (60 * 1000))+" minuts remaining";
             }
             if((diff>(60 * 60 * 1000) && diff<(24 * 60 * 60 * 1000)))
             {
                 return (diff / (60 * 60 * 1000))+" hours remaining";
             }
             else
                 return "Nothing";

         }

         if(day-sysdate.get(Calendar.DAY_OF_MONTH)==1)
         {
             return  "tomorrow "+time;
         }
         if((day-sysdate.get(Calendar.DAY_OF_MONTH)>1) && (day-sysdate.get(Calendar.DAY_OF_MONTH)<7))
         {
             return  getNameOfDay(givendate)+ " " +hours+":"+minuts;
         }
         else
         {

             return  getNameOfMonth(month)+" "+day+", "+year;
         }
     }

    }
    public static String getNameOfDay(Calendar calendar) {

       // calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR));

        int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);

        return days[dayIndex - 1];
    }
    public static String getNameOfMonth(int month) {

        // calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR));

        return months[month];
    }

}
