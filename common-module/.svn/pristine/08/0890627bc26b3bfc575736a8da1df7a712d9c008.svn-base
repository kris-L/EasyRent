package com.xw.common.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;

import com.xw.common.R;

import java.math.BigDecimal;
import java.util.Locale;

/**
 * Created by Horris on 2016/12/20.
 */
public class Units {
    public static String formatSpeed(int speed) {
        return (speed / 100) + "km/h";
    }

    /**
     * 格式化耗时
     **/
    public static String formatElapsedTime(int elapsedTime) {
        if (elapsedTime <= 0) {
            return "-";
        }
        return String.valueOf(elapsedTime);
    }

    /**
     * 格式化耗时
     **/
    public static String formatToMinuteZh(int second) {
        int minute = second / 60;
        int leftSecond = second % 60;
        return String.format("%02d分%02d秒", minute, leftSecond);
    }

    public static String formatMeterToKM(double distance) {
        if (distance <= 0) {
            return "-";
        }
        distance = distance / 1000;
        return new BigDecimal(String.format(Locale.CHINA, "%.2f", distance)).stripTrailingZeros().toPlainString();
    }

    public static String wrapPrice(@Nullable Double price) {
        if (null == price) {
            return "";
        }
        return new BigDecimal(String.format(Locale.CHINA, "%.2f", price)).stripTrailingZeros().toPlainString();
    }

    public static String wrapFenToYuan(@Nullable Double price) {
        if (null == price) {
            return "";
        }
        return wrapPrice(price / 100);
    }

    /**
     * 格式化单位为分的价格
     *
     * @return ¥ XX.XX
     */
    public static CharSequence formatPriceFenToRmb(Context context, @NonNull Double price) {
        return wrapRmbPrice(context, price / 100);
    }

    /**
     * 格式化单位为分的价格
     *
     * @return ¥  XX.XX
     */
    public static CharSequence formatPriceFenToRmbInterval(Context context, @NonNull Double price) {
        return Html.fromHtml(String.format("%s %s",
                context.getString(R.string.common__price_rmb),
                wrapPrice(price / 100)));
    }

    /**
     * 格式化单位为分的价格
     *
     * @return XX.XX 元
     */
    public static CharSequence formatPriceFenToYuan(Context context, @NonNull Double price) {
        return wrapYuanPrice(context, price / 100);
    }

    /**
     * ¥ XX.XX
     */
    private static CharSequence wrapRmbPrice(Context context, @Nullable Double price) {
        if (null == price) {
            return Html.fromHtml(context.getString(R.string.common__price_rmb));
        }
        return Html.fromHtml(String.format("%s%s",
                context.getString(R.string.common__price_rmb),
                wrapPrice(price)));
    }

    /**
     * XX.XX 元
     */
    private static CharSequence wrapYuanPrice(Context context, @Nullable Double price) {
        if (null == price) {
            return Html.fromHtml(context.getString(R.string.common__price_yuan));
        }
        return Html.fromHtml(String.format("%s%s",
                wrapPrice(price), context.getString(R.string.common__price_yuan)));
    }
}
