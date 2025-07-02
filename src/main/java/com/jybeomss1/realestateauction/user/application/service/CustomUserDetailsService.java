package com.jybeomss1.realestateauction.user.application.service;

import com.jybeomss1.realestateauction.common.exceptions.BaseException;
import com.jybeomss1.realestateauction.common.exceptions.ErrorCode;
import com.jybeomss1.realestateauction.user.application.port.out.UserPort;
import com.jybeomss1.realestateauction.user.domain.User;
import com.jybeomss1.realestateauction.user.domain.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserPort userPort;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userPort.findByEmail(email)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));
        return new CustomUserDetails(user);
    }
}