package com.dongyangbike.app.http.ack;

/**
 * Created by cmcc on 2018/6/3.
 */

public class WxPrepay extends BaseAck {


    /**
     * code : 200
     * message : 订单初始化成功
     * body : {"orderNo":"2217810166324135936","result":"SUCCESS","wxPayVo":{"appId":"wx5987fea5d3a0533d","mchId":"1496076952","nonceStr":"9a7ac44a68ec40298aca47473c17c008","packageStr":"Sign=WXPay","prepayId":"wx220935500028722e5a4db6dd0762071200","sign":"EB55961C04BDC14B3A503B0765CA3374","signType":"MD5","timeStamp":"1553218550"}}
     */

    private String body;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public static class BodyBean {
        /**
         * orderNo : 2218057005292062728
         * result : SUCCESS
         * wxPayVo : {"appId":"wx5987fea5d3a0533d","mchId":"1496076952","nonceStr":"a9b8800d03814bac8edb7cc5c66c3a90","packageStr":"Sign=WXPay","prepayId":"wx22174614685701c56cf625d80329138870","sign":"4921AD2FDD98DAB7932A4685CF875EA8","signType":"MD5","timeStamp":"1553247974"}
         */

        private String orderNo;
        private String result;
        private WxPayVoBean wxPayVo;

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

        public WxPayVoBean getWxPayVo() {
            return wxPayVo;
        }

        public void setWxPayVo(WxPayVoBean wxPayVo) {
            this.wxPayVo = wxPayVo;
        }

        public static class WxPayVoBean {
            /**
             * appId : wx5987fea5d3a0533d
             * mchId : 1496076952
             * nonceStr : a9b8800d03814bac8edb7cc5c66c3a90
             * packageStr : Sign=WXPay
             * prepayId : wx22174614685701c56cf625d80329138870
             * sign : 4921AD2FDD98DAB7932A4685CF875EA8
             * signType : MD5
             * timeStamp : 1553247974
             */

            private String appId;
            private String mchId;
            private String nonceStr;
            private String packageStr;
            private String prepayId;
            private String sign;
            private String signType;
            private String timeStamp;

            public String getAppId() {
                return appId;
            }

            public void setAppId(String appId) {
                this.appId = appId;
            }

            public String getMchId() {
                return mchId;
            }

            public void setMchId(String mchId) {
                this.mchId = mchId;
            }

            public String getNonceStr() {
                return nonceStr;
            }

            public void setNonceStr(String nonceStr) {
                this.nonceStr = nonceStr;
            }

            public String getPackageStr() {
                return packageStr;
            }

            public void setPackageStr(String packageStr) {
                this.packageStr = packageStr;
            }

            public String getPrepayId() {
                return prepayId;
            }

            public void setPrepayId(String prepayId) {
                this.prepayId = prepayId;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public String getSignType() {
                return signType;
            }

            public void setSignType(String signType) {
                this.signType = signType;
            }

            public String getTimeStamp() {
                return timeStamp;
            }

            public void setTimeStamp(String timeStamp) {
                this.timeStamp = timeStamp;
            }
        }
//        /**
//         * orderNo : 2217810166324135936
//         * result : SUCCESS
//         * wxPayVo : {"appId":"wx5987fea5d3a0533d","mchId":"1496076952","nonceStr":"9a7ac44a68ec40298aca47473c17c008","packageStr":"Sign=WXPay","prepayId":"wx220935500028722e5a4db6dd0762071200","sign":"EB55961C04BDC14B3A503B0765CA3374","signType":"MD5","timeStamp":"1553218550"}
//         */
//
//        private String orderNo;
//        private String result;
//        private WxPayVoBean wxPayVo;
//
//        public String getOrderNo() {
//            return orderNo;
//        }
//
//        public void setOrderNo(String orderNo) {
//            this.orderNo = orderNo;
//        }
//
//        public String getResult() {
//            return result;
//        }
//
//        public void setResult(String result) {
//            this.result = result;
//        }
//
//        public WxPayVoBean getWxPayVo() {
//            return wxPayVo;
//        }
//
//        public void setWxPayVo(WxPayVoBean wxPayVo) {
//            this.wxPayVo = wxPayVo;
//        }
//
//        public class WxPayVoBean {
//            /**
//             * appId : wx5987fea5d3a0533d
//             * mchId : 1496076952
//             * nonceStr : 9a7ac44a68ec40298aca47473c17c008
//             * packageStr : Sign=WXPay
//             * prepayId : wx220935500028722e5a4db6dd0762071200
//             * sign : EB55961C04BDC14B3A503B0765CA3374
//             * signType : MD5
//             * timeStamp : 1553218550
//             */
//
//            private String appId;
//            private String mchId;
//            private String nonceStr;
//            private String packageStr;
//            private String prepayId;
//            private String sign;
//            private String signType;
//            private String timeStamp;
//
//            public String getAppId() {
//                return appId;
//            }
//
//            public void setAppId(String appId) {
//                this.appId = appId;
//            }
//
//            public String getMchId() {
//                return mchId;
//            }
//
//            public void setMchId(String mchId) {
//                this.mchId = mchId;
//            }
//
//            public String getNonceStr() {
//                return nonceStr;
//            }
//
//            public void setNonceStr(String nonceStr) {
//                this.nonceStr = nonceStr;
//            }
//
//            public String getPackageStr() {
//                return packageStr;
//            }
//
//            public void setPackageStr(String packageStr) {
//                this.packageStr = packageStr;
//            }
//
//            public String getPrepayId() {
//                return prepayId;
//            }
//
//            public void setPrepayId(String prepayId) {
//                this.prepayId = prepayId;
//            }
//
//            public String getSign() {
//                return sign;
//            }
//
//            public void setSign(String sign) {
//                this.sign = sign;
//            }
//
//            public String getSignType() {
//                return signType;
//            }
//
//            public void setSignType(String signType) {
//                this.signType = signType;
//            }
//
//            public String getTimeStamp() {
//                return timeStamp;
//            }
//
//            public void setTimeStamp(String timeStamp) {
//                this.timeStamp = timeStamp;
//            }
//        }


    }
}
