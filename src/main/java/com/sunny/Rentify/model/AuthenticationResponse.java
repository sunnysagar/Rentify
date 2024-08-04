package com.sunny.Rentify.model;

public class AuthenticationResponse {

    private final String token;
//    private final String refreshToken;

    public AuthenticationResponse(String token) {
        this.token = token;
//        this.refreshToken = refreshToken;
    }

    public String getToken() {
        return token;
    }

//    public String getRefreshToken() {
//        return refreshToken;
//    }
}
