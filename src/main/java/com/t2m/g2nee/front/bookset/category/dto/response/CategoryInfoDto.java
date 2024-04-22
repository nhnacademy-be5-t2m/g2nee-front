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

    //카테고리 정보
    private Long categoryId;
    private String categoryName;
    private String categoryEngName;
    private Boolean isActivated;
}
