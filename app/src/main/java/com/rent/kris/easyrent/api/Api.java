package com.rent.kris.easyrent.api;

import com.rent.kris.easyrent.entity.UserProfile;
import com.xw.ext.http.retrofit.api.data.ApiResponse;

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

public interface Api {

    /**
     * 登录
     */
    @POST("http://app.tit306.com/appa/app2/public/index.php/mobile/Login/index.html")
    Observable<ApiResponse<UserProfile>> login(@Body Map<String, String> params);

    @Streaming
    @GET
    Observable<Response<ResponseBody>> downloadFile(@Url String fileUrl);

}
