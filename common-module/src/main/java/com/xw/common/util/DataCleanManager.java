package com.xw.common.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class DataCleanManager {

    /**
     * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache)
     */
    public static boolean cleanInternalCache(Context context) {
        return deleteFilesByDirectory(context.getCacheDir());
    }

    /**
     * 获取本应用内部缓存大小(/data/data/com.xxx.xxx/cache)
     */
    public static String getInternalCache(Context context) {
        return getDisSizeStr(context.getCacheDir());
    }

    /**
     * 清除/data/data/com.xxx.xxx/files下的内容
     */
    public static void cleanFiles(Context context) {
        deleteFilesByDirectory(context.getFilesDir());
    }

    /**
     * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache)
     */
    public static void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(context.getExternalCacheDir());
        }
    }

    /**
     * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理
     */
    private static boolean deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                boolean delete;
                if (item.isDirectory()) {
                    delete = deleteFilesByDirectory(item);
                    if (delete) {
                        delete = item.delete();
                    }
                } else {
                    delete = item.delete();
                }
                if (!delete) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 计算文件大小
     */
    private static long getDirSize(File dir) {
        if (dir == null) {
            return 0;
        }
        if (!dir.isDirectory()) {
            return 0;
        }
        long dirSize = 0;
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                dirSize += file.length();
            } else if (file.isDirectory()) {
                dirSize += file.length();
                dirSize += getDirSize(file); // 递归调用继续统计
            }
        }
        return dirSize;
    }

    public static String getDisSizeStr(File dir) {
        return formatFileSize(getDirSize(dir));
    }

    /***
     * 转换文件大小
     * @return B/KB/MB/G
     */
    private static String formatFileSize(long fileSize) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileSize <= 0) {
            fileSizeString = "0KB";
        } else if (fileSize < 1048576) {
            fileSizeString = df.format((double) fileSize / 1024) + "KB";
        } else if (fileSize < 1073741824) {
            fileSizeString = df.format((double) fileSize / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileSize / 1073741824) + "G";
        }
        return fileSizeString;
    }
}
