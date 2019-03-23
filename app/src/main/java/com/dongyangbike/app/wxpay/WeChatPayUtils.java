package com.dongyangbike.app.wxpay;

import android.content.Context;

import com.dongyangbike.app.base.ApiConstant;
import com.dongyangbike.app.wxpay.MD5;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.apache.http.NameValuePair;

import java.util.List;
import java.util.Random;

import tech.gujin.toast.ToastUtil;

/**
 * Created by sunboy on 15/10/20.
 */
public class WeChatPayUtils {

    private static IWXAPI msgApi;

    public static void pay(Context context, String appid, String partnerId, String prepayId, String packageValue,
                           String nonceStr, String timeStamp, String sign) {
        msgApi = WXAPIFactory.createWXAPI(context, appid, false);
        // 将该app注册到微信
        msgApi.registerApp(appid);
        //检测微信是否安装
        if(!msgApi.isWXAppInstalled()){
            ToastUtil.show( "未安装微信");
            return;
        }
        PayReq req = new PayReq();

        req.appId = appid;
        req.partnerId = partnerId;
        req.prepayId =prepayId;
        req.packageValue = packageValue;
        req.nonceStr = nonceStr;
        req.timeStamp = timeStamp;
        req.sign = sign;

        // 将该app注册到微信
        msgApi.registerApp(appid);
        ToastUtil.show("正常调起支付");
        msgApi.sendReq(req);
    }

    private static String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(ApiConstant.API_KEY);
        String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        return appSign;
    }

    private static String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    private static long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }


}
