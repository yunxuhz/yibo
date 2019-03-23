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
         * id : 1
         * name : B1
         * sort : 1
         * list : [{"id":7,"name":"A1007","locationNumber":"A1007","remark":null},{"id":2,"name":"A1002","locationNumber":"A1002","remark":null},{"id":3,"name":"A1003","locationNumber":"A1003","remark":null},{"id":6,"name":"A1006","locationNumber":"A1006","remark":null},{"id":4,"name":"A1004","locationNumber":"A1004","remark":null}]
         */

        private int id;
        private String name;
        private int sort;
        private List<ListBean> list;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 7
             * name : A1007
             * locationNumber : A1007
             * remark : null
             */

            private int id;
            private String name;
            private String locationNumber;
            private Object remark;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLocationNumber() {
                return locationNumber;
            }

            public void setLocationNumber(String locationNumber) {
                this.locationNumber = locationNumber;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }
        }
    }
}
