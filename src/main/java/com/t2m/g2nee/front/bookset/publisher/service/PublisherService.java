package com.t2m.g2nee.front.bookset.publisher.service;

import com.t2m.g2nee.front.bookset.publisher.dto.PublisherDto;
import com.t2m.g2nee.front.utils.PageResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 출판사 관리 service 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
@Service
@RequiredArgsConstructor
public class PublisherService {

    private final RestTemplate restTemplate;
    @Value("${g2nee.gateway}")
    String gatewayUrl;

    /**
     * 출판사 등록 메서드
     * @param request 등록할 정보가 있는 객체
     */
    public void registerPublisher(PublisherDto.Request request) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PublisherDto.Request> requestEntity = new HttpEntity<>(request, headers);

        String url = gatewayUrl + "/shop/publishers";

        restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String.class
        );


    }

    /**
     * 출판사 조회 메서드
     *
     * @return List<PublisherDto.Response>
     */
    public List<PublisherDto.Response> getAllPublisher(){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String url = gatewayUrl + "/shop/publishers/list";

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<PublisherDto.Response>>() {
                }
        ).getBody();
    }

    /**
     * 출판사 조회 메서드
     *
     * @param page 페이지 번호
     * @return PageResponse<PublisherDto.Response>
     */
    public PageResponse<PublisherDto.Response> getAllPublisher(int page) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String url = gatewayUrl + "/shop/publishers?page=" + page;

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<PageResponse<PublisherDto.Response>>() {
                }
        ).getBody();
    }

    /**
     * 출판사 수정 메서드
     * @param publisherId 출판사 아이디
     * @param request 수정할 정보가 담긴 객체
     */
    public void updatePublisher(Long publisherId, PublisherDto.Request request) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PublisherDto.Request> requestEntity = new HttpEntity<>(request, headers);

        String url = gatewayUrl + "/shop/publishers/" + publisherId;

        restTemplate.exchange(
                url,
                HttpMethod.PATCH,
                requestEntity,
                String.class
        );

    }

    public void deletePublisher(Long publisherId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String url = gatewayUrl + "/shop/publishers/" + publisherId;

        restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                requestEntity,
                String.class
        );

    }
}
