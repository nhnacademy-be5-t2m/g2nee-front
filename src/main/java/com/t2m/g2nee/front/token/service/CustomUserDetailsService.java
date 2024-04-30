package com.t2m.g2nee.front.token.service;

import static com.t2m.g2nee.front.token.util.JwtUtil.SESSION_ID;

import com.t2m.g2nee.front.member.dto.response.MemberDetailInfoResponseDto;
import com.t2m.g2nee.front.member.service.MemberService;
import com.t2m.g2nee.front.utils.CookieUtil;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final RedisTemplate<String, MemberDetailInfoResponseDto> redisTemplate;
    private final MemberService memberService;

    /**
     * 사용자 정보가 담긴 UserDetails 을 반환해주는 메소드
     *
     * @param accessToken 인증받은 accessToken.
     * @return 사용자 정보가 담겨있는 UserDetail
     * @throws RuntimeException 유효하지 않는 사용자 정보인 경우 예외를 던진다.
     */
    @Override
    public UserDetails loadUserByUsername(String accessToken) throws RuntimeException {

        ServletRequestAttributes servletRequestAttributes
                = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        HttpServletResponse response = servletRequestAttributes.getResponse();

        String sessionId = request.getSession().getId();
        CookieUtil.makeCookie(response, SESSION_ID, sessionId);

        MemberDetailInfoResponseDto member
                = memberService.getMemberDetailFromAccessToken(accessToken);

        if (Objects.isNull(member)) {
            throw new RuntimeException("member 정보가 없습니다.");
        }

        List<String> roles = Objects.requireNonNull(member).getAuthorities();

        List<SimpleGrantedAuthority> authorities =
                roles.stream().map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        redisTemplate.opsForHash().put("SPRING_SECURITY_CONTEXT", sessionId, member);

        return new User(member.getMemberId().toString(),
                "dummy",
                authorities);
    }

}