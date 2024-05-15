package com.t2m.g2nee.front.advice;

import static com.t2m.g2nee.front.aop.MemberAspect.MEMBER_INFO;
import static com.t2m.g2nee.front.token.util.JwtUtil.SESSION_ID;
import static com.t2m.g2nee.front.utils.CookieUtil.deleteCookie;

import com.t2m.g2nee.front.annotation.Member;
import com.t2m.g2nee.front.aop.MemberAspect;
import com.t2m.g2nee.front.exception.CustomException;
import com.t2m.g2nee.front.member.dto.response.MemberDetailInfoResponseDto;
import com.t2m.g2nee.front.shoppingcart.service.ShoppingCartService;
import com.t2m.g2nee.front.token.util.JwtUtil;
import com.t2m.g2nee.front.utils.CookieUtil;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 예외 발생시, 처리하기 위한 ControllerAdvice입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
@ControllerAdvice
public class CustomExceptionAdvice {
    public static String REQUIRE_LOGIN_MESSAGE = "로그인이 필요합니다.";
    private final RedisTemplate redisTemplate;
    private final ShoppingCartService shoppingCartService;
    private final MemberAspect memberAspect;

    public CustomExceptionAdvice(RedisTemplate redisTemplate, ShoppingCartService shoppingCartService,
                                 MemberAspect memberAspect) {
        this.shoppingCartService = shoppingCartService;
        this.memberAspect = memberAspect;
        this.redisTemplate = new RedisTemplate();
    }

    /**
     * e에는 백엔드 shop서버에서 받아오는 에러 코드, 메시지를 가지고 있고
     * 이를 errorPage에서 보여줍니다.
     *
     * @param e
     * @param model
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public String showError(CustomException e, Model model) {
        model.addAttribute("error", e);
        return "/error/errorPage";
    }

    /**
     * 프론트쪽에서 404 not found 시 처리합니다.
     *
     * @param model
     * @return
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public String notFoundError(Model model) {
        model.addAttribute("error", new CustomException(HttpStatus.NOT_FOUND, "페이지를 찾을 수 없습니다."));
        return "/error/errorPage";
    }

    /**
     * front에서 500 error 시 처리합니다.
     *
     * @param ex
     * @param model
     * @return
     */
    @ExceptionHandler(ResponseStatusException.class)
    public String serverError(ResponseStatusException ex, Model model) {
        if (ex.getStatus() == HttpStatus.INTERNAL_SERVER_ERROR) {
            model.addAttribute("error", new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 문제가 발생하였습니다."));
            return "/error/errorPage";
        }
        throw ex;
    }

    /**
     * front에서 403 권한없음 error 시 처리합니다.
     *
     * @param model
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    public String forbiddenError(Model model) {
        model.addAttribute("error", new CustomException(HttpStatus.FORBIDDEN, "페이지에 대한 권한이 없습니다."));
        return "/error/errorPage";

    }

    /**
     * 토큰이 유효하지 않을 때 재 로그인을 요청합니다.
     *
     * @param model
     * @return
     */
    @ExceptionHandler(HttpClientErrorException.class)
    @Member
    public String invalidToken(HttpClientErrorException ex, Model model) {
        if (ex.getRawStatusCode() == 401) {
            model.addAttribute("tokenError", "재로그인이 필요합니다.");
            ServletRequestAttributes servletRequestAttributes =
                    (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpServletResponse response = servletRequestAttributes.getResponse();
            // 리프레시 토큰이 만료되면 DB에 장바구니 정보를 옮깁니다.
            MemberDetailInfoResponseDto memberDetailInfoResponseDto =
                    (MemberDetailInfoResponseDto) memberAspect.getThreadLocal(MEMBER_INFO);
            Long memberId = memberDetailInfoResponseDto.getMemberId();
            shoppingCartService.migrateCartRedisToDB(String.valueOf(memberId));
            deleteCookie(response, JwtUtil.ACCESS_COOKIE);
            Cookie sessionCookie = CookieUtil.findCookie(SESSION_ID);
//        redisTemplate.opsForHash().delete("SPRING_SECURITY_CONTEXT", sessionCookie.getValue());
//        deleteCookie(response, SESSION_ID);
            return "redirect:/login";
        }
        model.addAttribute("error", new CustomException(HttpStatus.NOT_FOUND, "페이지를 찾을 수 없습니다."));
        return "/error/errorPage";
    }
}