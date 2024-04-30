package com.t2m.g2nee.front.config;

import com.t2m.g2nee.front.interceptor.CategoryInterceptor;
import com.t2m.g2nee.front.interceptor.MypageInterceptor;
import com.t2m.g2nee.front.interceptor.AuthorityCheckInterceptor;
import com.t2m.g2nee.front.interceptor.TokenExpireCheckInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final AuthorityCheckInterceptor authorityCheckInterceptor;
    private final CategoryInterceptor categoryInterceptor;
    private final MypageInterceptor mypageInterceptor;
    private final TokenExpireCheckInterceptor tokenExpireCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(categoryInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/static/**")
                .excludePathPatterns("/error");
        registry.addInterceptor(tokenExpireCheckInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/static/**")
                .excludePathPatterns("/error");
        registry.addInterceptor(authorityCheckInterceptor)
                .addPathPatterns("/admin/**");
        registry.addInterceptor(mypageInterceptor)
                .addPathPatterns("/mypage/**")
                .addPathPatterns("/mypage");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
}
