package com.t2m.g2nee.front.admin.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.t2m.g2nee.front.category.dto.request.CategorySaveDto;
import com.t2m.g2nee.front.category.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 카테고리 관리자페이지 접근을 위한 컨트롤러입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
@Controller
@RequestMapping("/admin")
public class AdminCategoryController {

    private final CategoryService categoryService;

    public AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * 관리자페이지: 카테고리 관리 페이지 보이기
     * @return
     */
    @GetMapping("/categories")
    public String categoryList() {

        return "admin/category/adminCategoryList";
    }

    /**
     * 카테고리 저장 작성 양식 보이기
     * @param model
     * @return
     * @throws JsonProcessingException
     */
    @GetMapping("/categories/save")
    public String createCategoryFrom(Model model){
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/category/adminCategorySave";
    }

    /**
     * 실제 카테고리 저장
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping("/categories/save")
    public String createCategory(@RequestParam("categoryName") String categoryName,
                                     @RequestParam("categoryEngName") String categoryEngName,
                                     @RequestParam("isActivated") boolean isActivated,
                                     @RequestParam("ancestorCategoryId") Long ancestorCategoryId){

        CategorySaveDto request = new CategorySaveDto(categoryName, categoryEngName, isActivated, ancestorCategoryId);
        categoryService.creatCategory(request);
        return "redirect:/admin/categories";
    }

    /**
     * 카테고리 수정 양식 보이기
     * @param categoryId
     * @param model
     * @return
     * @throws JsonProcessingException
     */
    @GetMapping("/categories/modify/{categoryId}")
    public String modifyCategoryFrom(@PathVariable("categoryId") Long categoryId, Model model){
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("category", categoryService.getCategory(categoryId));
        return "admin/category/adminCategoryModify";
    }

    /**
     * 실제 카테고리 수정
     * @param categoryId
     * @return
     * @throws JsonProcessingException
     */
    @PutMapping("/categories/modify/{categoryId}")
    public String modifyCategory(@PathVariable("categoryId") Long categoryId,
                                 @RequestParam("categoryName") String categoryName,
                                 @RequestParam("categoryEngName") String categoryEngName,
                                 @RequestParam("isActivated") boolean isActivated,
                                 @RequestParam("ancestorCategoryId") Long ancestorCategoryId){
        CategorySaveDto request = new CategorySaveDto(categoryName, categoryEngName, isActivated, ancestorCategoryId);
        categoryService.modifyCategory(categoryId, request);
        return "redirect:/admin/categories";
    }


    /**
     * 카테고리 이름으로 검색
     * @return
     */
    @GetMapping("/categories/search")
    public String categorySearch(@RequestParam("name") String name,
                                 @RequestParam int page, Model model) throws JsonProcessingException {
        model.addAttribute("searchCategoriesPage", categoryService.getCategoriesByName(name, page));
        return "admin/category/adminSearchCategoryList";
    }

    /**
     * 카테고리 비활성화
     * @param categoryId
     * @return
     */
    @DeleteMapping("/categories/{categoryId}")
    public String categoryDelete(@PathVariable("categoryId") Long categoryId){
        categoryService.deleteCategory(categoryId);
        return "redirect:/admin/categories";
    }

    /**
     * 카테고리 활성화
     * @param categoryId
     * @return
     */
    @PatchMapping("/categories/{categoryId}")
    public String categoryActivate(@PathVariable("categoryId") Long categoryId){
        categoryService.activeCategory(categoryId);
        return "redirect:/admin/categories";
    }
}
