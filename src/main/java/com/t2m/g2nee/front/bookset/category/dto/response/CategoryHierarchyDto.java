package com.t2m.g2nee.front.bookset.category.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 카테고리 계층을 위한 dto
 *
 * @author : 김수빈
 * @since : 1.0
 */
@Getter
@NoArgsConstructor
public class CategoryHierarchyDto extends CategoryInfoDto {

    /**
     * 카테고리의 자식 카테고리들
     */
    private List<CategoryHierarchyDto> children;

    public CategoryHierarchyDto(CategoryInfoDto categoryInfoDto, List<CategoryHierarchyDto> children) {
        super(categoryInfoDto.getCategoryId(), categoryInfoDto.getCategoryName(), categoryInfoDto.getCategoryEngName(),
                categoryInfoDto.getIsActivated());
        this.children = children;
    }
}