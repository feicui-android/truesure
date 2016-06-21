package com.feicuiedu.treasure.user.account;

import com.feicuiedu.treasure.commons.LogUtils;
import com.feicuiedu.treasure.net.NetClient;
import com.feicuiedu.treasure.user.UserPrefs;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/6/16 0016.
 */
public class AccountPresenter extends MvpNullObjectBasePresenter<AccountView> {

    private Call<UploadResult> uploadCall;
    private Call<UpdateResult> updataCall;
    private String photoUrl;


    /**
     * 上传头像
     */
    public void uploadPhoto(File file) {
        getView().showProgress();
        // 请求体
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
        // 多部分-部分请求
        MultipartBody.Part part = MultipartBody.Part.createFormData("image", "photo.png", requestBody);
        if (uploadCall != null) uploadCall.cancel();
        uploadCall = NetClient.getInstance().getUserApi().upload(part);
        uploadCall.enqueue(uploadCallback);
    }

    @Override public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (uploadCall != null) uploadCall.cancel();
        if (updataCall != null) updataCall.cancel();
    }

    /**
     * 上传头像Callback
     */
    private Callback<UploadResult> uploadCallback = new Callback<UploadResult>() {
        @Override
        public void onResponse(Call<UploadResult> call, Response<UploadResult> response) {
            if (response != null && response.isSuccessful()) {
                UploadResult result = response.body();
                if (result == null) {
                    getView().showMessage("unknown error");
                    return;
                }
                if (result.getCount() != 1) {
                    getView().showMessage(result.getMsg());
                    LogUtils.d("uploadCallback onResponse " + result.getMsg());
                    return;
                }
                photoUrl = result.getUrl();
                // 截取出后面图像文件名称(在更新头像时，服务器接口只要求传过去文件的名称)
                String phoneName = result.getUrl();
                phoneName = phoneName.substring(phoneName.lastIndexOf("/") + 1, phoneName.length());
                // 成功上传后 --- 通知服务器更新头像
                Update update = new Update();
                update.setTokenId(UserPrefs.getInstance().getTokenid());
                update.setPhotoUrl(phoneName);
                if (updataCall != null) updataCall.cancel();
                updataCall = NetClient.getInstance().getUserApi().update(update);
                updataCall.enqueue(updateCallback);
            }
        }

        @Override
        public void onFailure(Call<UploadResult> call, Throwable t) {
            getView().hideProgress();
            getView().showMessage(t.getMessage());
            LogUtils.d("uploadCallback onFailure " + t.getMessage());
        }
    };

    /**
     * 头像更新Callback
     */
    private Callback<UpdateResult> updateCallback = new Callback<UpdateResult>() {
        @Override
        public void onResponse(Call<UpdateResult> call, Response<UpdateResult> response) {
            getView().hideProgress();
            if (response != null && response.isSuccessful()) {
                UpdateResult result = response.body();
                if (result == null) {
                    getView().showMessage("unknown error");
                    return;
                }
                if (result.getCode() != 1) {
                    getView().showMessage(result.getMessage());
                    LogUtils.d("updateCallback onResponse " + result.getMessage());
                    return;
                }
                UserPrefs.getInstance().setPhoto(NetClient.BASE_URL + photoUrl);
                getView().updatePhoto(NetClient.BASE_URL + photoUrl);
            }
        }

        @Override
        public void onFailure(Call<UpdateResult> call, Throwable t) {
            getView().hideProgress();
            getView().showMessage(t.getMessage());
            LogUtils.d("updateCallback onResponse " + t.getMessage());
        }
    };
}
