package com.bolo.fit.events;

import org.springframework.context.ApplicationEvent;

public class FeedCacheEvent extends ApplicationEvent {

    public FeedCacheEvent(Object source) {
        super(source);
    }
}
