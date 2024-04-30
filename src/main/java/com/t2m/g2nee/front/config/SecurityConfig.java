package com.t2m.g2nee.front.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.t2m.g2nee.front.config.dto.MemberInfoDto;
import com.t2m.g2nee.front.filter.CustomAuthenticationFilter;
import com.t2m.g2nee.front.filter.CustomLoginFilter;
import com.t2m.g2nee.front.member.service.MemberService;
import com.t2m.g2nee.front.token.provider.CustomAuthenticationProvider;
import com.t2m.g2nee.front.token.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

/**
 * Security 설정 클래스
 *
 * @author : 정지은
 * @since : 1.0
 */
@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final MemberService memberService;
    private final RedisTemplate<String, MemberInfoDto> redisTemplate;
    private final CustomUserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;

    /**
     * spring security로 HTTP보안을 구성하는 메소드
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager
                = authenticationManager(http.getSharedObject(AuthenticationConfiguration.class));

        http.authorizeRequests()
                .antMatchers("/", "/login", "/signup").permitAll()
                .antMatchers("/mypage/**").hasAuthority("ROLE_USER")
                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                .anyRequest().permitAll()
                .and()
                .csrf()
                .disable().cors().disable()
                .formLogin()
                .disable()
                .logout()
                .disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterAt(customLoginFilter(authenticationManager),
                UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(customAuthenticationFilter(), SecurityContextPersistenceFilter.class);

    }


    /**
     * 유저의 비밀번호를 암호화, 검증 해주는 메소드 빈.
     *
     * @return BCryptEncoder를 반환.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * authenticationManager 의 빈 등록 메소드.
     *
     * @param authenticationConfiguration
     * @return authentication manager.
     * @throws Exception authentication maneger 를 가져오는 중에 발생하는 에러
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * 로그인 처리를 위한 빈 등록메소드
     *
     * @param authenticationManager token발행을 위한 로직이 실행되게 도와주는 클래스.
     * @return 커스텀한 로그인 필터.
     */
    @Bean
    public CustomLoginFilter customLoginFilter(AuthenticationManager authenticationManager) {
        CustomLoginFilter loginFilter
                = new CustomLoginFilter(memberService);
        loginFilter.setFilterProcessesUrl("/member/auth");
        loginFilter.setAuthenticationManager(authenticationManager);
        loginFilter.setUsernameParameter("username");
        loginFilter.setPasswordParameter("password");

        return loginFilter;
    }


    /**
     * authenticate의 구현을 담당한 provider 빈 등록
     *
     * @return 커스텀 한 authenticationProvider.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        CustomAuthenticationProvider authenticationProvider = new CustomAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }

    /**
     * 토큰을 securityContextHolder 에 넣어줄 필터를 위한 빈 등록
     *
     * @return 커스텀한 인증필터 반환
     */
    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter() {
        return new CustomAuthenticationFilter(redisTemplate, objectMapper);
    }

}
