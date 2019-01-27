package com.rent.kris.easyrent.prefs;


import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.rent.kris.easyrent.MyApplication;
import com.rent.kris.easyrent.entity.UserProfile;
import com.xw.common.prefs.LoginInfoPrefs;


public class UserProfilePrefs {

    private static final String PREFERENCE_NAME = "prefs_user_info";
    private static final String KEY_USER_PROFILE = "key_user_profile";
    private static final String KEY_FIRST_LOGIN_SUCCESS_PREFIX = "key_first_login_success_prefix_";
    private static final String KEY_USER_TOKEN = "key_user_token";

    private static UserProfilePrefs instance = null;
    private static Gson gson;
    private Context context;

    public static synchronized UserProfilePrefs getInstance() {
        if (instance == null) {
            instance = new UserProfilePrefs(MyApplication.getInstance());
            gson = new Gson();
        }
        return instance;
    }

    private final SharedPreferences prefs;

    private UserProfilePrefs(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public void saveUserProfile(UserProfile userProfile) {
        prefs.edit().putString(KEY_USER_PROFILE, gson.toJson(userProfile)).apply();
    }

    public UserProfile getUserProfile() {
        String str = prefs.getString(KEY_USER_PROFILE, "");
        return gson.fromJson(str, UserProfile.class);
    }


    public void saveUserToken(String token) {
        prefs.edit().putString(KEY_USER_TOKEN, token).apply();
    }

    public String getUserToken() {
        String token = prefs.getString(KEY_USER_TOKEN, "");
        return token;
    }


    public void clear() {
        prefs.edit().putString(KEY_USER_PROFILE, "").apply();
    }

    /**
     * 用户是否是第一次登录成功
     */
    public boolean isFirstLoginSuccessForUser() {
        String username = LoginInfoPrefs.getInstance(MyApplication.getInstance()).getUserName();
        if (!TextUtils.isEmpty(username)) {
            int value = prefs.getInt(KEY_FIRST_LOGIN_SUCCESS_PREFIX + username, 0);
            if (value == 0) {
                return true;
            }
        }
        return false;
    }

    public void saveUserFirstLoginSuccess() {
        String username = LoginInfoPrefs.getInstance(MyApplication.getInstance()).getUserName();
        if (!TextUtils.isEmpty(username)) {
            prefs.edit().putInt(KEY_FIRST_LOGIN_SUCCESS_PREFIX + username, 1).apply();
        }
    }

}
