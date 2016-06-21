package com.feicuiedu.treasure.user.register;

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
public class RegisterPresenter extends MvpNullObjectBasePresenter<RegisterView> {

    private Call<RegisterResult> call;

    public void register(User user) {
        getView().showProgress();
        if (call != null) call.cancel();
        call = NetClient.getInstance().getUserApi().register(user);
        call.enqueue(callback); // 异步执行
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (call != null) {
            call.cancel();
        }
    }

    private Callback<RegisterResult> callback = new Callback<RegisterResult>() {
        @Override
        public void onResponse(Call<RegisterResult> call, Response<RegisterResult> response) {
            getView().hideProgress();
            getView().clearEditView();
            // 成功得到响应 (200 - 299)
            if (response != null && response.isSuccessful()) {
                final RegisterResult result = response.body();
                if (result == null) {
                    getView().showMessage("unknown error");
                    return;
                }
                if (result.getCode() == 1) {// 注册成功(@see 接口文档)
                    UserPrefs.getInstance().setTokenid(result.getTokenId());
                    getView().navigateToHome();
                    return;
                }
                getView().showMessage(result.getMsg());
            }
        }

        @Override
        public void onFailure(Call<RegisterResult> call, Throwable t) {
            getView().hideProgress();
            getView().clearEditView();
            getView().showMessage(t.getMessage());
        }
    };
}