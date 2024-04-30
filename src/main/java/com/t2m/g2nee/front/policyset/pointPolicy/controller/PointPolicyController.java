package com.t2m.g2nee.front.policyset.pointPolicy.controller;

import com.t2m.g2nee.front.policyset.pointPolicy.dto.request.PointPolicySaveDto;
import com.t2m.g2nee.front.policyset.pointPolicy.service.PointPolicyService;
import java.math.BigDecimal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 관리자가 포인트정책을 관리하기 위한 컨트롤러 입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
@Controller
@RequestMapping("/admin/pointPolicies")
public class PointPolicyController {

    private final PointPolicyService service;


    public PointPolicyController(PointPolicyService service) {
        this.service = service;
    }

    /**
     * 포인트 정책 목록을 보여줍니다(메인)
     *
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String pointPolicyList(Model model) {
        model.addAttribute("pointPolicies", service.getAllPointPolicies(1));
        return "admin/pointPolicy/adminPointPolicyList";
    }

    /**
     * 포인트 정책 목록을 페이징처리하여 보여줍니다.
     *
     * @param page
     * @param model
     * @return
     */
    @GetMapping
    public String pointPolicyPage(@RequestParam int page, Model model) {
        model.addAttribute("pointPolicies", service.getAllPointPolicies(page));
        return "admin/pointPolicy/adminPointPolicyList";
    }

    /**
     * 포인트 정책 저장 양식 보여줍니다.
     *
     * @return
     */
    @GetMapping("/save")
    public String pointPolicySaveForm() {
        return "admin/pointPolicy/adminPointPolicySave";
    }

    /**
     * 실제로 포인트 정책 저장합니다.
     *
     * @param policyName
     * @param policyType
     * @param amount
     * @return
     */
    @PostMapping("/save")
    public String createPointPolicy(@RequestParam("policyName") String policyName,
                                    @RequestParam("policyType") String policyType,
                                    @RequestParam("amount") BigDecimal amount) {
        PointPolicySaveDto request = new PointPolicySaveDto(policyName, policyType, amount);
        service.createPointPolicy(request);
        return "redirect:/admin/pointPolicies/list";
    }

    /**
     * 포인트 정책 수정 양식 보여줍니다.
     *
     * @param pointPolicyId
     * @param model
     * @return
     */
    @GetMapping("/modify/{pointPolicyId}")
    public String modifyPointPolicyForm(@PathVariable("pointPolicyId") Long pointPolicyId, Model model) {
        model.addAttribute("pointPolicy", service.getPointPolicy(pointPolicyId));
        return "admin/pointPolicy/adminPointPolicySave";
    }

    /**
     * 실제로 포인트 정책 수정합니다.
     *
     * @param pointPolicyId
     * @param policyName
     * @param policyType
     * @param amount
     * @return
     */
    @PutMapping("/modify/{pointPolicyId}")
    public String modifyPointPolicy(@PathVariable("pointPolicyId") Long pointPolicyId,
                                    @RequestParam("policyName") String policyName,
                                    @RequestParam("policyType") String policyType,
                                    @RequestParam("amount") BigDecimal amount) {
        PointPolicySaveDto request = new PointPolicySaveDto(policyName, policyType, amount);
        service.updatePointPolicy(pointPolicyId, request);
        return "redirect:/admin/pointPolicies/list";
    }

    /**
     * 포인트 정책 비활성화합니다.
     *
     * @param pointPolicyId
     * @return
     */
    @PatchMapping("/{pointPolicyId}")
    public String deletePointPolicy(@PathVariable("pointPolicyId") Long pointPolicyId) {
        service.deletePointPolicy(pointPolicyId);
        return "redirect:/admin/pointPolicies/list";
    }

}
