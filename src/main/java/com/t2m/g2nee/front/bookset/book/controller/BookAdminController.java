package com.t2m.g2nee.front.bookset.book.controller;


import com.t2m.g2nee.front.bookset.book.dto.BookDto;
import com.t2m.g2nee.front.bookset.book.service.BookGetService;
import com.t2m.g2nee.front.bookset.book.service.BookMgmtService;
import com.t2m.g2nee.front.bookset.contributor.dto.ContributorDto;
import com.t2m.g2nee.front.bookset.contributor.service.ContributorService;
import com.t2m.g2nee.front.bookset.publisher.dto.PublisherDto;
import com.t2m.g2nee.front.bookset.publisher.service.PublisherService;
import com.t2m.g2nee.front.bookset.role.dto.RoleDto;
import com.t2m.g2nee.front.bookset.role.service.RoleService;
import com.t2m.g2nee.front.bookset.tag.dto.TagDto;
import com.t2m.g2nee.front.bookset.tag.service.TagService;
import com.t2m.g2nee.front.category.adaptor.CategoryAdaptor;
import com.t2m.g2nee.front.category.dto.response.CategoryHierarchyDto;
import com.t2m.g2nee.front.utils.PageResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;


/**
 * 관리자 책 관리 controller 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
@Controller
@RequestMapping("/admin/books")
@RequiredArgsConstructor
public class BookAdminController {

    private final BookMgmtService bookMgmtService;
    private final PublisherService publisherService;
    private final RoleService roleService;
    private final ContributorService contributorService;
    private final TagService tagService;
    private final BookGetService bookGetService;
    private final CategoryAdaptor categoryAdaptor;

    /**
     * 책을 등록하는 폼을 가져오는 컨트롤러 입니다.
     */
    @GetMapping("/bookFrom")
    public String getBookForm(Model model) {

        List<PublisherDto.Response> publisherList = publisherService.getAllPublisher();
        List<RoleDto.Response> roleList = roleService.getAllRole();
        List<ContributorDto.Response> contributorList = contributorService.getAllContributor();
        List<TagDto.Response> tagList = tagService.getAllTag();
        List<CategoryHierarchyDto> categoryList = categoryAdaptor.getRootCategories();

        model.addAttribute("publisherList", publisherList);
        model.addAttribute("roleList", roleList);
        model.addAttribute("contributorList", contributorList);
        model.addAttribute("tagList", tagList);
        model.addAttribute("categoryList", categoryList);

        return "admin/book/registerBookForm";
    }

    /**
     * 책을 수정하는 폼을 가져오는 컨트롤러 입니다.
     */
    @GetMapping("/updateForm/{bookId}")
    public String getUpdateBookForm(@PathVariable("bookId") Long bookId,
                                    @RequestParam("page") int page,
                                    Model model) {

        List<PublisherDto.Response> publisherList = publisherService.getAllPublisher();
        List<RoleDto.Response> roleList = roleService.getAllRole();
        List<ContributorDto.Response> contributorList = contributorService.getAllContributor();
        List<TagDto.Response> tagList = tagService.getAllTag();
        model.addAttribute("publisherList", publisherList);
        model.addAttribute("roleList", roleList);
        model.addAttribute("contributorList", contributorList);
        model.addAttribute("tagList", tagList);
        BookDto.Response book = bookMgmtService.getBook(bookId);
        model.addAttribute("book", book);
        model.addAttribute("page", page);

        return "admin/book/updateBookForm";
    }

    /**
     * 관리자 도서 관리 페이지로 가는 컨트롤러입니다.
     */
    @GetMapping("/main")
    public String getBookMain() {

        return "admin/book/bookMain";
    }


    /**
     * 등록된 책 정보를 조회하는 컨트롤러 입니다.
     */
    @GetMapping("/list")
    public String getBookList(Model model, @RequestParam(required = false, defaultValue = "1") int page) {


        PageResponse<BookDto.ListResponse> bookPage = bookMgmtService.getAllBookList(page);
        model.addAttribute("bookPage", bookPage);
        model.addAttribute("bookStatus", BookDto.BookStatus.values());

        return "admin/book/bookList";
    }


    /**
     * 책을 등록하는 컨트롤러 입니다.
     */
    @PostMapping
    public String postBook(@ModelAttribute BookDto.Request request,
                           @RequestParam("categoryIdList") List<Long> categoryIdList,
                           @RequestParam("contributorIdList") List<Long> contributorIdList,
                           @RequestParam("roleIdList") List<Long> roleIdList,
                           @RequestParam("tagIdList") List<Long> tagIdList,
                           @RequestPart MultipartFile thumbnail,
                           @RequestPart MultipartFile[] details) {

        List<Long> categoryList = new ArrayList<>();
        // 최하위 카테고리만 추출
        for (int i = 2; i < categoryIdList.size(); i += 3) {
            Long categoryId = categoryIdList.get(i);
            categoryList.add(categoryId);
        }

        request.setContributorIdList(contributorIdList);
        request.setCategoryIdList(categoryList);
        request.setRoleIdList(roleIdList);
        request.setTagIdList(tagIdList);
        bookMgmtService.registerBook(request, thumbnail, details);

        return "redirect:/admin/books/list";
    }


    /**
     * 책을 상태를 변경하는 컨트롤러 입니다.
     */
    @PatchMapping("/status/{bookId}")
    public String updateStatus(@PathVariable("bookId") Long bookId,
                               @ModelAttribute BookDto.Request request,
                               @RequestParam int page) {

        bookMgmtService.updateBookStatus(bookId, request);

        return "redirect:/admin/books/list?page=" + page;
    }


    /**
     * 책을 수정하는 컨트롤러 입니다.
     */
    @PatchMapping("/{bookId}")
    public String updateBook(@ModelAttribute BookDto.Request request,
                             @RequestPart(required = false) MultipartFile thumbnail,
                             @RequestPart(required = false) MultipartFile[] details,
                             @PathVariable("bookId") Long bookId,
                             @RequestParam(value = "page", defaultValue = "1") int page) {

        bookMgmtService.updateBook(bookId, request, thumbnail, details);

        return "redirect:/admin/books/list?page=" + page;

    }

    /**
     * 책 수량을 추가하는 컨트롤러 입니다.
     */
    @PatchMapping("/quantity/{bookId}")
    public String addBookQuantity(Model model,
                                  @RequestParam("quantity") int quantity,
                                  @PathVariable("bookId") Long bookId) {

        int updateQuantity = bookMgmtService.addBookQuantity(bookId, quantity);
        model.addAttribute("quantity", updateQuantity);

        return "redirect:/admin/books/" + bookId;
    }

    /**
     * 책 하나 정보를 가져오는 컨트롤러 입니다.
     */
    @GetMapping("/{bookId}")
    public String getBook(@PathVariable("bookId") Long bookId,
                          Model model) {

        BookDto.Response response = bookGetService.getBook(null,bookId);
        model.addAttribute("book", response);

        return "admin/book/bookDetail";
    }


}
