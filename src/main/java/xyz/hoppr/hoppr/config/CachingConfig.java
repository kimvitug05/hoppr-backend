package xyz.hoppr.hoppr.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Optional;

@Slf4j
@Configuration
@EnableCaching
public class CachingConfig {

    @Autowired
    private CacheManager cacheManager;

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("sabreDestinations", "yelpBusinesses");
    }

    public void evictAllCaches() {
        cacheManager.getCacheNames().forEach(cacheName ->
                Optional.ofNullable(cacheManager.getCache(cacheName)).ifPresent(Cache::clear));
    }

    @Scheduled(fixedDelay = 12 * 60 * 60 * 1000) // 12 hours
    public void evictAllCachesEveryTwelveHours() {
        log.info("Clearing all caches");
        evictAllCaches();
    }
}
