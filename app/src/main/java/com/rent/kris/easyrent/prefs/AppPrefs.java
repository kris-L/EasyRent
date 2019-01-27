package com.rent.kris.easyrent.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.rent.kris.easyrent.MyApplication;

public class AppPrefs {
    private static final String PREFERENCE_NAME = "prefs_globle";

    private static final String KEY_SERVER_IP = "key_server_ip";
    private static final String KEY_SERVER_PORT = "key_server_port";
    private static final String KEY_SHOULD_SAVE_USERNAME = "key_should_save_username";
    private static final String KEY_SHOULD_SAVE_PASSWORD = "key_should_save_password";
    private static final String KEY_AUTO_LOGIN = "key_auto_login";
    private static final String KEY_IS_FIRST_OPEN_APP = "key_is_first_open_app";
    private static final String KEY_FEEDBACK_FLAG = "key_feedback_flag";//判断意见反馈是不是新功能标志
    private static final String KEY_LDRC_FLAG = "key_ldrc_flag";//判断领导日程是不是新功能标志

    private static AppPrefs instance = null;
    private SharedPreferences prefs;
    //    private static Gson gson;
    private Context context;

    private AppPrefs(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static AppPrefs getInstance() {
        if (instance == null) {
            synchronized (AppPrefs.class) {
                if (instance == null) {
                    instance = new AppPrefs(MyApplication.getInstance());
//                    gson = new Gson();
                }
            }
        }
        return instance;
    }

    public void setServerIP(String ip) {
        prefs.edit().putString(KEY_SERVER_IP, ip).apply();
    }

    public String getServerIP() {
        return prefs.getString(KEY_SERVER_IP, "");
    }

    public void setServerPort(String port) {
        prefs.edit().putString(KEY_SERVER_PORT, port).apply();
    }

    public String getServerPort() {
        return prefs.getString(KEY_SERVER_PORT, "");
    }

    public void setShouldSaveUsername(boolean save) {
        prefs.edit().putBoolean(KEY_SHOULD_SAVE_USERNAME, save).apply();
    }

    public boolean getShouldSaveUsername() {
        return prefs.getBoolean(KEY_SHOULD_SAVE_USERNAME, false);
    }

    public void setShouldSavePassword(boolean save) {
        prefs.edit().putBoolean(KEY_SHOULD_SAVE_PASSWORD, save).apply();
    }

    public boolean getShouldSavePassword() {
        return prefs.getBoolean(KEY_SHOULD_SAVE_PASSWORD, false);
    }

    public void setAutoLogin(boolean autoLogin) {
        prefs.edit().putBoolean(KEY_AUTO_LOGIN, autoLogin).apply();
    }

    public boolean getAutoLogin() {
        return prefs.getBoolean(KEY_AUTO_LOGIN, false);
    }

    public void setIsFirstOpenApp(boolean isFirstOpenApp) {
        prefs.edit().putBoolean(KEY_IS_FIRST_OPEN_APP, isFirstOpenApp).apply();
    }

    public boolean getIsFirstOpenApp() {
        return prefs.getBoolean(KEY_IS_FIRST_OPEN_APP, true);
    }

    public void setFeedBackFlag(boolean flag) {
        prefs.edit().putBoolean(KEY_FEEDBACK_FLAG, flag).apply();
    }

    public boolean getFeedBackFlag() {
        return prefs.getBoolean(KEY_FEEDBACK_FLAG, true);
    }

    public void setLDRCFlag(boolean flag) {
        prefs.edit().putBoolean(KEY_LDRC_FLAG, flag).apply();
    }

    public boolean getLDRCFlag() {
        return prefs.getBoolean(KEY_LDRC_FLAG, true);
    }
}
