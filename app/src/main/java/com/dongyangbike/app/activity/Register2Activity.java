package com.dongyangbike.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dongyangbike.app.R;
import com.dongyangbike.app.base.ApiConstant;
import com.dongyangbike.app.http.ack.BaseAck;
import com.dongyangbike.app.util.AppUtils;
import com.dongyangbike.app.util.SmsCountDownTimer;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import tech.gujin.toast.ToastUtil;

public class Register2Activity extends BaseActivity {

    private EditText mPwdEt;
    private EditText mSmsEt;
    private TextView mAuth;
    private TextView mNext;

    private TextView mCountDown;
    private SmsCountDownTimer mTimer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register2);

        final String phoneNum = getIntent().getStringExtra("phone");

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mSmsEt = findViewById(R.id.sms_et);
        mPwdEt = findViewById(R.id.pwd_et);

        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mPwdEt.getEditableText().toString().length() >= 6) {
                    if(mSmsEt.getEditableText().toString().length() >= 4) {
                        HashMap<String, String> baseParam = AppUtils.getBaseHashMap();
                        baseParam.put("mobile", phoneNum);
                        baseParam.put("password", mPwdEt.getEditableText().toString());
                        baseParam.put("platForm", "android");
                        baseParam.put("smsCode", mSmsEt.getEditableText().toString());
                        OkHttpUtils.postString()
                                .url(ApiConstant.BASE_URL + ApiConstant.REGISTER)
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
                                        if(data != null && data.getCode().equals("200")) {
                                            startActivity(new Intent(Register2Activity.this, MainActivity.class));
                                            finish();
                                        } else {
                                            ToastUtil.show(data.getMessage());
                                        }
                                    }
                                });
                    } else {
                        ToastUtil.show("请输入正确的验证码");
                    }
                } else {
                    ToastUtil.show("密码不能少于6位");
                }
            }
        });

        mAuth = findViewById(R.id.auth);
        SpannableStringBuilder spannable = new SpannableStringBuilder(mAuth.getText().toString());
        ForegroundColorSpan black = new ForegroundColorSpan(getResources().getColor(R.color.meta_text_primary));
        ForegroundColorSpan green = new ForegroundColorSpan(getResources().getColor(R.color.mainColor));
        spannable.setSpan(black, 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(green, 10, mAuth.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mAuth.setText(spannable);

        mAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Register2Activity.this, WebviewActivity.class);
                intent.putExtra("url", "https://carxcx.jimilicai.com/h5/registration.html");
                startActivity(intent);
            }
        });

        mCountDown = findViewById(R.id.countDown);

        SmsCountDownTimer.FLAG_FIRST_IN = true;
        if(!SmsCountDownTimer.FLAG_FIRST_IN && SmsCountDownTimer.curMills + 60000 > System.currentTimeMillis()) {
            setCountDownTimer(SmsCountDownTimer.curMills + 60000 - System.currentTimeMillis());
            mTimer.timeStart(false);
        } else {
            setCountDownTimer(60000);
        }

        mCountDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    startProgressDialog("");
                    mTimer.timeStart(true);

                HashMap<String, String> baseParam = AppUtils.getBaseHashMap();
                baseParam.put("mobile", phoneNum);
                baseParam.put("type", "2");
                    OkHttpUtils.postString()
                            .url(ApiConstant.BASE_URL + ApiConstant.SMS_SEND)
                            .mediaType(MediaType.parse("application/json; charset=utf-8"))
                            .content(new Gson().toJson(baseParam))
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
