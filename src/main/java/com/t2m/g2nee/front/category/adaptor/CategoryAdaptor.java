package com.t2m.g2nee.front.category.adaptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.t2m.g2nee.front.category.dto.request.CategorySaveDto;
import com.t2m.g2nee.front.category.dto.response.CategoryHierarchyDto;
import com.t2m.g2nee.front.category.dto.response.CategoryInfoDto;
import com.t2m.g2nee.front.category.dto.response.CategoryUpdateDto;
import com.t2m.g2nee.front.utils.PageResponse;
import java.util.List;

/**
 * 백엔드에서 카테고리와 관련된 값을 가져오는 인터페이스
 *
 * @author : 김수빈
 * @since : 1.0
 */
public interface CategoryAdaptor {

    /**
     * 카테고리를 계층으로 가져옴
     *
     * @return
     */
    List<CategoryHierarchyDto> getRootCategories();

    /**
     * 모든 카테고리를 가져옴
     *
     * @return
     */
    List<CategoryInfoDto> getAllCategories();

    /**
     * 카테고리 단건 조회
     *
     * @param categoryId
     * @return
     */
    CategoryUpdateDto getCategory(Long categoryId);

    /**
     * 카테고리명으로 검색 및 페이징
     *
     * @param categoryName
     * @param page
     * @return
     */
    PageResponse<CategoryInfoDto> getCategoriesByName(String categoryName, int page);

    /**
     * 카테고리 생성
     *
     * @param request
     * @return
     * @throws JsonProcessingException
     */
    CategoryInfoDto requestCreatCategory(CategorySaveDto request) throws JsonProcessingException;

    /**
     * 카테고리 수정
     *
     * @param categoryId
     * @param request
     * @return
     */
    CategoryInfoDto requestModifyCategory(Long categoryId, CategorySaveDto request);

    /**
     * 카테고리 삭제
     *
     * @param categoryId
     * @return
     */
    Boolean requestDeleteCategory(Long categoryId);

    /**
     * 카테고리 활성화
     *
     * @param category
     * @return
     */
    Boolean requestActiveCategory(Long category);
}
