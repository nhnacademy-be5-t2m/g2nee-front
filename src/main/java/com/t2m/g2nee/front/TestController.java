package com.t2m.g2nee.front;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

@RestController
public class TestController {

    RestTemplate restTemplate;
    ObjectMapper objectMapper;

    @Autowired
    public TestController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = new RestTemplateBuilder()
                .requestFactory(() -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
                .setConnectTimeout(Duration.ofMillis(5L * 1000))
                .setReadTimeout(Duration.ofMillis(5L * 1000))
                .additionalMessageConverters(new StringHttpMessageConverter(StandardCharsets.UTF_8))
                .build();
        objectMapper = new ObjectMapper();
    }

    @GetMapping("/test")
    public ResponseEntity<String> goToShopTest() {
        return restTemplate.exchange(
                "http://133.186.208.183:8080/shop/hello",
                HttpMethod.GET,
                null,
                String.class
        );

    }
}