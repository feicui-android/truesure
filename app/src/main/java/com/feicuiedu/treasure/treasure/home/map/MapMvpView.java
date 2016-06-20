package com.feicuiedu.treasure.treasure.home.map;

import com.feicuiedu.treasure.treasure.Treasure;
import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

/**
 * Created by Administrator on 2016/6/20 0020.
 */
public interface MapMvpView extends MvpView{

    void showMessage(String msg);

    void setData(List<Treasure> data);
}
