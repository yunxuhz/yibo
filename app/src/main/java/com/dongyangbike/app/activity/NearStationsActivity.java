package com.dongyangbike.app.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.dongyangbike.app.R;
import com.dongyangbike.app.adapter.NearStationsAdapter;
import com.dongyangbike.app.adapter.SearchResultAdapter;
import com.dongyangbike.app.base.ApiConstant;
import com.dongyangbike.app.event.SwitchParkEvent;
import com.dongyangbike.app.http.ack.NearStationsAck;
import com.dongyangbike.app.util.AppUtils;
import com.dongyangbike.app.widget.SimpleDividerItemDecoration;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.MediaType;
import tech.gujin.toast.ToastUtil;

public class NearStationsActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private NearStationsAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_near_stations);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        double lat = getIntent().getDoubleExtra("lat", 30.0);
        double lon = getIntent().getDoubleExtra("lon", 120.0);

        getNearStations(lat, lon);

        mRecyclerView = findViewById(R.id.recyclerView);
    }

    private void getNearStations(double lat, double lon) {
        HashMap<String, String> baseParam = AppUtils.getBaseHashMap();
        baseParam.put("latitude", lat + "");
        baseParam.put("longitude", lon + "");
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
                        if (data != null && data.getCode().equals("200")) {
                            fillData(data.getPageList().getRows());
                        }
                    }
                });
    }

    private void fillData(final ArrayList<NearStationsAck.Data.Row> data) {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLayoutManager.setReverseLayout(false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.divider);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this, drawable, AppUtils.dip2px(this, 1)));
        mAdapter = new NearStationsAdapter(this, data, new NearStationsAdapter.ClickListener() {
            @Override
            public void onItemClick(int position) {
                new SwitchParkEvent(data.get(position)).post();
                finish();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }
}
