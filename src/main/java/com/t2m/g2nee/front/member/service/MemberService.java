package com.t2m.g2nee.front.member.service;


import static com.t2m.g2nee.front.utils.CookieUtil.deleteCookie;
import static com.t2m.g2nee.front.utils.HttpHeadersUtil.makeHttpHeaders;

import com.t2m.g2nee.front.config.dto.MemberInfoDto;
import com.t2m.g2nee.front.member.dto.request.MemberLoginRequestDto;
import com.t2m.g2nee.front.member.dto.request.SignupMemberRequestDto;
import com.t2m.g2nee.front.member.dto.response.MemberResponse;
import com.t2m.g2nee.front.token.util.JwtUtil;
import com.t2m.g2nee.front.utils.CookieUtil;
import java.util.Objects;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Member에 필요한 service
 *
 * @author : 정지은
 * @since : 1.0
 */
@Service
public class MemberService {
    private final RestTemplate restTemplate;
    private final RedisTemplate<String, MemberInfoDto> redisTemplate;

    @Value("${gatewayToShopUrl}")
    String gatewayToShopUrl;
    @Value("${gatewayToAuthUrl}")
    String gatewayToAuthUrl;

    public MemberService(RedisTemplate<String, MemberInfoDto> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.restTemplate = new RestTemplate();
    }


    public MemberResponse signup(SignupMemberRequestDto request) {
        HttpEntity<SignupMemberRequestDto> requestEntity = new HttpEntity<>(request, makeHttpHeaders());
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

<<<<<<< HEAD
    public ResponseEntity<Void> login(MemberLoginRequestDto request) {
        HttpEntity<MemberLoginRequestDto> requestEntity = new HttpEntity<>(request, makeHttpHeaders());
        return restTemplate.exchange(
                gatewayToAuthUrl + "/login",
=======
    public boolean login(MemberLoginRequestDto request) {
        //TODO: auth로 request를 보내 토큰처리를 해야함.
        HttpEntity<MemberLoginRequestDto> requestEntity = new HttpEntity<>(request, headers);
        ResponseEntity<Void> response = restTemplate.exchange(
                gatewayToAuthUrl + "/auth/login",
>>>>>>> parent of 7c899e1 (:sparkles: accesstoken 응답 후 저장)
                HttpMethod.POST,
                requestEntity,
                Void.class
        );
        
        return true;
    }

<<<<<<< HEAD
    /**
     * 회원의 세부정보를 받아오기 위한 메소드
     *
     * @param customerId
     * @return MemberDetailResponseDto 반환
     */
    public MemberDetailInfoResponseDto getMemberDetailInfoFromCustomerId(Long customerId) {
        ResponseEntity<MemberDetailInfoResponseDto> response = restTemplate.exchange(
                gatewayToShopUrl + "/member/getDetailInfo/" + customerId,
                HttpMethod.GET,
                new HttpEntity<>(makeHttpHeaders()),
                MemberDetailInfoResponseDto.class
        );
        return response.getBody();
    }

    /**
     * 회원의 세부정보를 받아오기 위한 메소드
     *
     * @param accessToken
     * @return MemberDetailResponseDto
     */
    public MemberDetailInfoResponseDto getMemberDetailFromAccessToken(String accessToken) {
        HttpEntity<String> requestEntity = new HttpEntity<>(accessToken, makeHttpHeaders());
        ResponseEntity<MemberDetailInfoResponseDto> response = restTemplate.exchange(
                gatewayToShopUrl + "/member/getDetailInfo",
                HttpMethod.POST,
                requestEntity,
                MemberDetailInfoResponseDto.class
        );
        return response.getBody();
    }

    /**
     * logout 시 삭제되어야할 요소들을 삭제하며 auth로 logout을 요청하는 메소드
     *
     * @param response logout을 호출할때의 response
     */
    public void logout(HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (Objects.nonNull(authentication)) {
            SecurityContextHolder.clearContext();

            restTemplate.exchange(
                    gatewayToAuthUrl + "/logout",
                    HttpMethod.POST,
                    new HttpEntity<>(makeHttpHeaders()),
                    Void.class
            );

            deleteCookie(response, JwtUtil.ACCESS_COOKIE);

            Cookie sessionCookie = CookieUtil.findCookie("auth-session");
            redisTemplate.opsForHash().delete("SPRING_SECURITY_CONTEXT", sessionCookie.getValue());
            deleteCookie(response, "auth-session");

        }
    }

    /**
     * header의 accessToken으로 token을 다시 발급받는 메소드
     *
     * @return auth에서의 응답 결과
     */
    public ResponseEntity<Void> tokenReIssueRequest() {
        return restTemplate.exchange(
                gatewayToAuthUrl + "/reissue",
                HttpMethod.POST,
                new HttpEntity<>(makeHttpHeaders()),
                Void.class
        );
    }
=======
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
>>>>>>> parent of 7c899e1 (:sparkles: accesstoken 응답 후 저장)
}