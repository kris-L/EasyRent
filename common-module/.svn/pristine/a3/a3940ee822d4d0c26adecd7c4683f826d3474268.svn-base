package com.xw.common.util;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StrikethroughSpan;
import android.util.LruCache;
import android.view.View;
import android.widget.TextView;

/**
 * Created by XWCHQ on 2016/11/21.
 */
public class TextViewUtils {

    public static void setTextOrEmpty(@NonNull TextView tv, CharSequence... css) {
        if (tv == null) {
            return;
        }
        tv.setText("");
        for (CharSequence cs : css) {
            if (TextUtils.isEmpty(cs)) {
                tv.append("");
            } else {
                tv.append(cs);
            }
        }
    }

    public static void setTextOrDefault(@NonNull TextView tv, CharSequence cs, CharSequence defaultCS) {
        if (tv == null) {
            return;
        }
        tv.setText("");
        if (TextUtils.isEmpty(cs)) {
            tv.append(defaultCS);
        } else {
            tv.append(cs);
        }
    }

    public static void setTextOrCancel(@NonNull TextView tv, CharSequence cs) {
        if (tv == null) {
            return;
        }
        if (!TextUtils.isEmpty(cs)) {
            tv.setText(cs);
        }
    }

    public static void setTextOrGone(@NonNull TextView tv, CharSequence cs) {
        if (tv == null) {
            return;
        }
        tv.setText("");
        if (TextUtils.isEmpty(cs)) {
            tv.setVisibility(View.GONE);
        } else {
            tv.setVisibility(View.VISIBLE);
            tv.append(cs);
        }
    }

    public static void setTextOrInVisible(@NonNull TextView tv, CharSequence cs) {
        if (tv == null) {
            return;
        }
        tv.setText("");
        if (TextUtils.isEmpty(cs)) {
            tv.setVisibility(View.INVISIBLE);
        } else {
            tv.setVisibility(View.VISIBLE);
            tv.append(cs);
        }
    }

    public static CharSequence getColorCharSequence(CharSequence cs, int color) {
        return getColorCharSequence(cs,color,0,cs.length());
    }

    public static CharSequence getStrikethroughCharSequence(CharSequence cs ) {
        return getStrikethroughCharSequence(cs,0,cs.length());
    }

    public static CharSequence getStrikethroughCharSequence(CharSequence cs, int startIndex,int endIndex   ) {
        SpannableString spanStr = new SpannableString(cs);
        StrikethroughSpan span = new StrikethroughSpan();
        spanStr.setSpan(span, startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spanStr;
    }

    public static CharSequence getColorCharSequence(CharSequence cs, int color,
                                                    int startIndex,int endIndex   ) {
        SpannableString spanStr = new SpannableString(cs);
        ForegroundColorSpan span = new ForegroundColorSpan(color);
        spanStr.setSpan(span, startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spanStr;
    }

    public static CharSequence getBackColorCharSequence(CharSequence cs, int color) {
        SpannableString spanStr = new SpannableString(cs);
        BackgroundColorSpan span = new BackgroundColorSpan(color);
        spanStr.setSpan(span, 0, cs.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spanStr;
    }

    public static CharSequence getIconCharSequence(Context context, int resId, int iconSize) {
        SpannableStringBuilder ssb = new SpannableStringBuilder("*");
        Drawable drawable = context.getResources().getDrawable(resId);
        drawable.setBounds(0, 0, iconSize, iconSize);
        ImageSpan span = new ImageSpan(drawable);
        ssb.setSpan(span, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return ssb;
    }

    public static CharSequence getIconText(Context context, CharSequence text, int iconIndex, int resourceId, int iconSize) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(text);
        Drawable drawable = context.getResources().getDrawable(resourceId);
        drawable.setBounds(0, 0, iconSize, iconSize);
        ImageSpan span = new ImageSpan(drawable);
        ssb.setSpan(span, iconIndex, iconIndex + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return ssb;
    }

    public static void setTextSynOrEmpty(@NonNull TextView tv, Object obj, LoadTextAsyTask task) {
        StringLruCache lruCache = StringLruCache.getInstance(tv.getContext());
        String text = lruCache.get(obj.toString());
        if (text != null) {
            tv.setText(text);
        } else {
            task.execute(obj.toString());
        }
    }

    public static class StringLruCache extends LruCache<String, String> {

        private static StringLruCache instance;

        public StringLruCache(int maxSize) {
            super(maxSize);
        }

        public static StringLruCache getInstance(Context context) {
            if (instance == null) {
                synchronized (StringLruCache.class) {
                    if (instance == null) {
                        ActivityManager actMan = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
                        int maxSize = actMan.getMemoryClass() / 8 * 1024 * 1024;
                        instance = new StringLruCache(maxSize);
                    }
                }
            }
            return instance;
        }

    }

    public static abstract class LoadTextAsyTask extends AsyncTask<String, Void, String> {

        private final String tag;
        private TextView tv;

        public LoadTextAsyTask(TextView tv, String tag) {
            this.tv = tv;
            this.tag = tag;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                StringLruCache.getInstance(tv.getContext()).put(tag, s);
                tv.setText(s);
            }
        }
    }
}
