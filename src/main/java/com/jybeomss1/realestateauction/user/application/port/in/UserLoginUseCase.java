package com.jybeomss1.realestateauction.user.application.port.in;


import com.jybeomss1.realestateauction.user.domain.dto.response.TokenResponse;

public interface UserLoginUseCase {
    TokenResponse login(String email, String password);
}
