package com.feicuiedu.treasure.user.account;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.feicuiedu.treasure.R;
import com.feicuiedu.treasure.commons.ActivityUtils;
import com.feicuiedu.treasure.components.IconSelectWindow;

import org.hybridsquad.android.library.CropHandler;
import org.hybridsquad.android.library.CropHelper;
import org.hybridsquad.android.library.CropParams;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountActivity extends AppCompatActivity {

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
    }
    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getTitle());
    }

    /** 在当前个人用户中心页面按下icon后,弹出POPWINDOW*/
    @OnClick(R.id.iv_userIcon)
    public void onClick() {
        if (iconSelectWindow == null) {
            iconSelectWindow = new IconSelectWindow(this, listener);
        }
        if(iconSelectWindow.isShowing()){
            iconSelectWindow.dismiss();
            return;
        }
        iconSelectWindow.show();
    }

    private CropHandler cropHandler = new CropHandler() {
        @Override
        public void onPhotoCropped(Uri uri) {
            // 这里收到的URI就是处理完成的photo
            // 这里等一下将来做业务逻辑
            activityUtils.showToast("剪切成功");
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
}
