package com.xw.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.ArrayRes;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

public class AppUtil {

    public static int dip2px(Context context, float dip) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip,
                context.getResources().getDisplayMetrics()));
    }

    public static int sp2px(Context context, float sp) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                context.getResources().getDisplayMetrics()));
    }

    public static PackageInfo getPackageInfo(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            throw new AssertionError(e);
        }
    }

    public static String getAppVersionName(Context context) {
        return "V" + getPackageInfo(context).versionName;
    }

    public static int getAppVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * @return 设备名+设备厂商
     */
    public static String getDeviceBrandAndModel() {
        String devModel = android.os.Build.MODEL; // 设备名
        String devBrand = android.os.Build.BRAND; // 设备厂商
        return devModel + devBrand;
    }

    public static Bitmap textAsBitmap(String text, float textSize, int textColor) {
        Paint paint = new Paint(ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent();
        int width = (int) (paint.measureText(text) + 0.0f);
        int height = (int) (baseline + paint.descent() + 0.0f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }

    private static final char[] DIGITS = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z'
    };

    private static final char[] UPPER_CASE_DIGITS = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z'
    };
    public static final Charset UTF_8 = Charset.forName("UTF-8");

    public static String md5(boolean upperCase, String s) {
        return bytesToHexString(md5(s.getBytes(UTF_8)), upperCase);
    }

    public static String sha1(boolean upperCase, String s) {
        return bytesToHexString(sha1(s.getBytes(UTF_8)), upperCase);
    }

    public static String bytesToHexString(byte[] bytes, boolean upperCase) {
        char[] digits = upperCase ? UPPER_CASE_DIGITS : DIGITS;
        char[] buf = new char[bytes.length * 2];
        int c = 0;
        for (byte b : bytes) {
            buf[c++] = digits[(b >> 4) & 0xf];
            buf[c++] = digits[b & 0xf];
        }
        return new String(buf);
    }

    public static byte[] md5(byte[]... bytes) {
        return digest("MD5", bytes);
    }

    public static byte[] sha1(byte[]... bytes) {
        return digest("SHA-1", bytes);
    }

    public static byte[] digest(String algorithm, byte[]... bytes) {
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            for (byte[] item : bytes) {
                messageDigest.update(item);
            }
            return messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("No such algorithm: " + algorithm);
        }
    }

    public static String createRandomValue() {
        Random random = new Random();
        return String.valueOf(Math.abs(random.nextInt()));
    }

    public static int[] getResourceArray(Context context, @ArrayRes int arrayId) {
        TypedArray typedArray = context.getResources().obtainTypedArray(arrayId);
        int[] res = new int[typedArray.length()];
        for (int i = 0; i < typedArray.length(); i++) {
            res[i] = typedArray.getResourceId(i, 0);
        }
        typedArray.recycle();
        return res;
    }

    /**
     * HmacSHA256加密
     *
     * @param test
     * @param key
     * @return
     */
    public static String toHMACSHA256String(String test, String key) {
        String mykey = key;
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret = new SecretKeySpec(mykey.getBytes("UTF-8"), "HmacSHA256");
            mac.init(secret);
            byte[] digest = mac.doFinal(test.getBytes("UTF-8"));
            return bytesToHexString(digest);
        } catch (Exception e) {
            return "error";
        }
    }

    /**
     * MD5加密
     *
     * @param s
     * @return
     */
    public final static StringBuffer MD5(String s) {
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            String ciphertext = bytesToHexString(md);
            StringBuffer sb = new StringBuffer(ciphertext);
            StringBuffer sb1 = new StringBuffer();
            for (int i = 0; i < sb.length(); i++) {
                if (i % 2 == 0 && i > 0) {
                    sb1.append("-");
                }
                sb1.append(sb.charAt(i));
            }
            return sb1;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString().toLowerCase();
    }

    /**
     * 保存当前的屏幕亮度值，并使之生效
     */
    public static void setScreenBrightness(Activity activity, int paramInt) {
        Window localWindow = activity.getWindow();
        WindowManager.LayoutParams localLayoutParams = localWindow.getAttributes();
        float f = paramInt / 255.0F;
        localLayoutParams.screenBrightness = f;
        localWindow.setAttributes(localLayoutParams);
    }

    public static void hideSoftInputFromWindow(Context context, TextView tv) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tv.getWindowToken(), 0);
    }

    public static void showSoftInputToEdit(Context context, TextView et) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, 0);
    }


    public static void logDevInfo(Context context) {
        StringBuffer sb = new StringBuffer();
        sb.append("Build.FINGERPRINT:").append(Build.FINGERPRINT).append("\n");
        sb.append("Build.TIME:").append(new SimpleDateFormat("yyyy-MM-dd HH;mm:ss").format(new Date(Build.TIME))).append("\n");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sb.append("Build.SUPPORTED_ABIS:").append(Arrays.deepToString(Build.SUPPORTED_ABIS)).append("\n");
        } else {
            sb.append("Build.SUPPORTED_ABIS:").append(Build.CPU_ABI).append(",").append(Build.CPU_ABI2).append("\n");
        }
        sb.append("Build.VERSION:").append(Build.VERSION.RELEASE).append(",").append(Build.VERSION.SDK_INT).append("\n");
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        sb.append(displayMetrics.toString());
        Log.d("DevInfo", sb.toString());
    }


    /**
     * 跳转到应用宝中的应用详情页
     */
    public static void toYYBMarKet(Context context) {
        if (isAvailable(context, "com.tencent.android.qqdownloader")) {
            launchAppDetail(context, AppUtil.getPackageInfo(context).packageName, "com.tencent.android.qqdownloader");
        } else {
            Uri uri = Uri.parse("http://a.app.qq.com/o/simple.jsp?pkgname=" + AppUtil.getPackageInfo(context).packageName);
            Intent it = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(it);
        }
    }

    // 判断应用市场APP是否存在的方法
    private static boolean isAvailable(Context context, String packageName) {
        // 获取所有已安装程序的包信息
        List<PackageInfo> packageInfoList = context.getPackageManager().getInstalledPackages(0);
        List<String> pName = new ArrayList<>();// 用于存储所有已安装程序的包名
        if (packageInfoList != null) {
            for (int i = 0; i < packageInfoList.size(); i++) {
                String pn = packageInfoList.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);// 判断pName中是否有目标程序的包名，有TRUE，没有FALSE
    }

    private static void launchAppDetail(Context context, String appPkg, String marketPkg) {
        try {
            if (TextUtils.isEmpty(appPkg))
                return;
            Uri uri = Uri.parse("market://details?id=" + appPkg);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (!TextUtils.isEmpty(marketPkg)) {
                intent.setPackage(marketPkg);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
