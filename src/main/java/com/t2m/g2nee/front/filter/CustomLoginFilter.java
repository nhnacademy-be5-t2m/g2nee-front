package com.t2m.g2nee.front.filter;

import static com.t2m.g2nee.front.token.util.JwtUtil.getExpireTime;
import static com.t2m.g2nee.front.token.util.JwtUtil.makeJwtCookie;

import com.t2m.g2nee.front.member.dto.request.MemberLoginRequestDto;
import com.t2m.g2nee.front.member.service.MemberService;
import com.t2m.g2nee.front.token.util.JwtUtil;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * auth 서버에서 토큰을 받아오는 필터
 *
 * @author : 정지은
 * @since : 1.0
 **/
@Slf4j
@RequiredArgsConstructor
public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {
    private final MemberService memberService;
    private static final String LOGIN_STATUS = "X-LOGIN";
    private String social;

    /**
     * 로그인 시 입력을 바탕으로 auth에서 token을 받아오는 부분
     *
     * @param request  request.
     * @param response response.
     * @return 토큰을 받아 토큰에 대한 인증 반환
     */
    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        MemberLoginRequestDto loginMemberRequestDto = new MemberLoginRequestDto(username, password);

        ResponseEntity<Void> jwtResponse
                = memberService.login(loginMemberRequestDto);

        String accessToken = Objects.requireNonNull(jwtResponse.getHeaders().get(JwtUtil.ACCESS_HEADER)).get(0);
        Long expireTime = null;
        try {
            expireTime = getExpireTime(accessToken);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(accessToken, password);

        Cookie cookie = makeJwtCookie(accessToken, expireTime);

        response.addCookie(cookie);

        return getAuthenticationManager().authenticate(token);
    }


    /**
     * 성공시 실행되는 메소드.
     *
     * @param request    request.
     * @param response   response.
     * @param chain      필터체인.
     * @param authResult 인증결과.
     */
    @Override
    protected void successfulAuthentication(
            HttpServletRequest request, HttpServletResponse response,
            FilterChain chain, Authentication authResult) throws IOException {
        SecurityContextHolder.clearContext();
        response.addHeader(LOGIN_STATUS, "success");
    }

}