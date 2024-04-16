package com.t2m.g2nee.front.bookset.contributor.service;

import com.t2m.g2nee.front.bookset.contributor.dto.ContributorDto;
import com.t2m.g2nee.front.bookset.tag.dto.TagDto;
import com.t2m.g2nee.front.pageUtils.PageResponse;
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

@Service
@RequiredArgsConstructor
public class ContributorService {

    private final RestTemplate restTemplate;
    @Value("${g2nee.gateway}")
    String gatewayUrl;

    public List<ContributorDto.Response> getAllContributor() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String url = gatewayUrl + "shop/contributors/list";

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
     * @param request 등록할 정보가 있는 객체
     */
    public void registerContributor(ContributorDto.Request request) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ContributorDto.Request> requestEntity = new HttpEntity<>(request, headers);

        String url = gatewayUrl + "shop/contributors";

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
     * PageResponse<ContributorDto.Response>
     */
    public PageResponse<ContributorDto.Response> getAllContributor(int page) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String url = gatewayUrl + "shop/contributors?page=" + page;

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
     * @param contributorId 태그 아이디
     * @param request 수정할 정보가 담긴 객체
     */
    public void updateContributor(Long contributorId, ContributorDto.Request request) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ContributorDto.Request> requestEntity = new HttpEntity<>(request, headers);

        String url = gatewayUrl + "shop/contributors/" + contributorId;

        restTemplate.exchange(
                url,
                HttpMethod.PATCH,
                requestEntity,
                String.class
        );

    }

    /**
     * 역할 삭제 메서드
     * @param contributorId 태그 아이디
     */
    public void deleteContributor(Long contributorId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String url = gatewayUrl + "shop/contributors/" + contributorId;

        restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                requestEntity,
                String.class
        );

    }
}
