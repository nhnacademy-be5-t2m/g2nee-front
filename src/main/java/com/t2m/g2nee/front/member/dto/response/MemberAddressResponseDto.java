package com.t2m.g2nee.front.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Member의 주소를 받아올 dto
 *
 * @author : 정지은
 * @since : 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberAddressResponseDto {
    private String address;
    private String alias;
    private String detail;
    private boolean isDefault;
    private String zipcode;
}
