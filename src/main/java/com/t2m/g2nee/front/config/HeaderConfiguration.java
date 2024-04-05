package com.t2m.g2nee.front.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

public class HeaderConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("Content-Type", "application/json");
            requestTemplate.header("Accept", "application/json");
        };
    }
}
