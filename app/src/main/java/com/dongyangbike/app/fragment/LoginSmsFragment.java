package com.dongyangbike.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dongyangbike.app.R;
import com.dongyangbike.app.activity.MainActivity;
import com.dongyangbike.app.activity.RegisterActivity;
import com.dongyangbike.app.base.ApiConstant;
import com.dongyangbike.app.event.LoginEvent;
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

public class LoginSmsFragment extends BaseFragment {

    private EditText mPhoneEt;
    private EditText mSmsEt;
    private TextView mCountDown;
    private TextView mLogin;
    private TextView mRegister;

    private SmsCountDownTimer mTimer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_smslogin, null);

        initView(view);
        return view;
    }

    private void initView(View view) {
        mPhoneEt = view.findViewById(R.id.phone_et);
        mSmsEt = view.findViewById(R.id.sms_et);
        mCountDown = view.findViewById(R.id.countDown);

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
                    baseParam.put("type", "1");
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

        mLogin = view.findViewById(R.id.login);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mPhoneEt.getEditableText().toString().length() == 11) {
                    if(mSmsEt.getEditableText().toString().length() >= 4) {
                        startProgressDialog("");
                        HashMap<String, String> baseParam = AppUtils.getBaseHashMap();
                        baseParam.put("mobile", mPhoneEt.getEditableText().toString());
                        baseParam.put("plat_form", "android");
                        baseParam.put("smsCode", mSmsEt.getEditableText().toString());
                        OkHttpUtils.postString()
                                .url(ApiConstant.BASE_URL + ApiConstant.SMS_LOGIN)
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
                                        final LoginAck data = JSON.parseObject(response, LoginAck.class);
                                        if (data != null && data.getCode().equals("200")) {
                                            SharedPreferenceUtils.put(getActivity(), "token", data.getToken());
                                            SharedPreferenceUtils.put(getActivity(), "phone", mPhoneEt.getEditableText().toString());
                                            new LoginEvent().post();
                                            startActivity(new Intent(getActivity(), MainActivity.class));
                                            getActivity().finish();
                                        } else {
                                            ToastUtil.show(data.getMessage());
                                        }
                                    }
                                });
                    } else {
                        ToastUtil.show("请输入正确的验证码");
                    }
                } else {
                    ToastUtil.show("请输入正确的手机号码");
                }
            }
        });

        mRegister = view.findViewById(R.id.register);
        SpannableStringBuilder spannable = new SpannableStringBuilder(mRegister.getText().toString());
        ForegroundColorSpan black = new ForegroundColorSpan(getResources().getColor(R.color.meta_text_primary));
        ForegroundColorSpan green = new ForegroundColorSpan(getResources().getColor(R.color.mainColor));
        spannable.setSpan(black, 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(green, 2, mRegister.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mRegister.setText(spannable);

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), RegisterActivity.class));
                getActivity().finish();
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
