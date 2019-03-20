package com.dongyangbike.app.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dongyangbike.app.R;
import com.dongyangbike.app.adapter.SearchResultAdapter;
import com.dongyangbike.app.base.ApiConstant;
import com.dongyangbike.app.http.ack.SearchAck;
import com.dongyangbike.app.util.AppUtils;
import com.dongyangbike.app.util.StringUtil;
import com.dongyangbike.app.widget.SimpleDividerItemDecoration;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.MediaType;
import tech.gujin.toast.ToastUtil;

public class SearchResultActivity extends BaseActivity {

    private EditText mInput;
    private RecyclerView mRecyclerView;
    private SearchResultAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_result);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mInput = findViewById(R.id.input);
        mRecyclerView = findViewById(R.id.recyclerView);

        mInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_SEARCH) {
                    if(!StringUtil.isStringEmpty(mInput.getEditableText().toString())) {
                        doSearch(mInput.getEditableText().toString());
                    }
                }
                return true;
            }
        });

        String name = getIntent().getStringExtra("input");

        doSearch(name);
    }

    private void doSearch(String name) {
        HashMap<String, String> baseParam = AppUtils.getBaseHashMap();
        baseParam.put("yardName", name);
        OkHttpUtils.postString()
                .url(ApiConstant.BASE_URL + ApiConstant.SEARCH)
                .content(new Gson().toJson(baseParam))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        final SearchAck data = JSON.parseObject(response, SearchAck.class);
                        if (data != null && data.getCode().equals("200")) {
                            fillData(data.getList());
                        }
                    }
                });
    }

    private void fillData(final ArrayList<SearchAck.Data> data) {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLayoutManager.setReverseLayout(false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.divider);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this, drawable, AppUtils.dip2px(this, 1)));
        mAdapter = new SearchResultAdapter(this, data, new SearchResultAdapter.ClickListener() {
            @Override
            public void onItemClick(int position) {
                ToastUtil.show(data.get(position).getId() + "");
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }
}
