package com.t2m.g2nee.front.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteLocatorConfig {
    @Bean
    public RouteLocator goGateway(RouteLocatorBuilder builder) {
        return builder.routes()
                //hello 요청이 오면 lb(load balancing) //g2nee-shop으로 로드벨런싱
                //기본값 50:50
                .route("g2nee-gateway",
                        p -> p.path("/**").and()
                                .uri("lb://G2NEE-GATEWAY/")
                )
                .build();
    }
}
