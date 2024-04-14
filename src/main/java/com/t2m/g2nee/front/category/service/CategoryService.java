package com.t2m.g2nee.front.category.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.t2m.g2nee.front.category.dto.request.CategorySaveDto;
import com.t2m.g2nee.front.category.dto.response.CategoryHierarchyDto;
import com.t2m.g2nee.front.category.dto.response.CategoryInfoDto;
import com.t2m.g2nee.front.category.dto.response.CategoryUpdateDto;
import com.t2m.g2nee.front.utils.PageResponse;
import java.util.List;

public interface CategoryService {

    List<CategoryHierarchyDto> getRootCategories() throws JsonProcessingException;

    List<CategoryInfoDto> getAllCategories() throws JsonProcessingException;

    CategoryUpdateDto getCategory(Long categoryId) throws JsonProcessingException;

    PageResponse<CategoryInfoDto> getCategoriesByName(String categoryName, int page) throws JsonProcessingException;

    void creatCategory(CategorySaveDto request) throws JsonProcessingException;

    void modifyCategory(Long categoryId, CategorySaveDto request) throws JsonProcessingException;

    void deleteCategory(Long categoryId) throws JsonProcessingException;

    void activeCategory(Long category) throws JsonProcessingException;
}
