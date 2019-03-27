package com.rent.kris.easyrent.api;

import com.rent.kris.easyrent.constant.Constant;
import com.rent.kris.easyrent.entity.CommonEntity;
import com.rent.kris.easyrent.entity.UploadResult;
import com.rent.kris.easyrent.entity.UserProfile;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;


public interface Api {

    /**
     * 登录
     */
    @POST(Constant.BASE_URL +"appa/app2/public/index.php/mobile/Login/index.html")
    Observable<MyApiResponse<UserProfile>> login(@Body Map<String, String> params);

    /**
     * 第三方登录
     */
    @POST(Constant.BASE_URL +"appa/app2/public/index.php/mobile/Connect/wqwregister")
    Observable<MyApiResponse<UserProfile>> wqwregister(@Body Map<String, String> params);

    /**
     * 注册
     */
    @POST(Constant.BASE_URL +"appa/app2/public/index.php/mobile/Connect/sms_register.html")
    Observable<MyApiResponse<UserProfile>> register(@Body Map<String, String> params);

    @Streaming
    @GET
    Observable<Response<ResponseBody>> downloadFile(@Url String fileUrl);

    /**
     * 获取验证码
     */
    @POST(Constant.BASE_URL +"appa/app2/public/index.php/mobile/Connect/smsfs.html")
    Observable<MyApiResponse<CommonEntity>> getCode(@Body Map<String, String> params);


    @Multipart
    @POST(Constant.BASE_URL +"appa/app2/public/index.php/mobile/Yijia/uploadFapiao")
    Observable<UploadResult> uploadPic(@Part List<MultipartBody.Part> partLis);

    @Multipart                  //这里用Multipart
    @POST(Constant.BASE_URL +"appa/app2/public/index.php/mobile/Yijia/uploadFapiao")
    Call<UploadResult> myUpload(@Part List<MultipartBody.Part> partLis);

    @Multipart                  //这里用Multipart
    @POST(Constant.BASE_URL +"appa/app2/public/index.php/mobile/Yijia/uploadWeixiu")
    Observable<UploadResult> myUploadS(@Part List<MultipartBody.Part> partLis);

}
