package com.feicuiedu.treasure.user.login;

import com.feicuiedu.treasure.net.NetClient;
import com.feicuiedu.treasure.user.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/6/12 0012.
 */
public class LoginPresenter extends MvpNullObjectBasePresenter<LoginView> {

    private final MediaType mediaType = MediaType.parse("text/*");
    private Gson gson;

    private Call<ResponseBody> loginCall;

    public static final int SUCCESS = 1;

    public LoginPresenter() {
        gson = new GsonBuilder().setLenient().create();// 非严格模式
    }

    public void login(User user) {
        getView().showProgress();
        RequestBody requestBody = RequestBody.create(mediaType, gson.toJson(user));
        if (loginCall != null) loginCall.cancel();
        // 构建登陆请求，得到登陆Call模型
        loginCall = NetClient.getInstance().getUserApi().login(requestBody);
        loginCall.enqueue(callback);
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (loginCall != null) {
            loginCall.cancel();
        }
    }

    // Retrofit CallBack在Android内是UI线程回调
    private final Callback<ResponseBody> callback = new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            getView().hideProgress();
            try {
                if (response.isSuccessful()) {
                    // 拿到响应体数据
                    String body = response.body().string();
                    LoginResult result = gson.fromJson(body, LoginResult.class);
                    if (result.getCode() == SUCCESS) {
                        getView().navigateToHome();
                        return;
                    }
                    getView().showMessage(result.getMsg());
                }
            } catch (IOException e) {
                onFailure(call, e);
            }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            getView().hideProgress();
            getView().showMessage(t.getMessage());
        }
    };
}