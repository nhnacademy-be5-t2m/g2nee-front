package com.t2m.g2nee.front.interceptor;

import static com.t2m.g2nee.front.advice.CustomExceptionAdvice.REQUIRE_LOGIN_MESSAGE;
import static com.t2m.g2nee.front.token.util.JwtUtil.ACCESS_COOKIE;
import static com.t2m.g2nee.front.token.util.JwtUtil.SESSION_ID;

import com.t2m.g2nee.front.exception.CustomException;
import com.t2m.g2nee.front.member.dto.response.MemberDetailInfoResponseDto;
import com.t2m.g2nee.front.utils.CookieUtil;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 토큰의 기간을 체크해주는 인터셉터입니다.
 *
 * @author : 정지은
 * @since : 1.0
 **/
@Component
@RequiredArgsConstructor
@Slf4j
public class AuthorityCheckInterceptor implements HandlerInterceptor {
    public static final String MEMBER_INFO_KEY = "SPRING_SECURITY_CONTEXT";
    public final RedisTemplate redisTemplate;

    /**
     * /admin 컨트롤러 진입 전에 실행하여 권한을 확인하는 인터셉터
     *
     * @param request  요청
     * @param response 응답
     * @param handler  핸들러
     * @return 유효한지의 여부를 boolean으로 반환
     * @throws CustomException 권한이 없음을 알리는 error
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws IOException {

        Cookie accessTokenCookie = CookieUtil.findCookie(ACCESS_COOKIE);

        if (Objects.isNull(accessTokenCookie)) {
            response.sendRedirect("/login");
            return false;
        }
        if (!existsAdminAuthority()) {
            throw new CustomException(HttpStatus.FORBIDDEN, REQUIRE_LOGIN_MESSAGE);
        }
        return true;
    }

    /**
     * 현재 사용자가 admin 권한이 있는지 확인
     *
     * @return admin권한이 있는지 여부를 boolean으로 반환
     */
    private boolean existsAdminAuthority() {
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        Cookie c = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(SESSION_ID))
                .findAny()
                .orElse(null);

        if (c != null) {
            String sessionId = c.getValue();
            MemberDetailInfoResponseDto memberInfo =
                    (MemberDetailInfoResponseDto) redisTemplate.opsForHash().get(MEMBER_INFO_KEY, sessionId);
            if (memberInfo.getAuthorities().contains("ROLE_ADMIN")) {
                return true;
            }
        }
        return false;
    }

}
