package com.feicuiedu.treasure.user.login;

import com.feicuiedu.treasure.mvpbase.MvpBaseView;

/**
 * Created by Administrator on 2016/6/12 0012.
 */
public interface LoginView extends MvpBaseView{
    /** 显示登陆中进度视图*/
    void showProgress();
    /** 隐藏登陆中进度视图*/
    void hideProgress();
    /** 显示提示信息*/
    void showMessage(String msg);
    /** 导航到HOME页面*/
    void navigateToHome();
}