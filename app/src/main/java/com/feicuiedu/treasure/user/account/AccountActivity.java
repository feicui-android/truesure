package com.feicuiedu.treasure.user.account;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.feicuiedu.treasure.R;
import com.feicuiedu.treasure.commons.ActivityUtils;
import com.feicuiedu.treasure.components.IconSelectWindow;
import com.feicuiedu.treasure.user.UserPrefs;
import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.hybridsquad.android.library.CropHandler;
import org.hybridsquad.android.library.CropHelper;
import org.hybridsquad.android.library.CropParams;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

// 1. 登陆后 - 服务器会返回用户最新信息数据 - 存   -------- %%
// 2. 注册 存
// 3. 当用户上传更新头像后 - 存

// 1. 当每次重新进入Home页面 - 取
// 2. 当每次重新进入个人用户中页面  - 取

public class AccountActivity extends MvpActivity<AccountView, AccountPresenter> implements AccountView {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.iv_userIcon)
    ImageView ivUserIcon;

    private ActivityUtils activityUtils;
    private IconSelectWindow iconSelectWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        setContentView(R.layout.activity_account);
        //更新一下用户头像
        String photoUrl = UserPrefs.getInstance().getPhoto();
        if (photoUrl != null) {
            ImageLoader.getInstance().displayImage(photoUrl, ivUserIcon);
        }
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getTitle());
    }

    @NonNull
    @Override
    public AccountPresenter createPresenter() {
        return new AccountPresenter();
    }

    /**
     * 在当前个人用户中心页面按下icon后,弹出POPWINDOW
     */
    @OnClick(R.id.iv_userIcon)
    public void onClick() {
        if (iconSelectWindow == null) {
            iconSelectWindow = new IconSelectWindow(this, listener);
        }
        if (iconSelectWindow.isShowing()) {
            iconSelectWindow.dismiss();
            return;
        }
        iconSelectWindow.show();
    }

    private CropHandler cropHandler = new CropHandler() {
        @Override
        public void onPhotoCropped(Uri uri) {
            // 剪切成功 - 进行图像上传工作
            File file = new File(uri.getPath());
            getPresenter().uploadPhoto(file);
        }

        @Override
        public void onCropCancel() {
            activityUtils.showToast("CropCancel");
        }

        @Override
        public void onCropFailed(String message) {
            activityUtils.showToast("CropFailed");
        }

        @Override
        public CropParams getCropParams() {
            CropParams cropParams = new CropParams();
            cropParams.aspectX = 300;
            cropParams.aspectY = 300;
            return cropParams;
        }

        @Override
        public Activity getContext() {
            return AccountActivity.this;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 帮助我们去处理结果(剪切完的图像)
        CropHelper.handleResult(cropHandler, requestCode, resultCode, data);
    }

    private final IconSelectWindow.Listener listener = new IconSelectWindow.Listener() {
        @Override
        public void toGallery() {
            // 按下相册时
            // 打开相册 - 到相册选择图像 - 对图像进行剪切 - 将图传回来 - 将图像上传到服务器
            CropHelper.clearCachedCropFile(cropHandler.getCropParams().uri);
            Intent intent = CropHelper.buildCropFromGalleryIntent(cropHandler.getCropParams());
            startActivityForResult(intent, CropHelper.REQUEST_CROP);
        }

        @Override
        public void toCamera() {
            // 按下相机时
            // 打开相机 - 拍照 - 打开相册 - 到相册选择图像 - 对图像进行剪切 - 将图传回来 - 将图像上传到服务器
            CropHelper.clearCachedCropFile(cropHandler.getCropParams().uri);
            Intent intent = CropHelper.buildCaptureIntent(cropHandler.getCropParams().uri);
            startActivityForResult(intent, CropHelper.REQUEST_CAMERA);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private ProgressDialog progressDialog;

    @Override
    public void showProgress() {
        activityUtils.hideSoftKeyboard();
        progressDialog = ProgressDialog.show(this, "", "头像上传中,请稍后...");
    }

    @Override
    public void hideProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }

    @Override
    public void updatePhoto(String url) {
        // ImageLoader使用前一定要初始化
        ImageLoader.getInstance().displayImage(url, ivUserIcon);
    }
}
