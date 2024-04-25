package com.t2m.g2nee.front.policyset.deliveryPolicy.adaptor.impl;


import static com.t2m.g2nee.front.utils.HttpHeadersUtil.makeHttpHeaders;

import com.t2m.g2nee.front.policyset.deliveryPolicy.adaptor.DeliveryPolicyAdaptor;
import com.t2m.g2nee.front.policyset.deliveryPolicy.dto.request.DeliveryPolicySaveDto;
import com.t2m.g2nee.front.policyset.deliveryPolicy.dto.response.DeliveryPolicyInfoDto;
import com.t2m.g2nee.front.utils.PageResponse;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * DeliveryPolicyAdaptor의 구현체 입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
@Component
public class DeliveryPolicyAdaptorImpl implements DeliveryPolicyAdaptor {

    private final RestTemplate restTemplate;

    @Value("${gatewayToShopUrl}")
    private String gateway;

    private String baseUrl;

    //페이징 처리를 위한 ref
    private static final ParameterizedTypeReference<PageResponse<DeliveryPolicyInfoDto>> PAGE_TYPE_REF
            = new ParameterizedTypeReference<>() {
    };

    public DeliveryPolicyAdaptorImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    public void initUrl() {
        baseUrl = gateway + "/deliveryPolicies";
    }

    @Override
    public DeliveryPolicyInfoDto requestCreateDeliveryPolicy(DeliveryPolicySaveDto request) {
        HttpEntity<DeliveryPolicySaveDto> entity = new HttpEntity<>(request, makeHttpHeaders());
        ResponseEntity<DeliveryPolicyInfoDto> response =
                restTemplate.exchange(baseUrl, HttpMethod.POST, entity, DeliveryPolicyInfoDto.class);

        return response.getBody();
    }

    @Override
    public DeliveryPolicyInfoDto getDeliveryPolicy() {
        UriComponents url = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/recentPolicy")
                .build();

        HttpEntity<String> entity = new HttpEntity<>(makeHttpHeaders());
        ResponseEntity<DeliveryPolicyInfoDto> response =
                restTemplate.exchange(url.toUriString(), HttpMethod.GET, entity, DeliveryPolicyInfoDto.class);

        return response.getBody();
    }

    @Override
    public PageResponse<DeliveryPolicyInfoDto> getAllDeliveryPolicies(int page) {
        UriComponents url = UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("page", page)
                .build();

        HttpEntity<String> entity = new HttpEntity<>(makeHttpHeaders());
        ResponseEntity<PageResponse<DeliveryPolicyInfoDto>> response =
                restTemplate.exchange(url.toUriString(), HttpMethod.GET, entity, PAGE_TYPE_REF);

        return response.getBody();
    }
}
