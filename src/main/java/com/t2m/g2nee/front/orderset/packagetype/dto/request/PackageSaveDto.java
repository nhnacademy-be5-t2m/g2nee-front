package com.t2m.g2nee.front.orderset.packagetype.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 포장지를 저장하기 위한 객체입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PackageSaveDto {

    /**
     * 저장할 포장지 이름
     */
    private String name;

    /**
     * 포장지 가격
     */
    private Integer price;

    /**
     * 포장지 활성 유무
     */
    private Boolean isActivated;
}
