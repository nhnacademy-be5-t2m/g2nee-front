package com.t2m.g2nee.front.interceptor;

import com.t2m.g2nee.front.category.service.CategoryService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.servlet.HandlerInterceptor;

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
            if (rootCache != null) {//캐시가 있을 때: 세션에 있는 값을 그대로 사용
                return true;
            }
        }

        // 캐시가 없거나 캐시가 지워진 경우에는 항상 새로운 데이터를 불러옴
        log.info("카테고리 계층 가져오기");
        HttpSession session = request.getSession();
        session.setAttribute("rootCategories", service.getRootCategories());

        return true;
    }
}
