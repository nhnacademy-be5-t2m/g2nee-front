package com.t2m.g2nee.front.member.service;


import com.t2m.g2nee.front.member.dto.request.MemberLoginRequestDto;
import com.t2m.g2nee.front.member.dto.request.SignupMemberRequestDto;
import com.t2m.g2nee.front.member.dto.response.MemberResponse;
import com.t2m.g2nee.front.order.dto.request.CustomerOrderCheckRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 회원가입에 필요한 service
 *
 * @author : 정지은
 * @since : 1.0
 */
@Service
public class MemberService {
    private final RestTemplate restTemplate;
    private final HttpHeaders headers;

    @Value("${gatewayToShopUrl}")
    String gatewayToShopUrl;
    @Value("${gatewayToShopUrl}")
    String gatewayToAuthUrl;

    public MemberService() {
        this.restTemplate = new RestTemplate();
        this.headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }


    public MemberResponse signup(SignupMemberRequestDto request) {
        HttpEntity<SignupMemberRequestDto> requestEntity = new HttpEntity<>(request, headers);
        ResponseEntity<MemberResponse> response = restTemplate.exchange(
                gatewayToShopUrl + "/member/signup",
                HttpMethod.POST,
                requestEntity,
                MemberResponse.class
        );
        if (response.getStatusCode() != HttpStatus.CREATED) {
            throw new RuntimeException("회원가입에 실패하였습니다.");
        }
        return response.getBody();
    }

    public boolean login(MemberLoginRequestDto request) {
        //TODO: auth로 request를 보내 토큰처리를 해야함.
        HttpEntity<MemberLoginRequestDto> requestEntity = new HttpEntity<>(request, headers);
        ResponseEntity<Void> response = restTemplate.exchange(
                gatewayToAuthUrl + "/auth/login",
                HttpMethod.POST,
                requestEntity,
                Void.class
        );
        
        return true;
    }

    public boolean customerLogin(CustomerOrderCheckRequestDto request) {
        HttpEntity<CustomerOrderCheckRequestDto> requestEntity = new HttpEntity<>(request, headers);
        ResponseEntity<Boolean> response = restTemplate.exchange(
                gatewayToShopUrl + "/customer/login",
                HttpMethod.POST,
                requestEntity,
                Boolean.class
        );
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("비회원 주문조회에 실패하였습니다.");
        }
        return response.getBody();
    }
}