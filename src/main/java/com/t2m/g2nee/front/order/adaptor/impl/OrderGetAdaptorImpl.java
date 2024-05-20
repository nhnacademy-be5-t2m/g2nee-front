package com.t2m.g2nee.front.order.adaptor.impl;

import static com.t2m.g2nee.front.utils.HttpHeadersUtil.makeHttpHeaders;

import com.t2m.g2nee.front.order.adaptor.OrderGetAdaptor;
import com.t2m.g2nee.front.order.dto.OrderDetailDto;
import com.t2m.g2nee.front.order.dto.response.OrderForPaymentDto;
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
    public void orderUrl() {
        orderUrl = gatewayUrl + "/orders";
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

        HttpEntity<String> detailEntity = new HttpEntity<>(makeHttpHeaders());
        String url = orderUrl + "/orderDetails/" + orderId;

        ResponseEntity<List<OrderDetailDto.Response>> detailResponse = restTemplate.exchange(
                url,
                HttpMethod.GET,
                detailEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        return detailResponse.getBody();
    }

    /**
     * 주문 정보 조회(주문Id)
     *
     * @param orderId 주문Id
     * @return OrderInfoResponseDto
     */
    @Override
    public OrderInfoDto.Response getOrderById(Long orderId) {
        HttpHeaders orderHeaders = new HttpHeaders();
        orderHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> orderEntity = new HttpEntity<>(makeHttpHeaders());

        String url = orderUrl + "/order/" + orderId;
        ResponseEntity<OrderInfoDto.Response> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                orderEntity,
                new ParameterizedTypeReference<>() {
                }
        );
        return responseEntity.getBody();
    }

    @Override
    public OrderInfoDto.Response getOrderByNumber(String orderNumber) {
        HttpHeaders orderHeaders = new HttpHeaders();
        orderHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> orderEntity = new HttpEntity<>(makeHttpHeaders());

        String url = orderUrl + "/nonmembers/" + orderNumber;

        ResponseEntity<OrderInfoDto.Response> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                orderEntity,
                new ParameterizedTypeReference<>() {
                }
        );
        return responseEntity.getBody();
    }

    @Override
    public Boolean existsOrder(String orderNumber) {
        HttpEntity<String> requestEntity = new HttpEntity<>(orderNumber, makeHttpHeaders());

        String url = orderUrl + "/existsOrder";

        return restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                Boolean.class
        ).getBody();
    }

    @Override
    public PageResponse<OrderForPaymentDto> getOrderListForMembers(Long customerId, int page) {
        HttpHeaders listHeaders = new HttpHeaders();
        listHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> stringHttpEntity = new HttpEntity<>(makeHttpHeaders());

        String url = orderUrl + "/members/" + customerId + "/list?page=" + page;
        ResponseEntity<PageResponse<OrderForPaymentDto>> orderListEntity
                = restTemplate.exchange(
                url,
                HttpMethod.GET,
                stringHttpEntity,
                new ParameterizedTypeReference<PageResponse<OrderForPaymentDto>>() {
                }
        );
        return orderListEntity.getBody();
    }

    @Override
    public PageResponse<OrderInfoDto.ListResponse> getAllOrderList(int page) {

        HttpHeaders listHeaders = new HttpHeaders();
        listHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> stringHttpEntity = new HttpEntity<>(makeHttpHeaders());

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

    @Override
    public void changeOrderStatus(Long orderId, OrderInfoDto.OrderState stateRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<OrderInfoDto.OrderState> stateEntity = new HttpEntity<>(stateRequest, makeHttpHeaders());
        String url = orderUrl + "/state/" + orderId;

        restTemplate.exchange(
                url,
                HttpMethod.PATCH,
                stateEntity,
                String.class
        ).getBody();
    }

    @Override
    public String getOrderName(Long orderId) {
        String url = orderUrl + "/orderName/" + orderId;
        HttpEntity<String> entity = new HttpEntity<>(makeHttpHeaders());

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {
                }
        );
        return responseEntity.getBody();
    }
}
