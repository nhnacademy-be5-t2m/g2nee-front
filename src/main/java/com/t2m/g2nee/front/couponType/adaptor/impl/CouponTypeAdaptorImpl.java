package com.t2m.g2nee.front.couponType.adaptor.impl;

import com.t2m.g2nee.front.couponType.adaptor.CouponTypeAdaptor;
import com.t2m.g2nee.front.couponType.dto.request.CouponTypeRequestDto;
import com.t2m.g2nee.front.couponType.dto.response.CouponTypeCreatedDto;
import com.t2m.g2nee.front.couponType.dto.response.CouponTypeInfoDto;
import com.t2m.g2nee.front.policyset.pointpolicy.dto.response.PointPolicyInfoDto;
import com.t2m.g2nee.front.utils.PageResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;

import static com.t2m.g2nee.front.utils.HttpHeadersUtil.makeHttpHeaders;

@Component
public class CouponTypeAdaptorImpl implements CouponTypeAdaptor {

    private final RestTemplate restTemplate;

    @Value("${gatewayToShopUrl}")
    private String gateway;

    private String baseUrl;

    private static final ParameterizedTypeReference<PageResponse<CouponTypeInfoDto>> PAGE_TYPE_REF
            = new ParameterizedTypeReference<>() {
    };
    public CouponTypeAdaptorImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    public void initUrl(){
        baseUrl = gateway + "/couponType";
    }

    @Override
    public CouponTypeCreatedDto requestCreateCouponType(CouponTypeRequestDto couponTypeRequestDto) {
        HttpEntity<CouponTypeRequestDto> couponTypeEntity = new HttpEntity<>(couponTypeRequestDto,makeHttpHeaders());
        ResponseEntity<CouponTypeCreatedDto> response =
                restTemplate.exchange(baseUrl+"/createCouponType", HttpMethod.POST,couponTypeEntity, CouponTypeCreatedDto.class);

        return response.getBody();
    }

    @Override
    public CouponTypeCreatedDto requestCreateBookCoupon(CouponTypeRequestDto couponTypeRequestDto) {
        HttpEntity<CouponTypeRequestDto> bookCouponEntity = new HttpEntity<>(couponTypeRequestDto,makeHttpHeaders());
        ResponseEntity<CouponTypeCreatedDto> response =
                restTemplate.exchange(baseUrl+"/createBookCoupon",HttpMethod.POST,bookCouponEntity,CouponTypeCreatedDto.class);

        return response.getBody();
    }

    @Override
    public CouponTypeCreatedDto requestCreateCategoryCoupon(CouponTypeRequestDto couponTypeRequestDto) {
        HttpEntity<CouponTypeRequestDto> categoryCouponEntity = new HttpEntity<>(couponTypeRequestDto,makeHttpHeaders());
        ResponseEntity<CouponTypeCreatedDto> response =
                restTemplate.exchange(baseUrl+"/createCategoryCoupon",HttpMethod.POST,categoryCouponEntity,CouponTypeCreatedDto.class);

        return response.getBody();
    }

    @Override
    public PageResponse<CouponTypeInfoDto> getAllCouponTypes(int page) {
        UriComponents url = UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("page",page)
                .build();

        HttpEntity<String> entity = new HttpEntity<>(makeHttpHeaders());
        ResponseEntity<PageResponse<CouponTypeInfoDto>> response =
                restTemplate.exchange(url.toUriString(),HttpMethod.GET,entity,PAGE_TYPE_REF);

        return response.getBody();
    }
}
