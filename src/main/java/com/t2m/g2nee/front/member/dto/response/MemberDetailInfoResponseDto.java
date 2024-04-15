package com.t2m.g2nee.front.member.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private List<String> authorities;
//    private List<MemberAddressResponseDto> address;

}