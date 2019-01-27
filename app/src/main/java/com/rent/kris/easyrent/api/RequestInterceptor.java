package com.rent.kris.easyrent.api;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zhm on 2018/12/28.
 */

public class RequestInterceptor implements Interceptor {
    private static final String CHARSET = "Charset";
    private static final String CHARSET_VALUE = "UTF-8";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_TYPE_VALUE = "application/json";
    private static final String ACCEPT = "Accept";
    private static final String ACCEPT_VALUE = "application/json";
    private static final String RQS_HEADER = "rqs-header";
    private static final String TOKEN = "token";
    private static final String APP_KEY = "app_key";
    private static final String VERSION = "version";
    private static final String TIMESTAMP = "timestamp";
    private static final String SIGN = "sign";

    private static RequestInterceptor apiRequestInterceptor = new RequestInterceptor();
    private Gson gson;
    private String rqsValue, tokenValue;

    private RequestInterceptor() {
        if (gson == null) {
            gson = new Gson();
        }
    }

    public static RequestInterceptor getInstance() {
        return apiRequestInterceptor;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder builder = original.newBuilder()
                .header(CHARSET, CHARSET_VALUE)
                .header(CONTENT_TYPE, CONTENT_TYPE_VALUE)
                .header(ACCEPT, ACCEPT_VALUE);
        if (getRqsValue() != null) {
            builder.addHeader(RQS_HEADER, getRqsValue());
        }
        if (!TextUtils.isEmpty(tokenValue)) {
            builder.addHeader(TOKEN, tokenValue);
            builder.addHeader(APP_KEY, "android");
            builder.addHeader(VERSION, "v1.0");
            builder.addHeader(TIMESTAMP,System.currentTimeMillis()+"");
            builder.addHeader(SIGN, "");
        }
        builder.method(original.method(), original.body());
        Request request = builder.build();
        return chain.proceed(request);
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public String getRqsValue() {
        return rqsValue;
    }

    public void setRqsValue(Object rqsObj) {
        setRqsGsonValue(gson.toJson(rqsObj));
    }

    public void setRqsGsonValue(String rqsValue) {
        this.rqsValue = rqsValue;
    }
}
