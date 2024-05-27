package com.bolo.fit.events;

import com.bolo.fit.service.ExerciseService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class FeedCacheEventListener implements ApplicationListener<FeedCacheEvent> {

    @Autowired
    ExerciseService exerciseService;

    @Override
    public void onApplicationEvent(FeedCacheEvent event) {
        log.info("Starting all image caching");
        try{
            exerciseService.feedCache();
            log.info("Finishing all image caching");
        }catch (Error e){
            log.info("Erro ao carregar cache de imagens. " + e.getMessage());
        }
    }
}
