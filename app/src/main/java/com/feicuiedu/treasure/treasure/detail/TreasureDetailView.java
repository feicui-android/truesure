package com.feicuiedu.treasure.treasure.detail;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Administrator on 2016/6/23 0023.
 */
public interface TreasureDetailView extends MvpView{

    void showMessage(String msg);

    void setData(TreasureDetailResult result);

    void navigateToHome();
}
