package com.feicuiedu.treasure.treasure.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.feicuiedu.treasure.R;
import com.feicuiedu.treasure.commons.ActivityUtils;
import com.feicuiedu.treasure.components.TreasureView;
import com.feicuiedu.treasure.treasure.home.Treasure;
import com.feicuiedu.treasure.user.UserPrefs;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TreasureDetailActivity extends MvpActivity<TreasureDetailView, TreasureDetailPresenter> implements TreasureDetailView {


    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.treasureView) TreasureView treasureView;

    @Bind(R.id.tv_detail_description) TextView tvDetailDescription;

    @Bind(R.id.frameLayout) FrameLayout frameLayout;

    private ActivityUtils activityUtils;

    private Treasure treasure;

    private static final String KEY_TREASURE_MODEL = "key_treasure";

    public static void open(Context context, Treasure treasure) {
        Intent intent = new Intent(context, TreasureDetailActivity.class);
        intent.putExtra(KEY_TREASURE_MODEL, treasure);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treasure_detail);
        activityUtils = new ActivityUtils(this);
    }

    @Override public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        treasure = (Treasure) getIntent().getSerializableExtra(KEY_TREASURE_MODEL);
        //
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(treasure.getTitle());
        //
        treasureView.bindTreasure(treasure);
        //
        LatLng latLng = new LatLng(treasure.getLatitude(), treasure.getLongitude());
        MapStatus ms = new MapStatus.Builder().overlook(-20).zoom(17).target(latLng).build();
        BaiduMapOptions options = new BaiduMapOptions()
                .mapStatus(ms)
                .compassEnabled(false)
                .zoomControlsEnabled(false)
                .zoomGesturesEnabled(false)
                .scrollGesturesEnabled(false)
                .overlookingGesturesEnabled(false)
                .rotateGesturesEnabled(false)
                .scaleControlEnabled(false);
        MapView mapView = new MapView(this, options);
        frameLayout.addView(mapView);
        addMarker(mapView.getMap());
        // 获取宝藏detail
        TreasureDetail treasureDetail = new TreasureDetail(treasure.getId(), 20, 1);
        getPresenter().getTreasureDetail(treasureDetail);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_treasure_info, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_find:
                // 找到宝藏
                int tokenid = UserPrefs.getInstance().getTokenid();
                int treasureId = treasure.getId();
                TreasureFind treasureFind = new TreasureFind(tokenid, treasureId);
                getPresenter().findTreasuer(treasureFind);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private BitmapDescriptor mBitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.mappin_traditional_expanded);

    private void addMarker(BaiduMap baiduMap) {
        LatLng latLng = new LatLng(treasure.getLatitude(), treasure.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .anchor(0.5f, 0.5f)
                .icon(mBitmapDescriptor);
        baiduMap.addOverlay(markerOptions);
    }

    @NonNull @Override public TreasureDetailPresenter createPresenter() {
        return new TreasureDetailPresenter();
    }

    @Override public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }

    @Override public void setData(TreasureDetailResult result) {
        tvDetailDescription.setText(result.description);
    }

    @Override public void navigateToHome() {
        finish();
    }
}
