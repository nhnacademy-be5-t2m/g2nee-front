package com.t2m.g2nee.front.booklike.adaptor.impl;

import com.t2m.g2nee.front.booklike.adaptor.BookLikeAdaptor;
import com.t2m.g2nee.front.booklike.dto.BookLikeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
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
    public BookLikeDto setLike(BookLikeDto request) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<BookLikeDto> requestEntity = new HttpEntity<>(request, headers);

        String url = gatewayUrl + "/likes";


        return restTemplate.exchange(
                url,
                HttpMethod.PUT,
                requestEntity,
                new ParameterizedTypeReference<BookLikeDto>() {
                }
        ).getBody();
    }

    /**
     * 회원 좋아요 개수를 조회하는 메서드
     *
     * @param memberId 회원아이디
     * @return Long
     */
    @Override
    public Long getMemberLikesNum(Long memberId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String url = gatewayUrl + "/likes/member/" + memberId;


        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                Long.class
        ).getBody();
    }
}
