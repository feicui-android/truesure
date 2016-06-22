package com.feicuiedu.treasure.treasure.home.map;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.feicuiedu.treasure.R;
import com.feicuiedu.treasure.commons.ActivityUtils;
import com.feicuiedu.treasure.commons.LogUtils;
import com.feicuiedu.treasure.components.TreasureView;
import com.feicuiedu.treasure.treasure.Treasure;
import com.feicuiedu.treasure.treasure.TreasureRepo;
import com.feicuiedu.treasure.treasure.home.Area;
import com.hannesdorfmann.mosby.mvp.MvpFragment;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/6/15 0015.
 */
public class MapFragment extends MvpFragment<MapMvpView, MapPresenter> implements MapMvpView {

    private MapView mapView; // 地图视图
    private BaiduMap baiduMap;// 地图的操作类

    @Bind(R.id.map_frame)
    FrameLayout mapFrame;

    @Bind(R.id.centerLayout)
    RelativeLayout conterLayout;

    @Bind(R.id.hide_treasure)
    RelativeLayout hideTreasure;

    @Bind(R.id.btn_HideHere)
    Button btnHideHere;

    @Bind(R.id.et_treasureTitle)
    EditText etTreasureTitle;

    // 下方用来显示宝藏信息的layout(默认时是隐藏的)
    @Bind(R.id.layout_bottom) FrameLayout bottomLayout;
    @Bind(R.id.treasureView) TreasureView treasureView;// 显示宝藏信息的卡片

    private ActivityUtils activityUtils;
    private final BitmapDescriptor dot = BitmapDescriptorFactory.fromResource(R.drawable.treasure_dot);
    private final BitmapDescriptor iconExpanded = BitmapDescriptorFactory.fromResource(R.drawable.treasure_expanded);

