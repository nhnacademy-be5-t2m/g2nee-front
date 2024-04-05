package com.t2m.g2nee.front.category.adaptor;

import com.t2m.g2nee.front.category.dto.request.CategorySaveDto;
import com.t2m.g2nee.front.category.dto.response.CategoryInfoDto;
import com.t2m.g2nee.front.utils.PageResponse;
import java.util.List;

public interface CategoryAdaptor {

    List<CategoryInfoDto> getRootCategories();

    List<CategoryInfoDto> getSubCategories(Long categoryId);

    PageResponse<CategoryInfoDto> getAllCategories(int page);

    CategoryInfoDto getCategory(Long categoryId);

    PageResponse<CategoryInfoDto> getCategoriesByName(String categoryName, int page);

    CategoryInfoDto requestCreatCategory(CategorySaveDto request);

    CategoryInfoDto requestModifyCategory(Long categoryId, CategorySaveDto request);

    Boolean requestDeleteCategory(Long categoryId);

    Boolean requestActiveCategory(Long category);
}
