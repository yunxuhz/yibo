package com.dongyangbike.app.http.ack;

import java.util.List;

public class CityListAck extends BaseAck {


    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * city_code : 330100
         * city : 杭州市
         * province_code : 330000
         * longitude : 120.114941
         * latitude : 30.306112
         */

        private int city_code;
        private String city;
        private String province_code;
        private double longitude;
        private double latitude;

        public int getCity_code() {
            return city_code;
        }

        public void setCity_code(int city_code) {
            this.city_code = city_code;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getProvince_code() {
            return province_code;
        }

        public void setProvince_code(String province_code) {
            this.province_code = province_code;
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
    }
}
