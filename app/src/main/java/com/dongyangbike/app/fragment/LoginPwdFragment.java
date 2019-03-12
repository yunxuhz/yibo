package com.dongyangbike.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dongyangbike.app.R;
import com.dongyangbike.app.activity.ForgetPwdActivity;
import com.dongyangbike.app.activity.MainActivity;
import com.dongyangbike.app.activity.RegisterActivity;
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

public class LoginPwdFragment extends BaseFragment {

    private EditText mPhoneEt;
    private EditText mPwdEt;

    private TextView mForgetPwd;
    private TextView mLogin;
    private TextView mRegister;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pwdlogin, null);

        initView(view);

        return view;
    }

    private void initView(View view) {
        mPhoneEt = view.findViewById(R.id.phone_et);
        mPwdEt = view.findViewById(R.id.pwd_et);
        mForgetPwd = view.findViewById(R.id.forgetpwd);
        mLogin = view.findViewById(R.id.login);
        mRegister = view.findViewById(R.id.register);

        mForgetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ForgetPwdActivity.class));
                getActivity().finish();
            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mPhoneEt.getEditableText().toString().length() == 11) {
                    if(mPwdEt.getEditableText().toString().length() >= 6) {
                        startProgressDialog("");
                        HashMap<String, String> baseParam = AppUtils.getBaseHashMap();
                        baseParam.put("mobile", mPhoneEt.getEditableText().toString());
                        baseParam.put("plat_form", "android");
                        baseParam.put("password", mPwdEt.getEditableText().toString());
                        OkHttpUtils.postString()
                                .url(ApiConstant.BASE_URL + ApiConstant.PWD_LOGIN)
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
                                        ToastUtil.show(response);
                                    }
                                });
                    } else {
                        ToastUtil.show("密码长度不够");
                    }
                } else {
                    ToastUtil.show("请输入正确的手机号码");
                }
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), RegisterActivity.class));
                getActivity().finish();
            }
        });
    }
}
