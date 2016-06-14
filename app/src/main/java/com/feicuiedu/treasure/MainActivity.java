package com.feicuiedu.treasure;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.feicuiedu.treasure.commons.ActivityUtils;
import com.feicuiedu.treasure.user.login.LoginActivity;
import com.feicuiedu.treasure.user.register.RegisterActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/** 入口页面*/
public class MainActivity extends AppCompatActivity {

    private ActivityUtils activityUtils;

    // 广播接收器(当登陆注册成功后入口页面进行关闭操作)
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // 注册本地广播(当登陆注册成功后入口页面进行关闭操作)
        IntentFilter intentFilter = new IntentFilter(Constants.ACTION_ENTER_HOME);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 取消
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    @OnClick({R.id.btn_Login,R.id.btn_Register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_Login:
                activityUtils.startActivity(LoginActivity.class);
                break;
            case R.id.btn_Register:
                activityUtils.startActivity(RegisterActivity.class);
                break;
        }
    }
}