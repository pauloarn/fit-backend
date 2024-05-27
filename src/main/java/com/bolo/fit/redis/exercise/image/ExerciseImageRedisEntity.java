package com.bolo.fit.redis.exercise.image;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@RedisHash(value = "ExerciseImage", timeToLive = 2592000)
public class ExerciseImageRedisEntity {
    @Id
    private String id;

    private String base64;
}
