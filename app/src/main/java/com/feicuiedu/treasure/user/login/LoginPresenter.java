package com.feicuiedu.treasure.user.login;

import com.feicuiedu.treasure.net.NetClient;
import com.feicuiedu.treasure.user.User;
import com.feicuiedu.treasure.user.UserPrefs;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/6/12 0012.
 */
public class LoginPresenter extends MvpNullObjectBasePresenter<LoginView> {

    private Call<LoginResult> loginCall;

    public static final int SUCCESS = 1;

    public void login(User user) {
        getView().showProgress();
        if (loginCall != null) loginCall.cancel();
        // 构建登陆请求，得到登陆Call模型
        loginCall = NetClient.getInstance().getUserApi().login(user);
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
    private final Callback<LoginResult> callback = new Callback<LoginResult>() {
        @Override
        public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
            getView().hideProgress();
            if (response.isSuccessful()) {
                LoginResult result = response.body();
                if (result == null) {
                    getView().showMessage("unknown error");
                    return;
                }
                if (result.getCode() == SUCCESS) {
                    UserPrefs.getInstance().setPhoto(NetClient.BASE_URL + result.getIconUrl());
                    UserPrefs.getInstance().setTokenid(result.getTokenId());
                    getView().navigateToHome();
                    return;
                }
                getView().showMessage(result.getMsg());
            }
        }

        @Override
        public void onFailure(Call<LoginResult> call, Throwable t) {
            getView().hideProgress();
            getView().showMessage(t.getMessage());
        }
    };
}