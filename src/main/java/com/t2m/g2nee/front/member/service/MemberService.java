package com.t2m.g2nee.front.member.service;


import static com.t2m.g2nee.front.aop.MemberAspect.MEMBER_INFO;
import static com.t2m.g2nee.front.token.util.JwtUtil.SESSION_ID;
import static com.t2m.g2nee.front.utils.CookieUtil.deleteCookie;
import static com.t2m.g2nee.front.utils.HttpHeadersUtil.makeHttpHeaders;

import com.t2m.g2nee.front.annotation.Member;
import com.t2m.g2nee.front.aop.MemberAspect;
import com.t2m.g2nee.front.config.dto.MemberInfoDto;
import com.t2m.g2nee.front.member.dto.request.MemberLoginRequestDto;
import com.t2m.g2nee.front.member.dto.request.SignUpNonMemberRequestDto;
import com.t2m.g2nee.front.member.dto.request.SignupMemberRequestDto;
import com.t2m.g2nee.front.member.dto.response.MemberDetailInfoResponseDto;
import com.t2m.g2nee.front.member.dto.response.MemberResponse;
import com.t2m.g2nee.front.shoppingcart.service.ShoppingCartService;
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

    private final ShoppingCartService shoppingCartService;
    private final MemberAspect memberAspect;

    @Value("${gatewayToShopUrl}")
    String gatewayToShopUrl;
    @Value("${gatewayToAuthUrl}")
    String gatewayToAuthUrl;

    public MemberService(RedisTemplate<String, MemberInfoDto> redisTemplate, ShoppingCartService shoppingCartService,
                         MemberAspect memberAspect) {
        this.redisTemplate = redisTemplate;
        this.shoppingCartService = shoppingCartService;
        this.memberAspect = memberAspect;
        this.restTemplate = new RestTemplate();
    }


    /**
     * 회원가입을 위한 메소드
     *
     * @param request 회원가입 시 필요한 정보가 담긴 dto
     * @return MemberResponse 회원가입한 member의 기본 정보가 담긴 dto 반환
     */
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

    /**
     * token 발급을 위해 auth server로 로그인 정보를 보내는 메소드
     *
     * @param request login 정보 (username, password)
     * @return ResponseEntity
     */
    public ResponseEntity<Void> login(MemberLoginRequestDto request) {
        HttpEntity<MemberLoginRequestDto> requestEntity = new HttpEntity<>(request, makeHttpHeaders());
        return restTemplate.exchange(
                gatewayToAuthUrl + "/login",
                HttpMethod.POST,
                requestEntity,
                Void.class
        );
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
    @Member
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
            MemberDetailInfoResponseDto memberDetailInfoResponseDto =
                    (MemberDetailInfoResponseDto) memberAspect.getThreadLocal(MEMBER_INFO);
            Long memberId = memberDetailInfoResponseDto.getMemberId();
            shoppingCartService.migrateCartRedisToDB(String.valueOf(memberId));

            deleteCookie(response, JwtUtil.ACCESS_COOKIE);

            Cookie sessionCookie = CookieUtil.findCookie(SESSION_ID);
            redisTemplate.opsForHash().delete("SPRING_SECURITY_CONTEXT", sessionCookie.getValue());
            deleteCookie(response, SESSION_ID);

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

    /**
     * shop DB에 username이 있는지 확인하는 메소드
     *
     * @param username 중복여부를 확인할 username
     * @return 응답을 true, false로 반환
     */
    public Boolean existsUsername(String username) {
        HttpEntity<String> requestEntity = new HttpEntity<>(username, makeHttpHeaders());
        ResponseEntity<Boolean> response = restTemplate.exchange(
                gatewayToShopUrl + "/member/existsUsername",
                HttpMethod.POST,
                requestEntity,
                Boolean.class
        );
        return response.getBody();
    }

    /**
     * shop DB에 nickname 있는지 확인하는 메소드
     *
     * @param nickname 중복여부를 확인할 nickname
     * @return 응답을 true, false로 반환
     */
    public Boolean existsNickName(String nickname) {
        HttpEntity<String> requestEntity = new HttpEntity<>(nickname, makeHttpHeaders());
        ResponseEntity<Boolean> response = restTemplate.exchange(
                gatewayToShopUrl + "/member/existsNickname",
                HttpMethod.POST,
                requestEntity,
                Boolean.class
        );
        return response.getBody();
    }

    /**
     * 회원을 탈퇴하는 메소드
     *
     * @param memberId 탈퇴할 회원의 memberId
     */
    public void quitMember(Long memberId) {
        HttpEntity<String> requestEntity = new HttpEntity<>("QUIT", makeHttpHeaders());
        restTemplate.exchange(
                gatewayToShopUrl + "/member/" + memberId + "/changeStatus",
                HttpMethod.POST,
                requestEntity,
                String.class
        );
    }

    /**
     * 비회원 회원가입을 위한 service
     *
     * @param request 비회원 회원가입위한 request dto
     * @return 생성된 customerId를 반환
     */
    public Long nonMemberSignUp(SignUpNonMemberRequestDto request) {
        HttpEntity<SignUpNonMemberRequestDto> requestEntity = new HttpEntity<>(request, makeHttpHeaders());
        ResponseEntity<Long> response = restTemplate.exchange(
                gatewayToShopUrl + "/customer/save",
                HttpMethod.POST,
                requestEntity,
                Long.class
        );
        return response.getBody();
    }
}