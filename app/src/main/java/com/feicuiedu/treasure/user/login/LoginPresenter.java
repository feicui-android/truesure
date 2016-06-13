package com.feicuiedu.treasure.user.login;

import android.os.AsyncTask;

import com.feicuiedu.treasure.commons.LogUtils;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2016/6/12 0012.
 */


// MvpView

public class LoginPresenter extends MvpNullObjectBasePresenter<LoginView>{

    public void login() {
       new LoginTask().execute();
    }

    private final class LoginTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            getView().showProgress();
        }

        @Override
        protected String doInBackground(String... params) {
            // compile 'com.squareup.okhttp3:okhttp:3.3.1'
            OkHttpClient okHttpClient = new OkHttpClient();
            try {
                Request request = new Request.Builder()
                        .url("http://www.baidu.com") // 没有设计请求体 (GET请求)
                        .build();
                // 执行一次 请求(Request), 得到一个响应(Response)
                Response response = okHttpClient.newCall(request).execute();
                // 响应码是否在  code >= 200 && code < 300 之间
                if(response.isSuccessful()){
                    ResponseBody responseBody = response.body(); // 响应体
                    String str = responseBody.string();
                    LogUtils.d(str);
                }

            } catch (IOException e) {
                e.printStackTrace();
                getView().hideProgress();
                getView().showMessage(e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            getView().hideProgress();
            getView().navigateToHome();
        }
    }
}