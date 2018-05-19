package com.facefive.meetbook;

/**
 * Created by Shahid on 3/22/2018.
 */

public class AppConfig {
    public static final String URL_LOGIN = "http://meetbookapp.com/APIs/login_user.php";

    public static final String URL_TIMATABLE = "http://meetbookapp.com/APIs/timetable_request.php";
    public static final String URL_GETTIMETABLE = "http://meetbookapp.com/APIs/getTimetable_request.php";

    public static final String URL_GETALLSENTMEETINGS = "http://meetbookapp.com/APIs/getSentMeetRequests.php";

    public static final String URL_CHANGEMEETINGSTATUS = "http://meetbookapp.com/APIs/getSentMeetRequests.php";

    public static final String URL_SAVEUPDATEMESSAGE = "http://meetbookapp.com/APIs/saveUpdateMessage.php";
    public static final String URL_GETUPDATEMESSAGES = "http://meetbookapp.com/APIs/getUpdateMessages_request.php";
    public static final String URL_DELUPDATEMESSAGE = "http://meetbookapp.com/APIs/deleteUpdateMessage.php";
    public static final String URL_SIGNUP = "http://meetbookapp.com/APIs/register_user.php";
    public static final String URL_GET_UNIVERSITIES= "http://www.meetbookapp.com/APIs/getUniversities.php";
    public static final String URL_CHECK_EMAIL_EXIST= "http://www.meetbookapp.com/APIs/checkEmailExist.php";
    public static final String URL_CHECK_OLD_PASSWORD= "http://www.meetbookapp.com/APIs/checkOldPassword.php";
    public static final String URL_CHANGE_PASSWORD= "http://www.meetbookapp.com/APIs/changePassword.php";
    public static final String URL_CHANGE_NAME= "http://www.meetbookapp.com/APIs/changeName.php";


    public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String PASSWORD_PATTERN = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{6,15}$";
    // Password Lentgh is 6-15
    //Password require atleast 1 uppercase and 1 lower case.


}
