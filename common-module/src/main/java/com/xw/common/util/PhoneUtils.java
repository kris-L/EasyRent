package com.xw.common.util;

public class PhoneUtils {

    /**
     * 隐藏手机号和邮箱显示
     *
     * @param old     需要隐藏的手机号或邮箱
     * @param keyType 1手机  2邮箱
     */
    public static String hide(String old, String keyType) {
        if ("1".equals(keyType)) {
            return old.substring(0, 3) + "****" + old.substring(7, 11);
        } else {
            StringBuilder sb = new StringBuilder();
            String[] s = old.split("@");
            int l = s[0].length();
            int z = l / 3;
            sb.append(s[0].substring(0, z));
            int y = l % 3;
            for (int i = 0; i < z + y; i++)
                sb.append("*");
            sb.append(s[0].substring(z * 2 + y, l));
            sb.append("@");
            sb.append(s[1]);
            return sb.toString();
        }
    }

}
