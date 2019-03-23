package com.dongyangbike.app.http.ack;

import com.dongyangbike.app.util.SerializeUtils;

import java.io.Serializable;

/**
 * Created by cmcc on 2018/7/1.
 */

public class AliPrepay extends BaseAck {

    /**
     * code : 200
     * message : 订单初始化成功
     * body : {"aliPayParam":"alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2017112500161892&biz_content=%7B%22out_trade_no%22%3A%22P201903221008030001%22%2C%22total_amount%22%3A%220.01000000000000000020816681711721685132943093776702880859375%22%2C%22subject%22%3A%22%E7%94%A8%E6%88%B7%E9%92%B1%E5%8C%85%E5%85%85%E5%80%BC%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=utf-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2F47.100.22.213%3A8080%2Fali%2Fnotify%2Fpay&sign=hME9A4NT%2FhBcRrRtTENXcUjBVOT4AwKWXXxFsBMsz9oNOQ7NJL%2FEEkvSxfkqWQMXM19YSV67TEDuTPYh9iEKz1WM1GLiTAUbojvbK876tL8pbgzs2ruDjsMFKqGsxo%2BaUG%2Fv1zygERvilKQiAMbu7XiFnrodezPoNGxZtJFoGyg%2BS73FTtn9xDocbFwx3gtpTBtH5CDUrOlLmL%2FPAS%2FWjBGsWzlJRRru5pjUa9GR14jX%2B%2FYM0wJ3TLbPKYuB4TW4CwmIaCsZvh4vwhmfLsWYvjnL%2FpYW4yHSpykIULIU0z9VJR60h98iNxuF6kicgigWPyNJ43dlcw6gCV3B9Xmu4g%3D%3D&sign_type=RSA2&timestamp=2019-03-22+10%3A08%3A03&version=1.0","orderNo":"2217826394673514496","result":"SUCCESS"}
     */

    private String body;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public class Bean {
        private String aliPayParam;
        private String orderNo;
        private String result;

        public String getAliPayParam() {
            return aliPayParam;
        }

        public void setAliPayParam(String aliPayParam) {
            this.aliPayParam = aliPayParam;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }

}
