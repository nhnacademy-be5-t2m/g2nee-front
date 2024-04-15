package com.t2m.g2nee.front.config.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * SecurityContextHolder에 올라갈 Authdto
 *
 * @author : 정지은
 * @since : 1.0
 **/
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AuthDto {
    private String memberNo;
    private String memberPwd;
    private List<String> authorities;
}