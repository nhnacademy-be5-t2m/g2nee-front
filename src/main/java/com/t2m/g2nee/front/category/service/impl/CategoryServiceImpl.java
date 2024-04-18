package com.t2m.g2nee.front.category.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.t2m.g2nee.front.category.adaptor.CategoryAdaptor;
import com.t2m.g2nee.front.category.dto.request.CategorySaveDto;
import com.t2m.g2nee.front.category.dto.response.CategoryHierarchyDto;
import com.t2m.g2nee.front.category.dto.response.CategoryInfoDto;
import com.t2m.g2nee.front.category.dto.response.CategoryUpdateDto;
import com.t2m.g2nee.front.category.service.CategoryService;
import com.t2m.g2nee.front.utils.PageResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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

    public CategoryServiceImpl(CategoryAdaptor adaptor) {
        this.adaptor = adaptor;
    }


    @Cacheable(key = "'root'")
    @Override
    public List<CategoryHierarchyDto> getRootCategories() {
        return adaptor.getRootCategories();
    }


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

    @Cacheable(key = "#categoryId", unless = "#result == null")
    @Override
    public CategoryUpdateDto getCategory(Long categoryId) {
        return adaptor.getCategory(categoryId);
    }

    @Override
    public PageResponse<CategoryInfoDto> getCategoriesByName(String categoryName, int page) {
        return adaptor.getCategoriesByName(categoryName, page);
    }

    @Caching(evict = {
            @CacheEvict(key = "'all'"),
            @CacheEvict(key = "'root'")
    })
    @Override
    public void creatCategory(CategorySaveDto request){
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
