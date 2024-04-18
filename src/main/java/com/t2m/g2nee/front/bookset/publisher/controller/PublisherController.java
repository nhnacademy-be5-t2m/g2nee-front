package com.t2m.g2nee.front.bookset.publisher.controller;

import com.t2m.g2nee.front.bookset.publisher.dto.PublisherDto;
import com.t2m.g2nee.front.bookset.publisher.service.PublisherService;
import com.t2m.g2nee.front.utils.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * 출판사 관리 controller 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
@Controller
@RequestMapping("/admin/publishers")
@RequiredArgsConstructor
public class PublisherController {

    private final PublisherService publisherService;


    /**
     * 출판사 등록 컨트롤러
     * @param request 출판사 객체 정보
     */
    @PostMapping
    public String registerPublisher(@ModelAttribute PublisherDto.Request request) {

        publisherService.registerPublisher(request);

        return "redirect:/admin/publishers/list";
    }

    /**
     * 출판사 조회 컨트롤러
     * @param page 페이지 번호
     */
    @GetMapping("/list")
    public String getPublisherList(Model model,
                                   @RequestParam(defaultValue = "1") int page) {

        PageResponse<PublisherDto.Response> publisherPage = publisherService.getAllPublisher(page);
        model.addAttribute("publisherPage", publisherPage);

        return "admin/book/publisherList";
    }

    /**
     * 출판사 수정 컨트롤러
     * @param publisherId 출판사 아이디
     * @param request 출판사 객체 정보
     * @param page 페이지 번호
     */
    @PatchMapping("/{publisherId}")
    public String updatePublisher(@PathVariable("publisherId") Long publisherId,
                                  @ModelAttribute PublisherDto.Request request,
                                  @RequestParam int page) {

        publisherService.updatePublisher(publisherId,request);


        return "redirect:/admin/publishers/list?page=" + page;

    }

    /**
     * 출판사 삭제 컨트롤러
     * @param publisherId 출판사 아이디
     * @param page 페이지 번호
     */
    @DeleteMapping("/{publisherId}")
    public String deletePublisher(@PathVariable("publisherId") Long publisherId,
                                  @RequestParam int page) {

        publisherService.deletePublisher(publisherId);

        return "redirect:/admin/publishers/list?page=" + page;
    }

}
