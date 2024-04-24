package com.t2m.g2nee.front.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 기본 회원정보의 응답을 받는 response
 *
 * @author : 정지은
 * @since : 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponse {
    private String userName;
    private String name;
    private String nickName;
    private String grade;
}