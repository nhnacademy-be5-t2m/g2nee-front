package com.t2m.g2nee.front.interceptor;

import static com.t2m.g2nee.front.token.util.JwtUtil.ACCESS_COOKIE;

import com.t2m.g2nee.front.utils.CookieUtil;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 마이페이지의 접근을 제어하는 인터셉터입니다.
 *
 * @author : 정지은
 * @since : 1.0
 **/
@Component
@RequiredArgsConstructor
@Slf4j
public class MypageInterceptor implements HandlerInterceptor {

    /**
     * /mypage 컨트롤러 진입 전에 실행하여 token이 유효하진 확인하는 메소드
     *
     * @param request  요청
     * @param response 응답
     * @param handler  핸들러
     * @return 유효한지의 여부를 boolean으로 반환
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Cookie accessTokenCookie = CookieUtil.findCookie(ACCESS_COOKIE);
        if (accessTokenCookie == null) {
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
