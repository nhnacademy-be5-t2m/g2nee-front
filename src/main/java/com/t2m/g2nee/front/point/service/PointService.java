package com.t2m.g2nee.front.point.service;

import static com.t2m.g2nee.front.utils.HttpHeadersUtil.makeHttpHeaders;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * point에 필요한 service
 *
 * @author : 정지은
 * @since : 1.0
 */
@Service
@RequiredArgsConstructor
public class PointService {
    private final RestTemplate restTemplate;

    @Value("${gatewayToShopUrl}")
    String gatewayToShopUrl;

    /**
     * meberId의 포인트 합계를 불러오는 메소드
     *
     * @param memberId 포인트 정보를 불러올 memberId
     * @return member의 포인트 합계
     */
    public Integer getTotalPoint(Long memberId){
        return restTemplate.exchange(
                gatewayToShopUrl + "/point/totalAmount/"+memberId,
                HttpMethod.GET,
                new HttpEntity<>(makeHttpHeaders()),
                Integer.class
        ).getBody();
    }
}
