package com.dongyangbike.app.http.ack;

public class RechargeAck extends BaseAck {

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    private String body;
}
