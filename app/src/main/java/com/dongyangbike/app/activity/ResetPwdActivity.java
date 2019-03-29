package com.dongyangbike.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.dongyangbike.app.R;
import com.dongyangbike.app.base.ApiConstant;
import com.dongyangbike.app.event.LoginEvent;
import com.dongyangbike.app.http.ack.BaseAck;
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

public class ResetPwdActivity extends BaseActivity {

    private EditText mPwd1;
    private EditText mPwd2;
    private EditText mPwd3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_resetpwd);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mPwd1 = findViewById(R.id.pwd1);
        mPwd2 = findViewById(R.id.pwd2);
        mPwd3 = findViewById(R.id.pwd3);

        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doResetPwd();
            }
        });
    }

    private void doResetPwd() {
        if(mPwd1.getEditableText().toString().length() < 6
                || mPwd2.getEditableText().toString().length() < 6
                || mPwd3.getEditableText().toString().length() < 6) {
            ToastUtil.show("密码长度不能少于六位");
        } else if(!mPwd2.getEditableText().toString().equals(mPwd3.getEditableText().toString())) {
            ToastUtil.show("两次密码不一致");
        } else {
            String phone = (String) SharedPreferenceUtils.get(this, "phone", "");
            HashMap<String, String> baseParam = AppUtils.getBaseHashMap();
            baseParam.put("mobile", phone);
            baseParam.put("newPassword", mPwd2.getEditableText().toString());
            baseParam.put("oldPassword", mPwd1.getEditableText().toString());
            OkHttpUtils.postString()
                    .url(ApiConstant.BASE_URL + ApiConstant.RESET_PWD)
                    .content(new Gson().toJson(baseParam))
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            ToastUtil.show(e.toString());
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            final BaseAck data = JSON.parseObject(response, BaseAck.class);
                            if (data != null && data.getCode().equals("200")) {
                                ToastUtil.show(data.getMessage());
                                finish();
                            } else {
                                ToastUtil.show(data.getMessage());
                            }
                        }
                    });
        }
    }
}
