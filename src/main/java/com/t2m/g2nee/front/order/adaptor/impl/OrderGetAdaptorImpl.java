package com.t2m.g2nee.front.order.adaptor.impl;

import com.t2m.g2nee.front.order.adaptor.OrderGetAdaptor;
import com.t2m.g2nee.front.order.dto.OrderDetailDto;
import com.t2m.g2nee.front.order.dto.response.OrderInfoResponseDto;
import com.t2m.g2nee.front.order.dto.response.OrderInfoDto;
import com.t2m.g2nee.front.utils.PageResponse;
import java.net.URLDecoder;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
    public List<OrderDetailDto.Response> getOrderDetailListByOrderId(Long orderId) {
            //
        return null;
    }

    /**
     * 주문 정보 조회(주문id)
     *
     * @param memberId 회원ID
     * @param orderId  주문Id
     * @return OrderInfoResponseDto
     */
    @Override
    public OrderInfoDto.ListResponse getOrderById(Long memberId, Long orderId) {
        HttpHeaders orderHeaders = new HttpHeaders();
        orderHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> orderEntity =new HttpEntity<>(orderHeaders);

        String url = URLDecoder.decode(UriComponentsBuilder
                .fromHttpUrl(gatewayUrl + "/orders/" + orderId)
                .queryParam("memberId", memberId)
                .toUriString());

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                orderEntity,
                new ParameterizedTypeReference<OrderInfoDto.ListResponse>() {
                }
        ).getBody();
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
    public PageResponse<OrderInfoDto.ListResponse> getAllOrderList(int page) {

        HttpHeaders listHeaders = new HttpHeaders();
        listHeaders.setContentType(MediaType.APPLICATION_JSON);
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("page", String.valueOf(page));

        HttpEntity<MultiValueMap<String, String>> stringHttpEntity = new HttpEntity<>(parameters, listHeaders);
        String url = gatewayUrl + "/orders/list?page=" + page;

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                stringHttpEntity,
                new ParameterizedTypeReference<PageResponse<OrderInfoDto.ListResponse>>() {
                }
        ).getBody();
    }

    @Override
    public PageResponse<OrderInfoDto> getAllOrderListByState(int page, String orderState) {
        return null;
    }
}
