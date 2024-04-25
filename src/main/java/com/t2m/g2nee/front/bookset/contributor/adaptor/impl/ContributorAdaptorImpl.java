package com.t2m.g2nee.front.bookset.contributor.adaptor.impl;

import com.t2m.g2nee.front.bookset.contributor.adaptor.ContributorAdaptor;
import com.t2m.g2nee.front.bookset.contributor.dto.ContributorDto;
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
 * 기여자 adaptor 구현 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
@RequiredArgsConstructor
@Component
public class ContributorAdaptorImpl implements ContributorAdaptor {

    private final RestTemplate restTemplate;
    @Value("${g2nee.gateway}")
    String gatewayUrl;

    @Override
    public List<ContributorDto.Response> getAllContributor() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String url = gatewayUrl + "/contributors/list";

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<ContributorDto.Response>>() {
                }
        ).getBody();
    }

    /**
     * 기여자 등록 메서드
     *
     * @param request 등록할 정보가 있는 객체
     */
    @Override
    public void registerContributor(ContributorDto.Request request) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ContributorDto.Request> requestEntity = new HttpEntity<>(request, headers);

        String url = gatewayUrl + "/contributors";

        restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

    }

    /**
     * 기여자 조회 메서드
     *
     * @param page 기여자 번호
     *             PageResponse<ContributorDto.Response>
     */
    @Override
    public PageResponse<ContributorDto.Response> getAllContributor(int page) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String url = gatewayUrl + "/contributors?page=" + page;

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<PageResponse<ContributorDto.Response>>() {
                }
        ).getBody();
    }

    /**
     * 기여자 수정 메서드
     *
     * @param contributorId 태그 아이디
     * @param request       수정할 정보가 담긴 객체
     */
    @Override
    public void updateContributor(Long contributorId, ContributorDto.Request request) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ContributorDto.Request> requestEntity = new HttpEntity<>(request, headers);

        String url = gatewayUrl + "/contributors/" + contributorId;

        restTemplate.exchange(
                url,
                HttpMethod.PATCH,
                requestEntity,
                String.class
        );

    }

    /**
     * 역할 삭제 메서드
     *
     * @param contributorId 태그 아이디
     */
    @Override
    public void deleteContributor(Long contributorId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String url = gatewayUrl + "/contributors/" + contributorId;

        restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                requestEntity,
                String.class
        );

    }
}
