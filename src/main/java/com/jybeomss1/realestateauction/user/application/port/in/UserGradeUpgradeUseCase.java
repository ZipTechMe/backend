package com.jybeomss1.realestateauction.user.application.port.in;

import com.jybeomss1.realestateauction.user.domain.dto.UserGradeUpgradeRequest;

public interface UserGradeUpgradeUseCase {
    void upgradeGrade(String userId, UserGradeUpgradeRequest request);
} 