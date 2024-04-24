package com.t2m.g2nee.front.utils;

import java.util.Arrays;
import java.util.Objects;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * cookie 유틸 클래스
 *
 * @author : 정지은
 * @since : 1.0
 **/
public class CookieUtil {

    private CookieUtil() {
    }

    /**
     * accessCookie 를 찾기위한 메소드
     *
     * @return 쿠키의 값들 반환
     */
    public static Cookie findCookie(String cookieName) {
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        if (Objects.isNull(request.getCookies())) {
            return null;
        }

        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findAny()
                .orElse(null);
    }

    /**
     * 쿠키를 생성하기 위한 메소드
     *
     * @param response
     * @param key      쿠키 이름
     * @param value    쿠키 값
     */
    public static void makeCookie(HttpServletResponse response,
                                  String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(-1);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }


    /**
     * 쿠키를 삭제하기 위한 메소드
     *
     * @param response
     * @param key      쿠키 이름
     */
    public static void deleteCookie(HttpServletResponse response, String key) {
        Cookie cookie = findCookie(key);
        Objects.requireNonNull(cookie).setMaxAge(0);
        response.addCookie(cookie);
    }


}