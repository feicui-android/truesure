package com.feicuiedu.treasure.treasure.home.map;

import com.feicuiedu.treasure.net.NetClient;
import com.feicuiedu.treasure.treasure.Treasure;
import com.feicuiedu.treasure.treasure.TreasureRepo;
import com.feicuiedu.treasure.treasure.home.Area;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/6/20 0020.
 * 1. 没有做宝藏的仓库 (做缓存处理)
 * 带来的问题：
 * 1. list模式时，没有宝藏数据 ---- 现在有了 --- 每当我们获取到宝藏数据后，将数据存到了仓库
 * 2. 每次地图状态更改时，都会来获取宝藏数据
 * <p/>
 * 2. 没有将"选中的"Marker宝藏,在下方用layout进行信息的bind显示
 * 代码在哪里？MapFragment里
 */
public class MapPresenter extends MvpNullObjectBasePresenter<MapMvpView> {

    private Call<List<Treasure>> call;
    private Area area;

    /**
     * 获取宝藏,根据指定区域
     */
    public void getTreasure(Area area) {
        // 如果当前这个区域已获取过
        if (TreasureRepo.getInstance().isCached(area)) {
            return;
        }
        this.area = area;
        if (call != null) call.cancel();
        // 构造出获取宝藏的Call模型
        call = NetClient.getInstance().getTreasureApi().getTreasureInArea(area);
        // 加入请求队列
        call.enqueue(callback);
    }

    private Callback<List<Treasure>> callback = new Callback<List<Treasure>>() {
        @Override public void onResponse(Call<List<Treasure>> call, Response<List<Treasure>> response) {
            if (response != null && response.isSuccessful()) {
                // 得到响应体数据
                List<Treasure> datas = response.body();
                if (datas == null) {
                    getView().showMessage("unknown error");
                    return;
                }
                // 加入缓存仓库(区域)
                TreasureRepo.getInstance().cache(area);
                // 加入缓存仓库(宝藏)
                TreasureRepo.getInstance().addTreasure(datas);
                // 通知视图
                getView().setData(datas);
            }
        }

        @Override public void onFailure(Call<List<Treasure>> call, Throwable t) {
            getView().showMessage(t.getMessage());
        }
    };

    @Override public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (call != null) {
            call.cancel();
        }
    }
}