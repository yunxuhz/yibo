package com.dongyangbike.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.dongyangbike.app.R;
import com.dongyangbike.app.base.ApiConstant;
import com.dongyangbike.app.event.LoginEvent;
import com.dongyangbike.app.http.ack.LoginAck;
import com.dongyangbike.app.util.AppUtils;
import com.dongyangbike.app.util.SharedPreferenceUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;

import okhttp3.Call;
import okhttp3.MediaType;
import tech.gujin.toast.ToastUtil;

public class ParkingRecordActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_parkingrecord);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getOrders();
    }

    private void getOrders() {
        startProgressDialog("");
        String phone = (String) SharedPreferenceUtils.get(this, "phone", "");
        HashMap<String, String> baseParam = AppUtils.getBaseHashMap();
        baseParam.put("mobile", phone);
        baseParam.put("page", "0");
        baseParam.put("size", "100");
        baseParam.put("status", "1");
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
                        stopProgressDialog();
//                        final LoginAck data = JSON.parseObject(response, LoginAck.class);
//                        if (data != null && data.getCode().equals("200")) {
//                            SharedPreferenceUtils.put(getActivity(), "token", data.getToken());
//                            SharedPreferenceUtils.put(getActivity(), "phone", mPhoneEt.getEditableText().toString());
//                            new LoginEvent().post();
//                            startActivity(new Intent(getActivity(), MainActivity.class));
//                            getActivity().finish();
//                        } else {
//                            ToastUtil.show(data.getMessage());
//                        }
                        ToastUtil.show(response);
                    }
                });
    }
}
