package com.t2m.g2nee.front.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.t2m.g2nee.front.config.dto.MemberInfoDto;
import com.t2m.g2nee.front.member.dto.response.MemberDetailInfoResponseDto;
import com.t2m.g2nee.front.token.util.JwtUtil;
import com.t2m.g2nee.front.utils.CookieUtil;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 로그인을 위한 custom filter
 *
 * @author : 정지은
 * @since : 1.0
 **/
@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends OncePerRequestFilter {
    private final RedisTemplate<String, MemberInfoDto> redisTemplate;
    private final ObjectMapper objectMapper;

    /**
     * 인증된 사용자면 로그인 상태 유지.
     *
     * @param request     요청 정보 객체.
     * @param response    응답 정보 객체.
     * @param filterChain security의 필터체인 객체.
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            if (notControllerUri(request)) {
                filterChain.doFilter(request, response);
                return;
            }


            Cookie sessionCookie = CookieUtil.findCookie("auth-session");
            if (notExistCookie(request, response, filterChain, sessionCookie)) {
                return;
            }

            Cookie jwtCookie = CookieUtil.findCookie(JwtUtil.ACCESS_COOKIE);
            if (notExistCookie(request, response, filterChain, jwtCookie)) {
                return;
            }

            String sessionId = Objects.requireNonNull(sessionCookie).getValue();
            MemberDetailInfoResponseDto member = (MemberDetailInfoResponseDto)
                    redisTemplate.opsForHash().get("SPRING_SECURITY_CONTEXT", sessionId);

            if (notExistLoginData(request, response, filterChain, member)) {
                return;
            }

            List<String> roles = Objects.requireNonNull(member).getAuthorities();

            List<SimpleGrantedAuthority> authorities =
                    roles.stream().map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(new UsernamePasswordAuthenticationToken(
                    member.getMemberId().toString(),
                    objectMapper.writeValueAsString(member),
                    authorities)
            );

            HttpSession session = request.getSession();
            session.setAttribute("username", member.getUsername());
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            SecurityContextHolder.clearContext();
        }
    }

    /**
     * 로그인 정보가 있는지 세션에서 확인하는 메소드
     *
     * @param request
     * @param response
     * @param filterChain
     * @param member      로그인 한 유저의 정보.
     * @return 정보의 존재 여부를 boolean으로 반환
     * @throws IOException
     * @throws ServletException
     */
    private static boolean notExistLoginData(HttpServletRequest request,
                                             HttpServletResponse response,
                                             FilterChain filterChain,
                                             MemberDetailInfoResponseDto member)
            throws IOException, ServletException {

        if (Objects.isNull(member)) {
            filterChain.doFilter(request, response);
            return true;
        }
        return false;
    }

    /**
     * 쿠키의 존재 여부를 확인하는 메소드
     *
     * @param request
     * @param response
     * @param filterChain
     * @param cookie      확인할 cookie
     * @return 쿠키의 존재 여부를 boolean으로 반환
     * @throws IOException
     * @throws ServletException
     */
    private static boolean notExistCookie(HttpServletRequest request,
                                          HttpServletResponse response,
                                          FilterChain filterChain,
                                          Cookie cookie) throws IOException, ServletException {
        if (Objects.isNull(cookie)) {
            filterChain.doFilter(request, response);
            return true;
        }
        return false;
    }

    /**
     * filter에 들어오지 않아도 되는 요청
     *
     * @param request request.
     * @return 들어오면 안되는 요청일 때 true 반환
     */
    private static boolean notControllerUri(HttpServletRequest request) {
        return request.getRequestURI().equals("/error")
                || request.getRequestURI().equals("/static");
    }

}