package com.rent.kris.easyrent.api;

import com.xw.ext.http.retrofit.api.data.ApiResponse;
import com.xw.ext.http.retrofit.api.error.ServerResultError;

import rx.functions.Func1;

/**
 * Created by lsz  on 2019-01-28
 */
public class MyApiResponseFunc<T> implements Func1<MyApiResponse<T>, T> {
    @Override
    public T call(MyApiResponse<T> tApiResponse) {
        if (tApiResponse.code != 200 && tApiResponse.code != 0) {
            throw new ServerResultError(tApiResponse.code, tApiResponse.message);
        }
        return tApiResponse.data;
    }

}
