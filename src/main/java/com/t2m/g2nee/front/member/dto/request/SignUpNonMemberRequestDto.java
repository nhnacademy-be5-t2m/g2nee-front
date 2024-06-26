package com.t2m.g2nee.front.member.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 비회원 정보저장 시 입력한 정보 request DTO개체.
 *
 * @author : 정지은
 * @since : 1.0
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpNonMemberRequestDto {
    @Pattern(regexp = "^[a-zA-Z가-힣]{2,20}$", message = "영어와 한글만 사용하여 2-20자의 형식으로 작성하여 주십시오.")
    private String name;

    @NotEmpty(message = "비밀번호를 입력하여 주십시오.")
    private String password;

    @Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "올바른 email 형식으로 작성하여 주십시오.")
    private String email;

    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "'-'를 포함한 전화번호형식으로 작성하여 주십시오.")
    private String phoneNumber;

}
