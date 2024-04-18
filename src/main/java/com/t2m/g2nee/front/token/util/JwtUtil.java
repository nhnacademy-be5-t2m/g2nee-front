package com.t2m.g2nee.front.token.util;

import java.util.Base64;
import javax.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

/**
 * 토큰 사용 클래스.
 *
 * @author : 정지은
 * @since : 1.0
 **/
@Component
@Slf4j
public class JwtUtil {
    public static final String ACCESS_COOKIE = "g2nee_accessToken";
    public static final String ACCESS_HEADER = "access";
    public static final String TOKEN_TYPE = "Bearer ";
    public static final Long MILL_SEC = 1000L;
    public static final Integer EXPIRE_TIME = 7200;


    private JwtUtil() {
    }

    /**
     * jwt를 보관할 쿠키를 만드는 메소드
     *
     * @param accessToken 유저 인증 토큰.
     * @param expireTime  만료시간.
     * @return Cookie.
     */
    public static Cookie makeJwtCookie(String accessToken, Long expireTime) {
        String tokenInfo = accessToken + "." + expireTime;

        Cookie cookie = new Cookie(JwtUtil.ACCESS_COOKIE, tokenInfo);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(EXPIRE_TIME);
        return cookie;
    }

    /**
     * 갱신한 토큰의 만료일을 파싱하는 메소드.
     *
     * @param accessToken
     * @return 만료일
     */
    public static Long getExpireTime(String accessToken) throws JSONException {
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String[] access_chunks = accessToken.split("\\.");
        String access_payload = new String(decoder.decode(access_chunks[1]));
        JSONObject aObject = new JSONObject(access_payload);
        return aObject.getLong("exp");
    }

    /**
     * 토큰에서 memberId를 파싱하는 메소드
     *
     * @param accessToken
     * @return memberId
     */
    public static Long getMemberId(String accessToken) throws JSONException {
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String[] access_chunks = accessToken.split("\\.");
        String access_payload = new String(decoder.decode(access_chunks[1]));
        JSONObject aObject = new JSONObject(access_payload);
        return aObject.getLong("username");
    }

}