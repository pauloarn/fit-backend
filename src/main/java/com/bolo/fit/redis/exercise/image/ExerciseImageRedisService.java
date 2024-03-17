package com.bolo.fit.redis.exercise.image;

import com.bolo.fit.redis.AbstractRedisService;
import jakarta.annotation.Nullable;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class ExerciseImageRedisService extends AbstractRedisService<ExerciseImageRedisRepository> {
    public ExerciseImageRedisService(ExerciseImageRedisRepository redisRepository) {
        super(redisRepository);
    }

    @Nullable
    public ExerciseImageRedisEntity getCacheById(String imgUrl) {
        log.info("Searching in cache img: {}", imgUrl);
        var imgOp = repository.findById(imgUrl);

        if (imgOp.isEmpty()) {
            log.info("Image not cached yet");
            return null;
        }

        log.info("Retrieved image cached");

        return imgOp.get();
    }

    public void saveCacheExerciseGif(String imgUrl, String base64) {
        log.info("Saving img in cache: {}", imgUrl);
        var entity = new ExerciseImageRedisEntity();
        entity.setId(imgUrl);
        entity.setBase64(base64);
        this.repository.save(entity);
    }
}
