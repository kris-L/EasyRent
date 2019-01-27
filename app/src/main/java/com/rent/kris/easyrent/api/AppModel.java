package com.rent.kris.easyrent.api;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.text.TextUtils;

import com.rent.kris.easyrent.BuildConfig;
import com.rent.kris.easyrent.MyApplication;
import com.rent.kris.easyrent.entity.UserProfile;
import com.rent.kris.easyrent.prefs.AppPrefs;
import com.rent.kris.easyrent.util.LoginHelper;
import com.xw.common.prefs.LoginInfoPrefs;
import com.xw.dialog.lib.WarnDialog;
import com.xw.ext.http.retrofit.api.ApiModel;
import com.xw.ext.http.retrofit.api.ApiResponseFunc;
import com.xw.ext.http.retrofit.api.error.ApiException;
import com.xw.ext.http.retrofit.api.error.ApiOnErrorFunc;
import com.xw.ext.http.retrofit.api.error.ErrorSubscriber;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

public class AppModel extends ApiModel<Api> {
    //    private static String sServerIP = "120.197.15.89";
//    private static String sServerPort = "210";
    private static AppModel model;
    private Application application;

    public static void createAppModel(Application app) {
        model = new AppModel(app);
    }

    public static AppModel model() {
        return model;
    }

    public static Application app() {
        return model.application;
    }

    protected AppModel(Application app) {
        super(BuildConfig.DEBUG, "http://120.197.15.89:210/", Api.class);
    }

    @Override
    protected Interceptor createRequestInterceptor() {
        return RequestInterceptor.getInstance();
    }

    public void login(final String userName, final String password, Subscriber<UserProfile> subscriber) {
        LoginInfoPrefs.getInstance(MyApplication.getInstance()).saveLoginInfo(userName, password);
//        subscriber.onNext(new UserProfile());
//        subscriber.onCompleted();
        Map<String, String> params = new HashMap<>();
        params.put("username", userName);
        params.put("password", password);
        params.put("client", "wap");
        Observable<UserProfile> observable = api().login(params)
                .map(new ApiResponseFunc<UserProfile>())
                .onErrorResumeNext(new ApiOnErrorFunc<UserProfile>())
                .flatMap(new Func1<UserProfile, Observable<UserProfile>>() {
                    @Override
                    public Observable<UserProfile> call(UserProfile userProfile) {
                        LoginHelper.onLogin(userName, password, userProfile);
                        RequestInterceptor.getInstance().setTokenValue(userProfile.key);
                        return Observable.just(userProfile);
                    }
                });

        toSubscribe(observable, subscriber, null);
    }

//    public void logout(Subscriber<Object> subscriber) {
//        toSubscribe(api().logout(AppPrefs.getInstance().getServerIP(), AppPrefs.getInstance().getServerPort()), subscriber);
//    }

    /**
     * 下载一个文件
     */
    public void downloadFile(final Context context, final String downloadUrl, Subscriber<File> subscriber) {
        api().downloadFile(downloadUrl)
                .onErrorResumeNext(new ApiOnErrorFunc<Response<ResponseBody>>())
                .flatMap(new Func1<Response<ResponseBody>, Observable<File>>() {
                    @Override
                    public Observable<File> call(final Response<ResponseBody> responseBodyResponse) {
                        if (responseBodyResponse.code() != 200) {
                            if (responseBodyResponse.code() == 404) {
                                return Observable.error(new ApiException("文件不存在", 404));
                            }
                            return Observable.error(new ApiException("下载文件失败" + responseBodyResponse.code(), responseBodyResponse.code()));
                        }
                        return Observable.create(new Observable.OnSubscribe<File>() {
                            @Override
                            public void call(Subscriber<? super File> subscriber) {
                                try {
                                    String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"), downloadUrl.length());
                                    File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsoluteFile();
                                    dir.mkdirs();   //确保文件夹存在
                                    File file = new File(dir, fileName);
                                    //File file = new File(Environment.getExternalStorageDirectory() + File.separator + "Download", fileName);
                                    if (file.exists()) {
                                        file.delete();
                                    }
                                    BufferedSink sink = Okio.buffer(Okio.sink(file));
                                    sink.writeAll(responseBodyResponse.body().source());
                                    sink.close();
                                    subscriber.onNext(file);
                                    subscriber.onCompleted();
                                } catch (Exception e) {
                                    if (context != null) {
                                        WarnDialog.normal(context, "下载数据处理错误", getAllInfo(e), "确定", null, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        }).show();
                                    }
                                    e.printStackTrace();
                                    if (e.getMessage().contains("Permission denied")) {
                                        subscriber.onError(new ApiException("下载数据失败，请给APP授权读写手机储存", 0));
                                    } else {
                                        subscriber.onError(new ApiException("下载数据处理错误" + "\n" + getAllInfo(e), 0));
                                    }
                                }
                            }
                        });
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public static String getAllInfo(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try {
            t.printStackTrace(pw);
            return t.toString() + "\n\n" + sw.toString();
        } catch (Exception e) {
            return t.toString();
        } finally {
            pw.close();
        }
    }







}
