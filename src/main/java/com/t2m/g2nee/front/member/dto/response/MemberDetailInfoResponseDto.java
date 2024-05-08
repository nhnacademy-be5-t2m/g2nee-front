package com.t2m.g2nee.front.member.dto.response;

import com.t2m.g2nee.front.member.dto.MemberStatus;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Member의 세부 정보를 받아올 dto
 *
 * @author : 정지은
 * @since : 1.0
 */
@Getter
@NoArgsConstructor
public class MemberDetailInfoResponseDto {
    private Long memberId;
    private String name;
    private String username;
    private String nickname;
    private String gender;
    private String birthday;
    private String phoneNumber;
    private String email;
    private String grade;
    private MemberStatus status;
    private List<String> authorities;
//    private List<MemberAddressResponseDto> address;

}