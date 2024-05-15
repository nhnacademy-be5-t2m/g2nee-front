package com.t2m.g2nee.front.bookset.category.service;

import com.t2m.g2nee.front.bookset.category.dto.request.CategorySaveDto;
import com.t2m.g2nee.front.bookset.category.dto.response.CategoryHierarchyDto;
import com.t2m.g2nee.front.bookset.category.dto.response.CategoryInfoDto;
import com.t2m.g2nee.front.bookset.category.dto.response.CategoryUpdateDto;
import com.t2m.g2nee.front.utils.PageResponse;
import java.util.List;

/**
 * 카테고리 서비스입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
public interface CategoryService {

    /**
     * 카테고리를 계층한 목록을 반환합니다.
     *
     * @return List<CategoryHierarchyDto>
     */
    List<CategoryHierarchyDto> getRootCategories();

    /**
     * 모든 카테고리 목록을 반환합니다.
     *
     * @return
     */
    List<CategoryHierarchyDto> getAllCategories();

    /**
     * 특정 카테고리 하나를 반환합니다.
     *
     * @param categoryId 카테고리 id
     * @return
     */
    CategoryUpdateDto getCategory(Long categoryId);

    /**
     * 카테고리를 이름으로 검색하고 페이징하여 반환합니다.
     *
     * @param categoryName 카테고리 이름
     * @param page         현제 페이지
     * @return
     */
    PageResponse<CategoryInfoDto> getCategoriesByName(String categoryName, int page);

    /**
     * 카테고리를 페이징하여 반환합니다.
     * @param categoryName
     * @param page
     * @return
     */
    PageResponse<CategoryHierarchyDto> getCategories(int page);

    /**
     * 카테고리를 생성합니다.
     *
     * @param request
     */
    void creatCategory(CategorySaveDto request);

    /**
     * 카테고리를 수정합니다.
     *
     * @param categoryId
     * @param request
     */
    void modifyCategory(Long categoryId, CategorySaveDto request);

    /**
     * 카테고르릴 softdelete합니다.
     *
     * @param categoryId
     */
    void deleteCategory(Long categoryId);

    /**
     * 카테고리를 활성화합니다.
     *
     * @param category
     */
    void activeCategory(Long category);

    /**
     * 카1단계, 2단계 카테고리만 가져옵니다.
     *
     * @return
     */
    List<CategoryHierarchyDto> getFirstAndSecondCategories();
}
