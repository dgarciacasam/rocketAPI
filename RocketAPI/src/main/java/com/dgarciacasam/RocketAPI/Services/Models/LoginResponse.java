package com.dgarciacasam.RocketAPI.Services.Models;

public class LoginResponse {
    private String token;

    private long expiresIn;

    public LoginResponse(){}
    public LoginResponse(String token, long expiresIn){
        this.token = token;
        this.expiresIn = expiresIn;
    }

    public String getToken() {
        return token;
    }
    public Long getExpiresIn(){return expiresIn;}
    public void setToken(String token){
        this.token = token;
    }

    public void setExpiresIn(long expiresIn){
        this.expiresIn = expiresIn;
    }
}
