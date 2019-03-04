package com.rent.kris.easyrent.web;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;

import com.rent.kris.easyrent.MyApplication;
import com.rent.kris.easyrent.util.Utils;
import com.xw.ext.http.retrofit.api.Cookies;

import java.io.File;
import java.util.HashSet;

public class WebViewHelper {
    public static String TAG = "WebViewHelper";
    private static long prevGoBackTime;

    private WebViewHelper() {
        // No instance.
    }

    public static boolean canGoBack(WebView webView) {
        long current = System.currentTimeMillis();
        if (current < prevGoBackTime + 300) {
            return false;
        }
        prevGoBackTime = current;
        return webView.canGoBack();
    }

    public static void destroy(WebView webView) {
        webView.stopLoading();
        webView.removeAllViews();
        if (webView.getParent() instanceof ViewGroup) {
            ((ViewGroup) webView.getParent()).removeView(webView);
        }
        webView.destroy();
    }

    public static String getWebCacheDirPath() {
        String cacheDirPath = Utils.getDiskCacheDir();
        Log.i(TAG, "cacheDirPath="+cacheDirPath);
        //设置数据库缓存路径
        if (!TextUtils.isEmpty(cacheDirPath)) {
            cacheDirPath += "/webcache";
            return cacheDirPath;
        }
        return "";
    }

    /**
     * 清除WebView缓存和Cookies
     */
    public static void clearWebViewCacheNCookies() {
        String cacheDirPath = getWebCacheDirPath();
        if (!TextUtils.isEmpty(cacheDirPath)) {
            File webviewCacheFiles = new File(cacheDirPath);
            if(webviewCacheFiles.exists()){
                Utils.deleteFile(webviewCacheFiles);
            }
        }

        CookieSyncManager.createInstance(MyApplication.getInstance());
        CookieManager.getInstance().removeAllCookie();
    }

    public static boolean syncCookie(Context context, String url) {
        CookieManager cookieManager = CookieManager.getInstance();

        if (Cookies.cookies != null) {
            HashSet<String> preferences = Cookies.cookies;
            for (String cookie : preferences) {
                cookieManager.setCookie(url, cookie);
            }
        }
        String newCookie = cookieManager.getCookie(url);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(context);
            cookieSyncManager.sync();
        }
        return !TextUtils.isEmpty(newCookie);
    }
}
