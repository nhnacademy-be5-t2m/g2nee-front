package com.t2m.g2nee.front.aop;

import static com.t2m.g2nee.front.token.util.JwtUtil.SESSION_ID;
import static com.t2m.g2nee.front.utils.CookieUtil.findCookie;

import com.t2m.g2nee.front.member.dto.response.MemberDetailInfoResponseDto;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@RequiredArgsConstructor
public class MemberAspect {

    public static final String MEMBER_INFO_KEY = "SPRING_SECURITY_CONTEXT";
    public final RedisTemplate redisTemplate;
    private final ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    @Pointcut("@annotation(com.t2m.g2nee.front.annotation.Member)")
    private void member() {
    }

    @Around("member()")
    private Object getMemberId(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();

        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        Cookie c = findCookie(SESSION_ID);

        if (c != null) {
            String sessionId = c.getValue();
            Long memberId = getMemberId(sessionId);
            threadLocal.set(memberId);
        }
        Object result = joinPoint.proceed(args);
        threadLocal.remove();
        return result;
    }


    public Long getMemberId(String sessionId) {
        MemberDetailInfoResponseDto memberInfo =
                (MemberDetailInfoResponseDto) redisTemplate.opsForHash().get(MEMBER_INFO_KEY, sessionId);
        return memberInfo.getMemberId();
    }

    public ThreadLocal getThreadLocal() {
        return this.threadLocal;
    }
}
