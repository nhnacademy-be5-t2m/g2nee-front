package com.t2m.g2nee.front.category.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.t2m.g2nee.front.category.dto.request.CategorySaveDto;
import com.t2m.g2nee.front.category.dto.response.CategoryHierarchyDto;
import com.t2m.g2nee.front.category.dto.response.CategoryInfoDto;
import com.t2m.g2nee.front.category.dto.response.CategoryUpdateDto;
import com.t2m.g2nee.front.utils.PageResponse;
import java.util.List;

public interface CategoryService {

    List<CategoryHierarchyDto> getRootCategories();

    List<CategoryInfoDto> getAllCategories();

    CategoryUpdateDto getCategory(Long categoryId);

    PageResponse<CategoryInfoDto> getCategoriesByName(String categoryName, int page);

    void creatCategory(CategorySaveDto request);

    void modifyCategory(Long categoryId, CategorySaveDto request);

    void deleteCategory(Long categoryId);

    void activeCategory(Long category);
}
