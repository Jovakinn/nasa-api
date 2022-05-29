package com.example.coolapp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@Slf4j
public class CoolAppApplication {
    public static final int TIME_TO_REMOVE_CACHE = 5 * 60 * 1000;

    public static void main(String[] args) {
        SpringApplication.run(CoolAppApplication.class, args);
    }

    @Scheduled(fixedDelay = TIME_TO_REMOVE_CACHE)
    @CacheEvict("largestPicture")
    public void refreshCache() {
        log.info("Removing NASA cache...");
    }
}
