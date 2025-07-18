package com.jybeomss1.realestateauction.user.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserJoinRequest {
    private String email;
    private String name;
    private String password;
}
