package com.feicuiedu.treasure;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.feicuiedu.treasure.user.UserPrefs;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by Administrator on 2016/6/16 0016.
 */
public class TreasureApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        // UserPrefs初始化
        UserPrefs.init(this);
        // ImageLoader初始化配置
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
        // 百度地图init
        SDKInitializer.initialize(getApplicationContext());
    }
}