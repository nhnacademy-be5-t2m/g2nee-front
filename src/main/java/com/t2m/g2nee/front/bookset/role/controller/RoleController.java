package com.t2m.g2nee.front.bookset.role.controller;

import com.t2m.g2nee.front.bookset.role.dto.RoleDto;
import com.t2m.g2nee.front.bookset.role.service.RoleService;
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
@RequestMapping("/admin/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;


    /**
     * 역할 등록 컨트롤러
     * @param request 역할 정보 객체
     */
    @PostMapping
    public String registerRole(@ModelAttribute RoleDto.Request request) {

        roleService.registerRole(request);

        return "redirect:/admin/roles/list";
    }

    /**
     * 역할 조회 컨트롤러
     * @param page 페이지 번호
     */
    @GetMapping("/list")
    public String getRolesList(Model model,
                                   @RequestParam(defaultValue = "1") int page) {

        PageResponse<RoleDto.Response> rolePage = roleService.getAllRole(page);
        model.addAttribute("rolePage", rolePage);

        return "admin/book/roleList";
    }

    /**
     * 역할 수정 컨트롤러
     * @param roleId 역할 아이디
     * @param request 역할 정보 객체
     * @param page 페이지 번호
     */
    @PatchMapping("/{roleId}")
    public String updateRole(@PathVariable("roleId") Long roleId,
                                  @ModelAttribute RoleDto.Request request,
                                  @RequestParam int page) {

        roleService.updateRole(roleId,request);


        return "redirect:/admin/roles/list?page=" + page;

    }

    /**
     * 역할 삭제 컨트롤러
     * @param roleId 역할 아이디
     * @param page 페이지 번호
     */
    @DeleteMapping("/{roleId}")
    public String deleteRole(@PathVariable("roleId") Long roleId,
                                  @RequestParam int page) {

        roleService.deleteRole(roleId);

        return "redirect:/admin/roles/list?page=" + page;
    }
}
