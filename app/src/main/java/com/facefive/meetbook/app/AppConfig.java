package com.facefive.meetbook.app;

/**
 * Created by Shahid on 3/22/2018.
 */

public class AppConfig {
    public static final String SENDER_EMAIL = "We.MeetBook@gmail.com";
    public static final String SENDER_PASSWORD = "mba@web2018";


    public static final String URL_GETSEARCHEDUSERS= "http://meetbookapp.com/APIs/getSearchedUsers.php";
    public static final String URL_LOGIN = "http://meetbookapp.com/APIs/login_user.php";
    public static final String URL_TIMATABLE = "http://meetbookapp.com/APIs/timetable_request.php";
    public static final String URL_GETTIMETABLE = "http://meetbookapp.com/APIs/getTimetable_request.php";

    public static final String URL_GETCOMPLETETIMETABLE = "http://meetbookapp.com/APIs/getCompeleteTimetable_request.php";

    public static final String URL_GETALLSENTMEETINGS = "http://meetbookapp.com/APIs/getSentMeetRequests.php";
    public static final String URL_CHANGEMEETINGSTATUS = "http://meetbookapp.com/APIs/ChangeMeetingStatus.php";
    public static final String URL_CHANGEFOLLOW = "http://meetbookapp.com/APIs/changeFollow.php";
    public static final String URL_SAVEUPDATEMESSAGE = "http://meetbookapp.com/APIs/saveUpdateMessage.php";
    public static final String URL_GETUPDATEMESSAGES = "http://meetbookapp.com/APIs/getUpdateMessages_request.php";
    public static final String URL_DELUPDATEMESSAGE = "http://meetbookapp.com/APIs/deleteUpdateMessage.php";
    public static final String URL_SIGNUP = "http://meetbookapp.com/APIs/register_user.php";
    public static final String URL_GET_UNIVERSITIES= "http://www.meetbookapp.com/APIs/getUniversities.php";
    public static final String URL_CHECK_EMAIL_EXIST= "http://www.meetbookapp.com/APIs/checkEmailExist.php";
    public static final String URL_CHECK_OLD_PASSWORD= "http://www.meetbookapp.com/APIs/checkOldPassword.php";
    public static final String URL_CHANGE_PASSWORD= "http://www.meetbookapp.com/APIs/changePassword.php";
    public static final String URL_CHANGE_NAME= "http://www.meetbookapp.com/APIs/changeName.php";
    public static final String URL_UPDATE_FCM_TOKEN= "http://www.meetbookapp.com/APIs/updateFCMToken.php";
    public static final String URL_SAVE_IMAGE= "http://www.meetbookapp.com/APIs/savePicture.php";
    public static final String URL_LOAD_IMAGE= "http://www.meetbookapp.com/APIs/getPicture.php";




    public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String PASSWORD_PATTERN = "^(?=.*?[a-z])(?=.*?[0-9]).{6,15}$";
    // Password Lentgh is 6-15
    //Password require atleast 1 uppercase and 1 lower case.


    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;

    public static final String CHANNEL_ID ="mb_ch_01";
    public static final String CHANNEL_NAME ="meetbook_channel_one";
    public static final String CHANNEL_DESCRIPTION ="This app is owned by facefive";


    public static final String SHARED_PREF = "ah_firebase";


}
