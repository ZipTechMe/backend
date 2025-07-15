package com.jybeomss1.realestateauction.user.adapter.in.web;


import com.jybeomss1.realestateauction.common.annotation.UserGradeUpgradeSwaggerDoc;
import com.jybeomss1.realestateauction.common.annotation.UserJoinSwaggerDoc;
import com.jybeomss1.realestateauction.common.annotation.UserLoginSwaggerDoc;
import com.jybeomss1.realestateauction.user.application.port.in.UserGradeUpgradeUseCase;
import com.jybeomss1.realestateauction.user.application.port.in.UserJoinUseCase;
import com.jybeomss1.realestateauction.user.application.port.in.UserLoginUseCase;
import com.jybeomss1.realestateauction.user.application.port.in.UserLogoutUseCase;
import com.jybeomss1.realestateauction.user.domain.dto.CustomUserDetails;
import com.jybeomss1.realestateauction.user.domain.dto.UserGradeUpgradeRequest;
import com.jybeomss1.realestateauction.user.domain.dto.UserJoinRequest;
import com.jybeomss1.realestateauction.user.domain.dto.UserLoginRequest;
import com.jybeomss1.realestateauction.user.domain.dto.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserJoinUseCase userJoinUseCase;
    private final UserLoginUseCase userLoginUseCase;
    private final UserLogoutUseCase userLogoutUseCase;
    private final UserGradeUpgradeUseCase userGradeUpgradeUseCase;

    @PostMapping("/join")
    @UserJoinSwaggerDoc
    public ResponseEntity<String> join(@RequestBody UserJoinRequest request) {
        userJoinUseCase.join(request);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/login")
    @UserLoginSwaggerDoc
    public ResponseEntity<TokenResponse> login(@RequestBody UserLoginRequest request) {
        return ResponseEntity.ok(userLoginUseCase.login(request.getEmail(), request.getPassword()));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestHeader("Authorization") String authorization
    ) {
        userLogoutUseCase.logout(authorization);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/grade/upgrade")
    @UserGradeUpgradeSwaggerDoc
    public ResponseEntity<String> upgradeGrade(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody UserGradeUpgradeRequest request
    ) {
        userGradeUpgradeUseCase.upgradeGrade(userDetails.getUserId(), request);
        return ResponseEntity.ok("success");
    }
}
