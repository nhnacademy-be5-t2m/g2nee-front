package com.t2m.g2nee.front.interceptor;

import com.t2m.g2nee.front.category.dto.response.CategoryInfoDto;
import com.t2m.g2nee.front.category.service.CategoryService;
import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class CategoryInterceptor implements HandlerInterceptor {


    @Resource
    private CategoryService service;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        if(Objects.isNull(request.getAttribute("rootCategories"))) {
            List<CategoryInfoDto> rootCategories = service.getRootCategories();
            request.setAttribute("rootCategories", rootCategories);

            for (CategoryInfoDto rootCategory : rootCategories) {
                List<CategoryInfoDto> subCategories = service.getSubCategories(rootCategory.getCategoryId());
                request.setAttribute("subCategories" + rootCategory.getCategoryId(), subCategories);
            }
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
