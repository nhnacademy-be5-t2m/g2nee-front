package com.t2m.g2nee.front.main.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.t2m.g2nee.front.category.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    private final CategoryService categoryService;

    public MainController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String main() {
        return "redirect:/books";
    }

    @GetMapping("/shop")
    public String shop(@RequestParam(required = false, defaultValue = "0") Long id,
                       Model model) throws JsonProcessingException {

        //혹시 url로 직접 shop을 치고 들어갔을 경우, 오류를 막기 위해 기본 설정을 해줌
        if (id == 0L) {
            id = categoryService.getAllCategories().get(0).getCategoryId();
        }

        model.addAttribute("category", categoryService.getCategory(id));

        return "main/shop";
    }

    @GetMapping("/shopDetail")
    public String detail() {
        return "main/detail";
    }

    @GetMapping("/cart")
    public String cart() {
        return "main/cart";
    }
}
