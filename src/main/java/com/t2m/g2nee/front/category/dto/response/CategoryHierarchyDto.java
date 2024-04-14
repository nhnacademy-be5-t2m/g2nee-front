package com.t2m.g2nee.front.category.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryHierarchyDto extends CategoryInfoDto{

    @Getter
    private List<CategoryHierarchyDto> children;

    public CategoryHierarchyDto(CategoryInfoDto categoryInfoDto, List<CategoryHierarchyDto> children) {
        super(categoryInfoDto.getCategoryId(), categoryInfoDto.getCategoryName(), categoryInfoDto.getCategoryEngName(), categoryInfoDto.getIsActivated());
        this.children = children;
    }
}