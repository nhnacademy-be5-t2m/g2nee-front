package com.t2m.g2nee.front.policyset.deliveryPolicy.controller;

import com.t2m.g2nee.front.policyset.deliveryPolicy.dto.request.DeliveryPolicySaveDto;
import com.t2m.g2nee.front.policyset.deliveryPolicy.service.DeliveryPolicyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 관리자가 배송비 정책을 관리하기 위한 controller입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
@Controller
@RequestMapping("/admin/deliveryPolicies")
public class DeliveryPolicyController {

    private final DeliveryPolicyService service;

    public DeliveryPolicyController(DeliveryPolicyService service) {
        this.service = service;
    }


    /**
     * 배송비 정책 목록 보이기(메인)
     *
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String deliveryPolicyList(Model model) {
        model.addAttribute("deliveryPolicies", service.getAllDeliveryPolicies(1));
        return "admin/deliveryPolicy/adminDeliveryPolicyList";
    }

    /**
     * 배송비 정책을 페이징처리하여 보여줍니다.
     *
     * @param page
     * @param model
     * @return
     */
    @GetMapping
    public String deliveryPolicyPage(@RequestParam int page, Model model) {
        model.addAttribute("deliveryPolicies", service.getAllDeliveryPolicies(page));
        return "admin/deliveryPolicy/adminDeliveryPolicyList";
    }

    /**
     * 배송비 정책 저장 양식을 보여줍니다.
     *
     * @return
     */
    @GetMapping("/save")
    public String deliveryPolicySaveForm(Model model) {
        model.addAttribute("deliveryPolicy", service.getDeliveryPolicy());
        return "admin/deliveryPolicy/adminDeliveryPolicySave";
    }


    /**
     * 실제 배송비 정책 저장합니다.
     *
     * @param deliveryFee
     * @param freeDeliveryStandard
     * @return
     */
    @PostMapping("/save")
    public String createPointPolicy(@RequestParam("deliveryFee") int deliveryFee,
                                    @RequestParam("freeDeliveryStandard") int freeDeliveryStandard) {
        DeliveryPolicySaveDto request = new DeliveryPolicySaveDto(deliveryFee, freeDeliveryStandard);
        service.createDeliveryPolicy(request);
        return "redirect:/admin/deliveryPolicies/list";
    }
}
