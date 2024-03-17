package com.bolo.fit.redis;

import org.springframework.data.repository.CrudRepository;

public abstract class AbstractRedisService<R extends CrudRepository> {
    protected R repository;

    protected AbstractRedisService(R redisRepository) {
        this.repository = redisRepository;
    }
}
