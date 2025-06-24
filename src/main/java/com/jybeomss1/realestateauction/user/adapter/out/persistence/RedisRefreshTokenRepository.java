package com.jybeomss1.realestateauction.user.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RedisRefreshTokenRepository {
    private final RedisTemplate<String, String> redisTemplate;
    private static final String REDIS_REFRESH_TOKEN = "REDIS_REFRESH_TOKEN";

    public void save(String userId, String token, long ttlMillis) {
        String key = REDIS_REFRESH_TOKEN + userId;
        redisTemplate.opsForValue().set(key, token, ttlMillis, TimeUnit.MILLISECONDS);
    }

    public boolean isValid(String userId, String refreshToken) {
        String stored = redisTemplate.opsForValue().get(REDIS_REFRESH_TOKEN + userId);
        return stored != null && stored.equals(refreshToken);
    }

    public void delete(String userId) {
        redisTemplate.delete(REDIS_REFRESH_TOKEN + userId);
    }

    public boolean getKey(String blackListKey) {
        return redisTemplate.hasKey(blackListKey);
    }
}
