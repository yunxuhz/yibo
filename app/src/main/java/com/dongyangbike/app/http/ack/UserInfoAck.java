package com.dongyangbike.app.http.ack;

public class UserInfoAck extends BaseAck {

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    private Data data;

    public class Data {
        public String getAduit_time() {
            return aduit_time;
        }

        public void setAduit_time(String aduit_time) {
            this.aduit_time = aduit_time;
        }

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        private String aduit_time;
        private int balance;
        private String create_time;
        private String mobile;
        private String name;
        private int user_id;
    }
}
