package com.dongyangbike.app.http.ack;

import java.util.List;

public class MyOrderAck extends BaseAck {


    /**
     * pageList : {"page":0,"size":100,"total":1,"rows":[{"id":21,"yard_id":1,"layer_name":"B1","location_number":"A1002","mobile":"18867101193","yard_name":"F1停车场(出口)","merchant_name":"城西银泰","serial_no":"b15ae30770ba46efa9cb1a98cbab7353","total_timelen":0,"amount":0,"status":-3,"appointment_time":1553396595000,"label":"账户余额支付,室内,露天","labelArr":["账户余额支付","室内","露天"],"longitude":120.1143722141,"latitude":30.2987369543,"address":"浙江省杭州市拱墅区拱苑路","surplusCount":5,"totalCount":7,"tollStandard":"5元/小时"}]}
     */

    private PageListBean pageList;

    public PageListBean getPageList() {
        return pageList;
    }

    public void setPageList(PageListBean pageList) {
        this.pageList = pageList;
    }

    public static class PageListBean {
        /**
         * page : 0
         * size : 100
         * total : 1
         * rows : [{"id":21,"yard_id":1,"layer_name":"B1","location_number":"A1002","mobile":"18867101193","yard_name":"F1停车场(出口)","merchant_name":"城西银泰","serial_no":"b15ae30770ba46efa9cb1a98cbab7353","total_timelen":0,"amount":0,"status":-3,"appointment_time":1553396595000,"label":"账户余额支付,室内,露天","labelArr":["账户余额支付","室内","露天"],"longitude":120.1143722141,"latitude":30.2987369543,"address":"浙江省杭州市拱墅区拱苑路","surplusCount":5,"totalCount":7,"tollStandard":"5元/小时"}]
         */

        private int page;
        private int size;
        private int total;
        private List<RowsBean> rows;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<RowsBean> getRows() {
            return rows;
        }

        public void setRows(List<RowsBean> rows) {
            this.rows = rows;
        }

        public static class RowsBean {
            /**
             * id : 21
             * yard_id : 1
             * layer_name : B1
             * location_number : A1002
             * mobile : 18867101193
             * yard_name : F1停车场(出口)
             * merchant_name : 城西银泰
             * serial_no : b15ae30770ba46efa9cb1a98cbab7353
             * total_timelen : 0
             * amount : 0
             * status : -3
             * appointment_time : 1553396595000
             * label : 账户余额支付,室内,露天
             * labelArr : ["账户余额支付","室内","露天"]
             * longitude : 120.1143722141
             * latitude : 30.2987369543
             * address : 浙江省杭州市拱墅区拱苑路
             * surplusCount : 5
             * totalCount : 7
             * tollStandard : 5元/小时
             */

            private int id;
            private int yard_id;
            private String layer_name;
            private String location_number;
            private String mobile;
            private String yard_name;
            private String merchant_name;
            private String serial_no;
            private int total_timelen;
            private int amount;
            private int status;
            private long appointment_time;
            private String label;
            private double longitude;
            private double latitude;
            private String address;
            private int surplusCount;
            private int totalCount;
            private String tollStandard;
            private List<String> labelArr;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getYard_id() {
                return yard_id;
            }

            public void setYard_id(int yard_id) {
                this.yard_id = yard_id;
            }

            public String getLayer_name() {
                return layer_name;
            }

            public void setLayer_name(String layer_name) {
                this.layer_name = layer_name;
            }

            public String getLocation_number() {
                return location_number;
            }

            public void setLocation_number(String location_number) {
                this.location_number = location_number;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getYard_name() {
                return yard_name;
            }

            public void setYard_name(String yard_name) {
                this.yard_name = yard_name;
            }

            public String getMerchant_name() {
                return merchant_name;
            }

            public void setMerchant_name(String merchant_name) {
                this.merchant_name = merchant_name;
            }

            public String getSerial_no() {
                return serial_no;
            }

            public void setSerial_no(String serial_no) {
                this.serial_no = serial_no;
            }

            public int getTotal_timelen() {
                return total_timelen;
            }

            public void setTotal_timelen(int total_timelen) {
                this.total_timelen = total_timelen;
            }

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public long getAppointment_time() {
                return appointment_time;
            }

            public void setAppointment_time(long appointment_time) {
                this.appointment_time = appointment_time;
            }

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public double getLongitude() {
                return longitude;
            }

            public void setLongitude(double longitude) {
                this.longitude = longitude;
            }

            public double getLatitude() {
                return latitude;
            }

            public void setLatitude(double latitude) {
                this.latitude = latitude;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getSurplusCount() {
                return surplusCount;
            }

            public void setSurplusCount(int surplusCount) {
                this.surplusCount = surplusCount;
            }

            public int getTotalCount() {
                return totalCount;
            }

            public void setTotalCount(int totalCount) {
                this.totalCount = totalCount;
            }

            public String getTollStandard() {
                return tollStandard;
            }

            public void setTollStandard(String tollStandard) {
                this.tollStandard = tollStandard;
            }

            public List<String> getLabelArr() {
                return labelArr;
            }

            public void setLabelArr(List<String> labelArr) {
                this.labelArr = labelArr;
            }
        }
    }
}
