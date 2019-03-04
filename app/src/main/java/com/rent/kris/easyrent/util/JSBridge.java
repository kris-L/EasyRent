package com.rent.kris.easyrent.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rent.kris.easyrent.entity.UploadInfo;
import com.rent.kris.easyrent.ui.LoginActivity;
import com.rent.kris.easyrent.ui.MainActivity;
import com.rent.kris.easyrent.ui.WebViewActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lsz  on 2019-01-28
 */
public class JSBridge {
    private final WebView mWebView;
    private final MainActivity mContext;

    private OnJSCallBack jsListener;
    public interface OnJSCallBack{
        void uploadImages(String type,String sonpath,String newname,String timestamp);
    }

    public JSBridge(@NonNull WebView webView, @NonNull Context context) {
        mWebView = webView;
        mContext = (MainActivity)context;
    }

    @JavascriptInterface
    public void loginNotify() {
        Log.e("JSBridge","loginNotify");
        LoginActivity.intentTo(mContext);
        mContext.finish();
    }

    @JavascriptInterface
    public void uploadImage(String jsonstr) {
        Log.e("lsz","uploadImage");
        Gson gson = new Gson();
        UploadInfo uploadInfo = gson.fromJson(jsonstr, new TypeToken<UploadInfo>() {
        }.getType());
        mContext.uploadImage(uploadInfo.type,uploadInfo.sonpath,uploadInfo.newname,uploadInfo.timestamp);
    }


    @JavascriptInterface
    public void uploadImages(String jsonstr) {
        Log.e("lsz","uploadImages");
        Gson gson = new Gson();
        UploadInfo uploadInfo = gson.fromJson(jsonstr, new TypeToken<UploadInfo>() {
        }.getType());
        mContext.uploadImages(uploadInfo.type,uploadInfo.sonpath,uploadInfo.newname,uploadInfo.timestamp);
    }


    //暴露给sdk的本地方法
    @JavascriptInterface
    public void native_launchFunc(final String funcName, final String jsonStr) {
        //这里基本上不会是ui线程
        mContext.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject jsonObject = new JSONObject(jsonStr);
                    if (funcName.startsWith("pickPhotoFromLibrary")) {
                        //去选取图片
                        boolean needCompress = jsonObject.getBoolean("needCompress");
                        goPickPhoto(funcName, needCompress);
                    } else if (funcName.startsWith("makePhotoFromCamera")) {
                        //去拍照
                        mContext.makePhoto(funcName);
                    } else if (funcName.startsWith("encrypt")) {
                        //去加密
//                        goEncrypt(funcName, jsonObject);
                    } else if (funcName.startsWith("playSnake")) {
                        //去玩蛇
//                        mContext.playSnake(funcName);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }



    private void goPickPhoto(final String funcName, boolean needCompress) {
        if (needCompress) {
            //压缩裁剪代码就不贴了，这里只是个简单的选择系统相册的示例
        } else {
            mContext.pickPhoto();
        }
    }

    @JavascriptInterface
    public void callPhone(String phone) {
        mContext.onCallPhone(phone);
    }



}
