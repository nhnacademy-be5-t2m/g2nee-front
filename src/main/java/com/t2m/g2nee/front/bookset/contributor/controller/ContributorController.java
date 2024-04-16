package com.t2m.g2nee.front.bookset.contributor.controller;

import com.t2m.g2nee.front.bookset.contributor.dto.ContributorDto;
import com.t2m.g2nee.front.bookset.contributor.service.ContributorService;
import com.t2m.g2nee.front.bookset.publisher.dto.PublisherDto;
import com.t2m.g2nee.front.bookset.publisher.service.PublisherService;
import com.t2m.g2nee.front.pageUtils.PageResponse;
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
 * 기여자 관리 controller 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
@Controller
@RequestMapping("/admin/contributors")
@RequiredArgsConstructor
public class ContributorController {

    private final ContributorService contributorService;


    /**
     * 기여자 등록 컨트롤러
     * @param request 기여자 객체 정보
     */
    @PostMapping
    public String registerContributor(@ModelAttribute ContributorDto.Request request) {

        contributorService.registerContributor(request);

        return "redirect:/admin/contributors/list";
    }

    /**'
     * 기여자 조회 컨트롤러
     * @param page 페이지 번호
     */
    @GetMapping("/list")
    public String getContributorList(Model model,
                                   @RequestParam(defaultValue = "1") int page) {

        PageResponse<ContributorDto.Response> contributorPage = contributorService.getAllContributor(page);
        model.addAttribute("contributorPage", contributorPage);

        return "admin/contributorList";
    }

    /**
     * 기여자 수정 컨트롤러
     * @param contributorId 기여자 아이디
     * @param request 기여자 객체 정보
     * @param page 페이지 번호
     */
    @PatchMapping("/{contributorId}")
    public String updatePublisher(@PathVariable("contributorId") Long contributorId,
                                  @ModelAttribute ContributorDto.Request request,
                                  @RequestParam int page) {

        contributorService.updateContributor(contributorId,request);


        return "redirect:/admin/contributors/list?page=" + page;

    }

    /**
     * 기여자 삭제 컨트롤러
     * @param contributorId 기여자 아이디
     * @param page 페이지 번호
     */
    @DeleteMapping("/{contributorId}")
    public String deletePublisher(@PathVariable("contributorId") Long contributorId,
                                  @RequestParam int page) {

        contributorService.deleteContributor(contributorId);

        return "redirect:/admin/contributors/list?page=" + page;
    }

}
