package com.t2m.g2nee.front.config;

import java.util.List;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 캐싱을 위한 설정
 *
 * @author : 김수빈
 * @since : 1.0
 */
@EnableCaching
@Configuration
public class CachingConfig {
    @Bean
    public CacheManager cacheManager() {
        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager();
        cacheManager.setAllowNullValues(false);//null의 경우 캐시에 저장하지 않음
        cacheManager.setCacheNames(List.of("categories"));//캐시 이름 설정
        return cacheManager;
    }
}
