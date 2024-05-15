package com.t2m.g2nee.front.utils;

import com.t2m.g2nee.front.token.util.JwtUtil;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.Cookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


/**
 * Header 생성의 중복을 방지하기 위한 유틸 클래스
 *
 * @author : 김수빈
 * @since : 1.0
 */
public class HttpHeadersUtil {


    public static HttpHeaders makeHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        Cookie accessTokenCookie = CookieUtil.findCookie(JwtUtil.ACCESS_COOKIE);
        if (Objects.nonNull(accessTokenCookie)) {
            int lastDotIndex = accessTokenCookie.getValue().lastIndexOf('.');
            String accessToken = accessTokenCookie.getValue().substring(0, lastDotIndex);
            httpHeaders.add("Authorization", JwtUtil.TOKEN_TYPE + accessToken);
        }
        return httpHeaders;
    }

    public static HttpHeaders makeFileHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        Cookie accessToken = CookieUtil.findCookie(JwtUtil.ACCESS_COOKIE);
        if (Objects.nonNull(accessToken)) {
            httpHeaders.add("Authorization", JwtUtil.TOKEN_TYPE + accessToken.getValue());
        }
        return httpHeaders;
    }
}
