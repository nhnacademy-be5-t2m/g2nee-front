package com.t2m.g2nee.front.booklike.adaptor.impl;

import com.t2m.g2nee.front.booklike.adaptor.BookLikeAdaptor;
import com.t2m.g2nee.front.booklike.dto.BookLikeDto;
import java.net.URLDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class BookLikeAdaptorImpl implements BookLikeAdaptor {

    private final RestTemplate restTemplate;
    @Value("${g2nee.gateway}")
    String gatewayUrl;

    @Override
    public void setLike(BookLikeDto request) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<BookLikeDto> requestEntity = new HttpEntity<>(request,headers);

        String url = gatewayUrl + "/likes";


        restTemplate.exchange(
                url,
                HttpMethod.PUT,
                requestEntity,
                String.class
        );
    }
}
