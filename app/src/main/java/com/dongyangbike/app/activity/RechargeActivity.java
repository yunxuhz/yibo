package com.dongyangbike.app.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.PayTask;
import com.dongyangbike.app.R;
import com.dongyangbike.app.alipay.AlipayUtils;
import com.dongyangbike.app.alipay.PayResult;
import com.dongyangbike.app.base.ApiConstant;
import com.dongyangbike.app.http.ack.AliPrepay;
import com.dongyangbike.app.http.ack.WxPrepay;
import com.dongyangbike.app.util.AppUtils;
import com.dongyangbike.app.util.SharedPreferenceUtils;
import com.dongyangbike.app.wxpay.WeChatPayUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import tech.gujin.toast.ToastUtil;

import static com.dongyangbike.app.alipay.AlipayUtils.SDK_PAY_FLAG;

public class RechargeActivity extends BaseActivity {

    private int mAmount = 0;
    private TextView mOneHundred;
    private TextView mTwoHundred;
    private TextView mFiveHundred;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recharge);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.wx_recharge).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAmount == 0) {
                    ToastUtil.show("请选择一个金额");
                } else {
                    doRechage(mAmount, "WX", "WX_APP");
                }
            }
        });

        findViewById(R.id.ali_recharge).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAmount == 0) {
                    ToastUtil.show("请选择一个金额");
                } else {
                    doRechage(mAmount, "ALI", "ALI_APP");
                }
            }
        });

        mOneHundred = findViewById(R.id.one_hundred);
        mTwoHundred = findViewById(R.id.two_hundred);
        mFiveHundred = findViewById(R.id.five_hundred);

        mOneHundred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAmount = 100;

                mOneHundred.setBackgroundResource(R.drawable.bg_shape_green_rect);
                mOneHundred.setTextColor(getResources().getColor(R.color.white));
                mTwoHundred.setBackgroundResource(R.drawable.bg_shape_white_rect_green_line);
                mTwoHundred.setTextColor(getResources().getColor(R.color.mainColor));
                mFiveHundred.setBackgroundResource(R.drawable.bg_shape_white_rect_green_line);
                mFiveHundred.setTextColor(getResources().getColor(R.color.mainColor));
            }
        });

        mTwoHundred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAmount = 200;

                mOneHundred.setBackgroundResource(R.drawable.bg_shape_white_rect_green_line);
                mOneHundred.setTextColor(getResources().getColor(R.color.mainColor));
                mTwoHundred.setBackgroundResource(R.drawable.bg_shape_green_rect);
                mTwoHundred.setTextColor(getResources().getColor(R.color.white));
                mFiveHundred.setBackgroundResource(R.drawable.bg_shape_white_rect_green_line);
                mFiveHundred.setTextColor(getResources().getColor(R.color.mainColor));
            }
        });

        mFiveHundred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAmount = 500;

                mOneHundred.setBackgroundResource(R.drawable.bg_shape_white_rect_green_line);
                mOneHundred.setTextColor(getResources().getColor(R.color.mainColor));
                mTwoHundred.setBackgroundResource(R.drawable.bg_shape_white_rect_green_line);
                mTwoHundred.setTextColor(getResources().getColor(R.color.mainColor));
                mFiveHundred.setBackgroundResource(R.drawable.bg_shape_green_rect);
                mFiveHundred.setTextColor(getResources().getColor(R.color.white));
            }
        });
    }

    private void doRechage(int amount, final String payChannel, String tradeType) {
        String phone = (String) SharedPreferenceUtils.get(this, "phone", "");

        HashMap<String, String> baseParam = AppUtils.getBaseHashMap();
        baseParam.put("payAmount", 0.01 + "");
        baseParam.put("mobile", phone);
        baseParam.put("payChannel", payChannel);
        baseParam.put("tradeType", tradeType);
        OkHttpUtils.postString()
                .url(ApiConstant.BASE_URL + ApiConstant.DO_RECHARGE)
                .content(new Gson().toJson(baseParam))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if(payChannel.equals("ALI")) {
                            AliPrepay data = JSON.parseObject(response, AliPrepay.class);
                            final AliPrepay.Bean bean = JSON.parseObject(data.getBody(), AliPrepay.Bean.class);
                                Runnable payRunnable = new Runnable() {

                                    @Override
                                    public void run() {
                                        PayTask alipay = new PayTask(RechargeActivity.this);
                                        String result = alipay.pay(bean.getAliPayParam());
                                        Log.i("msp", result.toString());

                                        Message msg = new Message();
                                        msg.what = AlipayUtils.SDK_PAY_FLAG;
                                        msg.obj = result;
                                        mHandler.sendMessage(msg);
                                    }
                                };
                                new Thread(payRunnable).start();
                        } else if(payChannel.equals("WX")) {
                            WxPrepay data = JSON.parseObject(response, WxPrepay.class);
                            WxPrepay.BodyBean bean = JSON.parseObject(data.getBody(), WxPrepay.BodyBean.class);
                            WeChatPayUtils.pay(RechargeActivity.this,
                                    bean.getWxPayVo().getAppId(),
                                    bean.getWxPayVo().getMchId(),
                                    bean.getWxPayVo().getPrepayId(),
                                    bean.getWxPayVo().getPackageStr(),
                                    bean.getWxPayVo().getNonceStr(),
                                    bean.getWxPayVo().getTimeStamp(),
                                    bean.getWxPayVo().getSign());
                        }
                    }
                });
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(RechargeActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(RechargeActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };
}
