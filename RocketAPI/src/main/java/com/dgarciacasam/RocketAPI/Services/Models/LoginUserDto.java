package com.dgarciacasam.RocketAPI.Services.Models;

public class LoginUserDto {
    private String username;

    private String password;

    public String getUsername(){
        return this.username;
    }
    public String getPassword(){
        return this.password;
    }

    public LoginUserDto(){}
    public LoginUserDto(String username, String password){
        this.username = username;
        this.password = password;
    }
}
