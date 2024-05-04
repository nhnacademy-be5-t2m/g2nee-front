package com.t2m.g2nee.front.policyset.pointPolicy.adaptor.impl;

import static com.t2m.g2nee.front.utils.HttpHeadersUtil.makeHttpHeaders;

import com.t2m.g2nee.front.exception.CustomException;
import com.t2m.g2nee.front.policyset.pointPolicy.adaptor.PointPolicyAdaptor;
import com.t2m.g2nee.front.policyset.pointPolicy.dto.request.PointPolicySaveDto;
import com.t2m.g2nee.front.policyset.pointPolicy.dto.response.PointPolicyInfoDto;
import com.t2m.g2nee.front.utils.PageResponse;
import java.util.Optional;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * PointPolicyAdaptor의 구현체 입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
@Component
public class PointPolicyAdaptorImpl implements PointPolicyAdaptor {

    private final RestTemplate restTemplate;

    @Value("${gatewayToShopUrl}")
    private String gateway;

    private String baseUrl;

    //페이징 처리를 위한 ref
    private static final ParameterizedTypeReference<PageResponse<PointPolicyInfoDto>> PAGE_TYPE_REF
            = new ParameterizedTypeReference<>() {
    };

    public PointPolicyAdaptorImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    public void initUrl() {
        baseUrl = gateway + "/pointPolicies";
    }

    @Override
    public PointPolicyInfoDto requestCreatePointPolicy(PointPolicySaveDto request) {
        HttpEntity<PointPolicySaveDto> entity = new HttpEntity<>(request, makeHttpHeaders());
        ResponseEntity<PointPolicyInfoDto> response =
                restTemplate.exchange(baseUrl, HttpMethod.POST, entity, PointPolicyInfoDto.class);

        return response.getBody();
    }

    @Override
    public PointPolicyInfoDto requestUpdatePointPolicy(Long pointPolicyId, PointPolicySaveDto request) {
        UriComponents url = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/" + pointPolicyId)
                .build();

        HttpEntity<PointPolicySaveDto> entity = new HttpEntity<>(request, makeHttpHeaders());
        ResponseEntity<PointPolicyInfoDto> response =
                restTemplate.exchange(url.toUriString(), HttpMethod.PUT, entity, PointPolicyInfoDto.class);

        return response.getBody();
    }

    @Override
    public boolean requestDeletePointPolicy(Long pointPolicyId) {
        UriComponents url = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/" + pointPolicyId)
                .build();

        HttpEntity<String> entity = new HttpEntity<>(makeHttpHeaders());
        ResponseEntity<Boolean> response =
                restTemplate.exchange(url.toUriString(), HttpMethod.PATCH, entity, Boolean.class);

        return Optional.ofNullable(response.getBody())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "삭제할 포인트 정책을 찾을 수 없습니다."));
    }

    @Override
    public PointPolicyInfoDto getPointPolicy(Long pointPolicyId) {
        UriComponents url = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/" + pointPolicyId)
                .build();

        HttpEntity<String> entity = new HttpEntity<>(makeHttpHeaders());
        ResponseEntity<PointPolicyInfoDto> response =
                restTemplate.exchange(url.toUriString(), HttpMethod.GET, entity, PointPolicyInfoDto.class);

        return response.getBody();
    }

    @Override
    public PageResponse<PointPolicyInfoDto> getAllPointPolicies(int page) {
        UriComponents url = UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("page", page)
                .build();

        HttpEntity<String> entity = new HttpEntity<>(makeHttpHeaders());
        ResponseEntity<PageResponse<PointPolicyInfoDto>> response =
                restTemplate.exchange(url.toUriString(), HttpMethod.GET, entity, PAGE_TYPE_REF);

        return response.getBody();
    }

    @Override
    public PointPolicyInfoDto getPointPolicyByPolicyName(String policyName) {
        UriComponents url = UriComponentsBuilder.fromUriString(baseUrl+"/getByPolicyName")
                .queryParam("policyName", policyName)
                .build();

        ResponseEntity<PointPolicyInfoDto> response =
                restTemplate.exchange(url.toUriString(), HttpMethod.GET, new HttpEntity<>(makeHttpHeaders()), PointPolicyInfoDto.class);

        return response.getBody();
    }

}
