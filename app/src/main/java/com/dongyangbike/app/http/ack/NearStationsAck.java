package com.dongyangbike.app.http.ack;

import java.util.ArrayList;

public class NearStationsAck extends BaseAck {

    public Data getPageList() {
        return pageList;
    }

    public void setPageList(Data pageList) {
        this.pageList = pageList;
    }

    private Data pageList;

    public class Data {
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

        public ArrayList<Row> getRows() {
            return rows;
        }

        public void setRows(ArrayList<Row> rows) {
            this.rows = rows;
        }

        private int page;
        private int size;
        private int total;
        public ArrayList<Row> rows;

        public class Row {
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

            public int getSurplusCount() {
                return surplusCount;
            }

            public void setSurplusCount(int surplusCount) {
                this.surplusCount = surplusCount;
            }

            public String getYardName() {
                return yardName;
            }

            public void setYardName(String yardName) {
                this.yardName = yardName;
            }

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public int getMerchantId() {
                return merchantId;
            }

            public void setMerchantId(int merchantId) {
                this.merchantId = merchantId;
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

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            private int id;
            private String merchantName;
            private int surplusCount;
            private String yardName;
            private String label;
            private int merchantId;
            private double longitude;
            private double latitude;
            private String address;
            private String distance;
        }
    }



}
