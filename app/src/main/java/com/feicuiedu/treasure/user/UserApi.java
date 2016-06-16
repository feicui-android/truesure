package com.feicuiedu.treasure.user;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
public interface UserApi {

    @POST("/Handler/UserHandler.ashx?action=login")
    Call<ResponseBody> login(@Body RequestBody body);

    @POST("/Handler/UserHandler.ashx?action=register")
    Call<ResponseBody> register(@Body RequestBody body);

    // 多部分（多个请求头和请求体）
    @Multipart
    @POST("/Handler/UserLoadPicHandler1.ashx")
    Call<ResponseBody> upload(@Part MultipartBody.Part part); // 上传头像接口

    @POST("/Handler/UserHandler.ashx?action=update")
    Call<ResponseBody> update(@Body RequestBody body); // 更新头像接口
}
