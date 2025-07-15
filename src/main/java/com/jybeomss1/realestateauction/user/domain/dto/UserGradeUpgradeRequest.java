package com.jybeomss1.realestateauction.user.domain.dto;

import com.jybeomss1.realestateauction.user.domain.UserGrade;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserGradeUpgradeRequest {
    private UserGrade userGrade;
} 