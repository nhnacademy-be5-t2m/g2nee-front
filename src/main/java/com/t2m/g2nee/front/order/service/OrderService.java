package com.t2m.g2nee.front.order.service;

import static com.t2m.g2nee.front.utils.HttpHeadersUtil.makeHttpHeaders;

import com.t2m.g2nee.front.config.dto.MemberInfoDto;
import com.t2m.g2nee.front.order.dto.request.OrderSaveRequestDto;
import com.t2m.g2nee.front.order.dto.response.OrderForPaymentDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * order에 필요한 service
 *
 * @author : 정지은
 * @since : 1.0
 */
@Service
public class OrderService {
    private final RestTemplate restTemplate;
    private final RedisTemplate<String, MemberInfoDto> redisTemplate;

    @Value("${gatewayToShopUrl}")
    String gatewayToShopUrl;
    @Value("${gatewayToAuthUrl}")
    String gatewayToAuthUrl;

    public OrderService(RedisTemplate<String, MemberInfoDto> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.restTemplate = new RestTemplate();
    }

    public OrderForPaymentDto saveOrder(OrderSaveRequestDto request) {
        HttpEntity<OrderSaveRequestDto> requestEntity = new HttpEntity<>(request, makeHttpHeaders());
        ResponseEntity<OrderForPaymentDto> response = restTemplate.exchange(
                gatewayToShopUrl + "/orders",
                HttpMethod.POST,
                requestEntity,
                OrderForPaymentDto.class
        );
        return response.getBody();
    }
}
