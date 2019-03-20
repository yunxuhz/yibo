package com.dongyangbike.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
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
import com.dongyangbike.app.activity.ForgetPwdActivity;
import com.dongyangbike.app.activity.LoginActivity;
import com.dongyangbike.app.activity.SearchResultActivity;
import com.dongyangbike.app.base.ApiConstant;
import com.dongyangbike.app.base.BaseApplication;
import com.dongyangbike.app.http.ack.BaseAck;
import com.dongyangbike.app.http.ack.CurCityAck;
import com.dongyangbike.app.http.ack.SearchAck;
import com.dongyangbike.app.util.AppUtils;
import com.dongyangbike.app.util.StringUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;

import okhttp3.Call;
import okhttp3.MediaType;
import tech.gujin.toast.ToastUtil;

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

    private TextView mCity;
    private EditText mInput;

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

        mCity = view.findViewById(R.id.city);
        mInput = view.findViewById(R.id.input);

        mCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_SEARCH) {
                    if(!StringUtil.isStringEmpty(mInput.getEditableText().toString())) {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), SearchResultActivity.class);
                        intent.putExtra("input", mInput.getEditableText().toString());
                        startActivity(intent);
                    }
                }
                return true;
            }
        });

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
                getCurCity(location.getLatitude(), location.getLongitude());
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

    private void getCurCity(double lat, double lon) {
        HashMap<String, String> baseParam = AppUtils.getBaseHashMap();
        baseParam.put("latitude", lat + "");
        baseParam.put("longitude", lon + "");
        OkHttpUtils.postString()
                .url(ApiConstant.BASE_URL + ApiConstant.GET_CITY)
                .content(new Gson().toJson(baseParam))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        final CurCityAck data = JSON.parseObject(response, CurCityAck.class);
                        if (data != null && data.getCode().equals("200")) {
                            mCity.setText(data.getCityName());
                        }
                    }
                });
    }
}
