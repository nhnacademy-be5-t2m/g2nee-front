package com.t2m.g2nee.front.payment.adaptor.impl;

import static com.t2m.g2nee.front.utils.HttpHeadersUtil.makeHttpHeaders;

import com.t2m.g2nee.front.payment.adaptor.PaymentAdaptor;
import com.t2m.g2nee.front.payment.dto.request.PaymentRequest;
import com.t2m.g2nee.front.payment.dto.response.PaymentInfoDto;
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

@Component
public class PaymentAdaptorImpl implements PaymentAdaptor {


    //페이징 처리를 위한 ref
    private static final ParameterizedTypeReference<PageResponse<PaymentInfoDto>> PAGE_TYPE_REF
            = new ParameterizedTypeReference<>() {
    };

    private final RestTemplate restTemplate;


    @Value("${gatewayToShopUrl}")
    private String gateway;

    private String baseUrl;

    public PaymentAdaptorImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    public void initUrl() {
        baseUrl = gateway + "/payments";
    }

    @Override
    public PaymentInfoDto requestPayment(PaymentRequest paymentRequest) {
        HttpEntity<PaymentRequest> entity = new HttpEntity<>(paymentRequest, makeHttpHeaders());
        ResponseEntity<PaymentInfoDto> response =
                restTemplate.exchange(baseUrl, HttpMethod.POST, entity, PaymentInfoDto.class);

        return response.getBody();
    }

    @Override
    public PaymentInfoDto requestCancelPayment(Long paymentId) {
        UriComponents url = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/" + paymentId)
                .build();

        HttpEntity<String> entity = new HttpEntity<>(makeHttpHeaders());
        ResponseEntity<PaymentInfoDto> response =
                restTemplate.exchange(url.toUriString(), HttpMethod.POST, entity, PaymentInfoDto.class);

        return response.getBody();
    }

    @Override
    public PageResponse<PaymentInfoDto> getPayments(Long customerId, int page) {
        UriComponents url = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/" + customerId)
                .queryParam("page", page)
                .build();

        HttpEntity<String> entity = new HttpEntity<>(makeHttpHeaders());
        ResponseEntity<PageResponse<PaymentInfoDto>> response =
                restTemplate.exchange(url.toUriString(), HttpMethod.GET, entity, PAGE_TYPE_REF);

        return response.getBody();
    }

    @Override
    public PaymentInfoDto getPayment(Long orderId) {
        UriComponents url = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/order")
                .path("/" + orderId)
                .build();

        HttpEntity<String> entity = new HttpEntity<>(makeHttpHeaders());
        ResponseEntity<PaymentInfoDto> response =
                restTemplate.exchange(url.toUriString(), HttpMethod.GET, entity, PaymentInfoDto.class);

        return response.getBody();
    }
}