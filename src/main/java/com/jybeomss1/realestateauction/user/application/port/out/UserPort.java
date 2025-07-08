package com.jybeomss1.realestateauction.user.application.port.out;


import com.jybeomss1.realestateauction.user.domain.User;

import java.util.Optional;

public interface UserPort {
    Optional<User> findByEmail(String email);
    Optional<User> findByUserId(String userId);
    void save(String email, String name, String password);
}
