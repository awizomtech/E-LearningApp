package com.awizomtech.elearning.SharePrefrence;

import android.content.Context;
import android.content.SharedPreferences;

import com.awizomtech.elearning.Model.LoginModel;

public class SharedPrefManager {

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private static final String SHARED_PREF_NAME = "learningsharepref";



    private static final String Key_UserID = "UserID";
    private static final String Key_UserName = "UserName";
    private static final String Key_ID = "Id";
    private static final String Key_Name = "Name";
    private static final String Key_Mobile = "Mobile";

    public SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean userLogin(LoginModel loginModel) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(Key_UserID, loginModel.UserID);
        editor.putString(Key_UserName, loginModel.UserName);
        editor.putInt(Key_ID, loginModel.ID);
        editor.putString(Key_Mobile, loginModel.MobileNo);
        editor.apply();
        return true;
    }
   /* public boolean ProfileView(ProfileModel profileModel) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Key_Name, profileModel.Name);
        editor.putString(Key_Photo, profileModel.Photo);
        editor.apply();
        return true;
    }*/

    public LoginModel getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        LoginModel token = new LoginModel();
        token.UserID = sharedPreferences.getString(Key_UserID, "");
        token.UserName = sharedPreferences.getString(Key_UserName, "");
        token.MobileNo = sharedPreferences.getString(Key_Mobile, "");
        token.ID = sharedPreferences.getInt(Key_ID, 0);
        return token;
    }
   /* public ProfileModel getProfileview() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        ProfileModel token = new ProfileModel();
        token.Name = sharedPreferences.getString(Key_Name, "");
        token.Photo = sharedPreferences.getString(Key_Photo, "");

        return token;
    }*/


    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(Key_ID, null) != null)
            return true;
        return false;
    }

    public boolean logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

}
