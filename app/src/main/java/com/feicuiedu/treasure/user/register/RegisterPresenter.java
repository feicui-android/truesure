package com.feicuiedu.treasure.user.register;

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
public class RegisterPresenter extends MvpNullObjectBasePresenter<RegisterView> {

    private final MediaType mediaType = MediaType.parse("text/*");
    private Gson gson;
    private Call<ResponseBody> call;

    public RegisterPresenter() {
        gson = new GsonBuilder().setLenient().create();// 非严格模式
    }

    public void register(User user) {
        getView().showProgress();
        if (call != null) call.cancel();
        RequestBody requestBody = RequestBody.create(mediaType, gson.toJson(user));
        call = NetClient.getInstance().getUserApi().register(requestBody);
        call.enqueue(callback); // 异步执行
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (call != null) {
            call.cancel();
        }
    }

    private Callback<ResponseBody> callback = new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            getView().hideProgress();
            getView().clearEditView();
            try {
                // 成功得到响应 (200 - 299)
                if (response != null && response.isSuccessful()) {
                    String body = response.body().string();
                    // 得到响应结果(注册结果)
                    final RegisterResult result = gson.fromJson(body, RegisterResult.class);
                    if (result.getCode() == 1) {// 注册成功(@see 接口文档)
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
            getView().clearEditView();
            getView().showMessage(t.getMessage());
        }
    };
}