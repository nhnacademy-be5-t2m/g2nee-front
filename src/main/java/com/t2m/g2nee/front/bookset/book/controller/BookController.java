package com.t2m.g2nee.front.bookset.book.controller;

import com.t2m.g2nee.front.annotation.Member;
import com.t2m.g2nee.front.aop.MemberAspect;
import com.t2m.g2nee.front.booklike.service.BookLikeService;
import com.t2m.g2nee.front.bookset.book.dto.BookDto;
import com.t2m.g2nee.front.bookset.book.dto.CategoryInfoDto;
import com.t2m.g2nee.front.bookset.book.service.BookGetService;
import com.t2m.g2nee.front.bookset.category.service.CategoryService;
import com.t2m.g2nee.front.review.dto.ReviewDto;
import com.t2m.g2nee.front.review.service.ReviewService;
import com.t2m.g2nee.front.utils.PageResponse;
import java.util.List;
import java.util.stream.Collectors;
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
    private final MemberAspect memberAspect;
    private final BookLikeService bookLikeService;
    private final ReviewService reviewService;

    /**
     * 책 하나 정보를 가져오고 해당 책 카테고리에 해당하는 추천 책 목록을 조회하는 컨트롤러 입니다.
     */
    @Member
    @GetMapping("/{bookId}")
    public String getBook(@PathVariable("bookId") Long bookId,
                          Model model) {

        Long memberId = (Long) memberAspect.getThreadLocal().get();
        BookDto.Response response = bookGetService.getBook(memberId, bookId);
        Long likesNum = bookLikeService.getMemberLikesNum(memberId);

        // 책의 카테고리 정보를 가져옵니다.
        List<Long> categoryIdList = response.getCategoryList().stream()
                .flatMap(cl -> cl.stream().map(CategoryInfoDto::getCategoryId))
                .collect(Collectors.toList());
        // 카테고리에 맞는 책 정보 목록을 가져옵니다.
        List<BookDto.ListResponse> bookList = bookGetService.getRecommendBooks(categoryIdList, bookId);
        PageResponse<ReviewDto.Response> reviewPage = reviewService.getReviews(bookId, 1);


        model.addAttribute("bookList", bookList);
        model.addAttribute("book", response);
        model.addAttribute("memberId", memberId);
        model.addAttribute("likesNum", likesNum);
        model.addAttribute("reviewPage", reviewPage);

        return "book/bookDetail";
    }


    /**
     * 메인 페이지 최근 발매된 책 6권을 조회하는 컨트롤러
     */
    @Member
    @GetMapping
    public String getNewBooks(Model model) {

        List<BookDto.ListResponse> bookList = bookGetService.getNewBooks();
        Long memberId = (Long) memberAspect.getThreadLocal().get();
        Long likesNum = bookLikeService.getMemberLikesNum(memberId);

        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("bookList", bookList);
        model.addAttribute("likesNum", likesNum);

        return "main/index";
    }

    /**
     * 키워드로 책을 검색하는 컨트롤러
     *
     * @param keyword 키워드
     * @param page    페이지 번호
     * @param sort    정렬 조건
     */
    @Member
    @GetMapping("/search")
    public String getBookBySearch(Model model,
                                  @RequestParam(defaultValue = "") String keyword,
                                  @RequestParam(defaultValue = "1") int page,
                                  @RequestParam(required = false) String sort) {

        if (!StringUtils.hasText(sort)) {
            sort = "viewCount";
        }

        Long memberId = (Long) memberAspect.getThreadLocal().get();
        Long likesNum = bookLikeService.getMemberLikesNum(memberId);

        PageResponse<BookDto.ListResponse> bookPage = bookGetService.getBooksBySearch(page, memberId, keyword, sort);
        model.addAttribute("keyword", keyword);
        model.addAttribute("bookPage", bookPage);
        model.addAttribute("sortName", BookDto.Sort.valueOf(sort.toUpperCase()).getValue());
        model.addAttribute("sort", sort);
        model.addAttribute("memberId", memberId);
        model.addAttribute("likesNum", likesNum);


        return "book/bookList";
    }

    /**
     * 카테고리와 키워드로 검색한 책을 조회하는 컨트롤러
     *
     * @param keyword    검색할 카워드
     * @param page       페이지 번호
     * @param sort       정렬 조건
     * @param categoryId 카테고리 아이디
     */
    @Member
    @GetMapping("/search/category/{categoryId}")
    public String getBookBySearchByCategoryId(Model model,
                                              @RequestParam(defaultValue = "") String keyword,
                                              @RequestParam(defaultValue = "1") int page,
                                              @RequestParam(required = false) String sort,
                                              @PathVariable("categoryId") Long categoryId) {

        if (!StringUtils.hasText(sort)) {
            sort = "viewCount";
        }

        Long memberId = (Long) memberAspect.getThreadLocal().get();

        Long likesNum = bookLikeService.getMemberLikesNum(memberId);

        PageResponse<BookDto.ListResponse> bookPage =
                bookGetService.getBooksBySearchByCategory(page, memberId, sort, keyword, categoryId);

        model.addAttribute("keyword", keyword);
        model.addAttribute("bookPage", bookPage);
        model.addAttribute("sortName", BookDto.Sort.valueOf(sort.toUpperCase()).getValue());
        model.addAttribute("sort", sort);
        model.addAttribute("category", categoryService.getCategory(categoryId));
        model.addAttribute("memberId", memberId);
        model.addAttribute("likesNum", likesNum);

        return "book/bookListByCategory";


    }

    /**
     * 카테고리로 책을 검색하는 컨트롤러
     *
     * @param page       페이지 번호
     * @param sort       정렬 기준
     * @param categoryId 카테고리 아이디
     */
    @Member
    @GetMapping("/category/{categoryId}")
    public String getBookByCategoryId(Model model,
                                      @RequestParam(defaultValue = "1") int page,
                                      @RequestParam(required = false) String sort,
                                      @PathVariable("categoryId") Long categoryId) {

        if (!StringUtils.hasText(sort)) {
            sort = "viewCount";
        }

        Long memberId = (Long) memberAspect.getThreadLocal().get();
        Long likesNum = bookLikeService.getMemberLikesNum(memberId);

        PageResponse<BookDto.ListResponse> bookPage =
                bookGetService.getBooksByCategory(page, memberId, sort, categoryId);
        model.addAttribute("bookPage", bookPage);
        model.addAttribute("sortName", BookDto.Sort.valueOf(sort.toUpperCase()).getValue());
        model.addAttribute("sort", sort);
        model.addAttribute("category", categoryService.getCategory(categoryId));
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("memberId", memberId);
        model.addAttribute("likesNum", likesNum);


        return "book/bookListByCategory";

    }

}
