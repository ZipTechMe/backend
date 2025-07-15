package com.jybeomss1.realestateauction.user.application.port.out;


import com.jybeomss1.realestateauction.user.domain.User;
import com.jybeomss1.realestateauction.user.domain.UserGrade;

import java.util.Optional;

public interface UserPort {
    Optional<User> findByEmail(String email);
    Optional<User> findByUserId(String userId);
    void save(String email, String name, String password);
    void updateUserGrade(String userId, UserGrade userGrade);
}
