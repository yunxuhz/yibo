package com.dongyangbike.app.alipay;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.alipay.sdk.app.PayTask;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by sunboy on 15/10/20.
 */
public class AlipayUtils {

    public static final int SDK_PAY_FLAG = 1;

    public static void pay(final Context context, final String out_trade_no, String total_fee, String tradeId, int type, final Handler mHandler) {
        // 订单
        String orderInfo = getOrderInfo(out_trade_no, total_fee, tradeId, type);

        // 对订单做RSA 签名
        String sign = SignUtils.sign(orderInfo, Keys.PRIVATE, false);
        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 完整的符合支付宝参数规范的订单信息
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
                + "sign_type=\"RSA\"";

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask((Activity) context);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 创建订单信息
     *
     * @param out_trade_no 商户网站唯一订单号
     * @param total_fee    商品金额
     * @param tradeId      商品详情中的订单号
     */
    public static String getOrderInfo(String out_trade_no, String total_fee, String tradeId, int type) {
        // 合作者身份ID
        String orderInfo = "partner=" + "\"" + Keys.PARTNER + "\"";

        // 卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + Keys.SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + out_trade_no + "\"";

        // 商品名称
        if (type == 1)
            orderInfo += "&subject=" + "\"" + "叮嗒出行订单支付" + "\"";
        else
            orderInfo += "&subject=" + "\"" + "叮嗒出行账户充值" + "\"";

        // 商品详情
        if (type == 1)
            orderInfo += "&body=" + "\"" + "叮嗒出行订单支付，订单号:" + tradeId + "\"";
        else
            orderInfo += "&body=" + "\"" + "叮嗒出行账户充值，订单号:" + tradeId + "\"";
        // 商品金额
        orderInfo += "&total_fee=" + "\"" + total_fee + "\"";

        // 服务器异步通知页面路径
        if (type == 1)
            try {
               orderInfo += "&notify_url=" + "\"" + URLEncoder.encode("/service/alipay/ticket/notify","UTF-8") + "\"";
               //orderInfo += "&notify_url=" + "\"" + URLEncoder.encode("http://bike.test.piaoniutong.com/service/alipay/ticket/notify","UTF-8") + "\"";
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        else if (type == 2)
            try {
                orderInfo += "&notify_url=" + "\"" + URLEncoder.encode("/service/alipay/recharge/notify","UTF-8") + "\"";
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        // 接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"15d\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }
}
