package com.dongyangbike.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.dongyangbike.app.R;
import com.dongyangbike.app.adapter.TextAdapter;
import com.dongyangbike.app.base.ApiConstant;
import com.dongyangbike.app.http.ack.BaseAck;
import com.dongyangbike.app.http.ack.MyOrderAck;
import com.dongyangbike.app.util.AppUtils;
import com.dongyangbike.app.util.LogUtil;
import com.dongyangbike.app.util.MapUtils;
import com.dongyangbike.app.util.SharedPreferenceUtils;
import com.dongyangbike.app.util.TimeUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Arrays;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.MediaType;
import tech.gujin.toast.ToastUtil;

public class AppointmentActivity extends BaseActivity {

    MapView mMapView;
    BaiduMap mBaiDuMap;

    private TextView yardName;
    private RecyclerView labelRecyclerView;
    private TextView daohang;
    private TextView command;
    private TextView cancel;
    private TextView total;
    private TextView empty;
    private TextView feeinfo;
    private TextView parkinfo;

    private TextView fee;
    private TextView time;

    private double lat;
    private double lon;
    private String address;

    BitmapDescriptor stationIcon = BitmapDescriptorFactory.fromResource(R.drawable.icon_station_red);

    private int mOrderId;
    private int mStatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_appointment);

        mMapView = findViewById(R.id.baiDu_mapView);
        mBaiDuMap = mMapView.getMap();
        mBaiDuMap.setMaxAndMinZoomLevel(23f, 13f);

        lat = getIntent().getDoubleExtra("lat", 120.0);
        lon = getIntent().getDoubleExtra("lon", 30.0);
        address = getIntent().getStringExtra("address");

        LatLng ll = new LatLng(lat, lon);
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(18.0f);
        mBaiDuMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        mBaiDuMap.addOverlay(new MarkerOptions()
                .position(new LatLng(lat, lon))
                .icon(stationIcon));
        mMapView.removeViewAt(1);
        mMapView.removeViewAt(2);

        yardName = findViewById(R.id.yardName);
        labelRecyclerView = findViewById(R.id.labelRecyclerView);
        daohang = findViewById(R.id.daohang);
        command = findViewById(R.id.command);
        cancel = findViewById(R.id.cancel);

        total = findViewById(R.id.total);
        empty = findViewById(R.id.empty);
        feeinfo = findViewById(R.id.feeinfo);
        parkinfo = findViewById(R.id.parkinfo);

        fee = findViewById(R.id.fee);
        time = findViewById(R.id.time);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doCancel();
            }
        });

        TextView title = findViewById(R.id.title);
        title.setText(address);

        getUnfinishOrder();
    }

    private void updateView(MyOrderAck data) {
        final MyOrderAck.PageListBean.RowsBean bean = data.getPageList().getRows().get(0);
        yardName.setText(bean.getMerchant_name() + bean.getYard_name());

        mOrderId = bean.getId();
        mStatus = bean.getStatus();

        if(mStatus == 0 || mStatus == 1) {
            cancel.setVisibility(View.VISIBLE);
            command.setText("开锁");
        } else {
            cancel.setVisibility(View.INVISIBLE);
            if(mStatus == -2 || mStatus == 2 || mStatus == 3) {
                command.setText("支付");
            }
        }

        if (bean.getLabel() != null) {
            String size[] = bean.getLabel().split(",");
            TextAdapter adapter = new TextAdapter(this, Arrays.asList(size));
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            labelRecyclerView.setLayoutManager(layoutManager);
            labelRecyclerView.setFocusable(false);
            labelRecyclerView.setHasFixedSize(true);
            labelRecyclerView.setAdapter(adapter);
        } else {
            labelRecyclerView.setVisibility(View.INVISIBLE);
        }

        command.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(command.getText().toString().equals("进场")) {
                    openLock(mOrderId);
                } else if(command.getText().toString().equals("支付")) {
                    doPay(mOrderId);
                }
            }
        });
        daohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MapUtils.isBaiduMapInstalled()){
                    MapUtils.openBaiDuNavi(AppointmentActivity.this, 0, 0, null, lat, lon, address);
                } else if (MapUtils.isGdMapInstalled()){
                    MapUtils.openGaoDeNavi(AppointmentActivity.this, 0, 0, null, lat, lon, address);
                } else {
                    ToastUtil.show("尚未安装百度地图和高德地图");
                }
            }
        });

        total.setText(bean.getTotalCount() + "");
        empty.setText(bean.getSurplusCount() + "");
        feeinfo.setText(bean.getTollStandard());

        parkinfo.setText("楼层：" + bean.getLayer_name() + "       位置：" + bean.getLocation_number());
        fee.setText(bean.getAmount() + "");
        time.setText(TimeUtils.getNormalTime(bean.getAppointment_time()));
    }

    private void getUnfinishOrder() {
        String phone = (String) SharedPreferenceUtils.get(this, "phone", "");
        HashMap<String, String> baseParam = AppUtils.getBaseHashMap();
        baseParam.put("mobile", phone);
        baseParam.put("page", "1");
        baseParam.put("size", "100");
        baseParam.put("status", "0");
        OkHttpUtils.postString()
                .url(ApiConstant.BASE_URL + ApiConstant.ORDERS)
                .content(new Gson().toJson(baseParam))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        stopProgressDialog();
                        ToastUtil.show(e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MyOrderAck ack = JSON.parseObject(response, MyOrderAck.class);
                        if(ack !=null && ack.getCode().equals("200")) {
                            updateView(ack);
                        }
                    }
                });
    }

    private void openLock(int id) {
        HashMap<String, String> baseParam = AppUtils.getBaseHashMap();
        baseParam.put("orderId", id + "");
        OkHttpUtils.postString()
                .url(ApiConstant.BASE_URL + ApiConstant.OPEN_LOCK)
                .content(new Gson().toJson(baseParam))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        stopProgressDialog();
                        ToastUtil.show(e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        BaseAck ack = JSON.parseObject(response, BaseAck.class);
                        if(ack != null && ack.getCode().equals("200")) {
                            command.setText("支付");
                        }
                        ToastUtil.show(ack.getMessage());
                    }
                });
    }

    private void doPay(int id) {
        HashMap<String, String> baseParam = AppUtils.getBaseHashMap();
        baseParam.put("orderId", id + "");
        OkHttpUtils.postString()
                .url(ApiConstant.BASE_URL + ApiConstant.PAY)
                .content(new Gson().toJson(baseParam))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        stopProgressDialog();
                        ToastUtil.show(e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        BaseAck ack = JSON.parseObject(response, BaseAck.class);
                        if(ack != null && ack.getCode().equals("105")) {
                            ToastUtil.show(ack.getMessage());
                            startActivity(new Intent(AppointmentActivity.this, RechargeActivity.class));
                        } else if(ack != null && ack.getCode().equals("200")){
                            ToastUtil.show("支付成功");
                            finish();
                        } else {
                            ToastUtil.show(ack.getMessage());
                        }
                    }
                });
    }

    private void doCancel() {
        HashMap<String, String> baseParam = AppUtils.getBaseHashMap();
        baseParam.put("orderId", mOrderId + "");
        OkHttpUtils.postString()
                .url(ApiConstant.BASE_URL + ApiConstant.CANCEL)
                .content(new Gson().toJson(baseParam))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        stopProgressDialog();
                        ToastUtil.show(e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        BaseAck ack = JSON.parseObject(response, BaseAck.class);
                        if(ack != null && ack.getCode().equals("200")) {
                            command.setText("开锁");
                            cancel.setVisibility(View.INVISIBLE);
                        }
                        ToastUtil.show(ack.getMessage());
                    }
                });
    }
}
