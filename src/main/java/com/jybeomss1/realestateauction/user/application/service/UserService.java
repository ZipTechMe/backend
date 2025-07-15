package com.jybeomss1.realestateauction.user.application.service;


import com.jybeomss1.realestateauction.common.exceptions.BaseException;
import com.jybeomss1.realestateauction.common.exceptions.ErrorCode;
import com.jybeomss1.realestateauction.jwt.JwtTokenProvider;
import com.jybeomss1.realestateauction.user.adapter.out.persistence.RedisRefreshTokenRepository;
import com.jybeomss1.realestateauction.user.application.port.in.UserGradeUpgradeUseCase;
import com.jybeomss1.realestateauction.user.application.port.in.UserJoinUseCase;
import com.jybeomss1.realestateauction.user.application.port.in.UserLoginUseCase;
import com.jybeomss1.realestateauction.user.application.port.in.UserLogoutUseCase;
import com.jybeomss1.realestateauction.user.application.port.out.UserPort;
import com.jybeomss1.realestateauction.user.domain.UserGrade;
import com.jybeomss1.realestateauction.user.domain.dto.CustomUserDetails;
import com.jybeomss1.realestateauction.user.domain.dto.UserGradeUpgradeRequest;
import com.jybeomss1.realestateauction.user.domain.dto.UserJoinRequest;
import com.jybeomss1.realestateauction.user.domain.dto.response.TokenResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserJoinUseCase, UserLoginUseCase, UserLogoutUseCase, UserGradeUpgradeUseCase {
    private final UserPort userPort;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisRefreshTokenRepository redisRefreshTokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void join(UserJoinRequest request) {
        if (userPort.findByEmail(request.getEmail()).isPresent()) {
            log.warn("이미 존재하는 이메일로 회원가입 시도: {}", request.getEmail());
            throw new BaseException(ErrorCode.EXIST_USER);
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        userPort.save(request.getEmail(), request.getName(), encodedPassword);
        log.info("회원가입 성공: {}", request.getEmail());
    }

    @Override
    public TokenResponse login(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = userDetails.getUserId();

        String accessToken = jwtTokenProvider.createAccessToken(userId);
        String refreshToken = jwtTokenProvider.createRefreshToken(userId);

        jwtTokenProvider.saveRefreshToken(userId, refreshToken);
        log.info("로그인 성공: {}", email);
        return new TokenResponse(accessToken, refreshToken);
    }

    @Override
    public void logout(String authorization) {
        String accessToken = jwtTokenProvider.resolveToken(authorization);
        String userId = jwtTokenProvider.getUserId(accessToken);
        String jti = jwtTokenProvider.getJti(authorization);
        Date exp = Jwts.parser()
                .setSigningKey(Decoders.BASE64.decode(jwtTokenProvider.secretKey))
                .build()
                .parseClaimsJws(accessToken)
                .getBody()
                .getExpiration();

        long ttl = exp.getTime() - System.currentTimeMillis();
        if (ttl > 0) {
            redisRefreshTokenRepository.save("blacklist:" + jti, "logout", ttl);
        }

        redisRefreshTokenRepository.delete(userId);
        log.info("로그아웃 성공: {}", userId);
    }

    @Override
    @Transactional
    public void upgradeGrade(String userId, UserGradeUpgradeRequest request) {
        userPort.findByUserId(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));

        // 등급 변경 가능: LITE, PRO
        if (request.getUserGrade() == UserGrade.LITE || request.getUserGrade() == UserGrade.PRO) {
            userPort.updateUserGrade(userId, request.getUserGrade());
            log.info("사용자 등급 변경 성공: userId={}, newGrade={}", userId, request.getUserGrade());
        } else {
            log.warn("유효하지 않은 등급 변경 시도: userId={}, requestedGrade={}", userId, request.getUserGrade());
            throw new BaseException(ErrorCode.INVALID_GRADE_UPGRADE);
        }
    }
}
