package com.movieTorch.movieTorch.service;

import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

    private final CacheManager cacheManager;

    public CacheService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    /**
     * Clears a specific cache
     * @param cacheName the name of the cache to clear
     * @return true if cache was found and cleared, false otherwise
     */
    public boolean clearCache(String cacheName) {
        if (cacheManager.getCache(cacheName) != null) {
            cacheManager.getCache(cacheName).clear();
            return true;
        }
        return false;
    }

    /**
     * Clears all caches
     */
    public void clearAllCaches() {
        cacheManager.getCacheNames().forEach(cacheName ->
                cacheManager.getCache(cacheName).clear());
    }

    /**
     * Evicts a specific key from a cache
     * @param cacheName the name of the cache
     * @param key the key to evict
     * @return true if cache was found and key evicted, false otherwise
     */
    public boolean evictCacheEntry(String cacheName, Object key) {
        if (cacheManager.getCache(cacheName) != null) {
            cacheManager.getCache(cacheName).evict(key);
            return true;
        }
        return false;
    }
}
