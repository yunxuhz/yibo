package com.dongyangbike.app.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.dongyangbike.app.R;
import com.dongyangbike.app.base.BaseApplication;

public class MainFragment extends BaseFragment implements BaiduMap.OnMapClickListener, BaiduMap.OnMarkerClickListener, BaiduMap.OnMapStatusChangeListener{

    MapView mMapView;
    BaiduMap mBaiDuMap;
    LocationClient mLocClient;
    boolean isFirstLoc = true;
    private static final int accuracyCircleFillColor = 0xAAFFD3D3;
    private static final int accuracyCircleStrokeColor = 0xAAFFFFFF;

    private double oldLongitude = 0;
    private double oldLatitude = 0;
    private LatLng firstLatLng;

    MapStatusUpdate mMapStatusUpdate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null);

        mMapView = view.findViewById(R.id.baiDu_mapView);
        mBaiDuMap = mMapView.getMap();
        mBaiDuMap.setMaxAndMinZoomLevel(23f, 13f);
        mBaiDuMap.setMyLocationConfigeration(new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.NORMAL, false, BitmapDescriptorFactory
                .fromResource(R.mipmap.baidu_map_icon), accuracyCircleFillColor, accuracyCircleStrokeColor));
        // 开启定位图层
        mBaiDuMap.setMyLocationEnabled(true);
        mBaiDuMap.setOnMarkerClickListener(this);
        mBaiDuMap.setOnMapClickListener(this);
        mBaiDuMap.setOnMapStatusChangeListener(this);
        //隐藏地图上百度地图logo图标-
        mMapView.removeViewAt(1);
        //隐藏地图上比例尺
        mMapView.removeViewAt(2);
        mMapView.showZoomControls(false);
        mLocClient = BaseApplication.getInstance().mLocationClient;
        mLocClient.registerLocationListener(new MyLocationListener());
        mLocClient.start();

        return view;
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiDuMap.setMyLocationData(locData);
            firstLatLng = new LatLng(location.getLatitude(),
                    location.getLongitude());
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(firstLatLng).zoom(17.0f);
            //缓存下初次定位的经纬度，为意见反馈使用
            BaseApplication.getInstance().setCurrentLatLng(firstLatLng);
            mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(builder.build());
            if (isFirstLoc) {
                isFirstLoc = false;
                mBaiDuMap.animateMapStatus(mMapStatusUpdate);
            }
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public boolean onMapPoiClick(MapPoi mapPoi) {
        return false;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus) {

    }

    @Override
    public void onMapStatusChange(MapStatus mapStatus) {

    }

    @Override
    public void onMapStatusChangeFinish(MapStatus mapStatus) {

    }
}
