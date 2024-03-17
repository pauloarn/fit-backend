package com.bolo.fit.redis.exercise.image;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseImageRedisRepository extends CrudRepository<ExerciseImageRedisEntity, String> {
}
