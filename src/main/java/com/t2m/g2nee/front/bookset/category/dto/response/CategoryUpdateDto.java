package com.t2m.g2nee.front.bookset.category.dto.response;


import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 카테고리 업데이트를 위한 dto
 *
 * @author : 김수빈
 * @since : 1.0
 */
@NoArgsConstructor
public class CategoryUpdateDto extends CategoryHierarchyDto{

    @Getter
    //틍록할 카테고리의 바로 상위 카테고리Id
    private Long ancestorCategoryId;

    public CategoryUpdateDto(CategoryInfoDto categoryInfoDto, List<CategoryHierarchyDto> children, Long ancestorCategoryId) {
        super(categoryInfoDto, children);
        this.ancestorCategoryId = ancestorCategoryId;
    }
}
