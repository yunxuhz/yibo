package com.dongyangbike.app.http.ack;

import java.util.List;

public class ParkDetailAck extends BaseAck {

    private List<ListBeanX> list;

    public List<ListBeanX> getList() {
        return list;
    }

    public void setList(List<ListBeanX> list) {
        this.list = list;
    }

    public static class ListBeanX {
        /**
         * id : 0
         * label : string
         * list : [{"id":0,"locationNumber":"string","name":"string","remark":"string"}]
         * name : string
         * place : string
         * sort : 0
         * yard_name : string
         */

        private int id;
        private String label;
        private String name;
        private String place;
        private int sort;
        private String yard_name;
        private List<ListBean> list;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getYard_name() {
            return yard_name;
        }

        public void setYard_name(String yard_name) {
            this.yard_name = yard_name;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 0
             * locationNumber : string
             * name : string
             * remark : string
             */

            private int id;
            private String locationNumber;
            private String name;
            private String remark;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getLocationNumber() {
                return locationNumber;
            }

            public void setLocationNumber(String locationNumber) {
                this.locationNumber = locationNumber;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }
        }
    }
}
