package com.t2m.g2nee.front.bookset.category.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 카테고리 저장을 위한 dto
 *
 * @author : 김수빈
 * @since : 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategorySaveDto {

    /**
     * 카테고리 이름
     */
    private String categoryName;

    /**
     * 카테고리 영물 이름
     */
    private String categoryEngName;

    /**
     * 카테고리 활성화 여부
     */
    private Boolean isActivated;

    /**
     * 등록할 카테고리의 바로 상위 id
     */
    private Long ancestorCategoryId;
}