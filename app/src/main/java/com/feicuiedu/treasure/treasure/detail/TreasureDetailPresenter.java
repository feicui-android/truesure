package com.feicuiedu.treasure.treasure.detail;

import com.feicuiedu.treasure.net.NetClient;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/6/23 0023.
 *
 */
public class TreasureDetailPresenter extends MvpNullObjectBasePresenter<TreasureDetailView> {

    private Call<List<TreasureDetailResult>> detailCall;
    private Call<TreasureFindResult> findCall;

    public void getTreasureDetail(TreasureDetail treasureDetail) {
        if (detailCall != null) detailCall.cancel();
        detailCall = NetClient.getInstance().getTreasureApi().getTreasureDetail(treasureDetail);
        detailCall.enqueue(detailCallback);
    }

    public void findTreasuer(TreasureFind treasureFind) {
        if (findCall != null) findCall.cancel();
        findCall = NetClient.getInstance().getTreasureApi().findTreasure(treasureFind);
        findCall.enqueue(findCallback);
    }

    @Override public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (detailCall != null) detailCall.cancel();
    }

    private Callback<List<TreasureDetailResult>> detailCallback = new Callback<List<TreasureDetailResult>>() {
        @Override public void onResponse(Call<List<TreasureDetailResult>> call, Response<List<TreasureDetailResult>> response) {
            if (response != null && response.isSuccessful()) {
                List<TreasureDetailResult> results = response.body();
                if (results == null) {
                    getView().showMessage("unknown error");
                    return;
                }
                if(results.size() <= 0){
                    getView().showMessage("没有记录");
                    return;
                }
                getView().setData(results.get(0));
            }
        }

        @Override public void onFailure(Call<List<TreasureDetailResult>> call, Throwable t) {
            getView().showMessage(t.getMessage());
        }
    };

    private Callback<TreasureFindResult> findCallback = new Callback<TreasureFindResult>() {
        @Override public void onResponse(Call<TreasureFindResult> call, Response<TreasureFindResult> response) {
            if(response != null && response.isSuccessful()){
                TreasureFindResult result = response.body();
                if (result == null) {
                    getView().showMessage("unknown error");
                    return;
                }
                getView().showMessage(result.getMsg());

                if(result.getCode() == 1){
                    getView().navigateToHome();
                    return;
                }
            }
        }

        @Override public void onFailure(Call<TreasureFindResult> call, Throwable t) {
            getView().showMessage(t.getMessage());
        }
    };
}
