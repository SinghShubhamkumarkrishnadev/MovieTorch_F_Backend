package com.movieTorch.movieTorch.controller;

import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/cache")
public class CacheController {

    private final CacheManager cacheManager;

    public CacheController(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getCacheInfo() {
        Map<String, Object> cacheInfo = new HashMap<>();

        cacheManager.getCacheNames().forEach(cacheName -> {
            org.springframework.cache.Cache springCache = cacheManager.getCache(cacheName);
            if (springCache instanceof CaffeineCache) {
                CaffeineCache caffeineCache = (CaffeineCache) springCache;
                Cache<Object, Object> nativeCache = caffeineCache.getNativeCache();

                Map<String, Object> cacheStats = new HashMap<>();
                cacheStats.put("size", nativeCache.estimatedSize());
                if (nativeCache.stats() != null) {
                    cacheStats.put("hitCount", nativeCache.stats().hitCount());
                    cacheStats.put("missCount", nativeCache.stats().missCount());
                    cacheStats.put("hitRate", nativeCache.stats().hitRate());
                    cacheStats.put("evictionCount", nativeCache.stats().evictionCount());
                }

                cacheInfo.put(cacheName, cacheStats);
            } else {
                cacheInfo.put(cacheName, "Available (not a Caffeine cache)");
            }
        });

        return ResponseEntity.ok(cacheInfo);
    }

    @GetMapping("/{cacheName}")
    public ResponseEntity<?> getCacheDetails(@PathVariable String cacheName) {
        if (!cacheManager.getCacheNames().contains(cacheName)) {
            return ResponseEntity.notFound().build();
        }

        org.springframework.cache.Cache springCache = cacheManager.getCache(cacheName);
        if (springCache == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, Object> cacheStats = new HashMap<>();
        cacheStats.put("name", cacheName);

        if (springCache instanceof CaffeineCache) {
            CaffeineCache caffeineCache = (CaffeineCache) springCache;
            Cache<Object, Object> nativeCache = caffeineCache.getNativeCache();

            cacheStats.put("size", nativeCache.estimatedSize());
            if (nativeCache.stats() != null) {
                cacheStats.put("hitCount", nativeCache.stats().hitCount());
                cacheStats.put("missCount", nativeCache.stats().missCount());
                cacheStats.put("hitRate", String.format("%.2f%%", nativeCache.stats().hitRate() * 100));
                cacheStats.put("evictionCount", nativeCache.stats().evictionCount());
                cacheStats.put("averageLoadPenalty", nativeCache.stats().averageLoadPenalty());
            }
        } else {
            cacheStats.put("type", "Not a Caffeine cache");
        }

        return ResponseEntity.ok(cacheStats);
    }

    @DeleteMapping("/{cacheName}")
    public ResponseEntity<Map<String, String>> clearCache(@PathVariable String cacheName) {
        Map<String, String> response = new HashMap<>();

        if (cacheManager.getCache(cacheName) != null) {
            cacheManager.getCache(cacheName).clear();
            response.put("status", "Cache '" + cacheName + "' cleared successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Cache '" + cacheName + "' not found");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping
    public ResponseEntity<Map<String, String>> clearAllCaches() {
        cacheManager.getCacheNames().forEach(cacheName ->
                cacheManager.getCache(cacheName).clear());

        Map<String, String> response = new HashMap<>();
        response.put("status", "All caches cleared successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getCacheSummary() {
        long totalSize = 0;
        long totalHits = 0;
        long totalMisses = 0;
        long totalEvictions = 0;

        Map<String, Long> cacheSizes = new HashMap<>();

        for (String cacheName : cacheManager.getCacheNames()) {
            org.springframework.cache.Cache springCache = cacheManager.getCache(cacheName);
            if (springCache instanceof CaffeineCache) {
                CaffeineCache caffeineCache = (CaffeineCache) springCache;
                Cache<Object, Object> nativeCache = caffeineCache.getNativeCache();

                long size = nativeCache.estimatedSize();
                totalSize += size;

                if (nativeCache.stats() != null) {
                    totalHits += nativeCache.stats().hitCount();
                    totalMisses += nativeCache.stats().missCount();
                    totalEvictions += nativeCache.stats().evictionCount();
                }

                cacheSizes.put(cacheName, size);
            }
        }

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalCaches", cacheManager.getCacheNames().size());
        summary.put("totalSize", totalSize);
        summary.put("totalHits", totalHits);
        summary.put("totalMisses", totalMisses);
        summary.put("overallHitRate", totalHits + totalMisses > 0 ?
                String.format("%.2f%%", (double) totalHits / (totalHits + totalMisses) * 100) : "N/A");
        summary.put("totalEvictions", totalEvictions);
        summary.put("cacheSizes", cacheSizes);

        return ResponseEntity.ok(summary);
    }
}
