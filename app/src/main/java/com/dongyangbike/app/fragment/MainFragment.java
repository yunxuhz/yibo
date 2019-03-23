package com.dongyangbike.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.dongyangbike.app.R;
import com.dongyangbike.app.activity.ForgetPwdActivity;
import com.dongyangbike.app.activity.LoginActivity;
import com.dongyangbike.app.activity.MainActivity;
import com.dongyangbike.app.activity.NearStationsActivity;
import com.dongyangbike.app.activity.RechargeActivity;
import com.dongyangbike.app.activity.SearchResultActivity;
import com.dongyangbike.app.adapter.ParkIdAdapter;
import com.dongyangbike.app.adapter.SuggestAdapter;
import com.dongyangbike.app.adapter.TextAdapter;
import com.dongyangbike.app.base.ApiConstant;
import com.dongyangbike.app.base.BaseApplication;
import com.dongyangbike.app.dialog.DialogClickListener;
import com.dongyangbike.app.dialog.DialogManager;
import com.dongyangbike.app.event.LoginEvent;
import com.dongyangbike.app.event.SwitchParkEvent;
import com.dongyangbike.app.http.ack.BaseAck;
import com.dongyangbike.app.http.ack.CurCityAck;
import com.dongyangbike.app.http.ack.NearStationsAck;
import com.dongyangbike.app.http.ack.ParkDetailAck;
import com.dongyangbike.app.http.ack.SearchAck;
import com.dongyangbike.app.util.AppUtils;
import com.dongyangbike.app.util.MapUtils;
import com.dongyangbike.app.util.SharedPreferenceUtils;
import com.dongyangbike.app.util.StringUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
    private RelativeLayout mNearStation;
    private ImageView mRelocation;
    private boolean mBaiduMapHeatEnable = false;

    BitmapDescriptor stationIconGreen = BitmapDescriptorFactory.fromResource(R.drawable.icon_station_green);
    BitmapDescriptor stationIconYellow = BitmapDescriptorFactory.fromResource(R.drawable.icon_station_yellow);
    BitmapDescriptor stationIconRed = BitmapDescriptorFactory.fromResource(R.drawable.icon_station_red);

    private ArrayList<NearStationsAck.Data.Row> mNearStations = new ArrayList<>();

    private InfoWindow mInfoWindow;
    private RelativeLayout mStationDetailWindow;
    private TextView yardName;
    private TextView count;
    private RecyclerView labelRecyclerView;
    private TextView daohang;
    private TextView yuyue;
    private TextView distance;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
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
        mBaiDuMap.setTrafficEnabled(mBaiduMapHeatEnable);
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

        mStationDetailWindow = view.findViewById(R.id.station_detail_window);
        yardName = view.findViewById(R.id.yardName);
        count = view.findViewById(R.id.count);
        labelRecyclerView = view.findViewById(R.id.labelRecyclerView);
        daohang = view.findViewById(R.id.daohang);
        yuyue = view.findViewById(R.id.yuyue);
        distance = view.findViewById(R.id.distance);

        mNearStation = view.findViewById(R.id.near_station);

        mNearStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(firstLatLng != null) {
                    getNearStations();
                }
            }
        });

        mRelocation = view.findViewById(R.id.request_location);
        mRelocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBaiDuMap.animateMapStatus(mMapStatusUpdate);
            }
        });

        view.findViewById(R.id.lukuang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBaiduMapHeatEnable = !mBaiduMapHeatEnable;
                mBaiDuMap.setTrafficEnabled(mBaiduMapHeatEnable);
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
                markNearStations();
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
        showAndHideInfoWindow(mNearStations.get(marker.getZIndex()));
        return false;
    }

    private void showAndHideInfoWindow(final NearStationsAck.Data.Row data) {
        yardName.setText(data.getYardName());
        distance.setText("距离" + data.getDistance());
        count.setText(data.getSurplusCount() + "个车位可预约");
        yuyue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showParkDetail(data);
            }
        });
        daohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MapUtils.isBaiduMapInstalled()){
                    MapUtils.openBaiDuNavi(getActivity(), 0, 0, null, data.getLatitude(), data.getLongitude(), data.getAddress());
                } else if (MapUtils.isGdMapInstalled()){
                    MapUtils.openGaoDeNavi(getActivity(), 0, 0, null, data.getLatitude(), data.getLongitude(), data.getAddress());
                } else {
                    ToastUtil.show("尚未安装百度地图和高德地图");
                }
            }
        });

        if (data.getLabel() != null) {
            String size[] = data.getLabel().split(",");
            TextAdapter adapter = new TextAdapter(getActivity(), Arrays.asList(size));
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            labelRecyclerView.setLayoutManager(layoutManager);
            labelRecyclerView.setFocusable(false);
            labelRecyclerView.setHasFixedSize(true);
            labelRecyclerView.setAdapter(adapter);
        } else {
            labelRecyclerView.setVisibility(View.INVISIBLE);
        }
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

    private void getNearStations() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), NearStationsActivity.class);
        intent.putExtra("lat", firstLatLng.latitude);
        intent.putExtra("lon", firstLatLng.longitude);
        startActivity(intent);
    }

    private void markNearStations() {

            HashMap<String, String> baseParam = AppUtils.getBaseHashMap();
            baseParam.put("latitude", firstLatLng.latitude + "");
            baseParam.put("longitude", firstLatLng.longitude + "");
            baseParam.put("page", "1");
            baseParam.put("size", "99");
            OkHttpUtils.postString()
                    .url(ApiConstant.BASE_URL + ApiConstant.NEAR_STATIONS)
                    .content(new Gson().toJson(baseParam))
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            final NearStationsAck data = JSON.parseObject(response, NearStationsAck.class);
                            if (data != null && data.getCode().equals("200") && data.getPageList() != null
                                    && data.getPageList().getRows() != null) {
                                mNearStations = data.getPageList().getRows();
                                for(int i = 0; i < mNearStations.size(); i++) {
                                    if(mNearStations.get(i).getSurplusCount() > 20) {
                                        mBaiDuMap.addOverlay(new MarkerOptions()
                                                .position(new LatLng(mNearStations.get(i).getLatitude(), mNearStations.get(i).getLongitude()))
                                                .icon(stationIconGreen).zIndex(i));
                                    } else if(data.getPageList().getRows().get(i).getSurplusCount() > 5) {
                                        mBaiDuMap.addOverlay(new MarkerOptions()
                                                .position(new LatLng(mNearStations.get(i).getLatitude(), mNearStations.get(i).getLongitude()))
                                                .icon(stationIconYellow).zIndex(i));
                                    } else {
                                        mBaiDuMap.addOverlay(new MarkerOptions()
                                                .position(new LatLng(mNearStations.get(i).getLatitude(), mNearStations.get(i).getLongitude()))
                                                .icon(stationIconRed).zIndex(i));
                                    }
                                }

                                showAndHideInfoWindow(mNearStations.get(0));
                            }
                        }
                    });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSwitchParkEvent(SwitchParkEvent event) {
        showAndHideInfoWindow(event.getData());

        LatLng ll = new LatLng(event.getData().getLatitude(),
                event.getData().getLongitude());
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(18.0f);
        mBaiDuMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    private void showParkDetail(final NearStationsAck.Data.Row data) {
        HashMap<String, String> baseParam = AppUtils.getBaseHashMap();
        baseParam.put("lotId", data.getId() + "");
        OkHttpUtils.postString()
                .url(ApiConstant.BASE_URL + ApiConstant.GET_PARK_DETAIL)
                .content(new Gson().toJson(baseParam))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        final ParkDetailAck parkdetail = JSON.parseObject(response, ParkDetailAck.class);
                        if (parkdetail != null && parkdetail.getCode().equals("200")) {
                            showParkDetailWindow(data.getMerchantName() + data.getYardName(), parkdetail);
                        }
                    }
                });
    }

    //推荐车位
    private int mSelectedParkId;
    private int mFloorId;

    private void showParkDetailWindow(String name, final ParkDetailAck data) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

        View dialogView = View.inflate(getActivity(), R.layout.dialog_park_detail, null);
        TextView mName = dialogView.findViewById(R.id.name);
        Button leftButton = dialogView.findViewById(R.id.dialog_leftbutton);
        Button rightButton = dialogView.findViewById(R.id.dialog_rightbutton);
        RecyclerView mSuggestView = dialogView.findViewById(R.id.suggestRecyclerView);
        RecyclerView mNumberView = dialogView.findViewById(R.id.numberRecyclerView);

        mName.setText(name);

        mFloorId = 1;

        SuggestAdapter adapter = new SuggestAdapter(getActivity(), data.getList(), new SuggestAdapter.ClickListener() {
            @Override
            public void onItemClick(int position) {
                mSelectedParkId = data.getList().get(position).getId();
                ToastUtil.show("挑选了推荐车位" + position);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mSuggestView.setLayoutManager(layoutManager);
        mSuggestView.setFocusable(false);
        mSuggestView.setHasFixedSize(true);
        mSuggestView.setAdapter(adapter);

        ParkIdAdapter mParkAdapter = new ParkIdAdapter(getActivity(), data.getList().get(0).getList(), new ParkIdAdapter.ClickListener() {
            @Override
            public void onItemClick(int position) {
                mSelectedParkId = data.getList().get(position).getId();
                ToastUtil.show("挑选了推荐车位" + position);
            }
        });
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        mNumberView.setLayoutManager(layoutManager2);
        mNumberView.setFocusable(false);
        mNumberView.setHasFixedSize(true);
        mNumberView.setAdapter(mParkAdapter);


        dialog.setView(dialogView);
        final AlertDialog dia = dialog.show();

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dia.dismiss();
            }
        });
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeAppointment(mSelectedParkId);
                dia.dismiss();
            }
        });
    }

    private void makeAppointment(int parkId) {
        ToastUtil.show("预约车位id：" + parkId);
//        String phone = (String)SharedPreferenceUtils.get(getContext(), "phone", "");
//        HashMap<String, String> baseParam = AppUtils.getBaseHashMap();
//        baseParam.put("car_license", "浙A88888");
//        baseParam.put("layer_name", "B1");
//        baseParam.put("lock_id", parkId + "");
//        baseParam.put("lock_position", parkId + "");
//        baseParam.put("merchant_id", parkId + "");
//        baseParam.put("mobile", phone);
//        baseParam.put("yard_id", parkId + "");
//
//        OkHttpUtils.postString()
//                .url(ApiConstant.BASE_URL + ApiConstant.MAKE_APPOINTMENT)
//                .content(new Gson().toJson(baseParam))
//                .mediaType(MediaType.parse("application/json; charset=utf-8"))
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                    }
//                });
    }
}
