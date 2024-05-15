package com.t2m.g2nee.front.point.adaptor.impl;

import com.t2m.g2nee.front.point.adaptor.PointAdaptor;
import com.t2m.g2nee.front.point.dto.PointResponseDto;
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
 * 포인트 adaptorimpl 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
@Component
@RequiredArgsConstructor
public class PointAdaptorImpl implements PointAdaptor {

    private final RestTemplate restTemplate;
    @Value("${g2nee.gateway}")
    String gatewayUrl;

    @Override
    public PageResponse<PointResponseDto> getMemberPointDetail(Long memberId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String url = gatewayUrl + "/point/member/" + memberId;

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<PageResponse<PointResponseDto>>() {
                }
        ).getBody();
    }
}
