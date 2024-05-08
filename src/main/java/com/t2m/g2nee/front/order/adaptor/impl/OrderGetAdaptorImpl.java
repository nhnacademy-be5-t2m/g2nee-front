package com.t2m.g2nee.front.order.adaptor.impl;

import com.t2m.g2nee.front.order.adaptor.OrderGetAdaptor;
import com.t2m.g2nee.front.order.dto.OrderDetailDto;
import com.t2m.g2nee.front.order.dto.response.OrderInfoResponseDto;
import com.t2m.g2nee.front.order.dto.response.OrderListForAdminResponseDto;
import com.t2m.g2nee.front.utils.PageResponse;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Component
public class OrderGetAdaptorImpl implements OrderGetAdaptor {
    private final RestTemplate restTemplate;

    @Value("${g2nee.gateway}")
    private String gatewayUrl;

    /**
     * 상세 주문 조회
     *
     * @param orderId
     * @return OrderDetailDto
     */
    @Override
    public OrderDetailDto getOrderDetail(Long orderId) {
        return null;
    }

    /**
     *  주문 정보 조회(주문id)
     *
     * @param customerId 회원ID
     * @param orderId 주문Id
     * @return OrderInfoResponseDto
     */
    @Override
    public OrderInfoResponseDto getOrderById(Long customerId, Long orderId) {
        return null;
    }

    @Override
    public OrderInfoResponseDto getOrderByNumber(Long customerId, String orderNumber) {
        return null;
    }

    @Override
    public PageResponse<OrderInfoResponseDto> getOrderListForMembers(int page, Long customerId) {
        return null;
    }

    @Override
    public PageResponse<OrderListForAdminResponseDto> getAllOrderList(int page) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> stringHttpEntity = new HttpEntity<>(httpHeaders);
        String url = URLDecoder.decode(UriComponentsBuilder.
                fromHttpUrl(gatewayUrl + "admin/orders/list").
                queryParam("page", page).
                toUriString(), StandardCharsets.UTF_8);

        PageResponse<OrderListForAdminResponseDto> adminResponse = restTemplate.exchange(
                url,
                HttpMethod.GET,
                stringHttpEntity,
                new ParameterizedTypeReference<PageResponse<OrderListForAdminResponseDto>>() {
                }
        ).getBody();

        List<OrderListForAdminResponseDto> orderList = adminResponse.getData().stream().
                collect(Collectors.toList());

        adminResponse.setData(orderList);
        return adminResponse;
    }

    @Override
    public PageResponse<OrderListForAdminResponseDto> getAllOrderListByState(int page, String orderState) {
        return null;
    }
}
