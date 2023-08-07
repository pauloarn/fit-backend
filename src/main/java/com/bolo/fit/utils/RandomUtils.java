package com.bolo.fit.utils;

public class RandomUtils {
    public static Integer selectNumberInRange(Integer maxLimit){
        return (int)Math.floor(Math.random() * maxLimit);
    }
}
