package com.t2m.g2nee.front.bookset.category.controller;

import com.t2m.g2nee.front.bookset.category.dto.request.CategorySaveDto;
import com.t2m.g2nee.front.bookset.category.service.CategoryService;
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
     * 카테고리 목록 페이지를 보여줍니다.
     *
     * @return 카테고리 목록 페이지
     */
    @GetMapping("/categories")
    public String categoryList(Model model, @RequestParam(defaultValue = "1") int page) {
        model.addAttribute("categories", categoryService.getCategories(page));
        return "admin/category/adminCategoryList";
    }

    /**
     * 카테고리 저장 작성을 위한 view를 보여줍니다.
     *
     * @param model 카테고리 저장을 위한 상위 카테고리 목록들
     * @return 카테고리 저장 view 페이지
     */
    @GetMapping("/categories/save")
    public String createCategoryFrom(Model model) {
        model.addAttribute("categories", categoryService.getFirstAndSecondCategories());
        return "admin/category/adminCategorySave";
    }

    /**
     * 실제 카테고리를 저장 합니다. 저장 후, 카테고리 목록 페이지로 이동합니다.
     *
     * @return 카테고리 목록 페이지
     */
    @PostMapping("/categories/save")
    public String createCategory(@RequestParam("categoryName") String categoryName,
                                 @RequestParam("categoryEngName") String categoryEngName,
                                 @RequestParam("isActivated") boolean isActivated,
                                 @RequestParam("ancestorCategoryId") Long ancestorCategoryId) {

        CategorySaveDto request = new CategorySaveDto(categoryName, categoryEngName, isActivated, ancestorCategoryId);
        categoryService.creatCategory(request);
        return "redirect:/admin/categories";
    }

    /**
     * 카테고리 수정 view를 보여줍니다
     *
     * @param categoryId 수정할 카테고리 id
     * @param model      수정할 카테고리 정보
     * @return 카테고리 수정 view
     */
    @GetMapping("/categories/modify/{categoryId}")
    public String modifyCategoryFrom(@PathVariable("categoryId") Long categoryId, Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("category", categoryService.getCategory(categoryId));
        return "admin/category/adminCategoryModify";
    }

    /**
     * 실제 카테고리를 수정합니다.
     *
     * @param categoryId         수정할 카테고리 id
     * @param categoryName       수정할 이름
     * @param categoryEngName    수정할 영문이름
     * @param isActivated        활성화 여부
     * @param ancestorCategoryId 상위 카테고리 id
     * @return 카테고리 목록으로 이동
     */
    @PutMapping("/categories/modify/{categoryId}")
    public String modifyCategory(@PathVariable("categoryId") Long categoryId,
                                 @RequestParam("categoryName") String categoryName,
                                 @RequestParam("categoryEngName") String categoryEngName,
                                 @RequestParam("isActivated") boolean isActivated,
                                 @RequestParam("ancestorCategoryId") Long ancestorCategoryId) {
        CategorySaveDto request = new CategorySaveDto(categoryName, categoryEngName, isActivated, ancestorCategoryId);
        categoryService.modifyCategory(categoryId, request);
        return "redirect:/admin/categories";
    }//카테고리 반영속도가 느림


    /**
     * 카테고리를 이름으로 검색합니다.
     *
     * @param name  카테고리 이름
     * @param page  검색 페이지
     * @param model 검색된 카테고리를 저장하기 위한 model
     * @return 카테고리 검색 목록
     */
    @GetMapping("/categories/search")
    public String categorySearch(@RequestParam("name") String name,
                                 @RequestParam(defaultValue = "1") int page, Model model) {
        model.addAttribute("searchCategoriesPage", categoryService.getCategoriesByName(name, page));
        return "admin/category/adminSearchCategoryList";
    }

    /**
     * 카테고리 비활성화합니다.
     *
     * @param categoryId 비활성화할 카테고리 id
     * @return 카테고리 목록
     */
    @DeleteMapping("/categories/{categoryId}")
    public String categoryDelete(@PathVariable("categoryId") Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return "redirect:/admin/categories";
    }

    /**
     * 카테고리 활성화합니다.
     *
     * @param categoryId 활성화할 카테고리 id
     * @return 카테고리 목록
     */
    @PatchMapping("/categories/{categoryId}")
    public String categoryActivate(@PathVariable("categoryId") Long categoryId) {
        categoryService.activeCategory(categoryId);
        return "redirect:/admin/categories";
    }
}
