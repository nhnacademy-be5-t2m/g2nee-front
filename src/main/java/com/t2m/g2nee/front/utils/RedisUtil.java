package com.t2m.g2nee.front.utils;

import com.t2m.g2nee.front.member.dto.response.MemberDetailInfoResponseDto;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisUtil {
    public static final String MEMBER_INFO_KEY = "SPRING_SECURITY_CONTEXT";

    public static Long getMemberId(RedisTemplate<String, MemberDetailInfoResponseDto> redisTemplate, String sessionId) {

        MemberDetailInfoResponseDto memberInfo =
                (MemberDetailInfoResponseDto) redisTemplate.opsForHash().get(MEMBER_INFO_KEY, sessionId);
        return memberInfo.getMemberId();
    }
}
