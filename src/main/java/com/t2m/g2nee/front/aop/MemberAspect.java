package com.t2m.g2nee.front.aop;

import static com.t2m.g2nee.front.token.util.JwtUtil.SESSION_ID;
import static com.t2m.g2nee.front.utils.CookieUtil.findCookie;

import com.t2m.g2nee.front.booklike.service.BookLikeService;
import com.t2m.g2nee.front.member.dto.response.MemberDetailInfoResponseDto;
import com.t2m.g2nee.front.shoppingcart.service.ShoppingCartService;
import java.util.HashMap;
import java.util.Map;
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
    private final ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();
    public static final String MEMBER_INFO = "memberInfo";
    public static final String LIKE_NUM = "likeNum";
    public static final String CART_ITEM_NUM = "cartItemNum";
    public final BookLikeService bookLikeService;
    public final ShoppingCartService shoppingCartService;

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

        if (threadLocal.get() == null) {
            threadLocal.set(new HashMap<>());
        }
        Long memberId = null;
        if (c != null) {
            String sessionId = c.getValue();
            memberId = addMemberInfo(sessionId);
        }
        addLikeNum(memberId);
        addCartItemNum(memberId);

        Object result = joinPoint.proceed(args);
        threadLocal.remove();
        return result;
    }


    public Long addMemberInfo(String sessionId) {
        MemberDetailInfoResponseDto memberInfo =
                (MemberDetailInfoResponseDto) redisTemplate.opsForHash().get(MEMBER_INFO_KEY, sessionId);
        if (memberInfo != null) {
            threadLocal.get().put(MEMBER_INFO, memberInfo);
            return memberInfo.getMemberId();
        }
        return null;
    }

    private void addLikeNum(Long memberId) {
        Long likeNum = bookLikeService.getMemberLikesNum(memberId);
        threadLocal.get().put(LIKE_NUM, likeNum);
    }

    private void addCartItemNum(Long memberId) {
        int cartItemNum = shoppingCartService.getCartItemNum(memberId);
        threadLocal.get().put(CART_ITEM_NUM, cartItemNum);
    }

    public Object getThreadLocal(String key) {
        if (this.threadLocal.get() == null) {
            return null;
        }
        return this.threadLocal.get().get(key);
    }
}
