package com.t2m.g2nee.front.orderset.packageType.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 포장지 정보를 반환하기 윈한 객체입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PackageInfoDto {
    /**
     * 포장지 id
     */
    private Long packageId;
    /**
     * 포장지 이름
     */
    private String name;
    /**
     * 포장지 가격
     */
    private int price;
    /**
     * 포장지 활성화 여부
     */
    private Boolean isActivated;
}
