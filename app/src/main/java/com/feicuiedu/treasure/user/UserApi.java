package com.feicuiedu.treasure.user;

import com.feicuiedu.treasure.user.account.Update;
import com.feicuiedu.treasure.user.account.UpdateResult;
import com.feicuiedu.treasure.user.account.UploadResult;
import com.feicuiedu.treasure.user.login.LoginResult;
import com.feicuiedu.treasure.user.register.RegisterResult;

import okhttp3.MultipartBody;
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
    Call<LoginResult> login(@Body User body);

    @POST("/Handler/UserHandler.ashx?action=register")
    Call<RegisterResult> register(@Body User body);

    // 多部分（多个请求头和请求体）
    @Multipart
    @POST("/Handler/UserLoadPicHandler1.ashx")
    Call<UploadResult> upload(@Part MultipartBody.Part part); // 上传头像接口

    @POST("/Handler/UserHandler.ashx?action=update")
    Call<UpdateResult> update(@Body Update body); // 更新头像接口
}
