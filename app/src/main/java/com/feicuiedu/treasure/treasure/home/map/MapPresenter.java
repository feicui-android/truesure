package com.feicuiedu.treasure.treasure.home.map;

import com.feicuiedu.treasure.net.NetClient;
import com.feicuiedu.treasure.treasure.Treasure;
import com.feicuiedu.treasure.treasure.home.Area;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/6/20 0020.
 */
public class MapPresenter extends MvpNullObjectBasePresenter<MapMvpView>{

    private Call<ResponseBody> call;
    private Gson gson;

    public MapPresenter(){
        gson = new GsonBuilder().setLenient().create();
    }

    /** 获取宝藏,根据指定区域*/
    public void getTreasure(Area area){
        MediaType mediaType = MediaType.parse("text/json");
        String sss = gson.toJson(area);
        RequestBody requestBody = RequestBody.create(mediaType, sss);
        if(call != null)call.cancel();
        // 构造出获取宝藏的Call模型
        call = NetClient.getInstance().getTreasureApi().getTreasureInArea(requestBody);
        // 加入请求队列
        call.enqueue(callback);
    }

    private Callback<ResponseBody> callback = new Callback<ResponseBody>() {
        @Override public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            if(response != null && response.isSuccessful()){
                // 得到响应体数据
                ResponseBody body = response.body();
                if (body == null) {
                    getView().showMessage("unknown error");
                    return;
                }
                // 解析响应体数据(GSON)
                try {
                    Type listType = new TypeToken<List<Treasure>>(){}.getType();
                    List<Treasure> datas = gson.fromJson(body.string(), listType);
                    // 通知视图
                    getView().setData(datas);
                } catch (IOException e) {
                    onFailure(call,e);
                    return;
                }
            }
        }

        @Override public void onFailure(Call<ResponseBody> call, Throwable t) {
            getView().showMessage(t.getMessage());
        }
    };
}