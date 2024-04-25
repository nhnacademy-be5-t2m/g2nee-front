package com.t2m.g2nee.front.bookset.category.controller;


import com.t2m.g2nee.front.bookset.category.dto.response.CategoryHierarchyDto;
import com.t2m.g2nee.front.bookset.category.service.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryRestController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryHierarchyDto>> getRootCategories() {

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(categoryService.getRootCategories());
    }

    @GetMapping("/{categoryId}/children")
    public ResponseEntity<List<CategoryHierarchyDto>> getChildCategories(@PathVariable("categoryId") Long categoryId){

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(categoryService.getCategory(categoryId).getChildren());
    }
}
