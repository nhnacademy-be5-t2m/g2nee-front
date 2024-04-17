package com.t2m.g2nee.front.bookset.tag.controller;

import com.t2m.g2nee.front.bookset.tag.dto.TagDto;
import com.t2m.g2nee.front.bookset.tag.service.TagService;
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
 * 역할 관리 controller 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
@Controller
@RequestMapping("/admin/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;


    /**
     * 태그 등록 컨트롤러
     *
     * @param request 태그 정보 객체
     */
    @PostMapping
    public String registerTag(@ModelAttribute TagDto.Request request) {

        tagService.registerTag(request);

        return "redirect:/admin/tags/list";
    }

    /**
     * 태그 조회 컨트롤러
     *
     * @param page 페이지 번호
     */
    @GetMapping("/list")
    public String getRolesList(Model model,
                               @RequestParam(defaultValue = "1") int page) {

        PageResponse<TagDto.Response> tagPage = tagService.getAllTag(page);
        model.addAttribute("tagPage", tagPage);

        return "admin/book/tagList";
    }

    /**
     * 태그 수정 컨트롤러
     *
     * @param tagId   태그 아이디
     * @param request 태그 정보 객체
     * @param page    페이지 번호
     */
    @PatchMapping("/{tagId}")
    public String updateTag(@PathVariable("tagId") Long tagId,
                            @ModelAttribute TagDto.Request request,
                            @RequestParam int page) {

        tagService.updateTag(tagId, request);


        return "redirect:/admin/tags/list?page=" + page;

    }

    /**
     * 태그 태그 삭제 컨트롤러
     *
     * @param tagId 테그 아이디
     * @param page  페이지 번호
     */
    @DeleteMapping("/{tagId}")
    public String deleteRole(@PathVariable("tagId") Long tagId,
                             @RequestParam int page) {

        tagService.deleteTag(tagId);

        return "redirect:/admin/tags/list?page=" + page;
    }
}
