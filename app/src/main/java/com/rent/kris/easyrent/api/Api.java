package com.rent.kris.easyrent.api;

import com.rent.kris.easyrent.entity.CommonEntity;
import com.rent.kris.easyrent.entity.UserProfile;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;
import rx.Subscriber;

public interface Api {

    /**
     * 登录
     */
    @POST("http://app.tit306.com/appa/app2/public/index.php/mobile/Login/index.html")
    Observable<MyApiResponse<UserProfile>> login(@Body Map<String, String> params);


    /**
     * 注册
     */
    @POST("http://app.tit306.com/appa/app2/public/index.php/mobile/Connect/sms_register.html")
    Observable<MyApiResponse<UserProfile>> register(@Body Map<String, String> params);

    @Streaming
    @GET
    Observable<Response<ResponseBody>> downloadFile(@Url String fileUrl);

    /**
     * 获取验证码
     */
    @POST("http://app.tit306.com/appa/app2/public/index.php/mobile/Connect/smsfs.html")
    Observable<MyApiResponse<CommonEntity>> getCode(@Body Map<String, String> params);


}
