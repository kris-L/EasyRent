package com.rent.kris.easyrent.event;

/**
 * Created by dell-pc on 2017/10/20.
 */
public class ResultToken {
    private String token;
    private String loginflag;//登录标识

    public String getToken() {
        return token;
    }

    public ResultToken setToken(String token) {
        this.token = token;
        return this;
    }

    public String getLoginflag() {
        return loginflag;
    }

    public ResultToken setLoginflag(String loginflag) {
        this.loginflag = loginflag;
        return this;
    }
}
