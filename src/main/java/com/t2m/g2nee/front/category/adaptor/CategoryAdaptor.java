package com.t2m.g2nee.front.category.adaptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.t2m.g2nee.front.category.dto.request.CategorySaveDto;
import com.t2m.g2nee.front.category.dto.response.CategoryHierarchyDto;
import com.t2m.g2nee.front.category.dto.response.CategoryInfoDto;
import com.t2m.g2nee.front.category.dto.response.CategoryUpdateDto;
import com.t2m.g2nee.front.utils.PageResponse;
import java.util.List;

public interface CategoryAdaptor {

    List<CategoryHierarchyDto> getRootCategories();

    List<CategoryInfoDto> getAllCategories();

    CategoryUpdateDto getCategory(Long categoryId);

    PageResponse<CategoryInfoDto> getCategoriesByName(String categoryName, int page);

    CategoryInfoDto requestCreatCategory(CategorySaveDto request) throws JsonProcessingException;

    CategoryInfoDto requestModifyCategory(Long categoryId, CategorySaveDto request);

    Boolean requestDeleteCategory(Long categoryId);

    Boolean requestActiveCategory(Long category);
}
