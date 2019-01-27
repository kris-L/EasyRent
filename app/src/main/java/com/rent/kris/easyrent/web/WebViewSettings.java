package com.rent.kris.easyrent.web;

import android.annotation.SuppressLint;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.WebSettings;

public class WebViewSettings {

    public static void config(WebSettings settings) {
        configJavaScript(settings);
        configWebCache(settings);

        settings.setSupportZoom(true);
//        settings.setBuiltInZoomControls(true);
//        settings.setDisplayZoomControls(true);

        //设置加载进来的页面自适应手机屏幕
//        settings.setUseWideViewPort(true);
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
//        settings.setLoadWithOverviewMode(true);

//        settings.setMinimumFontSize(30);
    }

    @SuppressLint("SetJavaScriptEnabled")
    public static void configJavaScript(WebSettings settings) {
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            settings.setAllowFileAccessFromFileURLs(false);
            settings.setAllowUniversalAccessFromFileURLs(false);
        }
    }

    private static void configWebCache(WebSettings settings) {
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式

        //String cacheDirPath = App.getInstance().getFilesDir().getAbsolutePath() + "/webcache";
        //String cacheDirPath = App.getInstance().getCacheDir().getAbsolutePath() + "/webcache";
        String cacheDirPath = WebViewHelper.getWebCacheDirPath();
        //Log.i(TAG, "cacheDirPath="+cacheDirPath);
        //设置数据库缓存路径
        if (!TextUtils.isEmpty(cacheDirPath)) {
            settings.setDatabasePath(cacheDirPath);
            //设置  Application Caches 缓存目录
            settings.setAppCachePath(cacheDirPath);
        }

        // 开启 DOM storage API 功能
        settings.setDomStorageEnabled(true);
        //开启 database storage API 功能
        settings.setDatabaseEnabled(true);
        //开启 Application Caches 功能
        settings.setAppCacheEnabled(true);
    }

    private WebViewSettings() {
        // No instance.
    }

}
