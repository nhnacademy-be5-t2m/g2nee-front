package com.t2m.g2nee.front.bookset.category.service;

import com.t2m.g2nee.front.bookset.category.dto.request.CategorySaveDto;
import com.t2m.g2nee.front.bookset.category.dto.response.CategoryHierarchyDto;
import com.t2m.g2nee.front.bookset.category.dto.response.CategoryInfoDto;
import com.t2m.g2nee.front.bookset.category.dto.response.CategoryUpdateDto;
import com.t2m.g2nee.front.utils.PageResponse;
import java.util.List;

public interface CategoryService {

    List<CategoryHierarchyDto> getRootCategories();

    List<CategoryHierarchyDto> getAllCategories();

    CategoryUpdateDto getCategory(Long categoryId);

    PageResponse<CategoryInfoDto> getCategoriesByName(String categoryName, int page);

    void creatCategory(CategorySaveDto request);

    void modifyCategory(Long categoryId, CategorySaveDto request);

    void deleteCategory(Long categoryId);

    void activeCategory(Long category);

    List<CategoryHierarchyDto> getFirstAndSecondCategories();
}
