package com.t2m.g2nee.front.bookset.category.service.impl;

import com.t2m.g2nee.front.bookset.category.adaptor.CategoryAdaptor;
import com.t2m.g2nee.front.bookset.category.dto.request.CategorySaveDto;
import com.t2m.g2nee.front.bookset.category.dto.response.CategoryHierarchyDto;
import com.t2m.g2nee.front.bookset.category.dto.response.CategoryInfoDto;
import com.t2m.g2nee.front.bookset.category.dto.response.CategoryUpdateDto;
import com.t2m.g2nee.front.bookset.category.service.CategoryService;
import com.t2m.g2nee.front.utils.PageResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

/**
 * Category 서비스
 * 캐시를 사용하여 빠르고 불필요한 통신이 이뤄지지 않게 함
 *
 * @author : 김수빈
 * @since : 1.0
 */
@Service
@CacheConfig(cacheNames = "categories")
public class CategoryServiceImpl implements CategoryService {

    private final CategoryAdaptor adaptor;
    private static final int MAXPAGEBUTTONS = 5;

    public CategoryServiceImpl(CategoryAdaptor adaptor) {
        this.adaptor = adaptor;
    }


    @Cacheable(key = "'root'")
    @Override
    public List<CategoryHierarchyDto> getRootCategories() {
        return adaptor.getRootCategories();
    }


    /**
     * 모든 카테고리 목록을 재귀를 통해 얻어옴
     *
     * @return 모든 카테고리 목록
     */
    @Cacheable(key = "'all'")
    @Override
    public List<CategoryHierarchyDto> getAllCategories() {
        List<CategoryHierarchyDto> allCategories = new ArrayList<>();
        List<CategoryHierarchyDto> rootCategories = getRootCategories();

        for (CategoryHierarchyDto root : rootCategories) {
            allCategories.add(root);
            allCategories.addAll(getAllSubCategories(root.getChildren()));
        }

        return allCategories;
    }

    public List<CategoryHierarchyDto> getAllSubCategories(List<CategoryHierarchyDto> categories) {
        List<CategoryHierarchyDto> allSubCategories = new ArrayList<>();
        for (CategoryHierarchyDto category : categories) {
            allSubCategories.add(category);
            allSubCategories.addAll(category.getChildren());
        }
        return allSubCategories;
    }

    /**
     * 카테고리의 1단계, 2단계에 해당하는 카테고리만 가져옴
     *
     * @return 카테고리 1단계, 2단계 카테고리 목록
     */
    public List<CategoryHierarchyDto> getFirstAndSecondCategories() {
        List<CategoryHierarchyDto> categories = new ArrayList<>();
        List<CategoryHierarchyDto> rootCategories = getRootCategories();

        // 1단계 카테고리 추가
        categories.addAll(rootCategories);

        for (CategoryHierarchyDto root : rootCategories) {
            List<CategoryHierarchyDto> children = root.getChildren();
            if (children != null && (!children.isEmpty())) {
                categories.addAll(children); // 2단계 카테고리 추가
            }
        }

        return categories;
    }


    @Cacheable(key = "#categoryId", unless = "#result == null")
    @Override
    public CategoryUpdateDto getCategory(Long categoryId) {
        return adaptor.getCategory(categoryId);
    }

    @Override
    public PageResponse<CategoryInfoDto> getCategoriesByName(String categoryName, int page) {
        return adaptor.getCategoriesByName(categoryName, page);
    }

    @Override
    public PageResponse<CategoryHierarchyDto> getCategories(int page) {
        List<CategoryHierarchyDto> categories = getRootCategories();

        Long pageSize = 1L;

        List<CategoryHierarchyDto> categoryList = categories.stream()
                .skip((page - 1) * pageSize).limit(pageSize)
                .collect(Collectors.toList());


        int totalCategories = categories.size();
        int totalPages = (int) Math.ceil((double) totalCategories / pageSize);

        int startPage =
                Math.max(1, Math.min(totalPages - MAXPAGEBUTTONS + 1, Math.max(1, page - (MAXPAGEBUTTONS / 2))));
        int endPage = Math.min(startPage + MAXPAGEBUTTONS - 1, totalPages);

        if (endPage - startPage + 1 < MAXPAGEBUTTONS) {
            startPage = Math.max(1, endPage - MAXPAGEBUTTONS + 1);
        }

        return PageResponse.<CategoryHierarchyDto>builder()
                .data(categoryList)
                .currentPage(page)
                .totalPage(totalPages)
                .startPage(startPage)
                .endPage(endPage)
                .totalElements(Long.valueOf(categories.size()))
                .build();
    }

    @Caching(evict = {
            @CacheEvict(key = "'all'"),
            @CacheEvict(key = "'root'")
    })
    @Override
    public void creatCategory(CategorySaveDto request) {
        adaptor.requestCreatCategory(request);
    }

    @Caching(evict = {
            @CacheEvict(key = "'all'"),
            @CacheEvict(key = "'root'"),
            @CacheEvict(key = "#categoryId")
    })
    @Override
    public void modifyCategory(Long categoryId, CategorySaveDto request) {
        adaptor.requestModifyCategory(categoryId, request);
    }

    @Caching(evict = {
            @CacheEvict(key = "'all'"),
            @CacheEvict(key = "'root'"),
            @CacheEvict(key = "#categoryId")
    })
    @Override
    public void deleteCategory(Long categoryId) {
        adaptor.requestDeleteCategory(categoryId);
    }

    @Caching(evict = {
            @CacheEvict(key = "'all'"),
            @CacheEvict(key = "'root'"),
            @CacheEvict(key = "#categoryId")
    })
    @Override
    public void activeCategory(Long categoryId) {
        adaptor.requestActiveCategory(categoryId);

    }
}
