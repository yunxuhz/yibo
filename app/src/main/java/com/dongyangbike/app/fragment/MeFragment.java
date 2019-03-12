package com.dongyangbike.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dongyangbike.app.R;
import com.dongyangbike.app.activity.LoginActivity;
import com.dongyangbike.app.activity.SettingActivity;
import com.dongyangbike.app.event.LoginEvent;
import com.dongyangbike.app.util.SharedPreferenceUtils;
import com.dongyangbike.app.util.StringUtil;
import com.google.zxing.common.StringUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MeFragment extends BaseFragment {

    private TextView mLogin;
    private TextView mAmount;
    private TextView mRecharge;
    private TextView mWithdraw;
    private RelativeLayout mRecord;
    private RelativeLayout mFeedback;
    private RelativeLayout mHelp;
    private RelativeLayout mSetting;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, null);

        initView(view);
        return view;
    }

    private void initView(View view) {
        mLogin = view.findViewById(R.id.name);
        mAmount = view.findViewById(R.id.text2);
        mRecharge = view.findViewById(R.id.text3);
        mWithdraw = view.findViewById(R.id.text4);
        mRecord = view.findViewById(R.id.record);
        mFeedback = view.findViewById(R.id.feedback);
        mHelp = view.findViewById(R.id.help);
        mSetting = view.findViewById(R.id.setting);

        String phone = (String)SharedPreferenceUtils.get(getContext(), "phone", "");
        if(StringUtil.isStringEmpty(phone)) {
            mLogin.setText("点击登录/注册");
            mLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            });
        } else {
            mLogin.setText(phone);
        }

        mSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = (String)SharedPreferenceUtils.get(getContext(), "phone", "");
                if(StringUtil.isStringEmpty(phone)) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), SettingActivity.class));
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEvent event) {
        String phone = (String)SharedPreferenceUtils.get(getContext(), "phone", "");
        if(StringUtil.isStringEmpty(phone)) {
            mLogin.setText("点击登录/注册");
            mLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            });
        } else {
            mLogin.setText(phone);
        }
    }
}
