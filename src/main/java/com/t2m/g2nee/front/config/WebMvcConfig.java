package com.t2m.g2nee.front.config;

import com.t2m.g2nee.front.interceptor.CategoryInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(categoryInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/admin/**");
    }

    @Bean
    public CategoryInterceptor categoryInterceptor() {
        return new CategoryInterceptor();
    }
}
