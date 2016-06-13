package com.feicuiedu.treasure.user.login;

import android.os.AsyncTask;

import com.feicuiedu.treasure.net.NetClient;
import com.feicuiedu.treasure.user.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2016/6/12 0012.
 */
public class LoginPresenter extends MvpNullObjectBasePresenter<LoginView> {

    private final String URL = "http://admin.syfeicuiedu.com/Handler/UserHandler.ashx?action=login";
    private final MediaType mediaType = MediaType.parse("text/*");
    private Gson gson;

    public LoginPresenter() {
        gson = new GsonBuilder().setLenient().create();// 非严格模式
    }

    public void login(User user) {
        new LoginTask().execute(user);
    }

    private final class LoginTask extends AsyncTask<User, String, LoginResult> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            getView().showProgress();
        }

        @Override
        protected LoginResult doInBackground(User... params) {
            User user = params[0];
            // 构建请求体
            RequestBody requestBody = RequestBody.create(mediaType, gson.toJson(user));
            // 构建请求
            Request request = new Request.Builder().url(URL)
                    .post(requestBody)
                    .build();
            // 构建一次呼叫请求-得到当前这个呼叫(请求,响应)对象
            Call call = NetClient.getInstance().getClient().newCall(request);
            try {
                // 执行这次呼叫,得到响应
                Response response = call.execute();
                // 对响应进行code判断
                if (response.isSuccessful()) {
                    // 从响应中,取出响应体
                    ResponseBody responseBody = response.body();
                    String body = responseBody.string();
                    // 将字符串body --> LoginResult实体对象
                    LoginResult loginResult = gson.fromJson(body, LoginResult.class);
                    return loginResult;
                }
            } catch (IOException e) {
                e.printStackTrace();
                publishProgress(e.getMessage());
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            getView().hideProgress();
            getView().clearEditView();
            getView().showMessage(values[0]);
        }

        @Override
        protected void onPostExecute(LoginResult result) {
            super.onPostExecute(result);
            getView().hideProgress();
            if (result != null) {
                switch (result.getCode()) {
                    case SUCCESS:
                        getView().navigateToHome();
                        break;
                    default:
                        getView().clearEditView();
                        getView().showMessage("登录失败");
                        break;
                }
            }
        }
    }

    public static final int SUCCESS = 1;

}