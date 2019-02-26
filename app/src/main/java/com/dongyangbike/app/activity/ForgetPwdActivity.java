package com.dongyangbike.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dongyangbike.app.R;
import com.dongyangbike.app.base.ApiConstant;
import com.dongyangbike.app.http.ack.BaseAck;
import com.dongyangbike.app.http.ack.LoginAck;
import com.dongyangbike.app.util.AppUtils;
import com.dongyangbike.app.util.SharedPreferenceUtils;
import com.dongyangbike.app.util.SmsCountDownTimer;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;

import okhttp3.Call;
import okhttp3.MediaType;
import tech.gujin.toast.ToastUtil;

public class ForgetPwdActivity extends BaseActivity {

    private EditText mPhoneEt;
    private EditText mSmsEt;
    private EditText mPwdEt;
    private EditText mPwd2Et;

    private TextView mCountDown;
    private SmsCountDownTimer mTimer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forgetpwd);

        mPhoneEt = findViewById(R.id.phone_et);
        mSmsEt = findViewById(R.id.sms_et);
        mPwdEt = findViewById(R.id.pwd_et);
        mPwd2Et = findViewById(R.id.pwd2_et);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mPhoneEt.getEditableText().toString().length() == 11) {
                    if(mSmsEt.getEditableText().toString().length() >= 4) {
                        if(mPwdEt.getEditableText().toString().equals(mPwd2Et.getEditableText().toString())) {
                            if(mPwdEt.getEditableText().toString().length() >= 6) {
                                startProgressDialog("");
                                HashMap<String, String> baseParam = AppUtils.getBaseHashMap();
                                baseParam.put("mobile", mPhoneEt.getEditableText().toString());
                                baseParam.put("platForm", "android");
                                baseParam.put("smsCode", mSmsEt.getEditableText().toString());
                                baseParam.put("password", mPwdEt.getEditableText().toString());
                                OkHttpUtils.postString()
                                        .url(ApiConstant.BASE_URL + ApiConstant.FORGET_PWD)
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
                                                final BaseAck data = JSON.parseObject(response, BaseAck.class);
                                                if (data != null && data.getCode().equals("200")) {
                                                    startActivity(new Intent(ForgetPwdActivity.this, LoginActivity.class));
                                                    finish();
                                                } else {
                                                    ToastUtil.show(data.getMessage());
                                                }
                                                ToastUtil.show(response);
                                            }
                                        });
                            } else {
                                ToastUtil.show("密码长度不能少于6位");
                            }
                        } else {
                            ToastUtil.show("两次密码不一致");
                        }
                    } else {
                        ToastUtil.show("请输入正确的验证码");
                    }
                } else {
                    ToastUtil.show("请输入正确的手机号码");
                }
            }
        });

        mCountDown = findViewById(R.id.countDown);

        if(!SmsCountDownTimer.FLAG_FIRST_IN && SmsCountDownTimer.curMills + 60000 > System.currentTimeMillis()) {
            setCountDownTimer(SmsCountDownTimer.curMills + 60000 - System.currentTimeMillis());
            mTimer.timeStart(false);
        } else {
            setCountDownTimer(60000);
        }

        mCountDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mPhoneEt.getEditableText().toString().length() == 11) {
                    startProgressDialog("");
                    mTimer.timeStart(true);
                    HashMap<String, String> baseParam = AppUtils.getBaseHashMap();
                    baseParam.put("mobile", mPhoneEt.getEditableText().toString());
                    baseParam.put("type", "3");
                    OkHttpUtils.postString()
                            .url(ApiConstant.BASE_URL + ApiConstant.SMS_SEND)
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
                                    ToastUtil.show(response);
                                }
                            });
                } else {
                    ToastUtil.show("请输入正确的手机号码");
                }
            }
        });
    }

    public void setCountDownTimer(final long countDownTime) {
        mTimer = new SmsCountDownTimer(countDownTime, 1000) {
            @Override
            public void onTick(long l) {
                mCountDown.setEnabled(false);
                mCountDown.setText((l / 1000) + " s");
            }

            @Override
            public void onFinish() {
                mCountDown.setEnabled(true);
                mCountDown.setText("重新获取");

                if(countDownTime != 60000) {
                    setCountDownTimer(60000);
                }
            }
        };
    }
}
