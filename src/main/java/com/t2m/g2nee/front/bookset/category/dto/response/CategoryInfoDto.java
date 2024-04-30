package com.t2m.g2nee.front.bookset.category.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 카테고리 기본 정보의 dto
 *
 * @author : 김수빈
 * @since : 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryInfoDto {

    /**
     * 카테고리 id
     */
    private Long categoryId;
    /**
     * 카테고리 이름
     */
    private String categoryName;
    /**
     * 카테고리 영문 이름
     */
    private String categoryEngName;
    /**
     * 카테고리 활성화 여부
     */
    private Boolean isActivated;
}
