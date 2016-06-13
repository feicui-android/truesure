package com.feicuiedu.treasure.user.register;

import android.os.Handler;
import android.os.Looper;

import com.feicuiedu.treasure.net.NetClient;
import com.feicuiedu.treasure.user.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2016/6/12 0012.
 */
public class RegisterPresenter extends MvpNullObjectBasePresenter<RegisterView> {

    private final String URL = "http://admin.syfeicuiedu.com/Handler/UserHandler.ashx?action=register";
    private final MediaType mediaType = MediaType.parse("text/*");
    private Gson gson;
    private Call call;
    private Handler handler;

    public RegisterPresenter() {
        // 将用来处理OkHttp异步执行CallBack中，视图的更新
        handler = new Handler(Looper.getMainLooper());
        gson = new GsonBuilder().setLenient().create();// 非严格模式
    }

    public void register(User user) {
        RequestBody requestBody = RequestBody.create(mediaType, gson.toJson(user));
        Request request = new Request.Builder()
                .url(URL)
                .post(requestBody) // 设置请求体
                .build();
        call = NetClient.getInstance().getClient().newCall(request);
        // 在执行请求前 视图先显示progress
        getView().showProgress();
        call.enqueue(callback); // 异步执行
    }

    private Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, final IOException e) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    getView().hideProgress();
                    getView().clearEditView();
                    getView().showMessage(e.getMessage());
                }
            });
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    getView().hideProgress();
                }
            });
            // 成功得到响应 (200 - 299)
            if (response != null && response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                String body = responseBody.string();
                // 得到响应结果(注册结果)
                final RegisterResult result = gson.fromJson(body, RegisterResult.class);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        switch (result.getCode()) {
                            case 1: // 注册成功(@see 接口文档)
                                getView().navigateToHome();
                                break;
                            default:
                                getView().clearEditView();
                                getView().showMessage("注册失败");
                                break;
                        }
                    }
                });
            }
        }
    };
}