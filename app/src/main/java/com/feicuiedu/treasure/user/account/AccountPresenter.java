package com.feicuiedu.treasure.user.account;

import com.feicuiedu.treasure.net.NetClient;
import com.feicuiedu.treasure.user.UserPrefs;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/6/16 0016.
 */
public class AccountPresenter extends MvpNullObjectBasePresenter<AccountView> {

    private Call<ResponseBody> call;
    private Gson gson;
    private String photoUrl;

    public AccountPresenter() {
        gson = new GsonBuilder().setLenient().create();// 非严格模式
    }

    /**
     * 上传头像
     */
    public void uploadPhoto(File file) {
        getView().showProgress();
        if (call != null) call.cancel();
        MediaType mediaType = MediaType.parse("image/png");
        // 请求体
        RequestBody requestBody = RequestBody.create(mediaType, file);
        // 多部分-部分请求
        MultipartBody.Part part = MultipartBody.Part.createFormData("image", "photo.png", requestBody);
        call = NetClient.getInstance().getUserApi().upload(part);
        call.enqueue(uploadCallback);
    }

    /**
     * 上传头像Callback
     */
    private Callback<ResponseBody> uploadCallback = new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            try {
                if (response != null && response.isSuccessful()) {
                    String body = response.body().string();
                    UploadResult result = gson.fromJson(body, UploadResult.class);
                    if (result == null) {
                        getView().showMessage("unknown error");
                        return;
                    }
                    if (result.getCount() != 1) {
                        getView().showMessage(result.getMsg());
                        return;
                    }
                    // /UpLoad/HeadPic/f683f88dc9d14b648ad5fcba6c6bc840_0.png
                    photoUrl = result.getUrl();
                    // 截取出后面图像文件名称(在更新头像时，服务器接口只要求传过去文件的名称)
                    String phoneName = result.getUrl();
                    phoneName = phoneName.substring(phoneName.lastIndexOf("/") + 1, phoneName.length());
                    // 成功上传后 --- 通知服务器更新头像
                    Update update = new Update();
                    update.setTokenId(UserPrefs.getInstance().getTokenid());
                    update.setPhotoUrl(phoneName);
                    MediaType mediaType = MediaType.parse("text/json");
                    RequestBody requestBody = RequestBody.create(mediaType, gson.toJson(update));
                    call = NetClient.getInstance().getUserApi().update(requestBody);
                    call.enqueue(updateCallback);
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

    /**
     * 头像更新Callback
     */
    private Callback<ResponseBody> updateCallback = new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            getView().hideProgress();
            try {
                if (response != null && response.isSuccessful()) {
                    String body = response.body().string();
                    UpdateResult result = gson.fromJson(body, UpdateResult.class);
                    if (result == null) {
                        getView().showMessage("unknown error");
                        return;
                    }
                    if (result.getCode() != 1) {
                        getView().showMessage(result.getMessage());
                        return;
                    }
                    UserPrefs.getInstance().setPhoto(NetClient.BASE_URL + photoUrl);
                    getView().updatePhoto(NetClient.BASE_URL + photoUrl);
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
