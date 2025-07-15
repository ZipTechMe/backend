package com.jybeomss1.realestateauction.user.adapter.out.persistence;

import com.jybeomss1.realestateauction.common.exceptions.BaseException;
import com.jybeomss1.realestateauction.common.exceptions.ErrorCode;
import com.jybeomss1.realestateauction.user.application.port.out.UserPort;
import com.jybeomss1.realestateauction.user.domain.User;
import com.jybeomss1.realestateauction.user.domain.UserGrade;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserJpaAdapter implements UserPort {
    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<UserJpaEntity> userJpaEntity = userJpaRepository.findByEmail(email);
        return userJpaEntity.map(UserJpaEntity::toDto);
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        Optional<UserJpaEntity> userJpaEntity = userJpaRepository.findById(UUID.fromString(userId));
        return userJpaEntity.map(UserJpaEntity::toDto);
    }

    @Override
    public void save(String email, String name, String password) {
        userJpaRepository.save(new UserJpaEntity(email, name, password));
    }

    @Override
    public void updateUserGrade(String userId, UserGrade userGrade) {
        UserJpaEntity userJpaEntity = userJpaRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));
        userJpaEntity.upgradeGrade(userGrade);
        userJpaRepository.save(userJpaEntity);
    }
}
