package com.t2m.g2nee.front.config;

import com.t2m.g2nee.front.exception.CustomExceptionHandler;
import java.time.Duration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    private final CustomExceptionHandler customExceptionHandler;

    public RestTemplateConfig(CustomExceptionHandler customExceptionHandler) {
        this.customExceptionHandler = customExceptionHandler;
    }

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        RestTemplate restTemplate = builder
                .setConnectTimeout(Duration.ofSeconds(5L))
                .setReadTimeout(Duration.ofSeconds(5L))
                .build();

        //예외처리를 위한 핸들러 등록
        restTemplate.setErrorHandler(customExceptionHandler);

        return restTemplate;
    }

}
