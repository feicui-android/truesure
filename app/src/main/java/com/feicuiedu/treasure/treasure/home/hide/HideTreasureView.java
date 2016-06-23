package com.feicuiedu.treasure.treasure.home.hide;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Administrator on 2016/6/23 0023.
 */
public interface HideTreasureView extends MvpView{
    void showProgress();
    void hideProgress();
    void showMessage(String msg);
    void navigateToHome();
}
