package com.jybeomss1.realestateauction.user.domain.dto;

import lombok.Getter;

@Getter
public class UserLoginRequest {
    private String email;
    private String password;
}
