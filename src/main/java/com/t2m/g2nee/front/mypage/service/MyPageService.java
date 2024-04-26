package com.t2m.g2nee.front.mypage.service;

import static com.t2m.g2nee.front.utils.HttpHeadersUtil.makeHttpHeaders;

import com.t2m.g2nee.front.bookset.book.dto.BookDto;
import com.t2m.g2nee.front.mypage.dto.response.AddressResponseDto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * MyPage에 필요한 service
 *
 * @author : 정지은
 * @since : 1.0
 */
@Service
public class MyPageService {
    private final RestTemplate restTemplate;

    @Value("${gatewayToShopUrl}")
    String gatewayToShopUrl;
    @Value("${gatewayToAuthUrl}")
    String gatewayToAuthUrl;


    public MyPageService(RestTemplate restTemplate) {
        this.restTemplate = new RestTemplate();
    }


    public List<AddressResponseDto> getAddressListByMemberId(Long memberId) {
        ResponseEntity<List<AddressResponseDto>> addressList = restTemplate.exchange(
                gatewayToShopUrl + "/address/getListByMemberId/" + memberId,
                HttpMethod.GET,
                new HttpEntity<>(makeHttpHeaders()),
                new ParameterizedTypeReference<List<AddressResponseDto>>() {
                }
        );
        return addressList.getBody();
    }
}
