package com.rent.kris.easyrent.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;

import com.rent.kris.easyrent.MyApplication;
import com.rent.kris.easyrent.prefs.UserProfilePrefs;
import com.rent.kris.easyrent.ui.WebViewActivity;
import com.xw.common.AppToast;
import com.xw.common.prefs.LoginInfoPrefs;
import com.xw.dialog.lib.WarnDialog;
import com.xw.ext.http.retrofit.api.error.ApiException;
import com.xw.ext.http.retrofit.api.error.ErrorSubscriber;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Utils {
    private static final String TAG = Utils.class.getSimpleName();
    private static final String sAppUrlFormat = "http://%s%s/obpm/portal/main_oa_phone?username=%s&backUrl=%s";
    private static final String sAppUrlFormat2 = "http://%s%s%s";
    private static final String sFlowUrlFormat = "http://%s%s/obpm/portal/phone/main.jsp?application=11de-f053-df18d577-aeb6-19a7865cfdb6&action=null&returnUrl=#form_view/_formid=%s/_docid=%s/application=11de-f053-df18d577-aeb6-19a7865cfdb6/pendingList=/processedList=";
    private static final String sFlowUrlFormat2 = "http://%s%s/obpm/portal/phone/main.jsp?viewid=%s&application=11de-f053-df18d577-aeb6-19a7865cfdb6&action=null&returnUrl=#form_view/_formid=%s/_docid=%s/application=11de-f053-df18d577-aeb6-19a7865cfdb6/pendingList=/processedList=";
    private static final String sNoticeUrlFormat = "http://%s%s/obpm/portal/phone/main.jsp?application=11de-f053-df18d577-aeb6-19a7865cfdb6&action=null&returnUrl=#subView_view/_docid=%s/_formid=%s/_templateForm=undefined/isSubDoc=true/signatureExist=false/FormID=11e7-acdc-8fa99ee6-93b9-4702b45d914a/ApplicationID=11de-f053-df18d577-aeb6-19a7865cfdb6/DocumentID=11e8-7435-2b90361e-ae17-573647415a09/currentDate=/viewEvent=/_openType=277/_fieldid=11e7-b79f-192f699b-aa79-3d17bf3d1332/parentid=11e8-efbf-87e16e5e-b233-05dbac0f597d/treedocid=/isinner=/isRelate=false/application=11de-f053-df18d577-aeb6-19a7865cfdb6/_viewid=11e7-b79f-192f699b-aa79-3d17bf3d1332/openType=277";
    private static final String sScheduleUrlFormat = "http://%s%s/obpm/portal/phone/main.jsp?application=11de-f053-df18d577-aeb6-19a7865cfdb6&action=null&returnUrl=#form_view/_docid=%s/_formid=%s/signatureExist=false/application=11de-f053-df18d577-aeb6-19a7865cfdb6/FormID=%s/ApplicationID=/DocumentID=%s";
    private static final String sMeetingUrlFormat = "http://%s%s/obpm/portal/phone/main.jsp?application=11de-f053-df18d577-aeb6-19a7865cfdb6&action=null&returnUrl=#form_view/_docid=%s/_formid=%s/signatureExist=false/application=11de-f053-df18d577-aeb6-19a7865cfdb6/FormID=%s/ApplicationID=/DocumentID=%s";
    private static final String sModifyBackUrl = "http://%s%s/bpm/ucar/flowmodify";

    public static String getDiskCacheDir() {
        File cacheDir;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                && !Environment.isExternalStorageRemovable()) {
            cacheDir = MyApplication.getInstance().getExternalCacheDir();
            if (cacheDir == null) { //可能没有权限
                cacheDir = MyApplication.getInstance().getCacheDir();
            }
        } else {
            cacheDir = MyApplication.getInstance().getCacheDir();
        }
        if (cacheDir != null) {
            return cacheDir.getPath();
        } else {
            return null;
        }
    }

    /**
     * 递归删除 文件/文件夹
     *
     * @param file
     */
    public static void deleteFile(File file) {
        Log.i(TAG, "delete file path=" + file.getAbsolutePath());

        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        } else {
            Log.e(TAG, "file does not exists " + file.getAbsolutePath());
        }
    }


    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(17[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }


    public static void GotoWebView(Context mContext,String url){
        Intent intent = new Intent(mContext, WebViewActivity.class);
        String title = "";
        url = url+"?key="+UserProfilePrefs.getInstance().getUserToken()+"&username="+
                LoginInfoPrefs.getInstance(MyApplication.getInstance()).getUserName();
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        mContext.startActivity(intent);
    }


}
