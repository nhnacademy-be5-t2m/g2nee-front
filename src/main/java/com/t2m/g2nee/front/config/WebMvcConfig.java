package com.t2m.g2nee.front.config;

import com.t2m.g2nee.front.interceptor.CategoryInterceptor;
import com.t2m.g2nee.front.interceptor.TokenCheckInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final TokenCheckInterceptor tokenCheckInterceptor;
    private final CategoryInterceptor categoryInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(categoryInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/static/**")
                .excludePathPatterns("/error")
                .excludePathPatterns("/orders/payments/**");
        registry.addInterceptor(tokenCheckInterceptor)
                .addPathPatterns("/mypage/**")
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/static/**")
                .excludePathPatterns("/error");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
}
