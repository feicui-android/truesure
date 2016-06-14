package com.feicuiedu.treasure.user;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
public interface UserApi {

    @POST("/Handler/UserHandler.ashx?action=login")
    Call<ResponseBody> login(@Body RequestBody body);

    @POST("/Handler/UserHandler.ashx?action=register")
    Call<ResponseBody> register(@Body RequestBody body);
}
