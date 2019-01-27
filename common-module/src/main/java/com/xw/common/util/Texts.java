package com.xw.common.util;

import android.text.TextUtils;

import java.util.List;

/**
 * Created by Horris on 2016/12/21.
 */

public class Texts {

    public static String ellipsizedAtEnd(String str, int maxLength) {
        if (str == null) {
            return "";
        }
        if (maxLength <= 0) {
            return str;
        }
        return str.trim().length() > maxLength
                ? TextUtils.substring(str, 0, maxLength - 1) + '…' : str;
    }
    public static String ellipsizedAfterLen(String str, int maxLength) {
        if (str == null) {
            return "";
        }
        if (maxLength <= 0) {
            return str;
        }
        return str.trim().length() > maxLength
                ? TextUtils.substring(str, 0, maxLength) + '…' : str;
    }

    public static String coupledStrings(List<? extends Object> list, String split) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).toString());
            if (i < list.size() - 1) {
                sb.append(split);
            }
        }
        return sb.toString();
    }
}
