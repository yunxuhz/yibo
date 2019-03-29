package com.dongyangbike.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dongyangbike.app.R;
import com.dongyangbike.app.event.LoginEvent;
import com.dongyangbike.app.util.SharedPreferenceUtils;

import tech.gujin.toast.ToastUtil;

public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setting);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.resetpwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingActivity.this, ResetPwdActivity.class));
                finish();
            }
        });

        findViewById(R.id.about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(SettingActivity.this, WebviewActivity.class);
                intent.putExtra("url", "https://carxcx.jimilicai.com/h5/aboutUs.html");
                startActivity(intent);
            }
        });

        findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.show("当前已是最新版本");
            }
        });

        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferenceUtils.put(SettingActivity.this, "phone", "");
                SharedPreferenceUtils.put(SettingActivity.this, "token", "");
                new LoginEvent().post();
                finish();
            }
        });
    }
}
