package com.jybeomss1.realestateauction.user.application.service;

import com.jybeomss1.realestateauction.common.exceptions.BaseException;
import com.jybeomss1.realestateauction.common.exceptions.ErrorCode;
import com.jybeomss1.realestateauction.jwt.JwtTokenProvider;
import com.jybeomss1.realestateauction.user.adapter.out.persistence.RedisRefreshTokenRepository;
import com.jybeomss1.realestateauction.user.application.port.out.UserPort;
import com.jybeomss1.realestateauction.user.domain.User;
import com.jybeomss1.realestateauction.user.domain.UserGrade;
import com.jybeomss1.realestateauction.user.domain.dto.UserGradeUpgradeRequest;
import com.jybeomss1.realestateauction.user.domain.dto.UserJoinRequest;
import com.jybeomss1.realestateauction.user.domain.dto.response.TokenResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

class UserServiceTest {
    @Mock UserPort userPort;
    @Mock PasswordEncoder passwordEncoder;
    @InjectMocks UserService userService;

    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    @DisplayName("회원가입 성공")
    void join_success() {
        UserJoinRequest req = new UserJoinRequest();
        req.setEmail("test@test.com");
        req.setName("테스터");
        req.setPassword("pw");
        given(userPort.findByEmail(anyString())).willReturn(Optional.empty());
        given(passwordEncoder.encode(anyString())).willReturn("encoded");
        // when, then
        assertThatCode(() -> userService.join(req)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("회원가입 - 이미 존재")
    void join_existUser() {
        UserJoinRequest req = new UserJoinRequest();
        req.setEmail("test@test.com");
        given(userPort.findByEmail(anyString())).willReturn(Optional.of(mock(User.class)));
        assertThatThrownBy(() -> userService.join(req))
                .isInstanceOf(BaseException.class)
                .hasMessageContaining(ErrorCode.EXIST_USER.getMessage());
    }

    @Test
    @DisplayName("등급 변경 성공")
    void upgradeGrade_success() {
        String userId = "uuid";
        UserGradeUpgradeRequest req = new UserGradeUpgradeRequest();
        req.setUserGrade(UserGrade.PRO);
        given(userPort.findByUserId(anyString())).willReturn(Optional.of(mock(User.class)));
        // when, then
        assertThatCode(() -> userService.upgradeGrade(userId, req)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("등급 변경 - 잘못된 등급")
    void upgradeGrade_invalid() {
        String userId = "uuid";
        UserGradeUpgradeRequest req = new UserGradeUpgradeRequest();
        // 잘못된 등급(null)
        req.setUserGrade(null);
        given(userPort.findByUserId(anyString())).willReturn(Optional.of(mock(User.class)));
        assertThatThrownBy(() -> userService.upgradeGrade(userId, req))
                .isInstanceOf(BaseException.class)
                .hasMessageContaining(ErrorCode.INVALID_GRADE_UPGRADE.getMessage());
    }
} 