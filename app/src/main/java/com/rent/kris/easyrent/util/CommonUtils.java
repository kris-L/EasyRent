package com.rent.kris.easyrent.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.File;

public class CommonUtils {

    public static final String TEMP_DIR = "imageTemp" + File.separator;//缓存目录


    //获取根目录信息
    public static String getBasePath(Context context) {
        String basePath = context.getCacheDir().getPath();
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && Environment.getExternalStorageState() != null
                && Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            basePath = context.getExternalCacheDir().getPath();
        }
        return basePath + File.separator;
    }

    //获取根目录信息
    public static String getImagePath(Context context) {
        String basePath = "";
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            basePath = Environment.getExternalStorageDirectory().toString()+ File.separator;//获取根目录
            Log.e("lsz", "外部存储可用..." + basePath);
        }
        return basePath;
    }

}
