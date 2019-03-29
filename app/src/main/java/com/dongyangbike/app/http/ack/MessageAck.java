package com.dongyangbike.app.http.ack;

import java.util.List;

public class MessageAck extends BaseAck {


    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * title : 用户账户扣款
         * content : 您的账户已成功扣款15.00元，请注意查看。
         * type : 用户账户扣款
         * create_time : 2019-03-24 16:04:01
         */

        private String title;
        private String content;
        private String type;
        private String create_time;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }
}
