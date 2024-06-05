package com.dgarciacasam.RocketAPI.Services.Models;

import lombok.Getter;
import lombok.Setter;

public class RegisterUserDto {

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String username;
}   
