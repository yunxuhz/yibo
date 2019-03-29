package com.dongyangbike.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.model.LatLng;
import com.dongyangbike.app.R;
import com.dongyangbike.app.adapter.CityAdapter;
import com.dongyangbike.app.base.ApiConstant;
import com.dongyangbike.app.event.LatLonEvent;
import com.dongyangbike.app.http.ack.CityListAck;
import com.dongyangbike.app.util.AppUtils;
import com.dongyangbike.app.widget.QuickLocationBar;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import tech.gujin.toast.ToastUtil;


public class CitySelectActivity extends BaseActivity {
    private ListView mCityLit;
    private TextView mCityName;

    private CityAdapter adapter;

    private QuickLocationBar mQuicLocationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cityselect);

        EventBus.getDefault().register(this);

        mCityName = findViewById(R.id.city_name);
        mCityName.setText(getIntent().getStringExtra("city"));

        mQuicLocationBar = findViewById(R.id.city_loactionbar);
        mQuicLocationBar.setOnTouchLitterChangedListener(new LetterListViewListener());
        mCityLit = findViewById(R.id.country_lvcountry);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getCityList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }

    private class LetterListViewListener implements
            QuickLocationBar.OnTouchLetterChangedListener {

        @Override
        public void touchLetterChanged(String s) {
            // TODO Auto-generated method stub
            Map<String, Integer> alphaIndexer = adapter.getCityMap();
            if (alphaIndexer.get(s) != null) {
                int position = alphaIndexer.get(s);
                mCityLit.setSelection(position);
            }
        }
    }

    private void getCityList() {
        HashMap<String, String> baseParam = AppUtils.getBaseHashMap();
        baseParam.put("chanel", "Android");
        OkHttpUtils.postString()
                .url(ApiConstant.BASE_URL + ApiConstant.GET_CITY_LIST)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(new Gson().toJson(baseParam))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.show(e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CityListAck ack = JSON.parseObject(response, CityListAck.class);
                        if(ack != null && ack.getCode().equals("200")) {
                            List<String> cityNames = new ArrayList<>();
                            for(CityListAck.ListBean bean : ack.getList()) {
                                cityNames.add(bean.getCity());
                            }
                            Collections.sort(cityNames);
                            adapter = new CityAdapter(CitySelectActivity.this, cityNames, ack.getList());
                            mCityLit.setAdapter(adapter);
                        }
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLatLonEvent(LatLonEvent event) {
        finish();
    }
}
