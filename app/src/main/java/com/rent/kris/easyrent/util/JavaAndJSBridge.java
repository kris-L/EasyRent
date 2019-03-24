package com.rent.kris.easyrent.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rent.kris.easyrent.entity.ShareInfo;
import com.rent.kris.easyrent.entity.UploadInfo;


public class JavaAndJSBridge {
    private final WebView mWebView;
    private final Context mContext;

    private OnJSCallBack jsListener;

    public void setOnJSCallBack(OnJSCallBack listener){
        this.jsListener = listener;
    }

    public interface OnJSCallBack{
        void uploadImage(String type,String sonpath,String newname,String timestamp);
        void uploadImages(String type,String sonpath,String newname,String timestamp);
        void onLoginNotify();
        void onCallPhone(String phone);
        void onModuleSelected(int index);
        void onWechatShare(ShareInfo data);
        void onGpsNotify();
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
    public void moduleSelectedAtIndex(int index) {
        if(jsListener != null){
            jsListener.onModuleSelected(index);
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
            jsListener.uploadImages(type,sonpath, newname,timestamp);
        }
    }

    @JavascriptInterface
    public void callPhone(String phone) {
        if(jsListener != null){
            jsListener.onCallPhone(phone);
        }
    }

    @JavascriptInterface
    public void wechatShare(String data) {
        Log.e("lsz","wechatShare");
        Gson gson = new Gson();
        ShareInfo mShareInfo = gson.fromJson(data, new TypeToken<ShareInfo>() {
        }.getType());

        if(jsListener != null){
            jsListener.onWechatShare(mShareInfo);
        }
    }


    @JavascriptInterface
    public void gpsNotify() {
        Log.e("lsz","gpsNotify");
        if(jsListener != null){
            jsListener.onGpsNotify();
        }
    }


}
