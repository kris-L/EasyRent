package com.rent.kris.easyrent.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;


public class JavaAndJSBridge {
    private final WebView mWebView;
    private final Context mContext;

    private OnJSCallBack jsListener;

    public void setOnJSCallBack(OnJSCallBack listener){
        this.jsListener = listener;
    }

    public interface OnJSCallBack{
        void onPickPhoto();
        void onMakePhoto();
        void onLoginNotify();
    }


    public JavaAndJSBridge(@NonNull WebView webView, @NonNull Context context,OnJSCallBack listener) {
        mWebView = webView;
        mContext = context;
        this.jsListener = listener;
    }

    @JavascriptInterface
    public void loginNotify() {
        if(jsListener != null){
            jsListener.onLoginNotify();
        }

    }

    //暴露给sdk的本地方法
    @JavascriptInterface
    public void native_launchFunc(final String funcName, final String jsonStr) {
        //这里基本上不会是ui线程

    }



}
