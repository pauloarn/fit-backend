package com.bolo.fit.service;

import com.bolo.fit.redis.exercise.image.ExerciseImageRedisService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Base64;

@RequiredArgsConstructor
@Log4j2
@Service
public class ExerciseImageService {
    private final ExerciseImageRedisService exerciseImageRedisService;

    public String getExerciseImage(String imgUrl) {
        log.info("Searching exercise image");

        var imgCached = exerciseImageRedisService.getCacheById(imgUrl);

        if (imgCached != null) return imgCached.getBase64();

        return getExerciseImageFromExternalServer(imgUrl);
    }

    @Nullable
    private String getExerciseImageFromExternalServer(String imgUrl) {
        log.info("Getting img from {}", imgUrl);
        try {
            var url = new URL(imgUrl);
            var imgByte = IOUtils.toByteArray(url);
            var imgBase64 = Base64.getEncoder().encodeToString(imgByte);
            exerciseImageRedisService.saveCacheExerciseGif(imgUrl, imgBase64);

            return imgBase64;
        } catch (Exception ex) {
            return null;
        }
    }
}
