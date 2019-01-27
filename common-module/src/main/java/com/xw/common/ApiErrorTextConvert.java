package com.xw.common;

import android.support.annotation.NonNull;

/**
 * 将错误码转化为通俗易懂的语句返回
 * Created by XWCHQ on 2017/1/23-17:52.
 */
public class ApiErrorTextConvert {
    public static final int TOKEN_INVALID = 553;
    /** 消息推送的token超时 */
    public static final int TOKEN_TIMEOUT = 1000004;
    /**
     * 教练在不同手机选择同一台设备培训
     */
    public static final int DUPLICATE_DEVICE_COACH = 200701;

    public static String getLoginServerError(int code, String defaultText) {
        String text = defaultText;
        switch (code) {
            default:
                text = getServerErrorText(code, defaultText);
        }
        return text;
    }

    public static String getServerErrorText(int code, @NonNull String defaultText) {
        String text = defaultText;
        switch (code) {
            case 110301:
                text = "用户不存在";
                break;
            case 110307:
                text = "请求用户系统登录失败";
                break;
            case 110308:
                text = "验证码验证失败";
                break;
            case 110310:
                text = "此用户名已存在";
                break;
            case 110311:
                text = "此手机号已存在";
                break;
            case 200107:
                text = "该学员已绑定";
                break;
            case 110314:
                text = "旧密码错误";
                break;
            case 200805:
                text = "重复预约";
                break;
            case 201701:
                text = "视频资源不存在";
                break;
            case 200809:
                text = "班次已有学员预约或已过期，不能取消";
                break;
            case TOKEN_INVALID:
                text = "登录已过期";
                break;
        }
        if (text.matches("[\\x00-\\xff]*")) {     // 没有双字节符（中文）
            text = "通信异常:" + code;
        }
        return text;
    }
}
