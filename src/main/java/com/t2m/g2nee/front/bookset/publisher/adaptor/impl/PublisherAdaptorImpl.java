package com.t2m.g2nee.front.bookset.publisher.adaptor.impl;

import com.t2m.g2nee.front.bookset.publisher.adaptor.PublisherAdaptor;
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
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


/**
 * 출판사 adaptor 구현 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
@RequiredArgsConstructor
@Component
public class PublisherAdaptorImpl implements PublisherAdaptor {

    private final RestTemplate restTemplate;
    @Value("${g2nee.gateway}")
    String gatewayUrl;

    @Override
    public void registerPublisher(PublisherDto.Request request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PublisherDto.Request> requestEntity = new HttpEntity<>(request, headers);

        String url = gatewayUrl + "/publishers";

        restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String.class
        );
    }

    @Override
    public List<PublisherDto.Response> getAllPublisher() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String url = gatewayUrl + "/publishers/list";

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<PublisherDto.Response>>() {
                }
        ).getBody();
    }

    @Override
    public PageResponse<PublisherDto.Response> getAllPublisher(int page) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String url = gatewayUrl + "/publishers?page=" + page;

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<PageResponse<PublisherDto.Response>>() {
                }
        ).getBody();
    }

    @Override
    public void updatePublisher(Long publisherId, PublisherDto.Request request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PublisherDto.Request> requestEntity = new HttpEntity<>(request, headers);

        String url = gatewayUrl + "/publishers/" + publisherId;

        restTemplate.exchange(
                url,
                HttpMethod.PATCH,
                requestEntity,
                String.class
        );
    }

    @Override
    public void deletePublisher(Long publisherId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String url = gatewayUrl + "/publishers/" + publisherId;

        restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                requestEntity,
                String.class
        );
    }
}
