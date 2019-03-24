package com.dongyangbike.app.http.ack;

public class MakeAppointmentAck extends BaseAck {


    /**
     * data : {"id":22,"serial_no":"84f59669ffd94b9bb54210150369c292","status":0}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 22
         * serial_no : 84f59669ffd94b9bb54210150369c292
         * status : 0
         */

        private int id;
        private String serial_no;
        private int status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSerial_no() {
            return serial_no;
        }

        public void setSerial_no(String serial_no) {
            this.serial_no = serial_no;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
