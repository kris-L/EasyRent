package com.rent.kris.easyrent.util;

import com.rent.kris.easyrent.MyApplication;
import com.rent.kris.easyrent.entity.UserProfile;
import com.rent.kris.easyrent.prefs.AppPrefs;
import com.rent.kris.easyrent.prefs.UserProfilePrefs;
import com.rent.kris.easyrent.web.WebViewHelper;
import com.xw.common.prefs.LoginInfoPrefs;

public class LoginHelper {
    public static void onLogin(String name, String password, UserProfile userProfile) {
        LoginInfoPrefs.getInstance(MyApplication.getInstance()).saveLoginInfo(name, password);
        UserProfilePrefs.getInstance().saveUserProfile(userProfile);
        UserProfilePrefs.getInstance().saveUserToken(userProfile.key);
        // 如果用户是第一次成功登录
        if (UserProfilePrefs.getInstance().isFirstLoginSuccessForUser()) {
            UserProfilePrefs.getInstance().saveUserFirstLoginSuccess();
            AppPrefs.getInstance().setShouldSaveUsername(true);
            AppPrefs.getInstance().setShouldSavePassword(false);
            AppPrefs.getInstance().setAutoLogin(true);
        }
        WebViewHelper.clearWebViewCacheNCookies();
    }

    public static void onLogout() {
        if (AppPrefs.getInstance().getShouldSaveUsername()) {
            if (!AppPrefs.getInstance().getShouldSavePassword()) {
                LoginInfoPrefs.getInstance(MyApplication.getInstance()).clearPassword();
            }
        } else {
            LoginInfoPrefs.getInstance(MyApplication.getInstance()).clearLoginInfo();
        }
        UserProfilePrefs.getInstance().clear();
        WebViewHelper.clearWebViewCacheNCookies();
    }
}