    @Override public void onAttach(Activity activity) {
        super.onAttach(activity);
        activityUtils = new ActivityUtils(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override public MapPresenter createPresenter() {
        return new MapPresenter();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initBaiduMap();
    }

    private void initBaiduMap() {
        // 地图状态配置
        MapStatus mapStatus = new MapStatus.Builder()
                .zoom(15)
                .overlook(-20) // 俯视角度 0 ~ -30
                .build();
        // 地图的配置
        BaiduMapOptions options = new BaiduMapOptions()
                .mapStatus(mapStatus)
                .zoomControlsEnabled(false); // 不激活 zoom (因为我们自己的UI做了zoom)
        // 地图初始化 ------------------------------------------------------------------------
        mapView = new MapView(getActivity(), options);
        mapFrame.addView(mapView, 0); // 将地图添加在最里层 (因为上面我们还放了其他布局内容)
        baiduMap = mapView.getMap();
        // 地图定位相关 ----------------------------------------------------------------------
        MyLocationConfiguration config = new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.NORMAL,//定位图层显示方式, 默认为 LocationMode.NORMAL 普通态
                false, // 是否允许显示方向信息
                null // 设置用户自定义定位图标，可以为 null
        );
        baiduMap.setMyLocationConfigeration(config);
        initlocationClient();
        // 地图状态进行监听
        baiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override public void onMapStatusChangeStart(MapStatus mapStatus) {

            }

            @Override public void onMapStatusChange(MapStatus mapStatus) {

            }

            @Override public void onMapStatusChangeFinish(MapStatus mapStatus) {
                updateMapArea();
            }
        });
        // 对Marker进行click监听
        baiduMap.setOnMarkerClickListener(markerClickListener);
    }

    // 定位的核心 API
    private LocationClient locationClient;
    private static LatLng myLocation;

    public static LatLng getMyLocation() {
        return myLocation;
    }

    private void initlocationClient() {
        // 激活我的位置(定位图层打开)
        baiduMap.setMyLocationEnabled(true);
        locationClient = new LocationClient(getActivity().getApplicationContext());
        locationClient.registerLocationListener(locationListener);// 注册监听
        // 定位设置
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开GPS
        option.setScanSpan(60000); // 扫描周期
        option.setCoorType("bd09ll");// 百度坐标类型
        locationClient.setLocOption(option);
        // 开始定位
        locationClient.start();
        locationClient.requestLocation();
    }

    // 定位SDK
    // 地图ＳＤＫ
    private final BDLocationListener locationListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            // 定位不成功
            if (bdLocation == null) {
                locationClient.requestLocation();
                return;
            }
            LogUtils.d("LocType : " + bdLocation.getLocType());
            // 当前位置的经纬度
            double lon = bdLocation.getLongitude();
            double lat = bdLocation.getLatitude();
            myLocation = new LatLng(lat, lon);
            LogUtils.d(bdLocation.getAddrStr());
            //
            MyLocationData myLocationData = new MyLocationData.Builder()
                    .accuracy(100f) // 精度
                    .longitude(lon) // 经度
                    .latitude(lat) //纬度
                    .build();
            // 设置我的位置
            baiduMap.setMyLocationData(myLocationData);
            if (isFirstLocated) {
                // 移动到当前位置上去
                animateMovetoMyLocation();
                isFirstLocated = false;
            }
        }
    };
    private boolean isFirstLocated = true;
    private Marker selectedMarker; // 当前选择的Marker

    private final BaiduMap.OnMarkerClickListener markerClickListener = new BaiduMap.OnMarkerClickListener() {
        @Override public boolean onMarkerClick(Marker marker) {
            if (selectedMarker != null) selectedMarker.setVisible(true);
            selectedMarker = marker;
            marker.setVisible(false); // 将当前click的marker设置不可见
            InfoWindow infoWindow = new InfoWindow(iconExpanded, marker.getPosition(), 0, infoWindowClickListener);
            baiduMap.showInfoWindow(infoWindow);
            // 将当前宝藏数据适配显示到下方UI上
            // UI: treasureView
            int treasureID = marker.getExtraInfo().getInt("id");
            Treasure treasure = TreasureRepo.getInstance().getTreasure(treasureID);
            treasureView.bindTreasure(treasure);
            // UI模式更改 (选中某一个marker时)
            changeUiMode(UI_MODE_SELECT);
            return false;
        }
    };

    private InfoWindow.OnInfoWindowClickListener infoWindowClickListener = new InfoWindow.OnInfoWindowClickListener() {
        @Override public void onInfoWindowClick() {
            selectedMarker.setVisible(true);
            baiduMap.hideInfoWindow();
            // UI模式更改 (普通模式)
            changeUiMode(UI_MODE_NORMAL);
        }
    };

    @OnClick(R.id.tv_located)
    public void animateMovetoMyLocation() {
        if (myLocation == null) activityUtils.showToast(R.string.locate_not_success);

        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(myLocation);// 当前位置
        builder.rotate(0); // 地图摆正
        builder.zoom(19); //
        baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    @OnClick({R.id.iv_scaleUp, R.id.iv_scaleDown})
    public void scaleMap(View view) {
        // 对地图状态进行了一个修改
        switch (view.getId()) {
            case R.id.iv_scaleUp:
                baiduMap.setMapStatus(MapStatusUpdateFactory.zoomIn());
                break;
            case R.id.iv_scaleDown:
                baiduMap.setMapStatus(MapStatusUpdateFactory.zoomOut());
                break;
        }
    }

    @OnClick(R.id.tv_satellite)
    public void switchMapType() {
        // 对地图的类型进行了一个修改
        int type = baiduMap.getMapType();
        type = type == BaiduMap.MAP_TYPE_SATELLITE ? BaiduMap.MAP_TYPE_NORMAL : BaiduMap.MAP_TYPE_SATELLITE;
        baiduMap.setMapType(type);
    }

    @OnClick(R.id.tv_compass)
    public void switchSatellite() {
        // 对指南进行激活的设置
        boolean isCompass = baiduMap.getUiSettings().isCompassEnabled();
        baiduMap.getUiSettings().setCompassEnabled(!isCompass);
    }

    @OnClick(R.id.hide_treasure)
    public void hideTreasure() {
        activityUtils.hideSoftKeyboard();
        String title = etTreasureTitle.getText().toString();
        if(TextUtils.isEmpty(title)){
            activityUtils.showToast(R.string.please_input_title);
            return;
        }
        activityUtils.showToast("埋藏宝藏处理...");
    }

    @Override public void showMessage(String msg) {

    }

    @Override public void setData(List<Treasure> data) {
        // 在这里将每个 Treasure 添加到地图上，做为Marker
        for (Treasure treasure : data) {
            LatLng latLng = new LatLng(treasure.getLatitude(), treasure.getLongitude());
            addMarker(latLng, treasure.getId());
        }
    }

    private void addMarker(final LatLng position, final int treasureID) {
        MarkerOptions options = new MarkerOptions();
        options.icon(dot); // 设置Marker的图标
        options.anchor(0.5f, 0.5f); // 设置Marker的锚点(居中)
        options.position(position); // 设置Marker的位置
        // 存入宝藏的ID号到每个Marker
        Bundle bundle = new Bundle();
        bundle.putInt("id", treasureID);
        options.extraInfo(bundle);
        baiduMap.addOverlay(options);
    }

    private void updateMapArea() {
        // 取得当前地图的状态(是想拿位置)
        MapStatus mapStatus = baiduMap.getMapStatus();
        double lng = mapStatus.target.longitude; // 12.433     --- 13   12
        double lat = mapStatus.target.latitude;  // 23.23432   --- 24   23
        // 确定及创建出"区域"
        Area area = new Area();
        area.setMaxLat(Math.ceil(lat)); // 向上取整
        area.setMaxLng(Math.ceil(lng));
        area.setMinLat(Math.floor(lat)); // 向下取整
        area.setMinLng(Math.floor(lng));
        // 业务逻辑进行宝藏数据获取
        getPresenter().getTreasure(area);
    }

    // HomeActivity按下埋藏宝藏时
    public void onHidePressed(){
        changeUiMode(UI_MODE_HIDE);
    }

    /**HomeActivity按下back按钮时*/
    public boolean onBackPressed(){
        if(this.uiMode != UI_MODE_NORMAL){
            changeUiMode(UI_MODE_NORMAL);
            return false;
        }
        return true;
    }

    private static final int UI_MODE_NORMAL = 0; // 普通(Map查看模块)
    private static final int UI_MODE_SELECT = 1;
    private static final int UI_MODE_HIDE = 2;

    private int uiMode = UI_MODE_NORMAL;

    private void changeUiMode(int uiMode) {
        if (this.uiMode == uiMode) return;
        this.uiMode = uiMode;
        switch (uiMode) {
            case UI_MODE_NORMAL:
                bottomLayout.setVisibility(View.GONE);// 隐藏下方的宝藏信息layout
                conterLayout.setVisibility(View.GONE);// 隐藏中间位置藏宝layout
                break;
            case UI_MODE_SELECT:
                bottomLayout.setVisibility(View.VISIBLE);// 显示下方的宝藏信息layout
                treasureView.setVisibility(View.VISIBLE);// 显示宝藏信息卡片
                conterLayout.setVisibility(View.GONE); // 隐藏中间位置藏宝layout
                hideTreasure.setVisibility(View.GONE); // 隐藏宝藏录入信息卡片
                break;
            case UI_MODE_HIDE:
                conterLayout.setVisibility(View.VISIBLE);// 显示中间位置藏宝layout
                bottomLayout.setVisibility(View.GONE);// 隐藏下方的宝藏信息layout
                // 按下藏宝时
                btnHideHere.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        bottomLayout.setVisibility(View.VISIBLE);// 显示下方的宝藏信息layout
                        hideTreasure.setVisibility(View.VISIBLE);// 显示宝藏录入信息卡片
                        treasureView.setVisibility(View.GONE);// 隐藏宝藏信息卡片
                    }
                });
                break;
        }
    }
}