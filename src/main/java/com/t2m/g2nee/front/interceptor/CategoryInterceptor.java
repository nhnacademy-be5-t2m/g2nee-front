package com.t2m.g2nee.front.interceptor;

import com.t2m.g2nee.front.category.dto.response.CategoryHierarchyDto;
import com.t2m.g2nee.front.category.service.CategoryService;
import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 카테고리 인터셉터를 통해 카테고리 목록을 계층으로 가져옴
 * 카테고리 목록은 항상 필요하기 때문...
 * 더 좋은 방법이 있으면 알려주세요
 *
 * @author : 김수빈
 * @since : 1.0
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CategoryInterceptor implements HandlerInterceptor {


    @Resource
    private CategoryService service;

    @Resource
    private CacheManager cacheManager;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        Cache cache = cacheManager.getCache("categories");
        if (cache != null) {
            Cache.ValueWrapper rootCache = cache.get("root");
            if (rootCache == null || rootCache.get() == null || rootCache.get().equals(Collections.emptyList())) {
                //캐시에 있는 값이 null이거나 빈 값이면 캐시를 지워 다시 받게 해줌
                cache.evict("root");
                log.info("캐시 삭제");
            }
        }

        //캐시가 없거나 캐시가 지워진 경우에는 항상 새로운 데이터를 불러와 캐시에 저장
        //캐시가 존재하는 경우, 서버에 저장된 캐시를 불러옴
        request.setAttribute("rootCategories", service.getRootCategories());

        return true;
    }
}
