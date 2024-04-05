package com.t2m.g2nee.front.category.service.impl;

import com.t2m.g2nee.front.category.adaptor.CategoryAdaptor;
import com.t2m.g2nee.front.category.dto.request.CategorySaveDto;
import com.t2m.g2nee.front.category.dto.response.CategoryInfoDto;
import com.t2m.g2nee.front.category.service.CategoryService;
import com.t2m.g2nee.front.utils.PageResponse;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryAdaptor adaptor;

    public CategoryServiceImpl(CategoryAdaptor adaptor) {
        this.adaptor = adaptor;
    }


    @Override
    public List<CategoryInfoDto> getRootCategories() {
        return adaptor.getRootCategories();
    }

    @Override
    public List<CategoryInfoDto> getSubCategories(Long categoryId) {
        return adaptor.getSubCategories(categoryId);
    }

    @Override
    public PageResponse<CategoryInfoDto> getAllCategories(int page) {
        return adaptor.getAllCategories(page);
    }

    @Override
    public CategoryInfoDto getCategory(Long categoryId) {
        return adaptor.getCategory(categoryId);
    }

    @Override
    public PageResponse<CategoryInfoDto> getCategoriesByName(String categoryName, int page) {
        return adaptor.getCategoriesByName(categoryName, page);
    }

    @Override
    public void creatCategory(CategorySaveDto request) {
        adaptor.requestCreatCategory(request);
    }

    @Override
    public void modifyCategory(Long categoryId, CategorySaveDto request) {
        adaptor.requestModifyCategory(categoryId, request);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        adaptor.requestDeleteCategory(categoryId);
    }

    @Override
    public void activeCategory(Long categoryId) {
        adaptor.requestActiveCategory(categoryId);

    }
}
