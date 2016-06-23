package com.feicuiedu.treasure.treasure.home.hide;

import com.feicuiedu.treasure.net.NetClient;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/6/23 0023.
 */
public class HideTreasurePresenter extends MvpNullObjectBasePresenter<HideTreasureView> {

    private Call<HideTreasureResult> call;

    public void hide(HideTreasure hideTreasure) {
        if(call != null) call.cancel();
        call=  NetClient.getInstance().getTreasureApi().hideTreasure(hideTreasure);
        call.enqueue(callback);
    }

    private Callback<HideTreasureResult> callback = new Callback<HideTreasureResult>() {
        @Override public void onResponse(Call<HideTreasureResult> call, Response<HideTreasureResult> response) {
            getView().hideProgress();
            if(response != null && response.isSuccessful()){
                HideTreasureResult result = response.body();
                if(result == null){
                    getView().showMessage("unknown erro");
                    return;
                }
                if(result.code == 1){
                    getView().navigateToHome();
                }else{
                    getView().showMessage(result.getMsg());
                }
            }
        }

        @Override public void onFailure(Call<HideTreasureResult> call, Throwable t) {
            getView().hideProgress();
            getView().showMessage(t.getMessage());
        }
    };

    @Override public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if(call != null){
            call.cancel();
            call = null;
        }
    }
}
