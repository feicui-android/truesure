package com.feicuiedu.treasure.mvpbase;

/**
 * 项目实践中
 *
 * 
 */
public class MvpBasePresenter<V extends MvpBaseView> {

    // 核心点: Activity将实现MvpBaseView接口（不同页面不同子接口）
    //        Presenter内持有MvpBaseView接口对象

    // Presenter内的视图对象(MvpBaseView或他的子类才行)
    private V mvpBaseView;

    public MvpBasePresenter(V mvpBaseView){
        this.mvpBaseView = mvpBaseView;
    }

    public V getMvpBaseView() {
        return mvpBaseView;
    }
}
