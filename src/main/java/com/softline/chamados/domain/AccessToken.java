package com.softline.chamados.domain;

public class AccessToken {

    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public AccessToken(String accessToken) {
        this.accessToken = accessToken;
    }


    public AccessToken() {

    }
}
