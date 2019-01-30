package com.rent.kris.easyrent.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rent.kris.easyrent.entity.UploadInfo;


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
        void uploadImage(String type,String sonpath,String newname,String timestamp);
        void onLoginNotify();
        void onCallPhone(String phone);
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

    @JavascriptInterface
    public void uploadImage(String jsonstr) {
        Log.e("lsz","uploadImage");
        Gson gson = new Gson();
        UploadInfo uploadInfo = gson.fromJson(jsonstr, new TypeToken<UploadInfo>() {
        }.getType());
        if(jsListener != null && uploadInfo != null){
            jsListener.uploadImage(uploadInfo.type,uploadInfo.sonpath,uploadInfo.newname,uploadInfo.timestamp);
        }
    }

    @JavascriptInterface
    public void uploadImages(String type,String sonpath,String newname,String timestamp) {
        if(jsListener != null){
            jsListener.uploadImage(type,sonpath, newname,timestamp);
        }
    }

    @JavascriptInterface
    public void callPhone(String phone) {
        if(jsListener != null){
            jsListener.onCallPhone(phone);
        }
    }



    //暴露给sdk的本地方法
    @JavascriptInterface
    public void native_launchFunc(final String funcName, final String jsonStr) {
        //这里基本上不会是ui线程

    }



}
