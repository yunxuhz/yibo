package com.dongyangbike.app.http.ack;

import java.util.ArrayList;

public class SearchAck extends BaseAck {

    public ArrayList<Data> getList() {
        return list;
    }

    public void setList(ArrayList<Data> list) {
        this.list = list;
    }

    private ArrayList<Data> list;

    public class Data {
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMerchantName() {
            return merchantName;
        }

        public void setMerchantName(String merchantName) {
            this.merchantName = merchantName;
        }

        public String getYardName() {
            return yardName;
        }

        public void setYardName(String yardName) {
            this.yardName = yardName;
        }

        private int id;
        private String merchantName;
        private String yardName;
    }

}
