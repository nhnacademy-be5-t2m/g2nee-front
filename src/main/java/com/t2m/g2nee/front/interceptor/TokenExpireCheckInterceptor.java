package com.t2m.g2nee.front.interceptor;

import static com.t2m.g2nee.front.token.util.JwtUtil.ACCESS_COOKIE;
import static com.t2m.g2nee.front.token.util.JwtUtil.SESSION_ID;
import static com.t2m.g2nee.front.token.util.JwtUtil.getExpireTime;

import com.t2m.g2nee.front.member.service.MemberService;
import com.t2m.g2nee.front.token.util.JwtUtil;
import com.t2m.g2nee.front.utils.CookieUtil;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;
import java.util.Objects;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenExpireCheckInterceptor implements HandlerInterceptor {
    private static final Long RENEW_TIME = Duration.ofMinutes(10).toSeconds();
    private static final String ERROR_MESSAGE = "X-MESSAGE";
    private final MemberService memberService;

    /**
     * 컨트롤러 진입 전에 실행하여 token이 유효하진 확인하는 메소드
     *
     * @param request  요청
     * @param response 응답
     * @param handler  핸들러
     * @return 유효한지의 여부를 boolean으로 반환
     * @throws IOException sendError에서 발생할 수 있는 에러.
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws IOException {

        Cookie accessTokenCookie = CookieUtil.findCookie(ACCESS_COOKIE);
        Cookie authSessionCookie = CookieUtil.findCookie(SESSION_ID);

        if (Objects.isNull(accessTokenCookie) || Objects.isNull(authSessionCookie)) {
            if (!Objects.isNull(accessTokenCookie)) {
                CookieUtil.deleteCookie(response, ACCESS_COOKIE);
            }
            if (!Objects.isNull(authSessionCookie)) {
                CookieUtil.deleteCookie(response, SESSION_ID);
            }
            return true;
        }

        String accessTokenCookieValue = Objects.requireNonNull(accessTokenCookie).getValue();
        String exp = accessTokenCookieValue.split("\\.")[3];
        String accessToken =
                accessTokenCookie.getValue()
                        .substring(0, accessTokenCookieValue.length() - (exp.length() - 1));


        if (getValidTime(exp)) {
            ResponseEntity<Void> result = memberService.tokenReIssueRequest();
            String newAccessToken = Objects.requireNonNull(result.getHeaders().get(JwtUtil.ACCESS_HEADER)).get(0);
            Long expireTime = getExpireTime(newAccessToken);
            updateAccessToken(response, accessTokenCookie, newAccessToken, expireTime);
            requestContainAccessToken(request, newAccessToken);
            return true;

        }

        requestContainAccessToken(request, accessToken);
        return true;
    }


    /**
     * accessToken이 갱신되었을 때 갱신된 accessToken을 cookie에 저장하는 메소드
     *
     * @param response         response.
     * @param jwtCookie        로그인하면서 발급된 accessToken이 담겨있는 cookie.
     * @param renewAccessToken 새로 갱신할 accessToken
     * @param expireTime       만료시간
     */
    private static void updateAccessToken(HttpServletResponse response, Cookie jwtCookie,
                                          String renewAccessToken, Long expireTime) {
        jwtCookie.setValue(renewAccessToken + "." + expireTime);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(7200);
        response.addCookie(jwtCookie);
    }

    /**
     * 토큰의 유효시간을 계산해주는 메소드
     *
     * @param exp 토큰의 만료시간
     * @return 토큰의 유효시간
     */
    private static boolean getValidTime(String exp) {
        Date now = new Date(System.currentTimeMillis());
        return (new Date(Long.parseLong(exp) * 1000).before(now));
    }

    /**
     * request에 token의 정보를 저장하는 메소드
     *
     * @param request     request.
     * @param accessToken accessToken 정보
     */
    private static void requestContainAccessToken(HttpServletRequest request, String accessToken) {
        request.setAttribute(JwtUtil.ACCESS_HEADER, JwtUtil.TOKEN_TYPE + accessToken);
    }
}
