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
import com.dongyangbike.app.adapter.CityAdapter;
import com.dongyangbike.app.adapter.MessageAdapter;
import com.dongyangbike.app.base.ApiConstant;
import com.dongyangbike.app.http.ack.CityListAck;
import com.dongyangbike.app.http.ack.MessageAck;
import com.dongyangbike.app.util.AppUtils;
import com.dongyangbike.app.util.SharedPreferenceUtils;
import com.dongyangbike.app.widget.SimpleDividerItemDecoration;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;
import tech.gujin.toast.ToastUtil;

public class MessageActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private MessageAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_message);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mRecyclerView = findViewById(R.id.recyclerView);

        getMessage();
    }

    private void getMessage() {
        String phone = (String) SharedPreferenceUtils.get(this, "phone", "");
        HashMap<String, String> baseParam = AppUtils.getBaseHashMap();
        baseParam.put("channel", "Android");
        baseParam.put("mobile", phone);
        OkHttpUtils.postString()
                .url(ApiConstant.BASE_URL + ApiConstant.GET_MESSAGE)
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
                        MessageAck ack = JSON.parseObject(response, MessageAck.class);
                        if(ack != null && ack.getCode().equals("200")) {
                            LinearLayoutManager mLayoutManager = new LinearLayoutManager(MessageActivity.this);
                            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            mLayoutManager.setReverseLayout(false);
                            mRecyclerView.setLayoutManager(mLayoutManager);
                            Drawable drawable = ContextCompat.getDrawable(MessageActivity.this, R.drawable.divider);
                            mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(MessageActivity.this, drawable, AppUtils.dip2px(MessageActivity.this, 1)));
                            mAdapter = new MessageAdapter(MessageActivity.this, ack.getList());
                            mRecyclerView.setAdapter(mAdapter);
                        }
                    }
                });
    }
}
