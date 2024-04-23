package com.t2m.g2nee.front.bookset.book.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.t2m.g2nee.front.bookset.book.dto.BookDto;
import com.t2m.g2nee.front.bookset.book.service.BookGetService;
import com.t2m.g2nee.front.bookset.category.service.CategoryService;
import com.t2m.g2nee.front.utils.PageResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 책 관리 controller 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookGetService bookGetService;
    private final CategoryService categoryService;
    /**
     * 책 하나 정보를 가져오는 컨트롤러 입니다.
     */
    @GetMapping("/{bookId}")
    public String getBook(@PathVariable("bookId") Long bookId,
                          Model model) {

        BookDto.Response response = bookGetService.getBook(bookId);
        model.addAttribute("book", response);

        return "book/bookDetail";
    }


    /**
     * 메인 페이지 최근 발매된 책 6권을 조회하는 컨트롤러
     */
    @GetMapping("/new")
    public String getNewBooks(Model model) {

        List<BookDto.ListResponse> bookList = bookGetService.getNewBooks();
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("bookList", bookList);

        return "main/index";
    }

    /**
     * 키워드로 책을 검색하는 컨트롤러
     * @param keyword 키워드
     * @param page 페이지 번호
     * @param sort 정렬 조건
     */
    @GetMapping("/search")
    public String getBookBySearch(Model model,
                                  @RequestParam(defaultValue = "") String keyword,
                                  @RequestParam(defaultValue = "1") int page,
                                  @RequestParam(required = false) String sort) {

        if (!StringUtils.hasText(sort)) {
            sort = "viewCount";
        }

        PageResponse<BookDto.ListResponse> bookPage = bookGetService.getBooksBySearch(page, keyword, sort);
        model.addAttribute("keyword", keyword);
        model.addAttribute("bookPage", bookPage);
        model.addAttribute("sort", BookDto.Sort.valueOf(sort.toUpperCase()).getValue());



        return "book/bookList";
    }

    /**
     * 카테고리와 키워드로 검색한 책을 조회하는 컨트롤러
     * @param keyword 검색할 카워드
     * @param page 페이지 번호
     * @param sort 정렬 조건
     * @param categoryId 카테고리 아이디
     * @throws JsonProcessingException
     */
    @GetMapping("/search/category/{categoryId}")
    public String getBookBySearchByCategoryId(Model model,
                                              @RequestParam(defaultValue = "") String keyword,
                                              @RequestParam(defaultValue = "1") int page,
                                              @RequestParam(required = false) String sort,
                                              @PathVariable("categoryId") Long categoryId)
             {

        if (!StringUtils.hasText(sort)) {
            sort = "viewCount";
        }

        PageResponse<BookDto.ListResponse> bookPage = bookGetService.getBooksBySearchByCategory(page, sort, keyword,categoryId);
        model.addAttribute("keyword", keyword);
        model.addAttribute("bookPage", bookPage);
        model.addAttribute("sort", BookDto.Sort.valueOf(sort.toUpperCase()).getValue());
        model.addAttribute("category", categoryService.getCategory(categoryId));

        return "book/bookListByCategory";


    }

    /**
     * 카테고리로 책을 검색하는 컨트롤러
     * @param page 페이지 번호
     * @param sort 정렬 기준
     * @param categoryId 카테고리 아이디
     * @throws JsonProcessingException
     */
    @GetMapping("/category/{categoryId}")
    public String getBookByCategoryId(Model model,
                                              @RequestParam(defaultValue = "1") int page,
                                              @RequestParam(required = false) String sort,
                                              @PathVariable("categoryId") Long categoryId)
             {

        if (!StringUtils.hasText(sort)) {
            sort = "viewCount";
        }

        PageResponse<BookDto.ListResponse> bookPage = bookGetService.getBooksByCategory(page, sort, categoryId);
        model.addAttribute("bookPage", bookPage);
        model.addAttribute("sort", BookDto.Sort.valueOf(sort.toUpperCase()).getValue());
        model.addAttribute("category", categoryService.getCategory(categoryId));
        model.addAttribute("categoryId", categoryId);


        return "book/bookListByCategory";

    }
}
