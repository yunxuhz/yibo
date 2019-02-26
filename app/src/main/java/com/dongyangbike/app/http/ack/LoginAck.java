package com.dongyangbike.app.http.ack;

public class LoginAck extends BaseAck {

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;
}
