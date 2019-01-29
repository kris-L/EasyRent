package com.rent.kris.easyrent.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

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
            mContext.pickPhoto(funcName);
        }
    }




}
