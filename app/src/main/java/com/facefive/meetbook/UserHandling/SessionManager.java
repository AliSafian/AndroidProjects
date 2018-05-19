package com.facefive.meetbook.UserHandling;

/**
 * Created by Shahid on 3/22/2018.
 */


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class SessionManager {
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "MeetbookLogin";
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    private static final String KEY_USER_ID = "UserID";
    private static final String KEY_NAME = "Name";
    private static final String KEY_EMAIL = "Email";
    private static final String KEY_PICTURE_PATH = "PicturePath";
    private static final String KEY_UNI_NAME = "UniName";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);

        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }

    public void setUser(int userID ,String name, String email, String picPath, String uniName)
    {
        editor.putInt(KEY_USER_ID, userID);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PICTURE_PATH, picPath);
        editor.putString(KEY_UNI_NAME, uniName);

        editor.commit();
    }

    public int getUserID()
    {
        return pref.getInt(KEY_USER_ID, 0);
    }
    public String getName()
    {
        return pref.getString(KEY_NAME, null);
    }
    public String getEmail()
    {
        return pref.getString(KEY_EMAIL, null);
    }
    public String getPicturePath()
    {
        return pref.getString(KEY_PICTURE_PATH, null);
    }
    public String getUniName()
    {
        return pref.getString(KEY_UNI_NAME, null);
    }

    public void setName(String name)
    {

        editor.putString(KEY_NAME, name);

        editor.commit();
    }

    public void setEmail(String email)
    {

        editor.putString(KEY_EMAIL, email);

        editor.commit();
    }
    public void setPicturePath(String picName)
    {

        editor.putString(KEY_PICTURE_PATH, picName);

        editor.commit();
    }
    public void setUniName(String uniName)
    {

        editor.putString(KEY_UNI_NAME, uniName);

        editor.commit();
    }
}