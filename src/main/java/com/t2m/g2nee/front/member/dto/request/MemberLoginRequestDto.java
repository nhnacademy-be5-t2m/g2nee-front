package com.t2m.g2nee.front.member.dto.request;

import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 회원의 로그인정보를 받기위한 클래스
 *
 * @author : 정지은
 * @since : 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginRequestDto {

    @Pattern(regexp = "^[a-zA-Z0-9]{4,20}$|^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "영어와 숫자만 사용한 4-20자의 형식이나 이메일의 형식으로 작성하여 주십시오.")
    private String userName;

    @Pattern(regexp = "^(?=.*?[A-Za-z])(?=.*?[0-9])(?=.*?[~?!@#$%^&*_-]).{8,20}$", message = "영어 숫자, 특수문자를 포함하여 8-20자의 형식으로 작성하여 주십시오.")
    private String password;
}
