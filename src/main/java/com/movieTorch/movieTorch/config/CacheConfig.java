package com.movieTorch.movieTorch.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    @Primary
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();

        // Set default cache configuration
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(30, TimeUnit.MINUTES)
                .initialCapacity(100)
                .maximumSize(1000)
                .recordStats());

        // Set the cache names
        cacheManager.setCacheNames(Arrays.asList(
                "popularMovies",
                "movieDetails",
                "searchResults",
                "topRated",
                "upcoming",
                "nowPlaying",
                "recommendations",
                "credits",
                "images",
                "similar",
                "reviews",
                "keywords",
                "videos"
        ));

        return cacheManager;
    }
}
