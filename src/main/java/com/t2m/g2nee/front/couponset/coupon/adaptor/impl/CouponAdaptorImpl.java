package com.t2m.g2nee.front.couponset.coupon.adaptor.impl;

import static com.t2m.g2nee.front.utils.HttpHeadersUtil.makeHttpHeaders;

import com.t2m.g2nee.front.couponset.coupon.adaptor.CouponAdaptor;
import com.t2m.g2nee.front.couponset.coupon.dto.request.CouponDownloadDto;
import com.t2m.g2nee.front.couponset.coupon.dto.request.CouponIssueDto;
import com.t2m.g2nee.front.couponset.coupon.dto.response.CouponInfoDto;
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
public class CouponAdaptorImpl implements CouponAdaptor {
    //페이징 처리를 위한 ref
    private static final ParameterizedTypeReference<PageResponse<CouponInfoDto>> PAGE_TYPE_REF
            = new ParameterizedTypeReference<>() {
    };
    private final RestTemplate restTemplate;

    @Value("${gatewayToShopUrl}")
    private String gateway;
    private String baseUrl;

    public CouponAdaptorImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    public void initUrl() {
        baseUrl = gateway + "/coupon";
    }
    @Override
    public CouponInfoDto requestIssueCoupon(CouponIssueDto request) {
        HttpEntity<CouponIssueDto> entity = new HttpEntity<>(request, makeHttpHeaders());
        ResponseEntity<CouponInfoDto> response =
                restTemplate.exchange(baseUrl, HttpMethod.POST, entity, CouponInfoDto.class);

        return response.getBody();
    }

    @Override
    public CouponInfoDto requestDownloadCoupon(CouponDownloadDto request) {
        UriComponents url = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/download")
                .build();

        HttpEntity<CouponDownloadDto> entity = new HttpEntity<>(request, makeHttpHeaders());
        ResponseEntity<CouponInfoDto> response =
                restTemplate.exchange(url.toUriString(), HttpMethod.POST, entity, CouponInfoDto.class);

        return response.getBody();
    }

    @Override
    public PageResponse<CouponInfoDto> getMyCoupons(Long customerId, int page) {
        UriComponents url = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/"+customerId)
                .queryParam("page", page)
                .build();

        HttpEntity<String> entity = new HttpEntity<>(makeHttpHeaders());
        ResponseEntity<PageResponse<CouponInfoDto>> response =
                restTemplate.exchange(url.toUriString(), HttpMethod.GET, entity, PAGE_TYPE_REF);

        return response.getBody();
    }

    @Override
    public PageResponse<CouponInfoDto> getBookCoupons(Long customerId, Long bookId, int page) {
        UriComponents url = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/"+customerId)
                .path("/book")
                .queryParam("bookId", bookId)
                .queryParam("page", page)
                .build();

        HttpEntity<String> entity = new HttpEntity<>(makeHttpHeaders());
        ResponseEntity<PageResponse<CouponInfoDto>> response =
                restTemplate.exchange(url.toUriString(), HttpMethod.GET, entity, PAGE_TYPE_REF);

        return response.getBody();
    }

    @Override
    public PageResponse<CouponInfoDto> getTotalCoupons(Long customerId, int page) {
        UriComponents url = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/"+customerId)
                .path("/all")
                .queryParam("page", page)
                .build();

        HttpEntity<String> entity = new HttpEntity<>(makeHttpHeaders());
        ResponseEntity<PageResponse<CouponInfoDto>> response =
                restTemplate.exchange(url.toUriString(), HttpMethod.GET, entity, PAGE_TYPE_REF);

        return response.getBody();
    }
}
