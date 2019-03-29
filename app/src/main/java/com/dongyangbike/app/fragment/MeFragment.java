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

import com.alibaba.fastjson.JSON;
import com.dongyangbike.app.R;
import com.dongyangbike.app.activity.FeedbackActivity;
import com.dongyangbike.app.activity.LoginActivity;
import com.dongyangbike.app.activity.ParkingRecordActivity;
import com.dongyangbike.app.activity.RechargeActivity;
import com.dongyangbike.app.activity.SettingActivity;
import com.dongyangbike.app.activity.WebviewActivity;
import com.dongyangbike.app.base.ApiConstant;
import com.dongyangbike.app.dialog.DialogClickListener;
import com.dongyangbike.app.dialog.DialogManager;
import com.dongyangbike.app.event.LoginEvent;
import com.dongyangbike.app.http.ack.BaseAck;
import com.dongyangbike.app.http.ack.NearStationsAck;
import com.dongyangbike.app.http.ack.UserInfoAck;
import com.dongyangbike.app.util.AppUtils;
import com.dongyangbike.app.util.SharedPreferenceUtils;
import com.dongyangbike.app.util.StringUtil;
import com.google.gson.Gson;
import com.google.zxing.common.StringUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

import okhttp3.Call;
import okhttp3.MediaType;
import tech.gujin.toast.ToastUtil;

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

    @Override
    public void onResume() {
        super.onResume();

        String phone = (String)SharedPreferenceUtils.get(getContext(), "phone", "");
        if(!StringUtil.isStringEmpty(phone)) {
            getUserInfo(phone);
        }
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
            getUserInfo(phone);
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

        mRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ParkingRecordActivity.class));
            }
        });

        mFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), FeedbackActivity.class));
            }
        });

        mRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogManager.showDialog(getActivity(), "是否确认充值？", "取消", "确认", new DialogClickListener() {
                    @Override
                    public void onLeftClick() {

                    }

                    @Override
                    public void onRightClick() {
                        startActivity(new Intent(getActivity(), RechargeActivity.class));
                    }

                    @Override
                    public void onDismiss() {

                    }
                });
            }
        });

        mWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogManager.showDialog(getActivity(), "是否确认提现？", "取消", "提现", new DialogClickListener() {
                    @Override
                    public void onLeftClick() {

                    }

                    @Override
                    public void onRightClick() {
                        doWithDraw(mAmount.getText().toString());
                    }

                    @Override
                    public void onDismiss() {

                    }
                });
            }
        });

        mHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), WebviewActivity.class);
                intent.putExtra("url", "https://carxcx.jimilicai.com/h5/helpCenter.html");
                startActivity(intent);
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

    private void getUserInfo(String phone) {
        HashMap<String, String> baseParam = AppUtils.getBaseHashMap();
        baseParam.put("channel", "Android");
        baseParam.put("mobile", phone);
        OkHttpUtils.postString()
                .url(ApiConstant.BASE_URL + ApiConstant.GET_USER_INFO)
                .content(new Gson().toJson(baseParam))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        final UserInfoAck data = JSON.parseObject(response, UserInfoAck.class);
                        if (data != null && data.getCode().equals("200")) {
                            mAmount.setText(data.getData().getBalance() + "");
                        }
                    }
                });
    }

    private void doWithDraw(String amount) {
        String phone = (String)SharedPreferenceUtils.get(getContext(), "phone", "");
        HashMap<String, String> baseParam = AppUtils.getBaseHashMap();
        baseParam.put("apply_amount", amount);
        baseParam.put("mobile", phone);
        OkHttpUtils.postString()
                .url(ApiConstant.BASE_URL + ApiConstant.DO_WITHDRAW)
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
                        if (data != null) {
                            ToastUtil.show(data.getMessage());
                        }
                    }
                });
    }
}
