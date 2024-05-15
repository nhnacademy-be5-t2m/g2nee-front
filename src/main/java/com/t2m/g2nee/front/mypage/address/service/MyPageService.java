package com.t2m.g2nee.front.mypage.address.service;

import static com.t2m.g2nee.front.utils.HttpHeadersUtil.makeHttpHeaders;

import com.t2m.g2nee.front.mypage.address.dto.request.AddressRequestDto;
import com.t2m.g2nee.front.mypage.address.dto.response.AddressResponseDto;
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


    /**
     * meberId로 주소정보를 모두 불러오는 메소드
     *
     * @param memberId 주소정보를 불러올 memberId
     * @return memberId에 해당하는 주소정보 list
     */
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


    /**
     * 기본 배송지를 바꿔주는 메소드
     *
     * @param addressId 기본 배송지로 바꿀 addressId
     * @return shop으로 보낸 요청의 응답
     */
    public ResponseEntity<Void> changeDefaultAddress(Long addressId) {
        return restTemplate.exchange(
                gatewayToShopUrl + "/address/changeDefaultAddress/" + addressId,
                HttpMethod.PUT,
                new HttpEntity<>(makeHttpHeaders()),
                Void.class
        );
    }

    /**
     * 배송지를 저장해주는 메소드
     *
     * @param addressRequestDto 저장할 배송지의 정보
     * @return shop으로 보낸 요청의 응답
     */
    public ResponseEntity<AddressResponseDto> saveAddress(AddressRequestDto addressRequestDto) {
        HttpEntity<AddressRequestDto> requestEntity = new HttpEntity<>(addressRequestDto, makeHttpHeaders());
        return restTemplate.exchange(
                gatewayToShopUrl + "/address/save",
                HttpMethod.POST,
                requestEntity,
                AddressResponseDto.class
        );
    }

    /**
     * 배송지 정보를 삭제해주는 메소드
     *
     * @param addressId 삭제할 배송지의 id
     * @return shop으로 보낸 요청의 응답
     */
    public void deleteAddress(Long addressId) {
        restTemplate.exchange(
                gatewayToShopUrl + "/address/delete/" + addressId,
                HttpMethod.DELETE,
                new HttpEntity<>(makeHttpHeaders()),
                Void.class
        );
    }

    public AddressResponseDto getAddress(Long addressId) {
        ResponseEntity<AddressResponseDto> response = restTemplate.exchange(
                gatewayToShopUrl + "/address/getByAddressId/" + addressId,
                HttpMethod.GET,
                new HttpEntity<>(makeHttpHeaders()),
                AddressResponseDto.class
        );
        return response.getBody();
    }
}
