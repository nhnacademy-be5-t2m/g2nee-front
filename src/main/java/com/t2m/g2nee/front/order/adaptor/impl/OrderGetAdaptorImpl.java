package com.t2m.g2nee.front.order.adaptor.impl;

import com.t2m.g2nee.front.order.adaptor.OrderGetAdaptor;
import com.t2m.g2nee.front.order.dto.OrderDetailDto;
import com.t2m.g2nee.front.order.dto.response.OrderInfoDto;
import com.t2m.g2nee.front.utils.PageResponse;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Component
public class OrderGetAdaptorImpl implements OrderGetAdaptor {
    private final RestTemplate restTemplate;
    @Value("${g2nee.gateway}")
    private String gatewayUrl;

    private String orderUrl;

    @PostConstruct
    public void orderUrl(){
        orderUrl =gatewayUrl +"/orders";
    }

    /**
     * 상세 주문 조회
     *
     * @param orderId 주문Id
     * @return OrderDetailDto
     */
    @Override
    public List<OrderDetailDto.Response> getOrderDetailListByOrderId(Long orderId) {
        HttpHeaders detailHeaders = new HttpHeaders();
        detailHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> detailEntity = new HttpEntity<> (detailHeaders);
        String url = gatewayUrl + "/orders/orderDetails/" + orderId;
//orders/orderDetails/{orderId}
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                detailEntity,
                new ParameterizedTypeReference<List<OrderDetailDto.Response>>() {
                }
        ).getBody();
    }

    /**
     * 주문 정보 조회(주문Id)
     *
     * @param memberId 회원Id
     * @param orderId  주문Id
     * @return OrderInfoResponseDto
     */
    @Override
    public OrderInfoDto.ListResponse getOrderById(Long memberId, Long orderId) {
        HttpHeaders orderHeaders = new HttpHeaders();
        orderHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> orderEntity = new HttpEntity<> (orderHeaders);

        String url = orderUrl + "/members/" + memberId + "/order/" + orderId;

        ResponseEntity<OrderInfoDto.ListResponse> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                orderEntity,
                new ParameterizedTypeReference<OrderInfoDto.ListResponse>() {
                }
        );
        return responseEntity.getBody();
    }

    @Override
    public OrderInfoDto.Response getOrderByNumber(Long customerId, String orderNumber) {
        return null;
    }

    @Override
    public PageResponse<OrderInfoDto.Response> getOrderListForMembers(int page, Long customerId) {
        return null;
    }

    @Override
    public PageResponse<OrderInfoDto.ListResponse> getAllOrderList(int page) {

        HttpHeaders listHeaders = new HttpHeaders();
        listHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> stringHttpEntity = new HttpEntity<> (listHeaders);

        String url = orderUrl + "/admin/orders/list?page=" + page;
        ResponseEntity<PageResponse<OrderInfoDto.ListResponse>> orderListEntity
                 = restTemplate.exchange(
                url,
                HttpMethod.GET,
                stringHttpEntity,
                new ParameterizedTypeReference<PageResponse<OrderInfoDto.ListResponse>>() {
                }
        );
        return orderListEntity.getBody();
    }

    @Override
    public PageResponse<OrderInfoDto> getAllOrderListByState(int page, String orderState) {
        return null;
    }
}
