package com.t2m.g2nee.front.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.HiddenHttpMethodFilter;

/**
 * configuration 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
@Configuration
public class JavaConfig {
    
    /**
     * ObjectMapper 빈 등록 메소드.
     *
     * @return ObjectMapper.
     */
    @Bean
    public ObjectMapper objectMapper() {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }


    @Bean
    public RestTemplate restTemplate() {

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        requestFactory.setConnectTimeout(5000);
        requestFactory.setReadTimeout(5000);

        return restTemplate;
    }

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {

        return new HiddenHttpMethodFilter();
    }
}
