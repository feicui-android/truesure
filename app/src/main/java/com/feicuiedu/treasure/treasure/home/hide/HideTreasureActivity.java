package com.feicuiedu.treasure.treasure.home.hide;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.baidu.mapapi.model.LatLng;
import com.feicuiedu.treasure.R;
import com.feicuiedu.treasure.commons.ActivityUtils;
import com.feicuiedu.treasure.treasure.TreasureRepo;
import com.feicuiedu.treasure.user.UserPrefs;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HideTreasureActivity extends MvpActivity<HideTreasureView,HideTreasurePresenter> implements HideTreasureView{

    private ActivityUtils activityUtils;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.et_description)EditText etDescription;

    private static final String EXTRA_KEY_TITLE = "key_title";
    private static final String EXTRA_KEY_LOCATION = "key_location";
    private static final String EXTRA_KEY_LAT_LNG = "key_latlng";
    private static final String EXTRA_KEY_ALTITUDE = "key_altitude";
    //
    public static void open(Context context, String title, String location,LatLng latLng, double altitude ){
        Intent intent = new Intent(context, HideTreasureActivity.class);
        intent.putExtra(EXTRA_KEY_TITLE, title);
        intent.putExtra(EXTRA_KEY_LOCATION, location);
        intent.putExtra(EXTRA_KEY_LAT_LNG, latLng);
        intent.putExtra(EXTRA_KEY_ALTITUDE, altitude);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hide_treasure);
        activityUtils = new ActivityUtils(this);
    }

    @Override public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getIntent().getStringExtra(EXTRA_KEY_TITLE));
        }
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_hide_treasure, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_send: // 发送(确定上传)
                Intent preIntent = getIntent();
                LatLng latLng = preIntent.getParcelableExtra(EXTRA_KEY_LAT_LNG);

                HideTreasure hideTreasure = new HideTreasure();
                hideTreasure.setAltitude(preIntent.getDoubleExtra(EXTRA_KEY_ALTITUDE, 0));
                hideTreasure.setLatitude(latLng.latitude);
                hideTreasure.setLongitude(latLng.longitude);
                hideTreasure.setLocation(preIntent.getStringExtra(EXTRA_KEY_LOCATION));
                hideTreasure.setTokenId(UserPrefs.getInstance().getTokenid());
                hideTreasure.setTitle(preIntent.getStringExtra(EXTRA_KEY_TITLE));
                hideTreasure.setDescription(etDescription.getText().toString());
                getPresenter().hide(hideTreasure);
                break;
        }
        return true;
    }

    @NonNull @Override public HideTreasurePresenter createPresenter() {
        return new HideTreasurePresenter();
    }

    private ProgressDialog progressDialog;

    @Override
    public void showProgress() {
        activityUtils.hideSoftKeyboard();
        progressDialog = ProgressDialog.show(this, "", "宝藏数据上传中,请稍后...");
    }

    @Override
    public void hideProgress() {
        if(progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }

    @Override public void navigateToHome() {
        finish();
        // 清理宝藏仓库
        TreasureRepo.getInstance().clear();
    }
}
