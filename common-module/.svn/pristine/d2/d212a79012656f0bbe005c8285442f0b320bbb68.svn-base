package com.xw.common.prefs;


import android.content.Context;
import android.content.SharedPreferences;

import com.xw.common.util.AESCipherUtil;

import timber.log.Timber;

public class LoginInfoPrefs {

    private static final String PREFERENCE_NAME = "school__login_info_prefs";
    private static final String LOGIN_USERNAME = "school__login_username";
    private static final String LOGIN_PWD = "school__login_pwd";

    private static LoginInfoPrefs instance = null;

    public static synchronized LoginInfoPrefs getInstance(Context context) {
        if (instance == null) {
            instance = new LoginInfoPrefs(context);
        }
        return instance;
    }

    private final SharedPreferences prefs;

    private LoginInfoPrefs(Context context) {
        prefs = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public void saveLoginInfo(String userName, String password) {
        try {
            String encrypt = AESCipherUtil.encrypt(password);
            prefs.edit().putString(LOGIN_PWD, encrypt).apply();
        } catch (Exception e) {
            Timber.e("AESCipherUtil::Encrypt::Exception::" + e.getMessage());
        }
        prefs.edit().putString(LOGIN_USERNAME, userName).apply();
    }

    public void clearLoginInfo() {
        prefs.edit().putString(LOGIN_PWD, "").apply();
        prefs.edit().putString(LOGIN_USERNAME, "").apply();
    }

    public String getUserName() {
        return prefs.getString(LOGIN_USERNAME, "");
    }

    public String getPassword() {
        String pwd = prefs.getString(LOGIN_PWD, "");
        try {
            pwd = AESCipherUtil.decrypt(pwd);
        } catch (Exception e) {
            Timber.e("AESCipherUtil::Decrypt::Exception::" + e.getMessage());
            pwd = "";
        }
        return pwd;
    }

    public void clearPassword() {
        prefs.edit().putString(LOGIN_PWD, "").apply();
    }
}
