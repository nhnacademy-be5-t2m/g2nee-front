package com.t2m.g2nee.front.member.service;


import com.t2m.g2nee.front.config.dto.MemberInfoDto;
import com.t2m.g2nee.front.member.dto.request.MemberLoginRequestDto;
import com.t2m.g2nee.front.member.dto.request.SignupMemberRequestDto;
import com.t2m.g2nee.front.member.dto.response.MemberDetailInfoResponseDto;
import com.t2m.g2nee.front.member.dto.response.MemberResponse;
import com.t2m.g2nee.front.order.dto.request.CustomerOrderCheckRequestDto;
import com.t2m.g2nee.front.token.utils.CookieUtil;
import java.util.Objects;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    private final HttpHeaders headers;

    @Value("${gatewayToShopUrl}")
    String gatewayToShopUrl;
    @Value("${gatewayToAuthUrl}")
    String gatewayToAuthUrl;

    public MemberService(RedisTemplate<String, MemberInfoDto> redisTemplate) {
        this.redisTemplate = redisTemplate;
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

    public ResponseEntity<Void> login(MemberLoginRequestDto request) {
        HttpEntity<MemberLoginRequestDto> requestEntity = new HttpEntity<>(request, headers);
        return restTemplate.exchange(
                gatewayToAuthUrl + "/login",
                HttpMethod.POST,
                requestEntity,
                Void.class
        );
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
                new HttpEntity<>(headers),
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
        HttpEntity<String> requestEntity = new HttpEntity<>(accessToken, headers);
        ResponseEntity<MemberDetailInfoResponseDto> response = restTemplate.exchange(
                gatewayToShopUrl + "/member/getDetailInfo",
                HttpMethod.POST,
                requestEntity,
                MemberDetailInfoResponseDto.class
        );
        return response.getBody();
    }

    /**
     * auth에게 logout을 요청하는 메소드
     */
    public void logout(HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (Objects.nonNull(authentication)) {
            SecurityContextHolder.clearContext();

            Cookie jwtCookie = CookieUtil.findCookie("g2nee_accessToken");
            jwtCookie.setMaxAge(0);
            jwtCookie.setValue("");
            response.addCookie(jwtCookie);

            Cookie sessionCookie = CookieUtil.findCookie("auth-session");
            sessionCookie.setMaxAge(0);
            sessionCookie.setValue("");
            response.addCookie(sessionCookie);

            redisTemplate.opsForHash().delete("SPRING_SECURITY_CONTEXT", "auth-session");

//            restTemplate.exchange(
//                    gatewayToAuthUrl + "/logout",
//                    HttpMethod.POST,
//                    new HttpEntity<>(headers),
//                    Void.class
//            );
        }
    }
}