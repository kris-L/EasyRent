package com.rent.kris.easyrent.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lsz  on 2019-01-28
 */
public class MyApiResponse<T> {

    @SerializedName("success")
    public boolean success;

    @SerializedName(value = "code", alternate = {"errorcode"})
    public int code;

    @SerializedName("result")
    public T data;

    @SerializedName("message")
    public String message;

}
