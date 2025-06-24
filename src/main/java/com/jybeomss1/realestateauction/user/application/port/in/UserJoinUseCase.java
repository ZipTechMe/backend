package com.jybeomss1.realestateauction.user.application.port.in;

import com.jybeomss1.realestateauction.user.domain.dto.UserJoinRequest;

public interface UserJoinUseCase {
    void join(UserJoinRequest request);
}
