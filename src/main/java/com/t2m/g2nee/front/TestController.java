package com.t2m.g2nee.front;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class TestController {

    RestTemplate restTemplate;
    ObjectMapper objectMapper;

    @Value("${gatewayToShopUrl}")
    String gatewayUrl;

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
                gatewayUrl + "/hello",
                HttpMethod.GET,
                null,
                String.class
        );

    }
}